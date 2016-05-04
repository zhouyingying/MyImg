package com.zhou.bean;

public class label {
	private int id;
	private String path;
	private String label;
	
	public label() {
		super();
	}
	
	public label(String path, String label) {
		super();
		this.path = path;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
}
