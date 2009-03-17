package org.eclipse.swordfish.registry;

import static org.eclipse.swordfish.registry.TstData.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;
import java.io.StringReader;

import org.junit.Test;


public class FileBasedWSDLManagerTest {

	public void givenIdAndContentReadFromShouldReturnWSDLWithSameIdAndContent() throws Exception {
		String content = "dfdsfsd";
		StringReader reader = new StringReader(content);
		
		FileBasedWSDLManager wsdlManager = new FileBasedWSDLManager();
		
		WSDLResource wsdl = wsdlManager.readFrom(ID_1, reader);
		
		assertThat(wsdl.getId(), equalTo(ID_1));
	}

}
