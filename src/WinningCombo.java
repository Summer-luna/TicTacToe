//
// Class: WinningCombo
//
// Description:
// a class stores all win combos
//
public class WinningCombo {
  private int[][] winnerCombo;

  public WinningCombo(){
    winnerCombo = new int[][]{
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 4, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {2, 4, 6}
    };
  }

  public int[][] getWinnerCombo() {
    return winnerCombo;
  }

}
