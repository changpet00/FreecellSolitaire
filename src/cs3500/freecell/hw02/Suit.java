package cs3500.freecell.hw02;

/**
 * Represents a suit of a card.
 */
public enum Suit {
  S("♠"), C("♣"), H("♥"), D("♦");

  private final String s;

  /**
   * Constructor for Suit.
   *
   * @param s suit
   */
  Suit(String s) {
    this.s = s;
  }

  @Override
  public String toString() {
    return s;
  }
}
