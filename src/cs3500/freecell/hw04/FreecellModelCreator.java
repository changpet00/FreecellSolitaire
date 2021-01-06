package cs3500.freecell.hw04;

import cs3500.freecell.hw02.FreecellModel;

/**
 * Factory class that represents which game is played, either a single or a multi move game.
 */
public class FreecellModelCreator {

  /**
   * Represents the Gametype of the Freecell game. Either a single move or a multi move.
   * Includes the factory method create, which returns a Freecell model of a single move
   * or multi move model. If neither, then it returns null.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * This is a constructor for FreecellModelCreator.
   */
  public FreecellModelCreator() {
    // Nothing happens in this constructor. Used to create an instance of the class.
  }

  /**
   * Returns an instance of the Freecell model depending on the value of the parameter.
   *
   * @param type the type of game (single or multi)
   * @return instance of the game wanted
   */
  public static FreecellModel create(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("The gametype cannot be null.");
    }
    switch (type) {
      case SINGLEMOVE:
        return new FreecellModel();
      case MULTIMOVE:
        return new FreecellMultimoveModel();
      default:
        return null;
    }
  }
}
