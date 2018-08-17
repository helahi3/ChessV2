package Pieces;

import Board.Cell;
import Board.Move;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.QUEEN);
    }

    public ArrayList<Cell> getPseudoLegalMoves() {
        int[][] offsetMultiplier = {{-1,-1},{-1,1},{1,-1},{1,1},{-1,0},{1,0},{0,-1},{0,1}}; //8 directions N, S, W, E {-1,0},{1,0},{0,-1},{0,1}
        int x = getRow(); int y = getColumn();

        return Piece.slidingPieceMoves(x,y,this.getColor(),offsetMultiplier);
    }

}
