package Pieces;

import Board.*;

import java.util.ArrayList;

/**
 * Knight object is subclass of Piece
 * Implements abstract methods
 */
public class Knight extends Piece {

    /**
     * Constructor that sets piece type to be KNIGHT
     */
    public Knight(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.KNIGHT);
    }

    /**
     * Generates list of pseudolegal moves
     * @return list of moves
     */
    public ArrayList<Cell> getPseudoLegalMoves() {

        Cell[][] board = Board.getBoard();
        ArrayList<Cell> moves = new ArrayList<Cell>();

        //Possible ways to move a knight
        int[][] possibleOffsets = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,-1}};

        for(int i=0; i<possibleOffsets.length; i++){
            int x = possibleOffsets[i][0] + getRow();
            int y = possibleOffsets[i][1] + getColumn();

            //Making sure we dont leave the board
            if(x >= board.length || x < 0
                    || y >= board.length || y < 0)
                continue;

            //Making sure destination does not contain a friendly piece
            if(Board.containsPieceOfColor(x,y,this.getColor()))
                continue;

            moves.add(board[x][y]);
        }

        return moves;
    }

}
