package com.laxen.auxiliaire.models;

import com.laxen.auxiliaire.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobsModel {

	private List<Job> jobs = new ArrayList<>();
	private List<Job> myJobs = new ArrayList<>();
	private HashMap<String, Integer> catToColor = new HashMap(); // maps a category to a color

	public JobsModel () {

		// maps all job categories to their color
		catToColor.put("Handiwork", R.color.colorPrimary);
		catToColor.put("Computers/IT", R.color.colorComputer);
		catToColor.put("Studies", R.color.colorStudies);
		catToColor.put("Cooking", R.color.colorStudies);
		catToColor.put("Other", R.color.colorOther);
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public List<Job> getMyJobs() {
		return myJobs;
	}

	public HashMap<String, Integer> getCatToColor() {
		return catToColor;
	}


	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

}
