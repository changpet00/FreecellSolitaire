package cs3500.freecell.hw02;

/**
 * Represents a rank of a card.
 */
public enum Rank {
  KING(13), QUEEN(12), JACK(11), TEN(10), NINE(9), EIGHT(8), SEVEN(7),
  SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(2), ACE(1);

  private final int r;

  /**
   * Constructor for Rank.
   *
   * @param r rank
   */
  Rank(int r) {
    this.r = r;
  }

  @Override
  public String toString() {
    switch (r) {
      case 1:
        return "A";
      case 2:
        return "2";
      case 3:
        return "3";
      case 4:
        return "4";
      case 5:
        return "5";
      case 6:
        return "6";
      case 7:
        return "7";
      case 8:
        return "8";
      case 9:
        return "9";
      case 10:
        return "10";
      case 11:
        return "J";
      case 12:
        return "Q";
      case 13:
        return "K";
      default:
        throw new IllegalArgumentException("Invalid rank!");
    }
  }
}
