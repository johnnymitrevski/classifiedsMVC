package com.blogspot.agilisto.classifieds.services;

import java.util.List;

import com.blogspot.agilisto.classifieds.model.Enquiry;

/**
 * CRUD service for Enquiry domain model
 */
public interface EnquiryService {
	
	/**
	 * Store the Enquiry record.
	 * 
	 * @param listing
	 * @return The <code>enquiry.id</code> of the record created.
	 */
	String save(Enquiry enquiry);
	
	/**
	 * Get all the Enquiry records associated with the query.
	 * 
	 * @param queryKey The field in {@link Enquiry} to match.
	 * @param queryValue The value of the field to match.
	 * @return List<Enquiry> object, <code>null</code> if not found.
	 */
	List<Enquiry> getEnquiries(String queryKey, Object queryValue);
	
	/**
	 * Update multiple Enquiry(s)
	 * 
	 * @param queryKey The field in {@link Enquiry} to match.
	 * @param queryValue The value of the field to match.
	 * @param updateKey The field in <code>Enquiry</code> to update.
	 * @param updateValue The object to update.
	 */
	void updateEnquiries(String queryKey, Object queryValue, String updateKey, Object updateValue);
	
	/**
	 * Delete a single Enquiry.
	 * 
	 * @param id The <code>Enquiry.id</code> of the record to delete.
	 */
	void deleteEnquiry(String id);
	
	/**
	 * Delete multiple Enquiry(s).
	 * 
	 * @param queryKey The field in {@link Enquiry} to match.
	 * @param queryValue The value of the field to match.
	 */
	void deleteEnquiries(String queryKey, Object queryValue);
}
