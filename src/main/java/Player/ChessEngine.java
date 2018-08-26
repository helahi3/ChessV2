package Player;

import Board.*;
import Board.Cell;
import Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class ChessEngine {

    /**
     * Modify this method to select which AI to use (playEngine1 etc.)
     * @return move coordinates
     */
    public int[] play(){
        return playEngine2();
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////    /////////////////////
    ///////////     //////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////




    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////// THIS AI WILL KILL THE 'BEST' PIECE THAT IT CAN /////////////////////
    /////////// LOOPS THROUGH ALL PIECES AND SELECTS MOVE THAT KILLS THE BEST PIECE //////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Loops through all moves of all pieces, killing the piece with the highest 'value'
     * Will always kill a piece if possible
     * @return coordinates of move
     */
    private int[] playEngine2() {
        ArrayList<Cell> moves;
        List<Piece> pieces = getPieces(Piece.PieceColor.BLACK);

        Piece pieceToMove = null;
        int score = 0;
        Cell destination = null;

        for(Piece piece : pieces){

            //move that kills the best piece
            Move temp = killBestPieceMove(piece.getLegalMoves());

            //if null, that means no piece could be kiled
            if(temp == null)
                continue;

            //if that move's score is higher than current high, replace it and destination
            //flag the piece we are goign to move
            if(temp.getScore() > score){
                score = temp.getScore();
                destination = temp.getEnd();
                pieceToMove = piece;
            }
        }

        //If no destination, that means no piece can be killed, so return a random move
        if(destination == null)
            return playEngine1();


        return doMove(pieceToMove,destination);
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////// THESE ARE THE MOVES FOR THE RANDOMLY MOVING AI /////////////////////
    ////////////////// LOOPS THROUGH ALL PIECES AND RANDOMLY SELECTS A MOVE //////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * relevant method for getting a random piece and movign it
     * Starting point for engine
     * @return coordinates of move
     */
    private int[] playEngine1(){
        Piece randomPiece;
        ArrayList<Cell> moves;
        do{
            randomPiece = getRandomPiece();
            moves = randomPiece.getLegalMoves();
        } while (moves.size() == 0);

        return doMove(randomPiece, getRandomMove(moves));
    }

    /**
     * Random move generator
     * @param moves list of moves
     * @return a random move from that list
     */
    private Cell getRandomMove(ArrayList<Cell> moves){
        int randNum = (int) (Math.random() * moves.size());
        return moves.get(randNum);
    }

    /**
     * Get a random piece (black)
     * @return a random piece from piece list
     */
    private Piece getRandomPiece(){
        ArrayList<Piece> blackPieces = (ArrayList<Piece>) Board.getBlackPieces();

        int randNum = (int) (Math.random() * blackPieces.size());
        return blackPieces.get(randNum);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// HELPER METHODS /////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets coordinates of a move for a piece to destination
     * @param piece the piece being moved
     * @param destination the destination cell
     * @return coordinates of the move
     */
    private int[] doMove(Piece piece, Cell destination){
        int row = piece.getRow(), col = piece.getColumn();
        int[] move = {row,col, destination.getRow(), destination.getColumn()};
        return move;
    }

    /**
     * Return pieces of a give color
     * @param color color of pieces we want
     * @return piece list
     */
    private List<Piece> getPieces(Piece.PieceColor color){
        if(color == Piece.PieceColor.WHITE)
            return Board.getWhitePieces();
        else
            return Board.getBlackPieces();
    }


    /**
     * Given a list of moves, gets the move which kills the best piece
     * @param moves list of moves
     * @return Move object that contains the destination and the score
     */
    private Move killBestPieceMove(List<Cell> moves){
        int score = 0;
        Cell tempDestination = null;

        for(Cell destination : moves){
            if(destination.getPiece() == null) continue;

            if(destination.getPiece().getVALUE() > score){
                score = destination.getPiece().getVALUE();
                tempDestination = destination;
            }
        }

        if(score == 0) return null;

        return new Move(tempDestination,score);
    }


}
