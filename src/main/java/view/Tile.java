package view;

import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {

    private int row, col;
    private Color backgroundColor = Color.YELLOW;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
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
        this.setBackground(Color.YELLOW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(backgroundColor);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }


}
