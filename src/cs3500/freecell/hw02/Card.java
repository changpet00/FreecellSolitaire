package cs3500.freecell.hw02;

import java.util.Objects;

/**
 * Represents a Card in the game of Freecell with a Rank and Suit value.
 */
public class Card {

  private final Suit suit;
  private final Rank rank;

  /**
   * Constructor for a Card.
   *
   * @param suit suit value (spade, club, heart, diamond)
   * @param rank rank value (ace - king
   */
  public Card(Suit suit, Rank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  /**
   * Returns the Suit and Rank of a Card.
   *
   * @return Card with Suit and Rank value
   */
  public String toString() {
    return rank.toString() + suit.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return suit == card.suit &&
        rank == card.rank;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suit, rank);
  }

  /**
   * Gets the suit value of the card.
   *
   * @return suit of card
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Gets the rank value of the card.
   *
   * @return rank of card
   */
  public Rank getRank() {
    return rank;
  }
}
