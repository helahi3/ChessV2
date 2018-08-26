package Board;

import Pieces.Piece;

public class Move {

    private Cell start, end;
    private Piece pieceCaptured;
    private int score;


    public Move(Cell start, Cell end) {
        this.start = start;
        this.end = end;
        pieceCaptured = end.getPiece();
    }

    public Move(Cell end, int score){
        this.end = end;
        this.score = score;
    }

    public boolean isPieceCaptured(){
        return this.pieceCaptured != null;
    }

    public Cell getStart() {
        return start;
    }

    public Cell getEnd() {
        return end;
    }

    public Piece getPieceCaptured() {
        return pieceCaptured;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
