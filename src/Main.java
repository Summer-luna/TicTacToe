//---------------------------------------------------------------------------
//
// TicTacToe Game
// Solves the 3 x 3 X and O cross game
//
// Author: Xinyue Chen
// Date: 02/12/23
// Class: MET CS622
// Issues: None known
//
// Description:
// This program stores the board in a 3 x 3 2d array.
// allows 2 play mode
// See comments below for more
// information.
//
// Assumptions:
// The first player always starts at X
//
//

public class Main {
  public static void main(String[] args) {
    TicTacToe game = new TicTacToe();
    game.playGame();
    game.playGame();
  }
}