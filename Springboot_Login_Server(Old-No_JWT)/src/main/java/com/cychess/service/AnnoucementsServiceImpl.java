package com.cychess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cychess.model.Annoucements;
import com.cychess.repository.AnnoucementsRepository;

/**
 * Implementation of the AnnouncementsService class used to interact with announcements 
 * @author bbanothu
 *
 */
@Service("AnnoucementsService")
public class AnnoucementsServiceImpl implements AnnoucementsService{
	@Autowired
	private AnnoucementsRepository AnnoucementsRepository;

		/**
		 * Saves announcement
		 * @param Ann Announcement object
		 */
		@Override
		public void saveAnnoucement(Annoucements Ann) {
			AnnoucementsRepository.save(Ann);
			
		}

	}
