# Chess Game and Engine created in Java

A Chess game with multiplayer and singleplayer (vs. Computer) functionality. 
The latest iteration of the AI implements a recursive Minimax algorithm

Java Swing was used to implement the GUI. 

You can try the app out by navigating to ``` /out/artifacts/ChessApp/chess_v2.jar ```


Currently supports 2 player version and against the computer. 
Players can only play as White against the computer. 
Currently pawns can only be promoted to Queen

Screenshots of the game are available in the /screenshots folder.

I am currently in the process of improving the chess engine.

Ongoing goal: Build a chess engine good enough to beat myself!

##### TODO: 

1. Complete castling movement logic and start working on en passant.
2. Implement Alpha-Beta pruning to improve speed while evaluating nodes (If a movement branch has increasingly bad scores, do not evaluate it further)
3. Improve board evaluation by using Piece-Square tables (Knight's are more valuable in middle of board, pawns more valuable if advanced...)


Note: The app is still in development, but feel free to test it out 
by downloading the jar file in ```/out/artifacts/ChessApp```

You can choose the AI difficulty by going to ```/src/main/java/Engine/ChessEngine```
and selecting a different AI
