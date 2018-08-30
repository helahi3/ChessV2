package view;

import Board.*;
import Pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {

    private int row, col;
    private Color defaultForeground;
    private Color defaultBackground;

    Tile(int row, int col) {
        this.row = row;
        this.col = col;
        defaultForeground = this.getForeground();
        defaultBackground = this.getBackground();

    }

    public void clear(){
        defaultBackground = null;
        defaultForeground = null;
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
            this.setForeground(Color.YELLOW);
            this.setBackground(Color.YELLOW);

    }

    public void unhighlight(){
        this.setForeground(defaultForeground);
        this.setBackground(defaultBackground);
    }


    public Piece getPiece(){
        Cell[][] board = Board.getBoard();
        return board[row][col].getPiece();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    protected void createAndShowGUI(){

    }

    public String toString(){
        return "tile: " + this.getRow() + " " + this.getCol();
    }

}
