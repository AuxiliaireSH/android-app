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
		catToColor.put("Handiwork", R.color.colorHandiworkStart);
		catToColor.put("Computers/IT", R.color.colorComputerStart);
		catToColor.put("Studies", R.color.colorStudiesStart);
		catToColor.put("Cooking", R.color.colorCookingStart);
		catToColor.put("Other", R.color.colorOtherStart);
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
