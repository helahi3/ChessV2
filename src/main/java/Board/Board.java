package Board;

import Pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static Cell[][] board;
    private static List<Piece> whitePieces = new ArrayList<Piece>();
    private static List<Piece> blackPieces = new ArrayList<Piece>();
    private static Piece whiteKing;
    private static Piece blackKing;


    public static Cell[][] getBoard(){

        if(board == null)
            initBoard();
        return board;

    }

    private static void initBoard(){

        board = new Cell[8][8];
        for(int i = 0; i< board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    //Gets the Cell from row and column, returns true if empty
    public static boolean isCellEmpty(int row, int col){
        Cell temp = board[row][col];
        return temp.isEmpty();
    }

    //Checks if a cell has a piece of input color
    public static boolean containsPieceOfColor(int row, int col, Piece.PieceColor color){

        System.out.println(row + " " + col);

        Piece temp = board[row][col].getPiece();
        if(temp == null)
            return false;
        if(temp.getColor() == color)
            return true;

        return false;
    }

    public static List<Piece> getWhitePieces(){
        if(whitePieces == null)
            initPieces();
        return whitePieces;
    }

    public static List<Piece> getBlackPieces(){
        if(blackPieces == null)
            initPieces();
        return blackPieces;
    }

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

    //Returns a given color's king
    public static Piece getKing(Piece.PieceColor color) {
        if(color == Piece.PieceColor.WHITE)
            return whiteKing;
        else
            return blackKing;
    }

    //Checks if the given color's king is in check
    //returns true if in check
    public static boolean kingInCheck(Piece.PieceColor color) {
        Piece king = getKing(color);

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

    //Moves a piece from start to end
    //if it kills a piece, returns the dead piece
    public static Piece movePiece(Cell start, Cell end){
        Piece movingPiece = start.removePiece();
        Piece deadPiece = end.replacePiece(movingPiece);

        return deadPiece;
    }

    //Reverts a move by moving a piece back to its original location
    //If a piece was killed, it is placed back
    public static void undoMove(Cell start, Cell end, Piece killedPiece){
        Piece movingPiece = end.getPiece();
        start.setPiece(movingPiece);

        end.replacePiece(killedPiece);
    }

    //given an array of pieces, sets the board at their given locations
    public static void setBoard(Piece[] pieces){
        for(Piece piece : pieces){
            board[piece.getRow()][piece.getColumn()].setPiece(piece);
        }
        initPieces();
    }

    //input is starting row and column
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

    public static void main(String[] args){
        initBoard();
        String result = "";
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                result += board[i][j];
            }
            result += "\n";
        }
        System.out.println(result);//result;
    }





}
