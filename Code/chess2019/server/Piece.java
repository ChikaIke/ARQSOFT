package edu.upc.etsetb.arqsoft.chess2019.server;

public abstract class Piece {
    protected Color color;


    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
