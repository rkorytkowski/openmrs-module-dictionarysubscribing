/**
b * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dictionarysubscribing.api;

import java.net.URL;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.metadatasharing.ImportedPackage;
import org.openmrs.module.metadatasharing.subscription.SubscriptionHeader;

/**
 * Contains public API methods related to subscribing to a remote concept dictionary and getting
 * periodic updates
 */
public interface DictionarySubscribingService extends OpenmrsService {
	
	/**
	 * Creates an {@link ImportedPackage} with the specified url and saves it to the database
	 * 
	 * @param subscriptionUrl
	 * @param version from which version to start
	 * @should subscribe to the dictionary at the specified url
	 * @should not create multiple subscriptions to the dictionary at the same url
	 */
	void subscribeToDictionary(String subscriptionUrl, int version);
	
	/**
	 * Checks if there are any updates to the concept dictionary that was subscribed to
	 * 
	 * @return {@link SubscriptionHeader}
	 */
	void checkForUpdates();
	
	/**
	 * Imports the latest dictionary. It tries to fetch packages from the currently imported version
	 * to the latest version.
	 */
	void importDictionaryUpdates() throws APIException;
	
	/**
	 * Unsubscribe from a dictionary at the specified url
	 * 
	 * @param subscriptionUrl
	 * @should unsubscribe from the dictionary at the specified url
	 * @should not unsubscribe from the dictionary it if has a different url
	 */
	void unsubscribeFromDictionary(String subscriptionUrl);
	
	/**
	 * Returns a package of the subscribed dictionary or null if none
	 * 
	 * @return the package or null
	 */
	ImportedPackage getSubscribedDictionary();
	
	/**
	 * Indicates whether dictionary is locked for edits or not
	 * 
	 * @return true if locked
	 */
	boolean isDictionaryLocked();
	
	/**
	 * Returns the count of concepts in a system
	 * 
	 * @return the count of concepts
	 */
	Long getConceptsCount();
	
	/**
	 * It's for internal use only.
	 * <p>
	 * It must have been put in the interface, because it needs to be transactional.
	 * 
	 * @param dictionary
	 * @param packageContentUrl
	 */
	void importPackage(ImportedPackage dictionary, URL packageContentUrl);
}
