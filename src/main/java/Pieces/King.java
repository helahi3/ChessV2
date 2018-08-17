package Pieces;

import Board.Cell;
import Board.Move;

import java.util.ArrayList;

public class King extends Piece {



    public King(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.KING);
    }

    @Override
    public ArrayList<Cell> getPseudoLegalMoves() {
        return null;
    }

}
