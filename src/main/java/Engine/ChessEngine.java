package Engine;

import Board.*;
import Board.Cell;
import Pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChessEngine {

    /**
     * Modify this method to select which AI to use (playEngine1, playEngine2 etc.)
     * @return move coordinates
     */
    public Move play() {
        return playEngine5(2,Piece.PieceColor.BLACK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// FIXES PROBLEMS WITH ENGINE 4 //////////////////////////////
    //////////////////////////// FINALLY MADE A DECENT ENGINE!!!! ////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


    private Move playEngine5(int depth, Piece.PieceColor color){

        //if depth is 0, get the move that kills the best possible piece (or a random move)
        if(depth == 0)
            return playEngine2(color);

        //Get all pieces and legal moves
        List<Piece> pieces = getPieces(color);
        List<Move> moves = getAllLegalMoves(pieces);

        int bestScore = -100;
        List<Move> flaggedMoves = new ArrayList<Move>();

        //loop through all legal moves
        //make move, get score, recursively run method for opponent, subtract the score from that move, undo move
        //flag all the best possible moves in a list
        //return a random move from that list
        for(Move move : moves){

            int currentScore = move.getScore();
            Board.makeMove(move);

            Move oppMove = playEngine5(depth - 1, Piece.getOppositeColor(color));
            currentScore = currentScore - oppMove.getScore();

            if(currentScore == bestScore){
                flaggedMoves.add(move);
            }

            if(currentScore > bestScore){
                flaggedMoves.clear();
                bestScore = currentScore;
                flaggedMoves.add(move);
            }

            Board.undoMove(move);
        }

        return getRandomMove(flaggedMoves);
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////// THIS AI WILL IMPLEMENT A RECURSIVE MINIMAX THEOREM ///////////////////
    /////////// 'MINIMAX' WORKS BY MINIMIZING THE OPPONENTS MAXIMUM RESPONSE MOVE ////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Will loop through all moves. Given opponent's best response of playEngine4(depth-1), it selects its best move
     * @param color
     * @return
     */
    private Move playEngine4(int depth, Piece.PieceColor color){

        //Get all pieces and all moves
        List<Piece> pieces = getPieces(color);
        if(depth == 0)
            return playEngine2(pieces.get(0).getColor());


        List<Move> moves = getAllLegalMoves(pieces);
        int bestScore = 0;
        Move flaggedMove = null;

        //if depth is 0, kill the best piece possible

        //Loop through all moves
        for(Move move : moves){
            //set current score as the score of the move (add value of piece killed)
            int currentScore = move.getScore();
            Board.makeMove(move);

            //Get the opponent's best possible move by recursively running playEngine4 for him
            Move opponentMove = playEngine4(depth-1,pieces.get(0).getEnemyColor());

            //subtract opponent move score from your score (only impacts if piece is killed)
            currentScore = currentScore - opponentMove.getScore();

            //set best score and flag a move if its a good move
            if(currentScore > bestScore){
                flaggedMove = move;
                bestScore = currentScore;
            }
            //undo move
            Board.undoMove(move);
        }

        //Return random move if no move flagged
        if(flaggedMove == null) {

            return playEngine1(color);
        }

        return flaggedMove;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////// THIS AI WILL IMPLEMENT THE MINIMAX THEOREM ///////////////////////
    /////////// 'MINIMAX' WORKS BY MINIMIZING THE OPPONENTS MAXIMUM RESPONSE MOVE ////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Will loop through all moves, Given opponent's best response of Engine2, it selects its best move
     * @param color
     * @return
     */
    private Move playEngine3(Piece.PieceColor color){
        List<Piece> pieces = getPieces(color);

        Move move = implementMM(pieces);

        //Return random move if null
        if(move == null){
            return playEngine1(color);
        }

        return move;
    }

    private Move implementMM(List<Piece> pieces){

        List<Move> moves = getAllLegalMoves(pieces);
        int bestScore = -1;
        Move flaggedMove = null;


        for(Move move : moves){

            int currentScore = move.getScore();
            Board.makeMove(move);

            //Get best move that opponent will do given this move
            //Subtract score of opponents move
            Move oppMove = playEngine2(pieces.get(0).getEnemyColor());
            currentScore = currentScore - oppMove.getScore();

            if(currentScore > bestScore){
                flaggedMove = move;
                bestScore = currentScore;
            }

            Board.undoMove(move);
        }

        return flaggedMove;
    }


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
    private Move playEngine2(Piece.PieceColor color) {
        List<Piece> pieces = getPieces(color);

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
            return playEngine1(color);


        return getMove(pieceToMove,destination);
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
//    private Move playEngine1(Piece.PieceColor color){
//        Piece randomPiece;
//        ArrayList<Cell> moves;
//        do{
//            randomPiece = getRandomPiece(color);
//            moves = randomPiece.getLegalMoves();
//        } while (moves.size() == 0);
//
//        return getMove(randomPiece, getRandomMove(moves));
//    }

    private Move playEngine1(Piece.PieceColor color){
        List<Piece> pieces = getPieces(color);
        List<Move> moves = new ArrayList<Move>();
        for(Piece piece : pieces){
            moves.addAll(piece.getMoves());
        }
        return getRandomMove(moves);
    }

    /**
     * Random move generator
     * @param moves list of moves
     * @return a random move from that list
     */
//    private Cell getRandomMove(ArrayList<Cell> moves){
//        int randNum = (int) (Math.random() * moves.size());
//        return moves.get(randNum);
//    }
    private Move getRandomMove(List<Move> moves){
        int randNum = (int) (Math.random() * moves.size());
        return moves.get(randNum);
    }

    /**
     * Get a random piece (black)
     * @return a random piece from piece list
     */
    private Piece getRandomPiece(Piece.PieceColor color){
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
    private Move getMove(Piece piece, Cell destination){
        int row = piece.getRow(), col = piece.getColumn();
        Cell[][] board = Board.getBoard();
        return new Move(board[row][col],board[destination.getRow()][destination.getColumn()]);
    }

    /**
     * Gets all legal moves from all pieces
     * @param pieces list of pieces
     * @return list of Moves
     */
    private List<Move> getAllLegalMoves(List<Piece> pieces){
        List<Move> moves = new LinkedList<Move>();

        for(Piece piece : pieces){
            moves.addAll(piece.getMoves());
        }

        return moves;
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
     * Converts move coordinates to move object
     * @param move
     * @return
     */
    private static Move intArrToMove(int[] move){
        int startX = move[0], startY = move[1], endX = move[2], endY = move[3];
        Cell[][] board = Board.getBoard();

        return new Move(board[startX][startY],board[endX][endY]);
    }


    private static int[] moveToInt(Move move){
        return new int[]{move.getStart().getRow(),move.getStart().getColumn(), move.getEnd().getRow(), move.getEnd().getColumn()};
    }




}
