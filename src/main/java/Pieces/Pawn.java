package Pieces;

import Board.*;

import java.util.ArrayList;

/**
 * Pawn object is subclass of Piece
 * Implements abstract methods
 */
public class Pawn extends Piece {


    /**
     * Constructor that sets piece type to be Pawn
     */
    public Pawn(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.PAWN);
    }

    /**
     * Generates list of pseudolegal moves
     * @return list of moves
     */
    @Override
    public ArrayList<Cell> getPseudoLegalMoves() {
        Cell[][] board = Board.getBoard();
        ArrayList<Cell> moves = new ArrayList<Cell>();

        //Black starts at top of the board
        if(this.getColor() == PieceColor.BLACK){

            if(Board.isCellEmpty(this.getRow() + 1, this.getColumn())){ //Check if the next spot is empty
                moves.add(board[getRow() + 1][getColumn()]); //then add it

                //if the piece has not moved, and the next 2 spots are empty
                //allow it to move 2 spaces
                if(!hasMoved && Board.isCellEmpty(this.getRow() + 2, this.getColumn()))
                    moves.add(board[getRow() + 2][getColumn()]);
            }

            //Checking if the attack-able spots contain enemy piece
            //Making sure it does not exceed the size of the board
            //Checks [row+1][col+1]  and [row+1][col-1]
            //i.e. black pawn at d7 threatens pieces at e6 and c6

            if(this.getColumn() + 1 < board.length && this.getRow() + 1 < board.length
                    && Board.containsPieceOfColor(this.getRow() + 1, this.getColumn() + 1, PieceColor.WHITE)) {

                moves.add(board[getRow() + 1][getColumn() + 1]);
            }

            if(this.getColumn() - 1 >= 0 && this.getRow() + 1 < board.length
                    && Board.containsPieceOfColor(this.getRow() + 1, this.getColumn() -1, PieceColor.WHITE)) {

                moves.add(board[getRow() + 1][getColumn() + 1]);
            }


        } else {
            //For white, same code with reversed direction

            if(Board.isCellEmpty(this.getRow() - 1, this.getColumn())){ //Check if the next spot is empty
                moves.add(board[getRow() - 1][getColumn()]); //then add it

                //if the piece has not moved, and the next 2 spots are empty
                //allow it to move 2 spaces
                if(!hasMoved && Board.isCellEmpty(this.getRow() - 2, this.getColumn()))
                    moves.add(board[getRow() - 2][getColumn()]);
            }


            //Checking if the attack-able spots contain enemy piece
            //Making sure it does not exceed the size of the board
            //Checks [row-1][col+1]  and [row-1][col-1]
            //i.e. white pawn at d5 threatens pieces at e6 and c6

            if(this.getColumn() + 1 < board.length && this.getRow() - 1 >= 0
                    && Board.containsPieceOfColor(this.getRow() - 1, this.getColumn() + 1, PieceColor.BLACK)) {

                moves.add(board[getRow() - 1][getColumn() + 1]);
            }

            if(this.getColumn() - 1 >= 0 && this.getRow() - 1 >= 0
                    && Board.containsPieceOfColor(this.getRow() - 1, this.getColumn() -1, PieceColor.WHITE)) {

                moves.add(board[getRow() - 1][getColumn() + 1]);
            }
        }

        return moves;
    }

}
