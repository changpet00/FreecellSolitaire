import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.Rank;
import cs3500.freecell.hw02.Suit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Test cases for the Freecell model. Verifying that game state is properly managed, and all game
 * actions are properly validated.
 */
public class FreecellModelTest {

  private FreecellModel freecell;
  private FreecellModel freecell2;
  private List<Card> deck;
  private List<Card> deck2;
  private ArrayList<ArrayList<Card>> cascadePile;
  private ArrayList<ArrayList<Card>> openPile;
  private ArrayList<ArrayList<Card>> foundationPile;

  @Before
  // sets up the initial data for the tests
  public void initData() {
    freecell = new FreecellModel();
    freecell2 = new FreecellModel();
    deck = freecell.getDeck();
    deck2 = freecell2.getDeck();
  }

  @Test
  // tests to see the contents of the deck
  public void getDeckTest() {
    assertEquals(
        "[K♠, K♣, K♥, K♦, Q♠, Q♣, Q♥, Q♦, J♠, J♣, J♥, J♦, 10♠, 10♣, 10♥, 10♦, 9♠, 9♣, 9♥, 9♦,"
            + " 8♠, 8♣, 8♥, 8♦, 7♠, 7♣, 7♥, 7♦, 6♠, 6♣, 6♥, 6♦, 5♠, 5♣, 5♥, 5♦, 4♠, 4♣, 4♥, 4♦, "
            + "3♠, 3♣, 3♥, 3♦, 2♠, 2♣, 2♥, 2♦, A♠, A♣, A♥, A♦]", deck.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  // tests to see if the deck is not 52 cards
  public void testInvalidDeckSize() {
    deck.remove(51);
    freecell.startGame(deck, 4, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests to see if there is a duplicate (same) card in the deck as another
  public void testDuplicateCards() {
    deck.set(1, new Card(Suit.H, Rank.ACE));
    freecell.startGame(deck, 4, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests to see if there is an invalid card (invalid suit or invalid number)
  public void testOneInvalidCard() {
    deck.set(1, new Card(null, Rank.ACE));
    freecell.startGame(deck, 4, 4, false);
  }

  @Test
  // tests to see if the isValid() method works correctly
  public void isValidTest() {
    assertEquals(true, freecell.isValid(deck));
    deck2.remove(0);
    assertEquals(false, freecell2.isValid(deck2));
  }

  @Test
  // tests for when you don't shuffle
  public void startGameFalseTest() {
    freecell.startGame(deck, 4, 4, false);
    assertNotEquals(freecell, freecell2);
    System.out.println(freecell.getGameState());
  }

  @Test
  // tests for when you do shuffle
  public void startGameTrueTest() {
    freecell.startGame(deck, 4, 4, true);
    deck2 = freecell2.getDeck();
    freecell2.startGame(deck2, 4, 4, false);
    assertNotEquals(freecell, freecell2);
  }

  @Test
  // tests for a different number of cascade piles
  public void startGameTest2() {
    freecell.startGame(deck, 8, 4, false);
    assertNotEquals(freecell, freecell2);
  }

  @Test
  // tests for if startGame() is called while a game is already in progress
  public void startGameTest3() {
    freecell.startGame(deck, 4, 4, false);
    freecell.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    freecell.startGame(deck, 4, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦", freecell.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  // tests for if there are less than 4 cascade piles
  public void startGameLessThanFourTest() {
    freecell.startGame(deck, 3, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests for if there are less than 1 open pile
  public void startGameLessThanOneTest() {
    freecell.startGame(deck, 4, 0, false);
  }

  @Test(expected = IllegalStateException.class)
  // tests to see if the game started
  public void gameStartedTest() {
    freecell.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 1);
    freecell.getCard(PileType.FOUNDATION, 2, 4);
  }

  @Test
  // test for moving card from cascade pile to open pile
  public void moveTest1() {
    freecell.startGame(deck, 4, 4, false);
    freecell.move(PileType.CASCADE, 0, 12, PileType.OPEN, 1);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2: A♠\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦", freecell.getGameState());
  }

  @Test
  // test for moving card from open pile to open pile
  public void moveTest2() {
    freecell.startGame(deck, 4, 4, false);
    freecell.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    freecell.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2: A♠\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦", freecell.getGameState());
  }

  @Test
  // test for moving card from cascade pile to foundation pile
  public void moveTest3() {
    freecell.startGame(deck, 4, 4, false);
    freecell.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 0);
    assertEquals("F1: A♦\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦", freecell.getGameState());
  }

  @Test
  // test for moving card to foundation pile that already has card in it
  public void moveTest4() {
    freecell.startGame(deck, 4, 4, false);
    freecell.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    assertEquals("F1: A♠, 2♠\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦", freecell.getGameState());
  }

  @Test
  // test for moving card from open pile to cascade pile
  public void moveTest5() {
    freecell.startGame(deck, 4, 4, false);
    freecell.move(PileType.CASCADE, 3, 12, PileType.OPEN, 0);
    freecell.move(PileType.OPEN, 0, 0, PileType.CASCADE, 3);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦", freecell.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  // tests that card can't be moved to a cascade pile if last card in that pile is
  // not one rank higher or opposite suit
  public void moveTest6() {
    freecell.startGame(deck, 4, 4, false);
    System.out.println(freecell.getGameState());
    freecell.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests that the foundation pile starts with an ace and the correct suit
  public void moveTest7() {
    freecell.startGame(deck, 4, 4, false);
    freecell.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests that card can't be moved to a foundation pile if last card in the pile is
  // not one rank higher and same suit
  public void moveTest8() {
    freecell.startGame(deck, 6, 4, false);
    freecell.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 3);
    System.out.println(freecell.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  // tests if card is moved to an open pile that already has a card
  public void moveTest9() {
    freecell.startGame(deck, 6, 4, false);
    freecell.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    freecell.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
  }

  @Test
  // tests of the game is really over
  public void isGameOverTest1() {
    freecell.startGame(deck, 4, 4, false);

    freecell.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 9, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 8, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 1, PileType.FOUNDATION, 0);
    freecell.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);

    freecell.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 10, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 9, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 8, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 5, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 4, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 3, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 2, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
    freecell.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 1);

    freecell.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 11, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 10, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 9, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 8, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 7, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 5, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 4, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 3, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 2, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 1, PileType.FOUNDATION, 2);
    freecell.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 2);

    freecell.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 11, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 10, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 9, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 8, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 7, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 5, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 4, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 2, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 1, PileType.FOUNDATION, 3);
    freecell.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 3);

    assertEquals(true, freecell.isGameOver());
  }

  @Test
  // tests if the game is not over
  public void isGameOverTest2() {
    freecell2.startGame(deck2, 4, 4, false);

    freecell2.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 9, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 8, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 1, PileType.FOUNDATION, 0);
    freecell2.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);

    assertEquals(false, freecell2.isGameOver());
  }

  @Test
  // tests the getCard() method with a valid specified card
  public void getCardTest() {
    freecell.startGame(deck, 4, 4, false);
    Card card = new Card(Suit.S, Rank.KING);
    assertEquals(card, freecell.getCard(PileType.CASCADE, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  // tests the getCard() method with a negative card index
  public void getCardTest2() {
    freecell.startGame(deck, 4, 4, false);
    freecell.getCard(PileType.CASCADE, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests the getCard() method with an index out of bounds or a null index for open pile
  public void getCardTest3() {
    freecell.startGame(deck, 4, 4, false);
    freecell.getCard(PileType.OPEN, 0, 2);
    freecell.getCard(PileType.OPEN, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests the getCard() method with an index out of bounds or a null index for foundation pile
  public void getCardTest4() {
    freecell.startGame(deck, 4, 4, false);
    freecell.getCard(PileType.FOUNDATION, 0, 13);
    freecell.getCard(PileType.FOUNDATION, 0, 0);
  }

  @Test
  // tests if the card of focus does not exist at the specified index
  public void cardNullTest() {
    freecell.startGame(deck, 4, 4, false);
    Card card = new Card(Suit.C, Rank.TEN);
    assertNotEquals(card, freecell.getCard(PileType.CASCADE, 0, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  // tests if the card index is less than 0
  public void cardIndexTest() {
    freecell.startGame(deck, 4, 4, false);
    Card card = new Card(Suit.C, Rank.TEN);
    assertEquals(card, freecell.getCard(PileType.CASCADE, 0, -1));
  }

  @Test
  // tests the display of the board with four and eight cascade piles
  public void getGameStateTest1() {
    freecell.startGame(deck, 4, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦", freecell.getGameState());
    System.out.println(freecell.getGameState());
    freecell2.startGame(deck2, 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦", freecell2.getGameState());
  }

  @Test
  // tests if startGame() throws an exception and game has not started, it should return
  // an empty string
  public void getGameStateTest2() {
    assertEquals("", freecell.getGameState());
  }
}