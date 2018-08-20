package Board;

import Pieces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The board class
 * Contains all static methods related to the board
 * the board is represented as a 2D array of Cell objects
 */
public class Board {

    /**
     * The main attributes of the Board class (Singleton Design Pattern)
     * Maintains a reference to all white and black pieces, and the 2 kings
     */
    private static Cell[][] board;
    private static List<Piece> whitePieces = new ArrayList<Piece>();
    private static List<Piece> blackPieces = new ArrayList<Piece>();
    private static Piece whiteKing;
    private static Piece blackKing;


    /**
     * Checks if the board has been initialized, then inits and returns it
     * @return the board
     */
    public static Cell[][] getBoard(){

        if(board == null)
            initBoard();
        return board;

    }

    /**
     * Initializes the board as a 2D Cell array, initializing each cell as an empty Cell
     */
    private static void initBoard(){

        board = new Cell[8][8];
        for(int i = 0; i< board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Checks if the cell at the given row/column is empty
     * @param row row of the cell
     * @param col column of the cell
     * @return whether the cell is empty
     */
    public static boolean isCellEmpty(int row, int col){
        Cell temp = board[row][col];
        return temp.isEmpty();
    }


    /**
     * Checks if the cell at the given row/column contains a piece of a given color
     * @param row row of the cell
     * @param col column of the cell
     * @param color the color of the piece we are checking
     * @return whether the cell contains a piece of the input color
     */
    public static boolean containsPieceOfColor(int row, int col, Piece.PieceColor color){


        //Check if the cell contains a piece, then check its color
        Piece temp = board[row][col].getPiece();
        if(temp == null)
            return false;
        if(temp.getColor() == color)
            return true;

        return false;
    }

    /**
     * Get the white pieces
     * @return list of white pieces
     */
    public static List<Piece> getWhitePieces(){
        if(whitePieces == null)
            initPieces();
        return whitePieces;
    }

    /**
     * Get the black pieces
     * @return list of black pieces
     */
    public static List<Piece> getBlackPieces(){
        if(blackPieces == null)
            initPieces();
        return blackPieces;
    }

    /**
     * Initialize the pieces in the respective list of pieces
     * Marks a reference to the 2 kings
     */
    public static void initPieces(){
        for(int i=0; i< board.length; i++){
            for(int j=0; j<board[i].length; j++){

                Piece temp = board[i][j].getPiece();

                if(temp == null)
                    continue;

                if(temp.getColor() == Piece.PieceColor.WHITE) {
                    whitePieces.add(temp);
                    if(temp.getType() == Piece.PieceType.KING)
                        whiteKing = temp;
                }

                else if(temp.getColor() == Piece.PieceColor.BLACK) {
                    blackPieces.add(temp);
                    if(temp.getType() == Piece.PieceType.KING)
                        blackKing = temp;

                }
            }
        }
    }


    /**
     * Gets the king of the input color
     * @param color of the king we want
     * @return the relevant king
     */
    public static Piece getKing(Piece.PieceColor color) {
        if(color == Piece.PieceColor.WHITE)
            return whiteKing;
        else
            return blackKing;
    }

    /**
     * Checks if the king of the input color is in check
     * @param color of the king we want to use
     * @return if the king is in check
     */
    public static boolean kingInCheck(Piece.PieceColor color) {
        Piece king = getKing(color);

        //loop through all enemy pieces, checking if they're threatening the enemy king
        //todo: Only loop through pieces with sliding moves? Maybe add isInCheckFlag for each piece?
        if(color == Piece.PieceColor.WHITE){
            for(Piece piece : blackPieces){
                if(piece.isThreateningEnemyKing(king))
                    return true;
            }
        } else {
            for(Piece piece : whitePieces) {
                if (piece.isThreateningEnemyKing(king))
                    return true;
            }
        }
        return false;
    }

    /**
     * Move a piece from start to end
     * @param start the starting cell
     * @param end the end destination
     * @return the piece killed (returns null if no piece killed)
     */
    public static Piece movePiece(Cell start, Cell end){
        Piece movingPiece = start.removePiece();
        Piece deadPiece = end.replacePiece(movingPiece);

        return deadPiece;
    }

    /**
     * Undo a move, and revert a killed piece (if any)
     * @param start the original starting spot
     * @param end the current location
     * @param killedPiece the piece that was killed during the move (if any)
     */
    public static void undoMove(Cell start, Cell end, Piece killedPiece){
        Piece movingPiece = end.getPiece();
        start.setPiece(movingPiece);


        end.replacePiece(killedPiece);
    }

    /**
     * Takes all pieces from the input array, setting them at their input locations on the board
     * @param pieces the pieces we want to place on the board
     */
    public static void setBoard(Piece[] pieces){
        for(Piece piece : pieces){
            board[piece.getRow()][piece.getColumn()].setPiece(piece);
        }
        initPieces();
    }

    /**
     * Move a piece given its starting and ending coordinates
     * @param sRow starting row
     * @param sCol starting column
     * @param eRow ending row
     * @param eCol ending column
     * @return if the piece was moved
     */
    public static boolean movePiece(int sRow, int sCol, int eRow, int eCol){

        Cell startCell = board[sRow][sCol];
        Cell endCell = board[eRow][eCol];

        //Get the piece at the starting point, return false otherwise
        Piece piece = startCell.getPiece();
        if(piece == null) {
            System.out.println("No piece there");
            return false;
        }

        //Get a list of all legal moves for that piece
        //Return false if
        ArrayList<Cell> possibleMoves = piece.getLegalMoves(piece.getPseudoLegalMoves());
        if(!possibleMoves.contains(endCell)) {
            System.out.println("cant move to that spot");
            return false;
        }
        else {
            movePiece(startCell, endCell);
            if(piece.getHasMoved() == false)
                piece.changeHasMoved();

            piece.setNewLocation(endCell);

            return true;
        }
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


    /**
     * toString method for testing purposes
     * @return
     */
    public String toString(){
        String result = "";
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                result += board[i][j];
            }
            result += "\n";
        }
        return result;
    }




}
