package Pieces;

import Board.*;

import java.util.ArrayList;

/**
 * Bishop object is subclass of PIece
 * implements abstract methods
 */
public class Bishop extends Piece {


    /**
     * Constructor that sets piece type as BISHOP
     */
    public Bishop(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.BISHOP);
    }

    /**
     * use superclass' slidingPieceMoves method to generate PL moves
     * @return list of pl moves
     */
    public ArrayList<Cell> getPseudoLegalMoves() {

        int[][] offsetMultiplier = {{-1,-1},{-1,1},{1,-1},{1,1}}; //4 directions NW, NE, SW, SE
        int x = getRow(); int y = getColumn();


        return Piece.slidingPieceMoves(x,y,this.getColor(),offsetMultiplier);

    }

}
