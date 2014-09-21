package com.shawnyapa.gridimagesearch;

import java.io.Serializable;

public class FilterSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	public String Size;
	public String Color;
	public String Type;
	public String Site;
	
	public void FilerSettings() {
		Size = "All";
		Color = "All";
		Type = "All";
		Site = "All";
		
	}

}
