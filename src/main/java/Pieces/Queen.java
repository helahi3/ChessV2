package Pieces;

import Board.Cell;
import Board.Move;

import java.util.ArrayList;

/**
 * Queen object is subclass of Piece
 * Implements the abstract method from piece
 */
public class Queen extends Piece {


    /**
     * Constructor that sets the piece type to queen
     */
    public Queen(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.QUEEN,9);
    }

    /**
     * Uses super-class' slidingPieceMoves to generate pseudolegal moves
     * @return list of PL moves
     */
    public ArrayList<Cell> getPseudoLegalMoves() {
        int[][] offsetMultiplier = {{-1,-1},{-1,1},{1,-1},{1,1},{-1,0},{1,0},{0,-1},{0,1}}; //8 directions N, S, W, E {-1,0},{1,0},{0,-1},{0,1}
        int x = getRow(); int y = getColumn();


        return Piece.slidingPieceMoves(x,y,this.getColor(),offsetMultiplier);
    }

}
