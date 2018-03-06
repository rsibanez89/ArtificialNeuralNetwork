package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import algorithms.supervised.artificialneuralnetwork.Perceptron;
import algorithms.supervised.artificialneuralnetwork.Tripla;

public class ArtificialNeuralNetworkGraphics {

	static Tripla[] input_data = new Tripla[] { new Tripla(1.7, 56.0, 1), new Tripla(1.72, 63.0, 0),
			new Tripla(1.6, 50.0, 1), new Tripla(1.7, 63.0, 0), new Tripla(1.74, 66.0, 0), new Tripla(1.72, 63.0, 0),
			new Tripla(1.58, 55.0, 1), new Tripla(1.83, 80.0, 0), new Tripla(1.82, 70.0, 0),
			new Tripla(1.65, 54.0, 1), };

	static Perceptron perceptron = new Perceptron(3);

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CartesianFrame frame = new CartesianFrame(200, 100, 10, 5);

				// training phase
				for (int i = 0; i < 1000; i++) {
					for (Tripla t : input_data) {
						int output = t.Gender;
						Vector<Double> inputs = new Vector<Double>();
						inputs.add(1.0);
						inputs.add(t.Height);
						inputs.add(t.Weight);
						int error = perceptron.train(inputs, output);
						// System.out.println(error);
					}
				}

				for (Tripla t : input_data) {
					if (t.Gender == 0) {
						frame.panel.drawPoint(new Point((int) (t.Height * 100), (int) t.Weight), Color.BLUE);
					} else {
						frame.panel.drawPoint(new Point((int) (t.Height * 100), (int) t.Weight), Color.PINK);
					}
				}
				
				perceptron.weights.forEach(w -> System.out.println(w));

				Point pointStart = new Point(0, 0);
				//leave Height as 0
				boolean limitFound = false;
				Vector <Double> test = new Vector<>();
				test.add(1.0);
				test.add(0d/100); // Height 
				test.add((double)0);
				int initialGender = perceptron.predict(test);
				for (int w = 1; w < 100 && !limitFound; w++)
				{
					test = new Vector<>();
					test.add(1.0);
					test.add(0d/100); // Height 
					test.add((double)w);
					if(perceptron.predict(test) != initialGender)
					{
						limitFound = true;
						pointStart = new Point(0, w);
					}
				}
					
				Point pointEnd = new Point(200, 50);
				//leave Height as 200
				limitFound = false;
				test = new Vector<>();
				test.add(1.0);
				test.add(200d/100); // Height 
				test.add((double)0);
				initialGender = perceptron.predict(test);
				for (int w = 1; w < 150 && !limitFound; w++)
				{
					test = new Vector<>();
					test.add(1.0);
					test.add(200d/100); // Height 
					test.add((double)w);
					if(perceptron.predict(test) != initialGender)
					{
						limitFound = true;
						pointEnd = new Point(200, w);
					}
				}
				
				frame.panel.drawLinePoint(pointStart, pointEnd, Color.GREEN);
				frame.showUI();
			}
		});
	}
}

class CartesianFrame extends JFrame {
	CartesianPanel panel;

	public CartesianFrame(int xCoordNumbers, int yCoordNumbers, int showEveryXNumber, int showEveryYNumber) {
		super();
		panel = new CartesianPanel(xCoordNumbers, yCoordNumbers, showEveryXNumber, showEveryYNumber);
		add(panel);
	}

	public void showUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Artificial Neural Network Graphics");
		setSize(1300, 900);
		setVisible(true);
	}
}

class CartesianPanel extends JPanel {
	// x-axis coord constants
	public static final int X_AXIS_FIRST_X_COORD = 50;
	public static final int X_AXIS_SECOND_X_COORD = 1200;
	public static final int X_AXIS_Y_COORD = 800;

	// y-axis coord constants
	public static final int Y_AXIS_FIRST_Y_COORD = 50;
	public static final int Y_AXIS_SECOND_Y_COORD = 800;
	public static final int Y_AXIS_X_COORD = 50;

	// arrows of axis are represented with "hipotenuse" of
	// triangle
	// now we are define length of cathetas of that triangle
	public static final int FIRST_LENGHT = 10;
	public static final int SECOND_LENGHT = 5;

	// size of start coordinate lenght
	public static final int ORIGIN_COORDINATE_LENGHT = 6;

	// distance of coordinate strings from axis
	public static final int AXIS_STRING_DISTANCE = 20;

	// Points and Lines to draw
	private List<Pair<Point, Color>> points = new ArrayList<>();
	private List<Triplet<Point, Point, Color>> lines = new ArrayList<>();

	// Number of point in each axis
	int xCoordNumbers;
	int yCoordNumbers;

