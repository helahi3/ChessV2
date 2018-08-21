import Board.*;
import Pieces.*;

import java.util.Scanner;

public class Chess {

    public static void main(String[] args){
        Cell[][] board = Board.getBoard();

       Board.initializeGame();
        game(board);
      //  print(board);
    }

//    public static void initializeGame(Cell[][] board){
//        Piece[] pieces = new Piece[32];
//
//        for(int i=0; i<8; i++) {
//            pieces[i] = new Pawn(6,i,Piece.PieceColor.WHITE); //White
//            pieces[i+16] = new Pawn(1,i,Piece.PieceColor.BLACK); //Black
//        }
//
//        pieces[8] = new Knight(7,1,Piece.PieceColor.WHITE); //White Knights
//        pieces[9] = new Knight(7,6,Piece.PieceColor.WHITE);
//        pieces[10] = new Bishop(7,2,Piece.PieceColor.WHITE); //White Bishops
//        pieces[11] = new Bishop(7,5,Piece.PieceColor.WHITE);
//        pieces[12] = new Rook(7,0,Piece.PieceColor.WHITE); //White Rooks
//        pieces[13] = new Rook(7,7,Piece.PieceColor.WHITE);
//        pieces[14] = new Queen(7,3,Piece.PieceColor.WHITE); //White Queen
//        pieces[15] = new King(7,4,Piece.PieceColor.WHITE); //White King
//
//        pieces[24] = new Knight(0,1,Piece.PieceColor.BLACK); //Black Knights
//        pieces[25] = new Knight(0,6,Piece.PieceColor.BLACK);
//        pieces[26] = new Bishop(0,2,Piece.PieceColor.BLACK); //Black Bishops
//        pieces[27] = new Bishop(0,5,Piece.PieceColor.BLACK);
//        pieces[28] = new Rook(0,0,Piece.PieceColor.BLACK); //Black Rooks
//        pieces[29] = new Rook(0,7,Piece.PieceColor.BLACK);
//        pieces[30] = new Queen(0,3,Piece.PieceColor.BLACK); //Black Queen
//        pieces[31] = new King(0,4,Piece.PieceColor.BLACK); //Black King
//
//
//        Board.setBoard(pieces);
//        //print(board);
//
//
//    }

    //todo: temp code for testing purposes
    //input will be in format d2, d4
    public static int[] convertNotation(String notation){
        if(notation.length() != 2)
            return null;

        char charX = notation.charAt(0); //Gets ABC..
        char charY = notation.charAt(1);
        if(charX > 'H' || charX < 'A' || charY > '8' || charY < '1') return null;

        int x = charX - 'A';
        int y = -charY + '1' + 7;

        int[] arr = {x,y};

        return arr;
    }



    public static void game(Cell[][] board){
        Scanner sc = new Scanner(System.in);
        int startX, startY, endX, endY;
        String temp; int[] arr;


        while(true) {
            print(board);
            System.out.println("Enter starting cell, followed by ending cell (In the form D2, D4). Enter 999 to quit");
            //	try {
            temp = sc.nextLine();

            arr = convertNotation(temp);
            startX = arr[1]; startY = arr[0];

            temp = sc.nextLine();

            arr = convertNotation(temp);
            endX = arr[1]; endY = arr[0];
            if(startX == 999) break;


            Board.movePiece(startX, startY, endX, endY);

//			} catch (NullPointerException e) {
//				System.out.println("Input in the wrong format");
//			}
        }
    }

    public static void print(Cell[][] board){
        String result = "";
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                result += board[i][j];
            }
            result += "\n";
        }
        System.out.println(result);//result;
    }


}
