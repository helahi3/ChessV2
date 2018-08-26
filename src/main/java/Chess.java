import Board.*;
import Pieces.*;
import com.sun.deploy.util.BlackList;

import java.util.Scanner;

public class Chess {

    public static void main(String[] args){
        Cell[][] board = Board.getBoard();

       initializeGame(board);
        game(board);
      //  print(board);
    }

    public static void initializeGame(Cell[][] board){
        Piece[] pieces = new Piece[5];


        pieces[0] = new Pawn(5,5,Piece.PieceColor.WHITE);
        pieces[1] = new Pawn(4,4,Piece.PieceColor.BLACK);
        pieces[2] = new King(7,5,Piece.PieceColor.WHITE);
        pieces[3] = new Queen(1,5,Piece.PieceColor.BLACK);
        pieces[4] = new King(0,0,Piece.PieceColor.BLACK);


        Board.setBoard(pieces);
        //print(board);


    }

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
                result += board[i][j].toString2();
            }
            result += "\n";
        }
        System.out.println(result);//result;
    }


}
