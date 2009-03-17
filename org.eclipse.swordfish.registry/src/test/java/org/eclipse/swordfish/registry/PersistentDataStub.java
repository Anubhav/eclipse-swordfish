package org.eclipse.swordfish.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PersistentDataStub implements PersistentData {

	public String getId() {
		return null;
	}

	public InputStream read() throws IOException {
		return null;
	}

	public OutputStream write() throws IOException {
		return null;
	}

	public void delete() {
	}
	
	
}
