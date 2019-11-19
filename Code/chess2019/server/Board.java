package edu.upc.etsetb.arqsoft.chess2019.server;

public class Board {
    private Square [][] squares;

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public Board (){
        squares= new Square [8][8] ;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(i, j);
            }
        }
    }
}
