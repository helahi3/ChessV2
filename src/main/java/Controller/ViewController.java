package Controller;

import view.App;
import Board.*;
import Pieces.*;
import view.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class ViewController implements Serializable {

    private App gui;
    private Tile[][] chessBoardSquares;
    private boolean firstClick = true;
    private Cell[][] board = Board.getBoard();

    public ViewController(App gui){
        this.gui = gui;
        this.chessBoardSquares = gui.getChessBoardSquares();
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


        chessBoardSquares[0][0].addActionListener(e -> highlight());

        for(int i = 0; i < chessBoardSquares.length; i++){
            for(int j=0; j<chessBoardSquares[i].length; j++){


                chessBoardSquares[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Tile selectedSquare = (Tile) e.getSource();
                        int row = selectedSquare.getRow(), col = selectedSquare.getCol();
                        if(firstClick){
                            ArrayList<Cell> highlightedCells = board[row][col].highlightAttackableCells();
                            for(Cell cell : highlightedCells){
                                chessBoardSquares[cell.getRow()][cell.getColumn()].highlight();
                            }
                        }
                    }
                });


            }
        }
    }

    private void highlight(){


    }


    private static void assignPiecesToIcons(){

    }


}
