package org.eclipse.swordfish.registry;

import static org.eclipse.swordfish.registry.IsIteratorReturning.isIteratorReturning;
import static org.eclipse.swordfish.registry.TstData.*;

import java.util.Iterator;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;


public class InMemoryRepositoryDeregisterTest {
	
	Matcher<Iterator<WSDLResource>> EMPTY = isIteratorReturning();

	private InMemoryRepositoryImpl wsdlRepository = new InMemoryRepositoryImpl();

	@Test
	public void deregisteredPortTypeWSDLShouldNotBeReturnedByGetByPortTypeName() {
		wsdlRepository.registerPortType(QNAME_11, WSDL_1);
		wsdlRepository.registerPortType(QNAME_11, WSDL_2);

		wsdlRepository.deregisterPortType(QNAME_11, WSDL_1);

		ListResource<WSDLResource> wsdl = wsdlRepository.getByPortTypeName(QNAME_11);		
		assertThat(wsdl.getResources(), isIteratorReturning(WSDL_2));
	}

	@Test
	public void deregisteringPortTypeWSDLFromEmptyRepositoryKeepsRepositoryEmpty() {
		wsdlRepository.deregisterPortType(QNAME_11, WSDL_1);

		ListResource<WSDLResource> wsdl = wsdlRepository.getByPortTypeName(QNAME_11);		
		assertThat(wsdl.getResources(), EMPTY);		
	}

	@Test
	public void givenServiceReferencingDeregisteredPortTypeGetReferencingPortTypeShouldReturnIt() {
		wsdlRepository.registerPortType(QNAME_11, WSDL_1);
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);
		wsdlRepository.registerService(QNAME_31, WSDL_2, QNAME_21);
		wsdlRepository.deregisterPortType(QNAME_11, WSDL_1);

		ListResource<WSDLResource> wsdl = wsdlRepository.getReferencingPortType(QNAME_11);		
		assertThat(wsdl.getResources(), isIteratorReturning(WSDL_2));
	}

	@Test
	public void deregisteredBindingWSDLShouldNotBeReturnedByGetByBindingName() {
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);
		wsdlRepository.deregisterBinding(QNAME_21, WSDL_2);

		ListResource<WSDLResource> wsdl = wsdlRepository.getByBindingName(QNAME_21);		
		assertThat(wsdl.getResources(), EMPTY);
	}

	@Test
	public void deregisteringBindingWSDLFromEmptyRepositoryKeepsRepositoryEmpty() {
		wsdlRepository.deregisterBinding(QNAME_21, WSDL_2);

		ListResource<WSDLResource> wsdl = wsdlRepository.getByBindingName(QNAME_21);		
		assertThat(wsdl.getResources(), EMPTY);		
	}

	@Test
	public void deregisteringBindingShouldInterruptChainFromPortType2Service() {
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);
		wsdlRepository.registerService(QNAME_31, WSDL_3, QNAME_21);
		wsdlRepository.deregisterBinding(QNAME_21, WSDL_2);

		ListResource<WSDLResource> wsdl = wsdlRepository.getReferencingPortType(QNAME_11);		
		assertThat(wsdl.getResources(), EMPTY);
	}

	@Test
	public void deregisteringAndRegsiteringBindingShouldNotChangeAnything() {
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);
		wsdlRepository.registerService(QNAME_31, WSDL_3, QNAME_21);
		
		wsdlRepository.deregisterBinding(QNAME_21, WSDL_2);
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);

		ListResource<WSDLResource> wsdl = wsdlRepository.getReferencingPortType(QNAME_11);		
		assertThat(wsdl.getResources(), isIteratorReturning(WSDL_3));
	}

	@Test
	public void deregisteringBindingShouldOnlyInterruptChainFromPortType2ServiceWhereBindingWasIncluded() {
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);

		wsdlRepository.registerBinding(QNAME_21, WSDL_3, QNAME_11);
		wsdlRepository.registerService(QNAME_31, WSDL_3, QNAME_21);

		wsdlRepository.deregisterBinding(QNAME_21, WSDL_2);

		ListResource<WSDLResource> wsdl = wsdlRepository.getReferencingPortType(QNAME_11);		
		assertThat(wsdl.getResources(), isIteratorReturning(WSDL_3));
	}

	@Test
	public void deregisteredserviceWSDLShouldNotBeReturnedByGetByServiceName() {
		wsdlRepository.registerService(QNAME_21, WSDL_2, QNAME_11, QNAME_12);
		wsdlRepository.deregisterService(QNAME_21, WSDL_2);

		ListResource<WSDLResource> wsdl = wsdlRepository.getByServiceName(QNAME_21);		
		assertThat(wsdl.getResources(), EMPTY);
	}

	@Test
	public void deregisteringServiceWSDLFromEmptyRepositoryKeepsRepositoryEmpty() {
		wsdlRepository.deregisterService(QNAME_21, WSDL_2);

		ListResource<WSDLResource> wsdl = wsdlRepository.getByBindingName(QNAME_21);		
		assertThat(wsdl.getResources(), EMPTY);		
	}
}
