package Controller;

import Pieces.Piece;
import com.sun.tools.internal.xjc.generator.util.WhitespaceNormalizer;
import view.GUI;
import Board.*;
import view.Tile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import static view.GUI.*;

public class ViewController implements Serializable {

    private GUI gui;
    private Tile[][] chessBoardSquares;
    private boolean firstClick = true;
    private Cell[][] board;
    private int turn = 0;

    private ArrayList<Tile> canMoveTo = new ArrayList<Tile>();
    private Tile tempTile;

    public ViewController(GUI gui){
        this.gui = gui;
        this.chessBoardSquares = gui.getChessBoardSquares();
        board = Board.getBoard();
        Board.initializeGame();
        initView();
    }

    //todo
    public void initView(){
       
    }

    public void initController(){

        ((JButton) gui.getTools().getComponentAtIndex(0)).addActionListener(e -> setupNewGame());

        for(int i = 0; i < chessBoardSquares.length; i++){
            for(int j=0; j<chessBoardSquares[i].length; j++){

                //Action listener for clicking the tiles
                chessBoardSquares[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //Get the selected square and its attributes
                        Tile selectedSquare = (Tile) e.getSource();
                        int row = selectedSquare.getRow(), col = selectedSquare.getCol();

                        //Check if its the first click or the second click
                        if(firstClick){
                            if(board[row][col].isEmpty()){} //do nothing if clicked an empty spot

                            //get a list of tiles that can be moved to, then set firstClick to false
                            else {
                                canMoveTo = cellsToTiles(board[row][col].getPiece().getLegalMoves());

                                if(!correctPieceClicked(isWhitesTurn(),selectedSquare)){
                                    gui.setMessage2("Not your move!");
                                    return;
                                }

                                checkPiece(selectedSquare);
                                firstClick = false;
                                tempTile = selectedSquare;
                            }
                        } else { //if not first click
                            //See if this tile is contained in the list and move the piece
                            if(canMoveTo.contains(selectedSquare)) {
                                movePiece(tempTile, selectedSquare);

                                turnComplete();

                            } else {
                                System.out.println("Cant move here");
                            }
                            firstClick = true;
                            tempTile = null;
                        }


                    }
                });



            }
        }
    }

    private void turnComplete(){
        turn++;
        if(isWhitesTurn())
            gui.setMessage2("White's Turn");
        else
            gui.setMessage2("Black's Turn");
        checkForCheck();
    }

    private boolean isWhitesTurn(){
        if(turn % 2 == 0)
            return true;
        return false;
    }

    private boolean correctPieceClicked(boolean whiteTurn, Tile tile){
        Piece.PieceColor color = tile.getPiece().getColor();
        if(whiteTurn &&
            color == Piece.PieceColor.WHITE)
            return true;
        if(!whiteTurn &&
                color == Piece.PieceColor.BLACK)
            return true;
        return false;
    }


    private void checkPiece(Tile tile){
        Piece piece = tile.getPiece();
        if(piece == null)
            gui.setMessage("Empty square selected");
        else
            gui.setMessage(piece.toString());
    }

    private void checkForCheck() {

        if(Board.check == true)
            gui.setMessage("Check!");


    }

    private void setupNewGame() {
        gui.setMessage("New Game");
    //    endGame();
        // set up the black pieces
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[0][ii].setIcon(new ImageIcon(
                    GUI.chessPieceImages[BLACK][STARTING_ROW[ii]]));
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[1][ii].setIcon(new ImageIcon(
                    GUI.chessPieceImages[BLACK][PAWN]));
        }
        // set up the white pieces
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[6][ii].setIcon(new ImageIcon(
                    GUI.chessPieceImages[WHITE][PAWN]));
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[7][ii].setIcon(new ImageIcon(
                    GUI.chessPieceImages[WHITE][STARTING_ROW[ii]]));
        }
    //    Board.initPieces();
    }

    /**
     * End the game by setting all icons to null
     * todo: save game data to a file/database
     */
    private void endGame(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                chessBoardSquares[i][j].setIcon(null);
            }
        }
        Board.clearBoard();
    }


    private void movePiece(Tile start, Tile end){
        Icon icon = start.getIcon();
        start.setIcon(null);
        end.setIcon(icon);

        Board.movePiece(start.getRow(), start.getCol(), end.getRow(), end.getCol());
    }

    private void highlight(Tile tile){
        tile.highlight();

    }


    private ArrayList<Tile> cellsToTiles(ArrayList<Cell> legalMoves){
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for(Cell cell : legalMoves){
            tiles.add(chessBoardSquares[cell.getRow()][cell.getColumn()]);
           // System.out.println(cell.getRow() + " " + cell.getColumn());
        }
        return tiles;
    }


}
