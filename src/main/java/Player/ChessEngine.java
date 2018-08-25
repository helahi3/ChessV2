package Player;

import Board.Board;
import Board.Cell;
import Pieces.Piece;

import java.util.ArrayList;

public class ChessEngine {





    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////// THESE ARE THE MOVES FOR THE RANDOMLY MOVING AI /////////////////////
    ////////////////// LOOPS THROUGH ALL PIECES AND RANDOMLY SELECTS A MOVE //////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


    public int[] play(){
        Cell[][] board = Board.getBoard();

        Piece randomPiece;
        ArrayList<Cell> moves;
        do{
            randomPiece = getRandomPiece(board);
            moves = randomPiece.getLegalMoves();
        } while (moves.size() == 0);

        int[] move = doMove(board, randomPiece, getRandomMove(moves));
        return move;
    }

    public int[] doMove(Cell[][] board, Piece piece, Cell destination){
        int row = piece.getRow(), col = piece.getColumn();
        int[] move = {row,col, destination.getRow(), destination.getColumn()};
        return move;
    }

    public Cell getRandomMove(ArrayList<Cell> moves){
        int randNum = (int) (Math.random() * moves.size());
        return moves.get(randNum);
    }


    public Piece getRandomPiece(Cell[][] board){
        ArrayList<Piece> blackPieces = (ArrayList<Piece>) Board.getBlackPieces();

        while(true) {

            int randNum = (int) (Math.random() * blackPieces.size());
            Piece randomPiece = blackPieces.get(randNum);

            return randomPiece;

        }
    }

}
