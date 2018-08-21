package Controller;

import view.GUI;
import Board.*;
import view.Tile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class ViewController implements Serializable {

    private GUI gui;
    private Tile[][] chessBoardSquares;
    private boolean firstClick = true;
    private Cell[][] board;

    private ArrayList<Tile> canMoveTo = new ArrayList<Tile>();
    private Tile tempTile;

    public ViewController(GUI gui){
        this.gui = gui;
        this.chessBoardSquares = gui.getChessBoardSquares();
        board = Board.getBoard();
        Board.initializeGame();
        initView();
    }

    public void initView(){
        for(int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
           //     chessBoardSquares[i][j] = board[i][j];
            }
        }
    }

    public void initController(){


        for(int i = 0; i < chessBoardSquares.length; i++){
            for(int j=0; j<chessBoardSquares[i].length; j++){


                //Action listener for clicking the tiles
                chessBoardSquares[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //Get the selected square and its attributes
                        Tile selectedSquare = (Tile) e.getSource();
                        System.out.println("selected" + selectedSquare);

                        int row = selectedSquare.getRow(), col = selectedSquare.getCol();

                        //Check if its the first click or the second click
                        if(firstClick){
                            if(board[row][col].isEmpty()){} //do nothing if clicked an empty spot

                            //get a list of tiles that can be moved to, then set firstClick to false
                            else {
                                canMoveTo = cellsToTiles(board[row][col].getPiece().getLegalMoves());
                                System.out.println(canMoveTo);
                                firstClick = false;
                                tempTile = selectedSquare;
                            }
                        } else { //if not first click
                            //See if this tile is contained in the list and move the piece
                            if(canMoveTo.contains(selectedSquare)){
                                System.out.println("Can move here");
                                movePiece(tempTile,selectedSquare);



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