	int showEveryYNumber;
	int showEveryXNumber;

	int xLength;
	int yLength;

	public CartesianPanel(int xCoordNumbers, int yCoordNumbers, int showEveryXNumber, int showEveryYNumber) {
		super();
		this.xCoordNumbers = xCoordNumbers;
		this.yCoordNumbers = yCoordNumbers;
		this.showEveryXNumber = showEveryXNumber;
		this.showEveryYNumber = showEveryYNumber;

		// numerate axis
		xLength = (X_AXIS_SECOND_X_COORD - X_AXIS_FIRST_X_COORD) / xCoordNumbers;
		yLength = (Y_AXIS_SECOND_Y_COORD - Y_AXIS_FIRST_Y_COORD) / yCoordNumbers;
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// x-axis
		g2.drawLine(X_AXIS_FIRST_X_COORD, X_AXIS_Y_COORD, X_AXIS_SECOND_X_COORD, X_AXIS_Y_COORD);
		// y-axis
		g2.drawLine(Y_AXIS_X_COORD, Y_AXIS_FIRST_Y_COORD, Y_AXIS_X_COORD, Y_AXIS_SECOND_Y_COORD);

		// draw origin Point
		g2.fillOval(X_AXIS_FIRST_X_COORD - (ORIGIN_COORDINATE_LENGHT / 2),
				Y_AXIS_SECOND_Y_COORD - (ORIGIN_COORDINATE_LENGHT / 2), ORIGIN_COORDINATE_LENGHT,
				ORIGIN_COORDINATE_LENGHT);

		// draw x-axis numbers
		for (int i = 1; i < xCoordNumbers; i++) {
			g2.drawLine(X_AXIS_FIRST_X_COORD + (i * xLength), X_AXIS_Y_COORD - SECOND_LENGHT,
					X_AXIS_FIRST_X_COORD + (i * xLength), X_AXIS_Y_COORD + SECOND_LENGHT);
			if (i % showEveryXNumber == 0)
				g2.drawString(Integer.toString(i), X_AXIS_FIRST_X_COORD + (i * xLength) - 3,
						X_AXIS_Y_COORD + AXIS_STRING_DISTANCE);
		}

		// draw y-axis numbers
		for (int i = 1; i < yCoordNumbers; i++) {
			g2.drawLine(Y_AXIS_X_COORD - SECOND_LENGHT, Y_AXIS_SECOND_Y_COORD - (i * yLength),
					Y_AXIS_X_COORD + SECOND_LENGHT, Y_AXIS_SECOND_Y_COORD - (i * yLength));
			if (i % showEveryYNumber == 0)
				g2.drawString(Integer.toString(i), Y_AXIS_X_COORD - AXIS_STRING_DISTANCE,
						Y_AXIS_SECOND_Y_COORD - (i * yLength));
		}

		// draw points
		points.forEach(p -> {
			g.setColor(p.getValue1());
			drawPointOnPanel(p.getValue0(), g);
			g.setColor(Color.BLACK);
		});

		// draw lines
		lines.forEach(p -> {
			g.setColor(p.getValue2());
			drawLineOnPanel(p.getValue0(), p.getValue1(), g);
			g.setColor(Color.BLACK);
		});
	}

	private void drawPointOnPanel(Point point, Graphics g) {
		final int pointDiameter = 10;
		final int x = X_AXIS_FIRST_X_COORD + (point.x * xLength) - pointDiameter / 2;
		final int y = Y_AXIS_SECOND_Y_COORD - (point.y * yLength) - pointDiameter / 2;

		g.fillOval(x, y, pointDiameter, pointDiameter);
	}

	private void drawLineOnPanel(Point pointStart, Point pointEnd, Graphics g) {
		final int pointDiameter = 10;
		final int xStart = X_AXIS_FIRST_X_COORD + (pointStart.x * xLength) - pointDiameter / 2;
		final int yStart = Y_AXIS_SECOND_Y_COORD - (pointStart.y * yLength) - pointDiameter / 2;

		final int xEnd = X_AXIS_FIRST_X_COORD + (pointEnd.x * xLength) - pointDiameter / 2;
		final int yEnd = Y_AXIS_SECOND_Y_COORD - (pointEnd.y * yLength) - pointDiameter / 2;

		g.drawLine(xStart, yStart, xEnd, yEnd);
	}

	public void drawPoint(Point point, Color color) {
		points.add(new Pair<Point, Color>(point, color));
		repaint();
	}

	public void drawLinePoint(Point pointStart, Point pointEnd, Color color) {
		lines.add(new Triplet<Point, Point, Color>(pointStart, pointEnd, color));
		repaint();
	}
}