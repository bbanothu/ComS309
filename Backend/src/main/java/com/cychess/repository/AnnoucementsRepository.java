package com.cychess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cychess.model.Annoucements;

/**
 * Interface used to create a repository for announcements
 * @author bbanothu
 *
 */
@Repository("AnnoucementsRepository")
public interface AnnoucementsRepository extends JpaRepository<Annoucements, Long> {

}



