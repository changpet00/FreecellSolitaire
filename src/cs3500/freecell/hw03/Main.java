package cs3500.freecell.hw03;

import cs3500.freecell.hw02.FreecellModel;
import java.io.InputStreamReader;


/**
 * Run a Freecell game interactively on the console.
 */
public class Main {

  /**
   * Run a Freecell game interactively on the console.
   */
  public static void main(String[] args) {
    FreecellModel m = new FreecellModel();
    int numCascades = 52;
    int numOpens = 4;
    new FreecellController(new InputStreamReader(System.in),
        System.out).playGame(m.getDeck(), m, numCascades,
        numOpens, false);
  }
}
