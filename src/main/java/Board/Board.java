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
    public static boolean check = false;
    public static boolean checkmate = false;

    public static boolean whiteCanCastle = true;
    public static boolean blackCanCastle = true;

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

        return temp.getColor() == color;

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
    private static void initPieces(){
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
    private static Piece getKing(Piece.PieceColor color) {
        if(color == Piece.PieceColor.WHITE){
            return whiteKing;
        }
        else{
            return blackKing;
        }
    }

    /**
     * Checks if the king of the input color is in check
     * @param color of the king we want to use
     * @return if the king is in check
     */
    public static boolean kingInCheck(Piece.PieceColor color) {
        Piece king = getKing(color);



        //loop through all enemy pieces, checking if they're threatening the enemy king
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
    public static Piece simpleMove(Cell start, Cell end){
        Piece movingPiece = start.removePiece();
        Piece deadPiece = end.replacePiece(movingPiece);
        killPiece(deadPiece);
        movingPiece.setNewLocation(end);

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
        addPiece(killedPiece);
        movingPiece.setNewLocation(start);
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
     */
    public static void movePiece(int sRow, int sCol, int eRow, int eCol){

        Cell startCell = board[sRow][sCol];
        Cell endCell = board[eRow][eCol];

        //Get the piece at the starting point, return false otherwise
        Piece piece = startCell.getPiece();
        if(piece == null) {
            System.out.println("No piece there");
            return;
        }


        //Get a list of all legal moves for that piece
        //Return false if
        //todo: possibly redundant if section
        ArrayList<Cell> possibleMoves = piece.getLegalMoves();
        if(!possibleMoves.contains(endCell)) {
            System.out.println("cant move to that spot");
        }
        else {



            simpleMove(startCell, endCell);
            if(!piece.getHasMoved())
                piece.changeHasMoved();

            piece.setNewLocation(endCell);

            //if we are moving out of check, change flag
            if(check)
                check = false;

            //if this piece is now threatening enemy king, flag check and look for checkmate
            if(piece.isThreateningEnemyKing(getKing(piece.getEnemyColor()))) {
                check = true;
                isCheckmate(piece.getEnemyColor());
            }
        }
    }

    /**
     * Make a move and return the piece killed
     * Only for evaluation by ChessEngine
     * @param move the move made
     */
    public static void makeMove(Move move){
        Cell start = move.getStart();
        Cell end = move.getEnd();
        simpleMove(start,end);
    }

    /**
     * Undo makeMove
     * Only for evaluation
     * @param move the mode undone
     */
    public static void undoMove(Move move){
        undoMove(move.getStart(),move.getEnd(),move.getPieceCaptured());
    }


    /**
     * Checks if a given King/Rook pair can castle
     * In order to castle, neither piece must have moved and none of the spaces from king - rook can be threatened
     * @param king
     * @param rook
     * @return if castling is legal
     */
    //TODO: Complete function by changing class variables
    public static boolean canCastle(King king, Rook rook){
        if(king.getHasMoved() || rook.getHasMoved()) {
            if(king.getColor() == Piece.PieceColor.WHITE)
                whiteCanCastle = false;
            else
                blackCanCastle = false;
            return false;
        }

        //todo/ blegh
        List<Piece> pieces;
        if(king.getColor() == Piece.PieceColor.WHITE)
            pieces = blackPieces;
        else
            pieces = whitePieces;


        ArrayList<Cell> interCells = cellsBetweenPieces(king, rook);

        //Loop through all enemy pieces and get PL moves
        //Loop through all cells in between rook adn king
        //Return false if any of them are threatened
        for(Piece piece : pieces){
            List<Cell> plMoves = piece.getPseudoLegalMoves();
            for(int i=0; i<interCells.size(); i++){
                if(plMoves.contains(interCells.get(i))){
                    return false;
                }
            }
        }

        return true;
    }

    private static ArrayList<Cell> cellsBetweenPieces(Piece p1, Piece p2){
        ArrayList<Cell> interCells = new ArrayList<Cell>();

        int row = p1.getRow(); int col = p1.getRow();

        //If we are castling king side, then the king's column will be less than the rook's column
        int kingSide;
        if(p1.getColumn() < p2.getColumn())
            kingSide = 1;
        else
            kingSide = -1;


        //Get the absolute difference of piece columns
        int columnDifference = Math.abs(p1.getColumn() - p2.getColumn());


        for(int i=0; i<columnDifference; i++){
            int tempColumn = kingSide * col * i;
            interCells.add(board[row][tempColumn]);
        }

        return interCells;
    }

    /**
     * Remove the killed piece from the list of pieces
     * @param piece the piece being killed
     */
    private static void killPiece(Piece piece){
        if(piece == null) return;
        if(piece.getColor() == Piece.PieceColor.WHITE){
            whitePieces.remove(piece);
        } else {
            blackPieces.remove(piece);
        }
    }

    /**
     * Replace a piece in the list. Mainly for undoing kills
     * @param piece the piece being added to the list
     */
    private static void addPiece(Piece piece){
        if(piece == null) return;
        if(piece.getColor() == Piece.PieceColor.WHITE){
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }

    //Check if input color has been checkmated
    //if any piece has at least 1 legal move, game over
    //maybe only call this method if check has been declared to avoid unnecessary calls
    private static boolean isCheckmate(Piece.PieceColor color) {
        List<Piece> friendlyPieces = whitePieces;
        if(color == Piece.PieceColor.BLACK)
            friendlyPieces = blackPieces;

        //Loop through all friendly pieces
        //if any of them has at least 1 legal move, return false;
        for(Piece piece : friendlyPieces){
            if(piece.getLegalMoves().size() > 0)
                return false;
        }
        checkmate = true;

        return true;
    }

    public static void initializeGame(){
        Piece[] pieces = new Piece[32];

        for(int i=0; i<8; i++) {
            pieces[i] = new Pawn(6,i,Piece.PieceColor.WHITE); //White
            pieces[i+16] = new Pawn(1,i,Piece.PieceColor.BLACK); //Black
        }

        pieces[8] = new Knight(7,1,Piece.PieceColor.WHITE); //White Knights
        pieces[9] = new Knight(7,6,Piece.PieceColor.WHITE);
        pieces[10] = new Bishop(7,2,Piece.PieceColor.WHITE); //White Bishops
        pieces[11] = new Bishop(7,5,Piece.PieceColor.WHITE);
        pieces[12] = new Rook(7,0,Piece.PieceColor.WHITE); //White Rooks
        pieces[13] = new Rook(7,7,Piece.PieceColor.WHITE);
        pieces[14] = new Queen(7,3,Piece.PieceColor.WHITE); //White Queen
        pieces[15] = new King(7,4,Piece.PieceColor.WHITE); //White King

        pieces[24] = new Knight(0,1,Piece.PieceColor.BLACK); //Black Knights
        pieces[25] = new Knight(0,6,Piece.PieceColor.BLACK);
        pieces[26] = new Bishop(0,2,Piece.PieceColor.BLACK); //Black Bishops
        pieces[27] = new Bishop(0,5,Piece.PieceColor.BLACK);
        pieces[28] = new Rook(0,0,Piece.PieceColor.BLACK); //Black Rooks
        pieces[29] = new Rook(0,7,Piece.PieceColor.BLACK);
        pieces[30] = new Queen(0,3,Piece.PieceColor.BLACK); //Black Queen
        pieces[31] = new King(0,4,Piece.PieceColor.BLACK); //Black King


        Board.setBoard(pieces);
        //print(board);

    }

    public static void clearBoard(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                board[i][j] = new Cell(i,j);
            }
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
     * @return the board in string form
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
