package Pieces;

import Board.*;
import Board.Cell;

import java.util.ArrayList;

import static Pieces.Piece.PieceColor.WHITE;

/**
 * Abstract class for the Piece object
 * Contains methods (some abstract) for using the given Piece
 * Movement works by first generating a list of pseudoLegal Moves (Moves that do not see if the king will be under check)
 * then looping through each of those moves to see which are strictly legal
 */
public abstract class Piece {

    /**
     * The piece color is WHITE or BLACK
     */
    public enum PieceColor {
        WHITE,BLACK
    }

    /**
     * The 6 possible types of a piece
     */
    public enum PieceType {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
    }

    /**
     * Private fields regarding the piece
     */
    private int row, column;
    private PieceType type;
    private PieceColor color;
    boolean hasMoved;
    private int VALUE;

    /**
     * Constructor for creating a piece
     * @param row starting row
     * @param column starting column
     * @param color the color
     * @param type the type
     */
    public Piece(int row, int column, PieceColor color, PieceType type, int value){
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;
        hasMoved = false;
        VALUE = value;
    }

    /**
     * Getter for row
     * @return the current row
     */
    public int getRow() {
        return row;
    }

    /**
     * getter for col
     * @return the curent col
     */
    public int getColumn() {
        return column;
    }

    /**
     * getter for colo r
     * @return the color
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * Getter for piece type
     * @return the type
     */
    public PieceType getType() { return type; }

    /**
     * Get the piece's location in Cell form
     * @return the Cell
     */
    private Cell getLocation() {
        return Board.getBoard()[row][column];
    }

    /**
     * @return if the piece has moved
     */
    public boolean getHasMoved() { return hasMoved; }

    /**
     * Change it to true once its moved, no way to change it to false;
     */
    public void changeHasMoved() { hasMoved = true; }

    /**
     * Set the pieces location to the input cell
     * @param location the cell we want to set this piece at
     */
    public void setNewLocation(Cell location) {
        this.row = location.getRow();
        this.column = location.getColumn();
    }

    /**
     * implemented by all sub classes
     * @return List of all cells that can be moved to
     */
    public abstract ArrayList<Cell> getPseudoLegalMoves();

    /**
     * checks if this piece is threatening the enemy king
     * @param enemyKing the king we are checking
     * @return true if threatening king
     */
    public boolean isThreateningEnemyKing(Piece enemyKing){
        Cell[][] board = Board.getBoard();
        Cell kingLocation = board[enemyKing.getRow()][enemyKing.getColumn()];


        return this.getPseudoLegalMoves().contains(kingLocation);

    }

    //Given a list of pseudo-legal moves, check if they leave the king in check
    //Return a list of strictly legal moves

    /**
     * Converts pseudoLegalMoves into legal moves by checking if they leave king in check
     * @return list of legal moves
     */
    public ArrayList<Cell> getLegalMoves() {
        ArrayList<Cell> pseudoLegalMoves = this.getPseudoLegalMoves();
        Cell[][] board = Board.getBoard();


        Cell start = board[this.getRow()][this.getColumn()];
        ArrayList<Cell> legalMoves = new ArrayList<Cell>();


        //Loop through all pseudolegal moves
        //Make each move, check if it leaves the king in check, then undo it
        //if a piece was killed during the move, revert that too
        for (Cell destination : pseudoLegalMoves) {
            Piece killedPiece = Board.simpleMove(start, destination);



            if (!Board.kingInCheck(this.getColor())) {
                legalMoves.add(destination);
            }

            Board.undoMove(start, destination, killedPiece);
        }

        return legalMoves;
    }

    /**
     * Converts legalMoves from Cell objects to Move objects
     * @return
     */
    public ArrayList<Move> getMoves() {
        ArrayList<Cell> legalMoves = getLegalMoves();
        ArrayList<Move> moves = new ArrayList<Move>();

        for(Cell cell : legalMoves){
            moves.add(new Move(getLocation(),cell));
        }

        return moves;
    }


    /**
     * Checks the movement of Sliding pieces (Queen, Bishop, Rook), using an offset multiplier to indicate directions
     * @param row peice's location
     * @param column pieces lcation
     * @param color pieces color
     * @param offsetMultiplier array indicating directions the piece can move
     * @return list of all (pseudolegal) moves for sliding pieces
     */
    public static ArrayList<Cell> slidingPieceMoves(int row, int column, PieceColor color, int[][] offsetMultiplier){
        Cell[][] board = Board.getBoard();
        ArrayList<Cell> moves = new ArrayList<Cell>();
        PieceColor enemyColor;

        if(color == WHITE)
            enemyColor = PieceColor.BLACK;
        else
            enemyColor = PieceColor.WHITE;


        //Loop in all 4 directions
        for(int i=0; i<offsetMultiplier.length; i++){
            int j= 1;
            int offsetX = offsetMultiplier[i][0];
            int offsetY = offsetMultiplier[i][1];


            //Keep looping while (x,y) + offset is part of board
            while(row + offsetX >= 0 && row + offsetX < board.length
                    && column + offsetY >= 0 && column + offsetY < board.length) {

                //If the cell is empty, add the move to the list and continue
                if (Board.isCellEmpty(row + offsetX, column + offsetY)) {
                    moves.add(board[row + offsetX][column + offsetY]);
                }

                //If the cell contains an enemy piece, add move (attack) to the list and break
                if (Board.containsPieceOfColor(row + offsetX, column + offsetY, enemyColor)) {
                    moves.add(board[row + offsetX][column + offsetY]);
                    break;
                }

                //If the cell contains a friendly piece, break
                if (Board.containsPieceOfColor(row + offsetX, column + offsetY, color)) {
                    break;
                }

                j++;
                offsetX = j * offsetMultiplier[i][0];
                offsetY = j * offsetMultiplier[i][1];

            }
        }
        return moves;
    }

    /**
     * Promotes a pawn to the input type
     * @param type
     */
    void promotePiece(PieceType type){
        this.type = type;
    }

    /**
     * Return opposite color, used for efficiency
     * @return the color of the enemy piece
     */
    public PieceColor getEnemyColor(){
        if(this.color == WHITE)
            return PieceColor.BLACK;
        return WHITE;
    }

    public int getVALUE() {
        return VALUE;
    }

    /**
     * toString method for console testing
     * @return First letter of piece type and first letter of piece color (PW, BR etc)
     */
    public String toString(){
        return "" + this.color.toString().toLowerCase() + " " +this.type.toString().toLowerCase();// + " " + this.getRow() + " " + this.getColumn();
        //return "" + this.color.toString().charAt(0) + this.type.toString().charAt(0);
    }


}
