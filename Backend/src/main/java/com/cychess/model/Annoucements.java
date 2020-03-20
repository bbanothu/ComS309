package com.cychess.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Announcements Model
 * @author bbanothu
 *
 */
@Entity
@Table(name = "Announcements")
public class Annoucements {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ANN_ID")
	private int id;
	
	@Column(name = "ANN_TEXT")
	private String text;
	
	@Column(name = "ANN_DATE")
	private String date;
	
	@Column(name = "ANN_USER_NAME")
	private String user;
	
	public Annoucements (String text) {
		LocalDate localDate = LocalDate.now();
		this.text = text;
		this.date = DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserName() {
		return user;
	}

	public void setUserName(String user) {
		this.user = user;
	}


}
