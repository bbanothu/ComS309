package com.cychess.service;

import com.cychess.model.Annoucements;

/**
 * Interface used to interact with announcements
 * @author bbanothu
 *
 */
public interface AnnoucementsService {
	
	/**
	 * Saves announcement
	 * @param Ann Announcement object
	 */
	public void saveAnnoucement(Annoucements Ann);
}
