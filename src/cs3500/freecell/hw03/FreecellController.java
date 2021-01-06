package cs3500.freecell.hw03;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw02.PileType;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * This is a Freecell controller to play the game. Utilizes the use of readable and appendable. The
 * playGame method allows for a user to input a sequence of operations to make a move.
 */
public class FreecellController implements IFreecellController<Card> {

  private final Appendable ap;
  private final Scanner scan;
  PileType sourceType;
  private PileType destinationType;
  private String sourcePile;
  private int sourcePileNum;
  private String destinationPile;
  private int destinationPileNum;
  private Integer cardIndex;
  private boolean quit;

  /**
   * Constructor for FreecellController.
   *
   * @param rd scanner input
   * @param ap output
   */
  public FreecellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.ap = ap;
    scan = new Scanner(rd);
  }

  @Override
  public void playGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
      int numOpens, boolean shuffle) {
    try {
      try {
        model.startGame(deck, numCascades, numOpens, shuffle);
        ap.append(model.getGameState() + "\n");
      } catch (IllegalArgumentException iae) {
        ap.append("Could not start game.");
        return;
      } catch (NullPointerException npe) {
        throw new IllegalArgumentException("The deck or model cannot be null.\n");
      }

      while (scan.hasNext()) {
        String in = scan.next();

        if (in.equalsIgnoreCase("Q")) {
          quit = true;
          ap.append("Game quit prematurely.\n");
          break;
        }

        if (sourceType == null) {
          try {
            sourcePileNum = Integer.parseInt(in.substring(1)) - 1;
          } catch (NumberFormatException ex) {
            ap.append("Invalid move. Try again. The source pile number was incorrect.\n");
            continue;
          }
          sourcePile = in.substring(0, 1);
          sourceType = pileHelper(sourcePile, in);

        } else if (cardIndex == null) {
          Integer temp = null;
          try {
            temp = Integer.parseInt(in);
          } catch (NumberFormatException ex) {
            ap.append("Invalid move. Try again. The input was " + in + ", but it needs "
                + "to be a valid card index.\n");
          }
          cardIndex = temp;
        } else if (destinationType == null) {
          try {
            destinationPileNum = Integer.parseInt(in.substring(1)) - 1;
          } catch (NumberFormatException ex) {
            ap.append("Invalid move. Try again. The destination pile number was incorrect.\n");
            continue;
          } catch (IllegalArgumentException iae) {
            ap.append(
                "Invalid move. Try again. The destination can't be the same as the source.\n");
          }

          destinationPile = in.substring(0, 1);
          destinationType = pileHelper(destinationPile, in);
        }

        if (destinationType != null) {
          try {
            model.move(sourceType, sourcePileNum, cardIndex - 1, destinationType,
                destinationPileNum);
            resetNull();
          } catch (IllegalArgumentException e) {
            resetNull();
            ap.append("Illegal move.\n");
            continue;
          } catch (IllegalStateException e) {
            ap.append("Game hasn't started.\n");
          }
          ap.append(model.getGameState() + "\n\n");
        }
      }
      if (model.isGameOver()) {
        ap.append("Game over.\n");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
    // determines if game is not over or the user has not input quit (no more inputs)
    if (!quit && !model.isGameOver()) {
      throw new IllegalStateException("There are no more inputs to read.");
    }
  }

  /**
   * Helps the playGame method by determining if the input for the source pile or destination pile
   * is a valid input (C, O, or F).
   *
   * @param pile The source or destination pile input
   * @param in   The input
   * @return The source or destination pile type
   * @throws IOException if the move is invalid
   */
  private PileType pileHelper(String pile, String in) throws IOException {
    PileType type = null;
    switch (pile) {
      case "C":
        type = PileType.CASCADE;
        break;
      case "F":
        type = PileType.FOUNDATION;
        break;
      case "O":
        type = PileType.OPEN;
        break;
      default:
        ap.append("Invalid move. Try again. The input was " + in + ", but it needs "
            + "to be a valid pile (C, F, O).\n");
    }
    return type;
  }

  /**
   * Resets the three inputs (sourceType, cardIndex, destinationType) to null to allow them to have
   * newly entered inputs.
   */
  private void resetNull() {
    sourceType = null;
    cardIndex = null;
    destinationType = null;
  }
}