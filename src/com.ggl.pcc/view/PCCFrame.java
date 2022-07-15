package com.ggl.pcc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.ggl.pcc.model.DoubleRange;
import com.ggl.pcc.model.IntegerRange;
import com.ggl.pcc.model.NumberRange;
import com.ggl.pcc.model.ParallelCoordinatePlotModel;

public class PCCFrame {
	
//	private final ParallelCoordinatePlotModel model;
	
	private final ParallelCoordinatePlotPanel plotPanel;

	public PCCFrame(ParallelCoordinatePlotModel model) {
//		this.model = model;
		String[] coordinateTitles = { "Cylinders", "Displacement (cc)", "Power (hp)",
				"Weight (lb)", "Acceleration<br>0 - 60 mph (sec)",
				"Mileage (mpg)", "Year" };
		NumberRange cylinderRange = new DoubleRange(3, 12, 1, 1);
		NumberRange displacementRange = new IntegerRange(50, 450, 50);
		NumberRange powerRange = new IntegerRange(0, 480, 40);
		NumberRange weightRange = new IntegerRange(1500, 5000, 500);
		NumberRange accelerationRange = new IntegerRange(8, 26, 2);
		NumberRange mileageRange = new IntegerRange(0, 50, 5);
		NumberRange yearRange = new IntegerRange(1981, 1995, 1);
		NumberRange[] coordinateRanges = { cylinderRange, displacementRange,
				powerRange, weightRange, accelerationRange, mileageRange,
				yearRange };
		Number[] coordinates1 = { 3.0, 75, 60, 1700, 18, 35, 1990 };
		Number[] coordinates2 = { 6.0, 180, 140, 2300, 12, 28, 1992 };
		Number[] coordinates3 = { 8.0, 320, 289, 3200, 9, 21, 1985 };
		
		model.setDrawingPanelDimension(new Dimension(1200, 800));
		model.setTitle("Parallel Coordinate Plot Example");
		model.setCoordinateTitles(coordinateTitles);
		model.setCoordinateRanges(coordinateRanges);
		model.addCoordinate(coordinates1);
		model.addCoordinate(coordinates2);
		model.addCoordinate(coordinates3);
		
		this.plotPanel = new ParallelCoordinatePlotPanel(model);
		createAndShowGUI();
	}
	
	private JFrame createAndShowGUI() {
		JFrame frame = new JFrame("Parallel Coordinate Plot Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(plotPanel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		
		return frame;
	}

}
