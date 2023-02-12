//
// Class: Player
//
// Description:
// an abstract class for player, can set, get player name
//
public abstract class Player {

  private String name;

  public Player(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
