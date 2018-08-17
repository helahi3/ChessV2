package Board;

import Pieces.Piece;

public class Move {

    private Cell start, end;
    private Piece pieceCaptured;


    public Move(Cell start, Cell end) {
        this.start = start;
        this.end = end;
        pieceCaptured = end.getPiece();
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
}
