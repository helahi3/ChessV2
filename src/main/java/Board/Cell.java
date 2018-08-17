package Board;

import Pieces.Piece;

public class Cell {

    private int row, column;
    private Piece piece;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        piece = null;
    }

    public int getRow() {
        return row;
    }


    public int getColumn() {
        return column;
    }

    public Piece getPiece() {

        return piece;
    }

    public void setPiece(Piece piece) {
        if(this.piece == null)
            this.piece = piece;
    }


    //Returns true if no piece on cell
    public boolean isEmpty(){
        return this.piece == null;
    }

    //Removes a piece from a spot, returning the old piece
    public Piece removePiece(){
        Piece temp = this.piece;
        piece = null;
        return temp;
    }

    //Replaces the piece at a spot, returning the old piece
    public Piece replacePiece(Piece newPiece){
        Piece oldPiece = this.piece;
        piece = newPiece;
        return oldPiece;
    }





    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    public String toString() {
        String res = "{ " + this.piece + " }";
        String res2 = "{    }";
        if(this.isEmpty()) return res2;
        else return res;

    }

    //Only used for testing
    public String toString2() {
        String res = "{" + row + "," + column + "}";
        return res;
    }


}
