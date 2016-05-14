package com.laxen.auxiliaire;

import java.util.Date;

/**
 * Created by jibbs on 14/05/16.
 */
public class Job {

	private Integer userid;
	private String username;
	private Integer price;
	private String jobtype;
	private Double lon;
	private Double lat;
	private Date date;
	private String description;
	private String title;

	public Job() {
	}

	public Job(Integer userid, String username, Integer price, String jobtype, Double lon, Double lat, Date date, String description, String title) {
		this.userid = userid;
		this.username = username;
		this.price = price;
		this.jobtype = jobtype;
		this.lon = lon;
		this.lat = lat;
		this.date = date;
		this.description = description;
		this.title = title;
	}

	public Integer getUserid() {
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

	public Double getLon() {
		return lon;
	}

	public Double getLat() {
		return lat;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public void setUserid(Integer userid) {
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

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
