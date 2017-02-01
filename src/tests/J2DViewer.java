package tests;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import liquid.Cell;
import liquid.Grid;
import liquid.Cell.CellType;

public class J2DViewer {

	Grid grid;

	private JFrame frame;
	private Canvas canvas;

	public J2DViewer(Grid grid) {
		this.grid = grid;

		canvas = new Canvas();

		canvas.setPreferredSize(new Dimension(800, 600));

		JFrame frame = new JFrame();
		frame.setTitle("Grid-based Liquid Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

		canvas.addMouseListener(Input.listener);
		canvas.addMouseMotionListener(Input.listener);
		canvas.createBufferStrategy(3);

		canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setup(canvas.getWidth(), canvas.getHeight());
			}
		});

		setup(canvas.getWidth(), canvas.getHeight());
	}

	public void run() {
		BufferStrategy bs = canvas.getBufferStrategy();
		try {
			while (true) {
				Graphics2D g = (Graphics2D) bs.getDrawGraphics();

				g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

				// 4 updates?
				update();
				update();
				update();
				update();

				render(g);

				bs.show();
				g.dispose();
				// Thread.sleep(20);
			}

		} finally {
			frame.dispose();
		}
	}

	AffineTransform transform = new AffineTransform();

	void setup(int viewportWidth, int viewportHeight) {
		transform.setToIdentity();
		transform.scale((float) viewportWidth / grid.width, (float) viewportHeight / grid.height);
	}

	void render(Graphics2D g) {
		for (int x = 0; x < grid.width; x++) {
			for (int y = 0; y < grid.height; y++) {
				Cell c = grid.cells[x][y];
				c.update();
				g.setTransform(transform);
				g.translate(x, y + 1);
				if (c.type == CellType.SOLID) {
					g.setColor(Color.BLACK);
					g.fillRect(0, -1, 1, 1);
				} else if (c.size > 0) {
					g.setColor(c.color.toColor());
					g.scale(1, -c.size);
					g.fillRect(0, 0, 1, 1);
				}
			}
		}
	}

	boolean fill = false;

	void update() {
		Point2D pos = new Point2D.Float();

		try {
			transform.inverseTransform(Input.mousePosition, pos);
		} catch (NoninvertibleTransformException e) {
			// why is this exception even a thing?
			// definitely not gaming oriented library
		}

		int x = (int) pos.getX();
		int y = (int) pos.getY();

		// Check if we are filling or erasing walls
		if (Input.isMouseJustPressed(1)) {
			if ((x > 0 && x < grid.width) && (y > 0 && y < grid.height)) {
				if (grid.cells[x][y].type == CellType.BLANK) {
					fill = true;
				} else {
					fill = false;
				}
			}
		}

		// Left click draws/erases walls
		if (Input.isMousePressed(1)) {
			if (x != 0 && y != 0 && x != grid.width - 1 && y != grid.height - 1) {
				if ((x > 0 && x < grid.width) && (y > 0 && y < grid.height)) {
					if (fill) {
						grid.cells[x][y].setType(CellType.SOLID);
					} else {
						grid.cells[x][y].setType(CellType.BLANK);
					}
				}
			}
		}

		// Right click places liquid
		if (Input.isMousePressed(3)) {
			if ((x > 0 && x < grid.width) && (y > 0 && y < grid.height)) {
				grid.cells[x][y].addLiquid(1);
			}
		}
		// Middle click places more liquid
		if (Input.isMousePressed(2)) {
			if ((x > 0 && x < grid.width) && (y > 0 && y < grid.height)) {
				grid.cells[x][y].addLiquid(10);
			}
		}
		Input.update();
		grid.update();
	}

}
