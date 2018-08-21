package Board;

import Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Cell object that makes up the individual squares of the chess board
 */
public class Cell {

    /**
     * private fields for location and the piece on the cell
     */
    private int row, column;
    private Piece piece;
    private boolean isSelected;

    /**
     * Constructor
     * @param row
     * @param column
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        piece = null;
    }

    /**
     * Getter method for row
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * getter method for column
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * getter for piece
     * @return the piece (can be null)
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Set a piece on the cell if its null
     * @param piece to be placed on the cell
     */
    public void setPiece(Piece piece) {
        if(this.piece == null)
            this.piece = piece;
    }

    /**
     * Check if cell is empty
     * @return boolean
     */
    public boolean isEmpty(){
        return this.piece == null;
    }

    /**
     * Remove the piece from the cell
     * @return the removed piece
     */
    public Piece removePiece(){
        Piece temp = this.piece;
        this.piece = null;
        return temp;
    }

    /**
     * Remove the old piece and place the new piece on the cell
     * @param newPiece the new piece to be put on the cell
     * @return the old piece that was removed
     */
    public Piece replacePiece(Piece newPiece){
        Piece oldPiece = this.piece;
       // if(newPiece != null)
            this.piece = newPiece;
        return oldPiece;
    }

    /**
     * Get a list of cells that the piece on this cell threatens (if any)
     * @return list of attackable cells (if any)
     */
    public ArrayList<Cell> highlightAttackableCells(){
        ArrayList<Cell> listOfAttackableCells = new ArrayList<Cell>();
        if(this.isEmpty())
            return listOfAttackableCells;

        Piece piece = this.getPiece();
        return piece.getLegalMoves();
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * for testing on console
     * @return cell as string
     */
    public String toString() {
        String res = "{ " + this.piece + " }";
        String res2 = "{    }";
        if(this.isEmpty()) return res2;
        else return res;

    }

    /**
     * for testing on console
     * @return cell as string
     */
    public String toString2() {
        String res = "{" + row + "," + column + "}";
        return res;
    }


}
