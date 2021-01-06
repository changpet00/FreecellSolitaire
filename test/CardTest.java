import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.Rank;
import cs3500.freecell.hw02.Suit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the Card class.
 */
public class CardTest {

  @Test
  // tests toString() method with different suits and numbers
  public void toString1() {
    assertEquals("4♥", new Card(Suit.H, Rank.FOUR).toString());
    assertEquals("10♠", new Card(Suit.S, Rank.TEN).toString());
    assertEquals("Q♦", new Card(Suit.D, Rank.QUEEN).toString());
    assertEquals("A♣", new Card(Suit.C, Rank.ACE).toString());
  }

  @Test
  // tests if the two cards are equal
  public void equals1() {
    assertEquals(true, new Card(Suit.H, Rank.FOUR).equals(new Card(Suit.H, Rank.FOUR)));
    assertEquals(false, new Card(Suit.H, Rank.FIVE).equals(new Card(Suit.D, Rank.FOUR)));
  }
}
