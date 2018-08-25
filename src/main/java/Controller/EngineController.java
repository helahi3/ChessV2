package Controller;

import Player.ChessEngine;
import view.GUI;
import view.Tile;

public class EngineController {

    private GUI gui;
    private Tile[][] chessBoardSquares;
    private static ChessEngine engine;


    public EngineController(GUI gui) {
        this.gui = gui;
        chessBoardSquares = gui.getChessBoardSquares();
        engine = new ChessEngine();
    }

    public static int[] move(){
        int[] moves = engine.play();
        return moves;
    }

}
