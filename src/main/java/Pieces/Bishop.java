package Pieces;

import Board.*;

import java.util.ArrayList;

public class Bishop extends Piece {


    public Bishop(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.BISHOP);
    }

    public ArrayList<Cell> getPseudoLegalMoves() {

        int[][] offsetMultiplier = {{-1,-1},{-1,1},{1,-1},{1,1}}; //4 directions N, S, W, E
        int x = getRow(); int y = getColumn();

        return Piece.slidingPieceMoves(x,y,this.getColor(),offsetMultiplier);

    }

}
