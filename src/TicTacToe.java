//
// Class: TicTacToe
//
// Description:
// a class that contains game logic
//
import java.util.*;

public class TicTacToe {
  private Cell[][] gameBoard;
  private Scanner kbd;
  private Player player1;
  private Player player2;
  private List<Integer> player1Moves;
  private List<Integer> player2Moves;
  private boolean gameOver;
  private int playRound;
  private int[][] winningCombos;
  private int gameMode;
  private Player turn;


  ///////////////////////////////////////////////////////////////////
  /// TicTacToe (constructor, initialize the instance variable)   ///
  /// Input : None                                                ///
  /// Output: None                                                ///
  /// No returns, undefined                                        ///
  ///                                                             ///
  ///////////////////////////////////////////////////////////////////

  public TicTacToe(){
    gameBoard = new Cell[][]{{Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}, {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}, {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}};
    kbd = new Scanner(System.in);
    gameOver = true;
    playRound = 1;
    player1Moves = new ArrayList<>();
    player2Moves = new ArrayList<>();
    winningCombos = new WinningCombo().getWinnerCombo();
  }

  /////////////////////////////////////////////////////////////////////////////////////
  /// playGame (start to play game)                                                 ///
  /// Input : None                                                                  ///
  /// Output: print out selection to allow user to decide which mode want to play.  ///
  /// No returns                                                                    ///
  ///                                                                               ///
  /////////////////////////////////////////////////////////////////////////////////////
  public void playGame(){
    System.out.println("Please select number to decide what you want to play:");
    System.out.println("1. human vs human");
    System.out.println("2. human vs computer");
    System.out.println("3. computer vs computer");

    int gameType = kbd.nextInt();
    kbd.nextLine();
    switch (gameType){
      case 1:
        humanVsHuman();
        break;
      case 2:
        computerVsHuman();
        break;
      case 3:

    }
  }

  /////////////////////////////////////////////////////////////////////////////////////
  /// humanVsHuman (human vs human mode game initial setting)                       ///
  /// Input : None                                                                  ///
  /// Output: None                                                                  ///
  /// No returns                                                                    ///
  ///                                                                               ///
  /////////////////////////////////////////////////////////////////////////////////////

  private void humanVsHuman(){
    gameMode = 1;

    player1 = new Human("Human 1");
    player2 = new Human("Human 2");

    turn = player1;

    start();
  }

  ////////////////////////////////////////////////////////////////////////////
  /// humanVsHuman (computer vs human mode game initial setting, )         ///
  /// Input : None                                                         ///
  /// Output: let human decide to play first or second                      ///
  /// No returns                                                           ///
  ///                                                                      ///
  ////////////////////////////////////////////////////////////////////////////

  private void computerVsHuman(){
    gameMode = 2;

    player1 = new Human("Human 1");
    player2 = new Computer("Computer 2");

    // let player select play first or not
    System.out.println("Do you want to play first? 0 is yes, 1 is no");

    if(kbd.nextInt() == 0){
      turn = player1;
    } else {
      turn = player2;
    }

    start();
  }

