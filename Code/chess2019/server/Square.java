package edu.upc.etsetb.arqsoft.chess2019.server;

public class Square {
    private int r, col ;
    private Piece piece ;

    public Square(int r, int c){
        this.r=r ;
        this.col=c ;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
