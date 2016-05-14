package com.laxen.auxiliaire;

import java.util.Date;

/**
 * Created by jibbs on 14/05/16.
 */
public class Job {

	private String userid;
	private String username;
	private Integer price;
	private String jobtype;
	private Integer lon;
	private Integer lat;
	private Date date;
	private String description;

	public String getUserid() {
		return userid;
	}

	public String getUsername() {
		return username;
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

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	public void setLon(Integer lon) {
		this.lon = lon;
	}

	public void setLat(Integer lat) {
		this.lat = lat;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
