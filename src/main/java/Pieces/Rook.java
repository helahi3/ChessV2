package Pieces;

import Board.Board;
import Board.Cell;
import Board.Move;

import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.ROOK);
    }

    public ArrayList<Cell> getPseudoLegalMoves() {


        int[][] offsetMultiplier = {{-1,0},{1,0},{0,-1},{0,1}}; //4 directions N, S, W, E
        int x = getRow(); int y = getColumn();

        return Piece.slidingPieceMoves(x,y,this.getColor(),offsetMultiplier);
    }

















    public ArrayList<Move> getLegalMoves() {
        return null;
    }
}
