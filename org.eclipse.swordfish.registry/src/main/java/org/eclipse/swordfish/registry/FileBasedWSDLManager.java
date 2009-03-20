/*******************************************************************************
* Copyright (c) 2008, 2009 SOPERA GmbH.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* SOPERA GmbH - initial API and implementation
*******************************************************************************/
package org.eclipse.swordfish.registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileBasedWSDLManager implements WSDLReader {

	private static final Logger LOGGER = LoggerFactory
	.getLogger(FileBasedWSDLManager.class);

	private static final String LOCATION_PROPERTY = "org.eclipse.swordfish.registry.fileLocation";

	private File wsdlDirectory; 
	
	private InMemoryRepository repos;
	
	FileBasedWSDLManager() {
	}
	
	public void setRepository(InMemoryRepository repository) {
		repos = repository;
	}
	

	public void setDirectory(String directoryName)  throws RegistryException {
		setDirectory(new File(directoryName));
	}
	
	public void setDirectory(File directory) throws RegistryException {
		if (!(directory.exists() && directory.isDirectory())) {
			throw new RegistryException("The directory "  + directory.getAbsolutePath() + " specified to contain the registry WSDL's does either not exist or is not a directory.");			
		}
		wsdlDirectory = directory;
	}

	public void fill() throws RegistryException {
		ensureDirectoryDefined();
		
		File[] files = wsdlDirectory.listFiles();
		
		for (File file : files) {
			WSDLResource wsdl = new WSDLResource(new FileData(file));
			try {
				repos.add(wsdl);
			} catch (RegistryException e) {
				LOGGER.error("Unable to load file " + file.getAbsolutePath(), e);
				
			}
		}
	}
	
	public WSDLResource readFrom(String id, Reader content) throws RegistryException {
		File location = new File(wsdlDirectory,id);
		WSDLResource wsdl= new WSDLResource(new FileData(location));
		try {
			wsdl.persist(content);
		} catch (IOException e) {
			throw new InternalRegistryException(e);
		}
		return wsdl;
	}
	
	private void ensureDirectoryDefined() throws RegistryException {
		if (wsdlDirectory == null) {
			String fileLocation = System.getProperty(LOCATION_PROPERTY);
		
			if (fileLocation == null) {
				throw new RegistryException("The system property " + LOCATION_PROPERTY + " is not defined.");
			}
			setDirectory(fileLocation);
		}
	}
	
	static class FileData implements PersistentData {
		private File file;

		FileData(File file) {
			this.file = file;
		}
		
		public String getId() {
			return file.getName();	
		}
		
		public InputStream read() throws IOException {
			return new FileInputStream(file);
		}
		
		public OutputStream write() throws IOException{
			return new FileOutputStream(file); 
		}
		
		public void delete() {
			file.delete();
		}
	}
}
