package com.ggl.pcc;

import javax.swing.SwingUtilities;

import com.ggl.pcc.model.ParallelCoordinatePlotModel;
import com.ggl.pcc.view.PCCFrame;

public class PCCExample implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new PCCExample());
	}

	@Override
	public void run() {
		new PCCFrame(new ParallelCoordinatePlotModel());
	}

}
