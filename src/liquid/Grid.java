package liquid;

import liquid.Cell.CellType;

public class Grid {
	public int width = 80;
	public int height = 60;

	public Cell[][] cells;
	Liquid simulator;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		createGrid();
		
		simulator = new Liquid ();
		simulator.initialize(cells);
	}
	
	
	void createGrid() {

		cells = new Cell[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Cell cell = new Cell();
				
				// add a border
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					cell.setType ( CellType.SOLID );
				}

				cells [x][y] = cell;
			}
		}
		updateNeighbors ();
	}
	
	// Sets neighboring cell references
	void updateNeighbors() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x > 0) {
					cells[x][y].left = cells [x - 1][ y];
				}
				if (x < width - 1) {
					cells[x][y].right = cells [x + 1][y];
				}
				if (y > 0) {
					cells[x][y].top = cells [x][y - 1];
				}
				if (y < height - 1) {
					cells[x][y].bottom = cells [x][y + 1];
				}
			}
		}
	}
	
	
	
	public void update() {
		simulator.simulate(cells, width, height);
	}
}
