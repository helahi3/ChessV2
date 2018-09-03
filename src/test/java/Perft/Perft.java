package Perft;


import Board.*;
import Pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Perft class is for testing my movement generator
 * It recursively generates moves for the current position, and all children up to a certain depth
 * Counts all legal moves, then compares to a table of values
 * More info in comments
 */
public class Perft {


    static HashMap<String,Integer> hs = new HashMap<>();

    /**
     *
     * @param depth the depth we are testing
     * @return
     */
    static long perft(int depth){
        int numMoves;
        long nodes = 0;

        if(depth == 0)
            return 1;

        //Get all pieces of a given color
        Board.getBoard();
        Board.initializeGame();
        List<Piece> pieces = Board.getWhitePieces();
       // List<Piece> pieces = Board.getBlackPieces();

        //Get all legal moves
        List<Move> moves = new ArrayList<Move>();
        for(Piece piece : pieces){
            moves.addAll(piece.getMoves());
        }

        //Make each move, calculate the perft, then undo the move
        for(Move move : moves){

            //Add the move to a hashmap to count how many times it appears
            String stringMove = move.getPieceMoved().toString();
            Integer count = hs.get(stringMove);
            hs.put(stringMove, (count == null) ? 1 : count + 1);

            Board.makeMove(move);
            nodes += perft(depth -1);
            Board.undoMove(move);
        }

        return nodes;
    }

    public static void main(String[] args){

        for(int i=0; i<4; i++){
            System.out.println("Perft with depth " + i + ": " + perft(i));
            System.out.println(hs);
        }
    }


}
/*



    MOVE move_list[256];
    int n_moves, i;
    u64 nodes = 0;

    if (depth == 0) return 1;

    n_moves = GenerateLegalMoves(move_list);
    for (i = 0; i < n_moves; i++) {
        MakeMove(move_list[i]);
        nodes += Perft(depth - 1);
        UndoMove(move_list[i]);
    }
    return nodes;

 */