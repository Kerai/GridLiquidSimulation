import liquid.Cell.CellType;
import liquid.Grid;
import tests.J2DViewer;

public class Start {

	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "True");
		Grid grid = new Grid(80, 60);
		
//		grid.cells[10][40].liquid = 1;
//		grid.cells[9][58].setType(CellType.SOLID);
//		grid.cells[12][58].setType(CellType.SOLID);
		
		new J2DViewer(grid).run();
	}

}
