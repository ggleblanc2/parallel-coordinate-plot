package com.ggl.pcc.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class ParallelCoordinatePlotModel {
	
	private Dimension drawingPanelDimension;
	
	private List<Number[]> coordinateList;
	
	private NumberRange[] coordinateRanges;
	
	private String title;
	
	private String[] coordinateTitles;
	
	public ParallelCoordinatePlotModel() {
		this.drawingPanelDimension = new Dimension(1000, 600);
		this.title = "";
		this.coordinateTitles = new String[0];
		this.coordinateRanges = new NumberRange[0];
		this.coordinateList = new ArrayList<>();
	}
	
	public void addCoordinate(Number[] coordinates) {
		this.coordinateList.add(coordinates);
	}

	public Dimension getDrawingPanelDimension() {
		return drawingPanelDimension;
	}

	public void setDrawingPanelDimension(Dimension drawingPanelDimension) {
		this.drawingPanelDimension = drawingPanelDimension;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getCoordinateTitles() {
		return coordinateTitles;
	}

	public void setCoordinateTitles(String[] coordinateTitles) {
		this.coordinateTitles = coordinateTitles;
	}

	public NumberRange[] getCoordinateRanges() {
		return coordinateRanges;
	}

	public void setCoordinateRanges(NumberRange[] coordinateRanges) {
		this.coordinateRanges = coordinateRanges;
	}

	public List<Number[]> getCoordinateList() {
		return coordinateList;
	}

}
