package Controller;

import Pieces.Piece;

import view.GUI;
import Board.*;
import view.Tile;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static view.GUI.*;

public class ViewController implements Serializable {

    /**
     * private fields
     */
    private GUI gui;
    private Tile[][] chessBoardSquares;
    private boolean firstClick = true;
    private Cell[][] board;
    private int turn = 0;

    private ArrayList<Tile> movementTiles = new ArrayList<>();
    private Tile tempTile;

    private boolean singlePlayer;

    /**
     * Constructor that intializes the fields, the view, and the model
     * @param gui gui
     */
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

    /**
     * Does black's turn, getting a move from the engine and making the move
     */
    private void doComputerTurn(Piece.PieceColor color) {
        //Get the move coordinates
        Move move = EngineController.move(color);

        //int startX = move[0], startY = move[1], endX = move[2], endY = move[3];
        int startX = move.getStart().getRow(), startY = move.getStart().getColumn();
        int endX = move.getEnd().getRow(), endY = move.getEnd().getColumn();

        //Move the tile
        Tile start = chessBoardSquares[startX][startY];
        Tile end = chessBoardSquares[endX][endY];
        movePiece(start,end);

        //highlight the tiles moved
        movementTiles.add(start); movementTiles.add(end);
        highlight(movementTiles);

        //move the board
        Board.movePiece(startX, startY, endX, endY);

        //end the turn
        turnComplete(end);
        unhighlight(movementTiles);

    }

    /**
     * Initializes the controller
     * Adds an action listener to each tile
     * Implements game logic (i.e. white and black do turn sequentially
     */
    public void initController(){

        ((JButton) gui.getTools().getComponentAtIndex(0)).addActionListener(e -> setupSinglePlayer());
        ((JButton) gui.getTools().getComponentAtIndex(1)).addActionListener(e -> setupMultiPlayer());

        for(int i = 0; i < chessBoardSquares.length; i++){
            for(int j=0; j<chessBoardSquares[i].length; j++){

                //Action listener for clicking the tiles
                chessBoardSquares[i][j].addActionListener(e -> {

                    //Get the selected square and its attributes
                    Tile selectedSquare = (Tile) e.getSource();
                    int row = selectedSquare.getRow(), col = selectedSquare.getCol();

                    if(singlePlayer)
                        singlePlayerAction(row,col,selectedSquare);
                    else
                        multiPlayerAction(row,col,selectedSquare);
                });
            }
        }
    }


    private void setupSinglePlayer(){
        singlePlayer = true;
        setupNewGame();
    }

    private void setupMultiPlayer(){
        singlePlayer = false;
        setupNewGame();
    }

    private void singlePlayerAction(int row, int col, Tile selectedSquare){
        //Check if its the first click or the second click
        if(firstClick){
            //unhighlight(movementTiles);
            gui.setMessage2("");

            if(board[row][col].isEmpty()){} //do nothing if clicked an empty spot

            //get a list of tiles that can be moved to, then set firstClick to false
            else {
                movementTiles = cellsToTiles(board[row][col].getPiece().getLegalMoves());

                if(!correctPieceClicked(isWhitesTurn(),selectedSquare)){
                    gui.setMessage2("Not your move!");
                    return;
                }

                checkPiece(selectedSquare);

                highlight(movementTiles);

                firstClick = false;
                tempTile = selectedSquare;
            }
        } else { //if not first click
            //See if this tile is contained in the list and move the piece
            if(movementTiles.contains(selectedSquare)) {
                movePiece(tempTile, selectedSquare);

                //and then do black's turn
                turnComplete(selectedSquare);
                doComputerTurn(Piece.PieceColor.BLACK);

            } else {
                gui.setMessage2("Cant move here");
            }

            unhighlight(movementTiles);

            firstClick = true;
            tempTile = null;
        }
    }

    private void multiPlayerAction(int row, int col, Tile selectedSquare){
        //Check if its the first click or the second
        if(firstClick){
            gui.setMessage2("");
            if(board[row][col].isEmpty()){} //do nothing

            //get a list of tiles that can be moved to, highlight them and set firstClick to false
            else {
                movementTiles = cellsToTiles(board[row][col].getPiece().getLegalMoves());

                if(!correctPieceClicked(isWhitesTurn(),selectedSquare)){
                    gui.setMessage2("Not your move");
                    return;
                }

                checkPiece(selectedSquare);
                highlight(movementTiles);
                firstClick = false;
                tempTile = selectedSquare;
            }
        } else {

            if(movementTiles.contains(selectedSquare)) {
                movePiece(tempTile,selectedSquare);

                turnComplete(selectedSquare);
            } else {
                gui.setMessage2("Cant move here");
            }

            unhighlight(movementTiles);

            firstClick = true;
            tempTile = null;
        }
    }

