package Controller;

import Board.Move;
import Engine.ChessEngine;
import Pieces.Piece;

/**
 * Engine controller that interacts with the ViewController and the ChessEngine
 */
public class EngineController {

    /**
     * private fields
     */
    private static ChessEngine engine;


    /**
     * Constructor that initializes the chess engine
     */
    public EngineController() {
        engine = new ChessEngine();
    }

    /**
     * Gets the move from the engine and returns it
     * @return int[] that contains starting and ending coordinates of move
     */
    public static Move move(Piece.PieceColor color){
        return engine.play(color);
    }

}
