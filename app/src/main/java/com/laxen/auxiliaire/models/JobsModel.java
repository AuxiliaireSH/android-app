package com.laxen.auxiliaire.models;

import java.util.ArrayList;
import java.util.List;

public class JobsModel {

	private List<Job> jobs = new ArrayList<>();
	private List<Job> myJobs = new ArrayList<>();

	public List<Job> getJobs() {
		return jobs;
	}

	public List<Job> getMyJobs() {
		return myJobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public void setMyJobs(List<Job> myJobs) {
		this.myJobs = myJobs;
	}
}