    /**
     * Ends the turn by displaying a message
     */
    private void turnComplete(Tile tile){
        promotePiece(tile);

        turn++;
        if(isWhitesTurn())
            gui.setMessage2("White's Turn");
        else
            gui.setMessage2("Black's Turn");
        checkForCheck();
    }

    private boolean canPromote(){
        return Board.hasPromoteablePiece;
    }

    private void promotePiece(Tile tile){
        if(!canPromote())
            return;
        if(tile.getRow() == 7)
            tile.setIcon(new ImageIcon(
                    GUI.chessPieceImages[BLACK][STARTING_ROW[3]]));
        else if (tile.getRow() == 0)
            tile.setIcon(new ImageIcon(
                    GUI.chessPieceImages[WHITE][STARTING_ROW[3]]));
    }

    /**
     * Checks which side's turn
     * @return true if white's turn
     */
    private boolean isWhitesTurn(){
        return turn % 2 == 0;
    }

    /**
     * Confirms whether the correct side's piece was selected
     * Should be white clicked and white's turn OR !whiteClicked and !white's turn
     * @param whiteTurn if its white's turn
     * @param tile the tile selected
     * @return whether the tile has a piece of appropriate color
     */
    private boolean correctPieceClicked(boolean whiteTurn, Tile tile){
        Piece.PieceColor color = tile.getPiece().getColor();
        if(whiteTurn &&
            color == Piece.PieceColor.WHITE)
            return true;

        return !whiteTurn &&
                color == Piece.PieceColor.BLACK;
    }

    /**
     * Looks at the piece on the tile
     * @param tile the tile selected
     */
    private void checkPiece(Tile tile){
        Piece piece = tile.getPiece();
        if(piece == null)
            gui.setMessage("Empty square selected");
        else
            gui.setMessage(piece.toString());
    }

    /**
     * Looks at board to see if a team is under check
     */
    private void checkForCheck() {
        if(Board.check)
            gui.setMessage3("Check!");
        if(Board.checkmate){
            gui.setMessage3("Checkmate");
            gui.gameOver(turn %2);
        }
    }

    /**
     * Set up a new game by putting all the pieces on the view
     */
    private void setupNewGame() {
        gui.setMessage("New Game");
        endGame();
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
        Board.initializeGame();
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


    /**
     * Move a piece icon from one tile to another
     * @param start starting tile
     * @param end ending tile
     */
    private void movePiece(Tile start, Tile end){
        Icon icon = start.getIcon();
        start.setIcon(null);
        end.setIcon(icon);

        Board.movePiece(start.getRow(), start.getCol(), end.getRow(), end.getCol());
//        if(Board.hasPromoteablePiece){
//            promotePiece(end);
//        }
    }


    /**
     * Color a particular tile
     * //todo: appears to be useless method. Figure out a use for it
     * @param tile the tile we want to highlight
     */
    private void highlight(Tile tile){
        tile.highlight();
    }

    /**
     * Color a list of tiles
     * @param tiles
     */
    private void highlight(List<Tile> tiles){
        for(Tile tile : tiles){
            highlight(tile);
        }
    }

    private void unhighlight(List<Tile> tiles){
        for(Tile tile : tiles){
            tile.unhighlight();
        }
    }


    /**
     * Converts a List of legal moves in Cell form to Tile objects
     * @param legalMoves list of cell objects
     * @return list of legal moves in tile format
     */
    private ArrayList<Tile> cellsToTiles(ArrayList<Cell> legalMoves){
        ArrayList<Tile> tiles = new ArrayList<>();
        for(Cell cell : legalMoves){
            tiles.add(chessBoardSquares[cell.getRow()][cell.getColumn()]);
        }
        return tiles;
    }

    private Tile cellToTile(Cell cell){
        return chessBoardSquares[cell.getRow()][cell.getColumn()];
    }

}
