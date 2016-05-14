package com.laxen.auxiliaire;

import java.util.Date;

/**
 * Created by jibbs on 14/05/16.
 */
public class Job {

	private String userid;
	private String name;
	private Integer price;
	private String jobtype;
	private Integer lon;
	private Integer lat;
	private Date date;
	private String description;

	public String getUserid() {
		return userid;
	}

	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}

	public String getJobtype() {
		return jobtype;
	}

	public Integer getLon() {
		return lon;
	}

	public Integer getLat() {
		return lat;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}



}
