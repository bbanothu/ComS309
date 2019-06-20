package com.cychess.model;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.ClassUtils;

import com.mysql.jdbc.Driver;

/**
 * DAO used to connect with the announcement table in the database
 * @author pjd
 *
 */
public class AnnouncementDAO {

	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Constructs new AnnouncementDAO
	 */
	public AnnouncementDAO() {
		try {
			this.jdbcTemplate = jdbcTemplate();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates new jdbc Template
	 * @return jdbcTemplate
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	@Bean
	JdbcTemplate jdbcTemplate() throws IllegalAccessException, InvocationTargetException, InstantiationException {
	    // extract this 4 parameters using your own logic
	    final String driverClassName = "com.mysql.jdbc.Driver";
	    final String jdbcUrl = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309yt5?useSSL=false";
	    final String username = "dbu309yt5";
	    final String password = "gVTCFBgb";
	    // Build dataSource manually:
	    final Class<?> driverClass = ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());
	    final Driver driver = (Driver) ClassUtils.getConstructorIfAvailable(driverClass).newInstance();
	    final DataSource dataSource = new SimpleDriverDataSource(driver, jdbcUrl, username, password);
	    // and make the jdbcTemplate
	    return new JdbcTemplate(dataSource);
	}
	
	public void UploadAnnoucement(String annn) {
		//new Date(String, String, String);
	}
	
	/**
	 * Returns announcement text at specific announcement id
	 * @param id announcement id
	 * @return String announcement text
	 */
	public String getAnnouncementAtId(int id) {
		return jdbcTemplate.queryForObject(String.format("select ann_text from announcements where ann_id = ?;",  id), String.class);
	}
	
}