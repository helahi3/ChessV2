package Pieces;

import Board.Board;
import Board.Cell;


import java.util.ArrayList;
import java.util.List;

public class King extends Piece {


    public King(int row, int column, PieceColor color) {
        super(row, column, color, PieceType.KING,100);
    }

    /**
     * Movement for the King
     * @return list of pl moves for king
     */
    @Override
    public ArrayList<Cell> getPseudoLegalMoves() {
        ArrayList<Cell> moves = new ArrayList<Cell>();
        Cell[][] board = Board.getBoard();

        //All 8 directions, NW, NE, SW, SE, N, S, W, E
        int[][] directions = {{-1,-1},{-1,1},{1,-1},{1,1},{-1,0},{1,0},{0,-1},{0,1}};
        int offsetX, offsetY, x = getRow(), y = getColumn();

        //loop through all directions
        for(int i=0; i< directions.length; i++){
            offsetX = directions[i][0];
            offsetY = directions[i][1];

            //Make sure move doesnt take you off the board
            if(x + offsetX >= 0 && x + offsetX < board.length
                    && y + offsetY >= 0 && y + offsetY < board.length){

                //Make sure there is either no piece, or an enemy piece at the spot
                Cell temp = board[x + offsetX][y + offsetY];
                if(temp.getPiece() == null || temp.getPiece().getColor() != this.getColor()){
                    moves.add(temp);
                }
            }
        }
        return moves;
    }


    public boolean isInCheck(){
        List<Piece> enemyPieces = Board.getWhitePieces();
        if(this.getColor() == PieceColor.WHITE)
            enemyPieces = Board.getBlackPieces();

        Cell location = (Board.getBoard())[getRow()][getColumn()];

        for(Piece piece : enemyPieces){
            System.out.println("King is currently at " + location.toString());
            System.out.println("Checking if in check while moving " + piece + " " + piece.getPseudoLegalMoves());

            if(piece.getPseudoLegalMoves().contains(location))
                return true;
        }

        return false;
    }

}