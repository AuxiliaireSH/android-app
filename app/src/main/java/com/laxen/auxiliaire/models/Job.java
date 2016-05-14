package com.laxen.auxiliaire.models;

import java.util.Date;

public class Job {

	private Integer userid;
	private String username;
	private Integer price;
	private String kind;
	private Double longitude;
	private Double latitude;
	private Date created_at;
	private Date updated_at;
	private String description;
	private String title;


	public Job() {
	}

	public Job(Integer userid, String username, Integer price, String kind, Double longitude, Double latitude, Date created_at, Date updated_at, String description, String title) {
		this.userid = userid;
		this.username = username;
		this.price = price;
		this.kind = kind;
		this.longitude = longitude;
		this.latitude = latitude;
		this.created_at = created_at;
		this.updated_at = updated_at;
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

	public String getKind() {
		return kind;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public Date getCreated_at() {
		return created_at;
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

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Job{" +
				"userid=" + userid +
				", username='" + username + '\'' +
				", price=" + price +
				", kind='" + kind + '\'' +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", created_at=" + created_at +
				", updated_at=" + updated_at +
				", description='" + description + '\'' +
				", title='" + title + '\'' +
				'}';
	}
}
