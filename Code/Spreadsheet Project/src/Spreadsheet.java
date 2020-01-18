import org.jetbrains.annotations.Contract;

import java.util.LinkedList;

public class Spreadsheet {
    protected int row;
    protected int col;


    private int[][] cells;

    public Spreadsheet() {
        cells = new int[4][4];
        for(int i=0; i<cells.length; i++)
        {
            for(int j=0; j<cells[i].length; j++)
            {
                cells[i][j]=i+1;
            }
        }
        System.out.println(cells);
    }


  //  public Spreadsheet[][] getCells() {
  //      return cells;
  //  }

  //  public void setCells(Spreadsheet[][] cells) {
  //      this.cells = cells;
  //  }
 //   public getValue(int row, int col) {

 //   }

}
