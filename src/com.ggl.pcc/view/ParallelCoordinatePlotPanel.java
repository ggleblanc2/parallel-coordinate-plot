package com.ggl.pcc.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.ggl.pcc.model.DoubleRange;
import com.ggl.pcc.model.IntegerRange;
import com.ggl.pcc.model.NumberRange;
import com.ggl.pcc.model.ParallelCoordinatePlotModel;

public class ParallelCoordinatePlotPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int yPlotStart, yPlotEnd;
	private final int margin;
	
	private final ParallelCoordinatePlotModel model;

	public ParallelCoordinatePlotPanel(ParallelCoordinatePlotModel model) {
		this.model = model;
		this.margin = 20;
		this.setPreferredSize(model.getDrawingPanelDimension());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		
		g2d.setColor(Color.BLACK);
		drawTitle(g2d);
		
		if (!drawCoordinateTitles(g2d)) {
			return;
		}
		
		drawAxes(g2d);
		
		g2d.setColor(Color.BLUE);
		drawCoordinates(g2d);
	}
	
	private void drawTitle(Graphics2D g2d) {
		if (model.getTitle().isEmpty()) {
			yPlotStart = margin;
		} else {
			Font titleFont = getTitleFont();
			int pointSize = titleFont.getSize();
			Rectangle r = new Rectangle(0, 20, getWidth(), pointSize);
			centerString(g2d, r, model.getTitle(), titleFont);
			yPlotStart = margin + margin + titleFont.getSize(); 
		}
		
		yPlotEnd = getHeight() - margin - margin;
	}
	
	private boolean drawCoordinateTitles(Graphics2D g2d) {
		String[] coordinateTitles = model.getCoordinateTitles();
		if (coordinateTitles.length <= 0) {
			return false;
		}

		int width = getWidth() - margin - margin;
		int coordinateWidth = width / coordinateTitles.length;
		Font subtitleFont = getSubtitleFont();
		int coordinateHeight = subtitleFont.getSize() + 4;
		int x = margin;
		int y = yPlotStart;
		int maxLineCount = 1;

		for (String coordinateTitle : coordinateTitles) {
			String[] parts = coordinateTitle.split("<br>");
			int y1 = y;
			for (String part : parts) {
				Rectangle r = new Rectangle(x, y1, coordinateWidth,
						coordinateHeight);
				centerString(g2d, r, part, subtitleFont);
				y1 += coordinateHeight;
			}
			x += coordinateWidth;
			maxLineCount = Math.max(maxLineCount, parts.length);
		}
		
		yPlotStart += coordinateHeight * maxLineCount + 20;

		return true;
	}
	
	private void drawAxes(Graphics2D g2d) {
		NumberRange[] coordinateRanges = model.getCoordinateRanges();
		if (coordinateRanges.length == 0) {
			return;
		}

		String[] coordinateTitles = model.getCoordinateTitles();
		if (model.getCoordinateRanges().length != coordinateTitles.length) {
			return;
		}

		int width = getWidth() - margin - margin;
		int coordinateWidth = width / coordinateTitles.length;
		Font textFont = getTextFont();
		int coordinateHeight = textFont.getSize() + 4;
		int x = margin + coordinateWidth / 2;
		int y = yPlotEnd;
		
		for (NumberRange range : coordinateRanges) {
			g2d.setStroke(new BasicStroke(3f));
			if (range instanceof IntegerRange) {
				drawIntegerRange(g2d, range, textFont, x, y, coordinateHeight);
			} else if (range instanceof DoubleRange) {
				drawDoubleRange(g2d, range, textFont, x, y, coordinateHeight);
			}
			g2d.setStroke(new BasicStroke(5f));
			g2d.drawLine(x, yPlotStart, x, yPlotEnd);
			x += coordinateWidth;
		}
	}

	private void drawIntegerRange(Graphics2D g2d, NumberRange range,
			Font textFont, int x, int y, int coordinateHeight) {
		IntegerRange integerRange = (IntegerRange) range;
		int minimum = integerRange.getMinimum();
		int maximum = integerRange.getMaximum();
		int increment = integerRange.getIncrement();
		int tickCount = (maximum - minimum) / increment;
		int tickHeight = (yPlotEnd - yPlotStart) / tickCount;
		int leftOver = (yPlotEnd - yPlotStart) % tickCount;
		int y1 = y;
		
		for (int index = minimum; index <= maximum; index += increment) {
			int tickLength = 16;
			g2d.drawLine(x, y1, x - tickLength, y1);
			
			String s = Integer.toString(index);
			Rectangle2D r2d = getBoundingRectangle(s, textFont);
			int textWidth = (int) Math.round(r2d.getWidth());
			int x1 = x - tickLength - margin / 2 - textWidth;
			int y2 = y1 - coordinateHeight / 2;
			Rectangle r = new Rectangle(x1, y2, textWidth, coordinateHeight);
			centerString(g2d, r, s, textFont);
			
			y1 -= tickHeight;
			if (leftOver > 0) {
				y1--;
				leftOver--;
			}
		}
	}
	
	private void drawDoubleRange(Graphics2D g2d, NumberRange range,
			Font textFont, int x, int y, int coordinateHeight) {
		DoubleRange doubleRange = (DoubleRange) range;
		int precision = doubleRange.getPrecision();
		double multiplier = Math.round(Math.pow(10, precision));
		long minimum = (long) (multiplier * doubleRange.getMinimum());
		long maximum = (long) (multiplier * doubleRange.getMaximum());
		long increment = (long) (multiplier * doubleRange.getIncrement());
		
		int tickCount = (int) ((maximum - minimum) / increment);
		int tickHeight = (yPlotEnd - yPlotStart) / tickCount;
		int leftOver = (yPlotEnd - yPlotStart) % tickCount;
		int y1 = y;
		
		for (long index = minimum; index <= maximum; index += increment) {
			int tickLength = 16;
			g2d.drawLine(x, y1, x - tickLength, y1);
			
			String formatter = "%." + precision + "f";
			double value = index / multiplier;
			String s = String.format(formatter, value);
			Rectangle2D r2d = getBoundingRectangle(s, textFont);
			int textWidth = (int) Math.round(r2d.getWidth());
			int x1 = x - tickLength - margin / 2 - textWidth;
			int y2 = y1 - coordinateHeight / 2;
			Rectangle r = new Rectangle(x1, y2, textWidth, coordinateHeight);
			centerString(g2d, r, s, textFont);
			
			y1 -= tickHeight;
			if (leftOver > 0) {
				y1--;
				leftOver--;
			}
		}
	}
	
	private void drawCoordinates(Graphics2D g2d) {
		List<Number[]> coordinateList = model.getCoordinateList();
		NumberRange[] coordinateRanges = model.getCoordinateRanges();
		
		int width = getWidth() - 40;
		int coordinateWidth = width / coordinateRanges.length;
		
		g2d.setStroke(new BasicStroke(3f));
		for (Number[] coordinates : coordinateList) {
			List<Point> points = new ArrayList<>();
			int x = 20 + coordinateWidth / 2;
			for (int index = 0; index < coordinates.length; index++) {
				int y = calculatePoint(coordinateRanges[index], coordinates[index]);
				points.add(new Point(x, y));
				x += coordinateWidth;
			}
			
			if (points.size() <= 0) {
				return;
			}
			
			Point previousPoint = points.get(0);
			for (int index = 1; index < points.size(); index++) {
				Point point =  points.get(index);
				g2d.drawLine(previousPoint.x, previousPoint.y, point.x, point.y);
				previousPoint = point;
			}
		}
	}
	
	private int calculatePoint(NumberRange numberRange, Number number) {
		if (numberRange instanceof IntegerRange) {
			IntegerRange integerRange = (IntegerRange) numberRange;
			int totalPlotRange = yPlotEnd - yPlotStart;
			int minimum = integerRange.getMinimum();
			int maximum = integerRange.getMaximum();
			int difference = maximum - minimum;
			int value = (Integer) number;
			double fraction = (double) (value - minimum) / difference;
			int plotRange = (int) Math.round(fraction * totalPlotRange);
			
			return yPlotEnd - plotRange;
		} else if (numberRange instanceof DoubleRange) { 
			DoubleRange doubleRange = (DoubleRange) numberRange;
			int totalPlotRange = yPlotEnd - yPlotStart;
			int precision = doubleRange.getPrecision();
			double multiplier = Math.round(Math.pow(10, precision));
			long minimum = (long) (multiplier * doubleRange.getMinimum());
			long maximum = (long) (multiplier * doubleRange.getMaximum());
			int difference = (int) (maximum - minimum);
			double value = (Double) number * multiplier;
			double fraction = (value - minimum) / difference;
			int plotRange = (int) Math.round(fraction * totalPlotRange);
			
			return yPlotEnd - plotRange;
		} else {
			return 0;
		}
	}
	
	private void centerString(Graphics2D g2d, Rectangle r, String s,
			Font font) {
		Rectangle2D r2d = getBoundingRectangle(s, font);
		int rWidth = (int) Math.round(r2d.getWidth());
		int rHeight = (int) Math.round(r2d.getHeight());
		int rX = (int) Math.round(r2d.getX());
		int rY = (int) Math.round(r2d.getY());
		int a = (r.width / 2) - (rWidth / 2) - rX;
		int b = (r.height / 2) - (rHeight / 2) - rY;

		g2d.setFont(font);
		g2d.drawString(s, r.x + a, r.y + b);
	}

	private Rectangle2D getBoundingRectangle(String s, Font font) {
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2d = font.getStringBounds(s, frc);
		return r2d;
	}
	
	private Font getTitleFont() {
		return new Font(Font.DIALOG, Font.BOLD, 36);
	}
	
	private Font getSubtitleFont() {
		return new Font(Font.DIALOG, Font.BOLD, 18);
	}
	
	private Font getTextFont() {
		return new Font(Font.DIALOG, Font.PLAIN, 14);
	}

}
