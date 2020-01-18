public class User {
    public static void main(String[] var0) {

        Spreadsheet ss = new Spreadsheet();
        int[][] cells;
        cells = new int[4][4];
        for(int i=0; i<cells.length; i++)
        {
            for(int j=0; j<cells[i].length; j++)
            {
                cells[i][j]=38+i;
            }
        }
        System.out.println(cells[3][0]);
    }
}
