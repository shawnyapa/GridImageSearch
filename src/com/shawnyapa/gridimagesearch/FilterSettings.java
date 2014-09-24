package com.shawnyapa.gridimagesearch;

import java.io.Serializable;

public class FilterSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	public String size;
	public String color;
	public String type;
	public String site;
	
	public FilterSettings() {
		super();
		size = "All";
		color = "All";
		type = "All";
		site = "All";
	}

}
