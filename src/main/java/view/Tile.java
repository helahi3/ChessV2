package view;

import Board.*;
import Pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {

    private int row, col;
    private boolean isSelected = false;
    private Color defaultColor;

    Tile(int row, int col) {
        this.row = row;
        this.col = col;
        defaultColor = this.getForeground();

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void highlight(){
        if(!isSelected) {
            this.setForeground(Color.ORANGE);
            isSelected = true;
        } else {
            this.setForeground(defaultColor);
            isSelected = false;
        }
       // this.setBackground(Color.ORANGE);
    }

    public Piece getPiece(){
        Cell[][] board = Board.getBoard();
        return board[row][col].getPiece();
    }

    @Override
    protected void paintComponent(Graphics g) {
//        if (getModel().isSelected()) {
//            g.setColor(backgroundColor);
//        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    protected void createAndShowGUI(){

    }

    public String toString(){
        return "tile: " + this.getRow() + " " + this.getCol();
    }

}
