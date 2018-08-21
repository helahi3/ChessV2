package Pieces;

import Board.Board;
import Board.Cell;
import Board.Move;

import java.util.ArrayList;

/**
 * Rook object is subclass of Piece object
 * Implements abstract method (PL moves)
 */
public class Rook extends Piece {
    /**
     * Constructor that sets piece type to be rook
     */
    public Rook(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.ROOK);
    }

    /**
     * Uses super-class' slidingPieceMoves to generate pseudolegal moves
     * @return list of PL moves
     */
    public ArrayList<Cell> getPseudoLegalMoves() {

        int[][] offsetMultiplier = {{-1,0},{1,0},{0,-1},{0,1}}; //4 directions N, S, W, E
        int x = getRow(); int y = getColumn();

        return Piece.slidingPieceMoves(x,y,this.getColor(),offsetMultiplier);
    }




}
