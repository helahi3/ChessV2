package Pieces;

import Board.Board;
import Board.Cell;
import Board.Move;

import java.util.ArrayList;

import static Pieces.Piece.PieceColor.WHITE;

public abstract class Piece {

    public enum PieceColor {
        WHITE,BLACK;
    }

    public enum PieceType {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;
    }

    private int row, column;
    private PieceType type;
    private PieceColor color;
    boolean hasMoved;

    public Piece(int row, int column, PieceColor color, PieceType type){
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;
        hasMoved = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {

        return type;
    }

    public abstract ArrayList<Cell> getPseudoLegalMoves();



    public boolean isThreateningEnemyKing(Piece enemyKing){
        Cell[][] board = Board.getBoard();
        Cell kingLocation = board[enemyKing.getRow()][enemyKing.getColumn()];


        if(this.getType() == PieceType.KING)
            return false;

        if(this.getPseudoLegalMoves().contains(kingLocation))
            return true;

        return false;
    }

    //Given a list of pseudo-legal moves, check if they leave the king in check
    //Return a list of strictly legal moves
    public ArrayList<Cell> getLegalMoves(ArrayList<Cell> pseudoLegalMoves) {
        Cell[][] board = Board.getBoard();

        Cell start = board[this.getRow()][this.getColumn()];
        ArrayList<Cell> legalMoves = new ArrayList<Cell>();


        //Loop through all pseudolegal moves
        //Make each move, check if it leaves the king in check, then undo it
        //if a piece was killed during the move, revert that too
        for (Cell destination : pseudoLegalMoves) {
            Piece killedPiece = Board.movePiece(start, destination);

            if (!Board.kingInCheck(this.getColor())) {
                legalMoves.add(destination);
            }
            if (killedPiece != null)
                Board.undoMove(start, destination, killedPiece);
        }

        return legalMoves;
    }


    //Works for Rook, Bishop and Queen
    public static ArrayList<Cell> slidingPieceMoves(int row, int column, PieceColor color, int[][] offsetMultiplier){
        Cell[][] board = Board.getBoard();
        ArrayList<Cell> moves = new ArrayList<Cell>();
        int x = row; int y= column;
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
            while(x + offsetX > 0 && x + offsetX < board.length
                    && y + offsetY > 0 && y + offsetY < board.length) {

                //If the cell is empty, add the move to the list and continue
                if (Board.isCellEmpty(x + offsetX, y + offsetY)) {
                    moves.add(board[x + offsetX][y + offsetY]);
                }

                //If the cell contains an enemy piece, add move (attack) to the list and break
                if (Board.containsPieceOfColor(x + offsetX, y + offsetY, enemyColor)) {
                    moves.add(board[x + offsetX][y + offsetY]);
                    break;
                }

                //If the cell contains a friendly piece, break
                if (Board.containsPieceOfColor(x + offsetX, y + offsetY, color)) {
                    break;
                }

                j++;
                offsetX = j * offsetMultiplier[i][0];
                offsetY = j * offsetMultiplier[i][1];

            }
        }
        return moves;
    }

    public PieceColor getEnemyColor(){
        if(this.color == WHITE)
            return PieceColor.BLACK;
        return WHITE;
    }

    public String toString(){
        return "" + this.type.toString().charAt(0) + this.color.toString().charAt(0);
    }

}