  private void computerVsComputer(){
    player1 = new Computer("Computer 1");
    player2 = new Computer("Computer 2");
    gameMode = 3;

    // let player select play first or not
    System.out.println("Do you want to play first? 0 is yes, 1 is no");

    turn = player1;

    start();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /// start (start to play game, executes different algorithm based on game mode)                           ///
  /// Input : None                                                                                          ///
  /// Output: print out the round number and current player, current board status and position selection    ///
  /// No returns                                                                                            ///
  ///                                                                                                       ///
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private void start(){
    System.out.println("----------------------");
    System.out.printf("Starting to play. %s VS %s%n", player1.getName(), player2.getName());

    // the game is start, set gameOver to false
    gameOver = false;

    // when game is not over && play round is not greater than 10
    // we continuously play the game
    while(!gameOver && playRound < 10){

      System.out.printf("Round %d, %s start to choose which position want to move to:%n", playRound, turn.getName());

      // print out the position
      System.out.println(printPosition());

      // print out the game board before moves
      System.out.println(printGameBoard());

      if(gameMode == 1){
        if(moveTo(kbd.nextInt())) {

          // increase play round
          checkWinner();
          playRound++;

          // switch the turn
          turn = turn == player1 ? player2 : player1;

        } else {
          System.out.println("This position has been used or the position is not in the bound, please choose another one.");
        }
      } else if (gameMode == 2) {

        int position;

        if(turn.equals(player1)){
          position = kbd.nextInt();
        } else {
          Random rnd = new Random();
          position = rnd.nextInt(9);
        }

        if(moveTo(position)) {
          // increase play round
          checkWinner();
          playRound++;

          // switch the turn
          turn = turn == player1 ? player2 : player1;
        } else {
          System.out.println("This position has been used or the position is not in the bound, please choose another one.");
        }

      }

      System.out.println("----------------------");
    }

    // if game over or playRound > 9, print out game over and the winner
    System.out.println(printGameBoard());
    if(playRound > 9){
      System.out.printf("Game Over! %s and %s draw ", player1.getName(), player2.getName());
    } else if(playRound % 2 == 0){
      System.out.printf("Game Over! %s Wins", player1.getName());
    } else {
      System.out.printf("Game Over! %s Wins", player2.getName());
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// moveTo (move cell to specific position)                                        ///
  /// Input : the position where move to                                            ///
  /// Output:                                                                       ///
  /// Returns true if move is successful, false otherwise                           ///
  ///                                                                               ///
  /////////////////////////////////////////////////////////////////////////////////////
  private boolean moveTo(int position){

    boolean success = false;

    // get the row and col player want to move to
    int row = position / 3;
    int col = position % 3;

    // check if the position has been selected and if user input position is out of bound
    // save moved position to corresponding playerMoves Set
    if(position < 9 && position >= 0 && gameBoard[row][col] == Cell.EMPTY){
      // first player always be X
      if(playRound % 2 == 1){
        gameBoard[row][col] = Cell.X;
        player1Moves.add(position);
      } else {
        gameBoard[row][col] = Cell.O;
        player2Moves.add(position);
      }
      success = true;
    }

    return success;

  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////
  /// checkWinner (after every moves check if current player's moves list match one of winningCombo)     ///
  /// Input : None                                                                                       ///
  /// Output:                                                                                            ///
  /// No return                                                                                          ///
  ///                                                                                                    ///
  //////////////////////////////////////////////////////////////////////////////////////////////////////////
  private void checkWinner(){

    List<Integer> playerMoves;

    // first to sort the player list, because the winning Combos are in ascending order.
    if(playRound % 2 == 1){
      Collections.sort(player1Moves);
      playerMoves = player1Moves;
    } else {
      Collections.sort(player2Moves);
      playerMoves = player2Moves;
    }

    // if player moves list element is greater than 3,

    if(playerMoves.size() >= 3) {
      // loop through all winningCombo,
      // and we start to check if element in move list is match one of winningCombos

      for (int[] winningCombo : winningCombos) {
        int moveIndex = 0;
        int winnerComboIndex = 0;

        // loop player's move list, to check if winningCombo element equals element of player's move list.
        // both start at index 0
        while (moveIndex < playerMoves.size()) {
          // if equals increase winnerCombo index
          if (winningCombo[winnerComboIndex] == playerMoves.get(moveIndex)) {
            winnerComboIndex++;
          }

          // if not equal or equal, the player's move index increase
          moveIndex++;
        }

        // after looped player's move list
        // check if all element in winnerCombo list has been looped
        // if yes, game over
        // if no continue then game
        if (winnerComboIndex == winningCombo.length) {
          gameOver = true;
          break;
        }
      } // end the loop means there is no match, so game continue.

    }
  }


  ///////////////////////////////////////////////////////////////////////
  /// printPosition (display corresponding position board)            ///
  /// Input : None                                                    ///
  /// Output: None                                                    ///
  /// Return corresponding position board Strings                     ///
  ///                                                                 ///
  //////////////////////////////////////////////////////////////////////
  private String printPosition(){
    StringBuilder string = new StringBuilder();
    int number = 0;

    for(Cell[] row : gameBoard){
      for(Cell cell : row){
        string.append(String.format("%s     ", number));
        number++;
      }
      string.append("\n");
    }

    return string.toString();
  }

  ///////////////////////////////////////////////////////////////////////
  /// printGameBoard (display game board status)                      ///
  /// Input : None                                                    ///
  /// Output: None                                                    ///
  /// Return game board status Strings                                ///
  ///                                                                 ///
  //////////////////////////////////////////////////////////////////////
  public String printGameBoard() {
    StringBuilder string = new StringBuilder();

    for(Cell[] row : gameBoard){
      for(Cell cell : row){
        string.append(String.format("%-5s|", cell));
      }
      string.append("\n");
    }

    return string.toString();
  }
}
