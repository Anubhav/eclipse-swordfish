package org.eclipse.swordfish.api.policy;

import javax.xml.namespace.QName;

/**
 * Wrapper for a raw policy definition.
 */
public interface PolicyDefinitionDescription {

	/**
	 * Name of the corresponding service.
	 *
	 * @return fully qualified service name.
	 */
	QName getServiceQName();
}
