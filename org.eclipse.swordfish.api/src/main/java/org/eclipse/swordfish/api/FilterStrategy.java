/*******************************************************************************
 * Copyright (c) 2008, 2009 SOPERA GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     SOPERA GmbH - initial API and implementation
 *******************************************************************************/

package org.eclipse.swordfish.api;

import java.util.List;


/**
 * The strategy that can remove some interceptors from the interceptor chain
 * based on the supplied hints.
 * Supposed to be plugged into the Swordfish framework as an osgi service.
 *
 */
public interface FilterStrategy extends Strategy {

	/**
	 * Create and return an interceptor chain with the interceptors which
	 * shall process subsequent message exchanges. The following rules apply
	 * for filtering:
	 * <ul>
	 * <li>The original interceptor chain must not be modified. Either the
	 * unmodified List of interceptors is returned, or a new List with the
	 * interceptors which have passed the filter is created.</li>
	 * <li>The interceptors in the List returned are in the same order as
	 * in the original interceptor chain, the only difference is that
	 * interceptors which are filtered out are not present in the result
	 * List.</li>
	 * </ul>
	 * @param interceptors - the interceptor chain to be filtered. This
	 * original List will not be modified.
	 * @param registry - the interceptor registry containing property data
	 * for the interceptors of the chain using the corresponding interceptor
	 * instance as key. The properties are merged by Swordfish from the
	 * properties returned by the <code>getProperties()</code> method of
	 * the interceptor, the property dictionary provided at OSGI service
	 * registration, and the Swordfish configuration. Values from the
	 * Swordfish configuration have precedence over those provided at OSGI
	 * service registration. Hard-coded properties from the
	 * <code>getProperties()</code> method have lowest precedence.
	 * @param hints - If a {@link HintExtractor} has been registered, the List of
	 * Hints extracted from the current message exchange is provided in this
	 * argument. Otherwise, it is <code>null</code>.
	 * @return the filtered interceptor chain (must not be <code>null</code>).
	 */
	List<Interceptor> filter(List<Interceptor> interceptors,
			ReadOnlyRegistry<Interceptor> registry, List<Hint<?>> hints);

}
