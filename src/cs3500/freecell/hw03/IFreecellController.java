package cs3500.freecell.hw03;

import cs3500.freecell.hw02.FreecellOperations;
import java.util.List;

/**
 * Represents a controller for Freecell: handle user moves by executing them using the model; convey
 * move outcomes to the user in some form.
 */
public interface IFreecellController<Card> {

  /**
   * Execute a single game of Freecell given a Freecell Model. When the game is over, the playGame
   * method ends.
   *
   * @param deck        a non-null deck of cards
   * @param model       a non-null Freecell Model
   * @param numCascades number of cascade piles
   * @param numOpens    number of open piles
   * @param shuffle     if true, shuffle the deck. If false, deal the deck as-is
   * @throws IllegalStateException if the controller has not been initialized properly to receive
   *                               input and transmit output
   */
  void playGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
      int numOpens, boolean shuffle);
}
