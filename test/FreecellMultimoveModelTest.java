import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw03.FreecellController;
import cs3500.freecell.hw03.IFreecellController;
import cs3500.freecell.hw04.FreecellModelCreator;
import cs3500.freecell.hw04.FreecellModelCreator.GameType;
import cs3500.freecell.hw04.FreecellMultimoveModel;
import java.io.StringReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the Freecell multimove model. Verifying that game state is properly managed, and
 * all game actions are properly validated.
 */
public class FreecellMultimoveModelTest {

  private FreecellModelCreator creator;
  private FreecellMultimoveModel freecell;
  private List<Card> deck;

  @Before
  // sets up the initial data for the tests
  public void initData() {
    creator = new FreecellModelCreator();
    freecell = new FreecellMultimoveModel();
    deck = freecell.getDeck();
  }

  @Test(expected = IllegalArgumentException.class)
  // tests if the Freecell model creator will start a single game
  public void testGameTypeSingle() {
    FreecellModel singleGame = creator.create(GameType.SINGLEMOVE);
    singleGame.startGame(deck, 4, 4, false);
    // do a multi move to throw an error
    singleGame.move(PileType.CASCADE, 0, 8, PileType.OPEN, 0);
    singleGame.move(PileType.CASCADE, 2, 7, PileType.CASCADE, 0);
  }

  @Test
  // tests if the Freecell model creator will start a multi game
  public void testGameTypeMulti() {
    FreecellModel multiGame = creator.create(GameType.MULTIMOVE);
    multiGame.startGame(deck, 52, 4, false);
    multiGame.move(PileType.CASCADE, 10, 0, PileType.CASCADE, 5);
    multiGame.move(PileType.CASCADE, 5, 0, PileType.CASCADE, 2);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♣\n"
        + "C3: K♥, Q♣, J♥\n"
        + "C4: K♦\n"
        + "C5: Q♠\n"
        + "C6:\n"
        + "C7: Q♥\n"
        + "C8: Q♦\n"
        + "C9: J♠\n"
        + "C10: J♣\n"
        + "C11:\n"
        + "C12: J♦\n"
        + "C13: 10♠\n"
        + "C14: 10♣\n"
        + "C15: 10♥\n"
        + "C16: 10♦\n"
        + "C17: 9♠\n"
        + "C18: 9♣\n"
        + "C19: 9♥\n"
        + "C20: 9♦\n"
        + "C21: 8♠\n"
        + "C22: 8♣\n"
        + "C23: 8♥\n"
        + "C24: 8♦\n"
        + "C25: 7♠\n"
        + "C26: 7♣\n"
        + "C27: 7♥\n"
        + "C28: 7♦\n"
        + "C29: 6♠\n"
        + "C30: 6♣\n"
        + "C31: 6♥\n"
        + "C32: 6♦\n"
        + "C33: 5♠\n"
        + "C34: 5♣\n"
        + "C35: 5♥\n"
        + "C36: 5♦\n"
        + "C37: 4♠\n"
        + "C38: 4♣\n"
        + "C39: 4♥\n"
        + "C40: 4♦\n"
        + "C41: 3♠\n"
        + "C42: 3♣\n"
        + "C43: 3♥\n"
        + "C44: 3♦\n"
        + "C45: 2♠\n"
        + "C46: 2♣\n"
        + "C47: 2♥\n"
        + "C48: 2♦\n"
        + "C49: A♠\n"
        + "C50: A♣\n"
        + "C51: A♥\n"
        + "C52: A♦", multiGame.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  // tests if the Freecell model creator will not start a single or multi game
  public void testGameTypeNull() {
    FreecellModel nullGame = creator.create(null);
  }

  @Test
  // tests a valid multi move of 3 card build from cascade pile to cascade pile (2 empty cascade
  // piles and 4 empty open piles)
  public void testValid3CardMove() {
    freecell.startGame(deck, 52, 4, false);
    freecell.move(PileType.CASCADE, 10, 0, PileType.CASCADE, 5);
    freecell.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 5);
    freecell.move(PileType.CASCADE, 5, 0, PileType.CASCADE, 2);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♣\n"
        + "C3: K♥, Q♣, J♥, 10♣\n"
        + "C4: K♦\n"
        + "C5: Q♠\n"
        + "C6:\n"
        + "C7: Q♥\n"
        + "C8: Q♦\n"
        + "C9: J♠\n"
        + "C10: J♣\n"
        + "C11:\n"
        + "C12: J♦\n"
        + "C13: 10♠\n"
        + "C14:\n"
        + "C15: 10♥\n"
        + "C16: 10♦\n"
        + "C17: 9♠\n"
        + "C18: 9♣\n"
        + "C19: 9♥\n"
        + "C20: 9♦\n"
        + "C21: 8♠\n"
        + "C22: 8♣\n"
        + "C23: 8♥\n"
        + "C24: 8♦\n"
        + "C25: 7♠\n"
        + "C26: 7♣\n"
        + "C27: 7♥\n"
        + "C28: 7♦\n"
        + "C29: 6♠\n"
        + "C30: 6♣\n"
        + "C31: 6♥\n"
        + "C32: 6♦\n"
        + "C33: 5♠\n"
        + "C34: 5♣\n"
        + "C35: 5♥\n"
        + "C36: 5♦\n"
        + "C37: 4♠\n"
        + "C38: 4♣\n"
        + "C39: 4♥\n"
        + "C40: 4♦\n"
        + "C41: 3♠\n"
        + "C42: 3♣\n"
        + "C43: 3♥\n"
        + "C44: 3♦\n"
        + "C45: 2♠\n"
        + "C46: 2♣\n"
        + "C47: 2♥\n"
        + "C48: 2♦\n"
        + "C49: A♠\n"
        + "C50: A♣\n"
        + "C51: A♥\n"
        + "C52: A♦", freecell.getGameState());
  }

  @Test
  // tests if any card build can be moved to an empty cascade pile
  public void testMoveAnyToEmptyCascade() {
    freecell.startGame(deck, 52, 4, false);
    freecell.move(PileType.CASCADE, 10, 0, PileType.CASCADE, 5);
    freecell.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 5);
    freecell.move(PileType.CASCADE, 5, 0, PileType.CASCADE, 10);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♣\n"
        + "C3: K♥\n"
        + "C4: K♦\n"
        + "C5: Q♠\n"
        + "C6:\n"
        + "C7: Q♥\n"
        + "C8: Q♦\n"
        + "C9: J♠\n"
        + "C10: J♣\n"
        + "C11: Q♣, J♥, 10♣\n"
        + "C12: J♦\n"
        + "C13: 10♠\n"
        + "C14:\n"
        + "C15: 10♥\n"
        + "C16: 10♦\n"
        + "C17: 9♠\n"
        + "C18: 9♣\n"
        + "C19: 9♥\n"
        + "C20: 9♦\n"
        + "C21: 8♠\n"
        + "C22: 8♣\n"
        + "C23: 8♥\n"
        + "C24: 8♦\n"
        + "C25: 7♠\n"
        + "C26: 7♣\n"
        + "C27: 7♥\n"
        + "C28: 7♦\n"
        + "C29: 6♠\n"
        + "C30: 6♣\n"
        + "C31: 6♥\n"
        + "C32: 6♦\n"
        + "C33: 5♠\n"
        + "C34: 5♣\n"
        + "C35: 5♥\n"
        + "C36: 5♦\n"
        + "C37: 4♠\n"
        + "C38: 4♣\n"
        + "C39: 4♥\n"
        + "C40: 4♦\n"
        + "C41: 3♠\n"
        + "C42: 3♣\n"
        + "C43: 3♥\n"
        + "C44: 3♦\n"
        + "C45: 2♠\n"
        + "C46: 2♣\n"
        + "C47: 2♥\n"
        + "C48: 2♦\n"
        + "C49: A♠\n"
        + "C50: A♣\n"
        + "C51: A♥\n"
        + "C52: A♦", freecell.getGameState());
  }


  @Test
  // tests a single move made by user input, moving from open pile to cascade pile
  public void testOpenToCascade() {
    int numCascades = 4;
    int numOpens = 4;
    StringBuilder gameLog = new StringBuilder();
    IFreecellController c = new FreecellController(new StringReader(
        "C1 13 O1 C3 13 O2 O1 1 C3 q"), gameLog);
    c.playGame(deck, freecell, numCascades, numOpens, false);
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
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♠\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♠\n"
        + "O2: A♥\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2: A♥\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n"
        + "C2: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n"
        + "C4: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "\n"
        + "Game quit prematurely.\n", gameLog.toString());
  }

  @Test
  // tests a valid single move of moving from open pile to foundation pile
  public void testMoveToFoundationPileFromOpenPile() {
    freecell.startGame(deck, 6, 4, false);
    freecell.move(PileType.CASCADE, 0, 8, PileType.OPEN, 0);
    freecell.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals("F1: A♠\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, Q♥, 10♠, 9♥, 7♠, 6♥, 4♠, 3♥\n"
        + "C2: K♣, Q♦, 10♣, 9♦, 7♣, 6♦, 4♣, 3♦, A♣\n"
        + "C3: K♥, J♠, 10♥, 8♠, 7♥, 5♠, 4♥, 2♠, A♥\n"
        + "C4: K♦, J♣, 10♦, 8♣, 7♦, 5♣, 4♦, 2♣, A♦\n"
        + "C5: Q♠, J♥, 9♠, 8♥, 6♠, 5♥, 3♠, 2♥\n"
        + "C6: Q♣, J♦, 9♣, 8♦, 6♣, 5♦, 3♣, 2♦", freecell.getGameState());
  }

  @Test
  // tests a multi move in the Controller play game
  public void testController() {
    int numCascades = 52;
    int numOpens = 4;
    StringBuilder gameLog = new StringBuilder();
    IFreecellController c = new FreecellController(new StringReader("C51 1 C46 C46 1 C43 q"),
        gameLog);
    c.playGame(deck, freecell, numCascades, numOpens, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♣\n"
        + "C3: K♥\n"
        + "C4: K♦\n"
        + "C5: Q♠\n"
        + "C6: Q♣\n"
        + "C7: Q♥\n"
        + "C8: Q♦\n"
        + "C9: J♠\n"
        + "C10: J♣\n"
        + "C11: J♥\n"
        + "C12: J♦\n"
        + "C13: 10♠\n"
        + "C14: 10♣\n"
        + "C15: 10♥\n"
        + "C16: 10♦\n"
        + "C17: 9♠\n"
        + "C18: 9♣\n"
        + "C19: 9♥\n"
        + "C20: 9♦\n"
        + "C21: 8♠\n"
        + "C22: 8♣\n"
        + "C23: 8♥\n"
        + "C24: 8♦\n"
        + "C25: 7♠\n"
        + "C26: 7♣\n"
        + "C27: 7♥\n"
        + "C28: 7♦\n"
        + "C29: 6♠\n"
        + "C30: 6♣\n"
        + "C31: 6♥\n"
        + "C32: 6♦\n"
        + "C33: 5♠\n"
        + "C34: 5♣\n"
        + "C35: 5♥\n"
        + "C36: 5♦\n"
        + "C37: 4♠\n"
        + "C38: 4♣\n"
        + "C39: 4♥\n"
        + "C40: 4♦\n"
        + "C41: 3♠\n"
        + "C42: 3♣\n"
        + "C43: 3♥\n"
        + "C44: 3♦\n"
        + "C45: 2♠\n"
        + "C46: 2♣\n"
        + "C47: 2♥\n"
        + "C48: 2♦\n"
        + "C49: A♠\n"
        + "C50: A♣\n"
        + "C51: A♥\n"
        + "C52: A♦\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♣\n"
        + "C3: K♥\n"
        + "C4: K♦\n"
        + "C5: Q♠\n"
        + "C6: Q♣\n"
        + "C7: Q♥\n"
        + "C8: Q♦\n"
        + "C9: J♠\n"
        + "C10: J♣\n"
        + "C11: J♥\n"
        + "C12: J♦\n"
        + "C13: 10♠\n"
        + "C14: 10♣\n"
        + "C15: 10♥\n"
        + "C16: 10♦\n"
        + "C17: 9♠\n"
        + "C18: 9♣\n"
        + "C19: 9♥\n"
        + "C20: 9♦\n"
        + "C21: 8♠\n"
        + "C22: 8♣\n"
        + "C23: 8♥\n"
        + "C24: 8♦\n"
        + "C25: 7♠\n"
        + "C26: 7♣\n"
        + "C27: 7♥\n"
        + "C28: 7♦\n"
        + "C29: 6♠\n"
        + "C30: 6♣\n"
        + "C31: 6♥\n"
        + "C32: 6♦\n"
        + "C33: 5♠\n"
        + "C34: 5♣\n"
        + "C35: 5♥\n"
        + "C36: 5♦\n"
        + "C37: 4♠\n"
        + "C38: 4♣\n"
        + "C39: 4♥\n"
        + "C40: 4♦\n"
        + "C41: 3♠\n"
        + "C42: 3♣\n"
        + "C43: 3♥\n"
        + "C44: 3♦\n"
        + "C45: 2♠\n"
        + "C46: 2♣, A♥\n"
        + "C47: 2♥\n"
        + "C48: 2♦\n"
        + "C49: A♠\n"
        + "C50: A♣\n"
        + "C51:\n"
        + "C52: A♦\n"
        + "\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♣\n"
        + "C3: K♥\n"
        + "C4: K♦\n"
        + "C5: Q♠\n"
        + "C6: Q♣\n"
        + "C7: Q♥\n"
        + "C8: Q♦\n"
        + "C9: J♠\n"
        + "C10: J♣\n"
        + "C11: J♥\n"
        + "C12: J♦\n"
        + "C13: 10♠\n"
        + "C14: 10♣\n"
        + "C15: 10♥\n"
        + "C16: 10♦\n"
        + "C17: 9♠\n"
        + "C18: 9♣\n"
        + "C19: 9♥\n"
        + "C20: 9♦\n"
        + "C21: 8♠\n"
        + "C22: 8♣\n"
        + "C23: 8♥\n"
        + "C24: 8♦\n"
        + "C25: 7♠\n"
        + "C26: 7♣\n"
        + "C27: 7♥\n"
        + "C28: 7♦\n"
        + "C29: 6♠\n"
        + "C30: 6♣\n"
        + "C31: 6♥\n"
        + "C32: 6♦\n"
        + "C33: 5♠\n"
        + "C34: 5♣\n"
        + "C35: 5♥\n"
        + "C36: 5♦\n"
        + "C37: 4♠\n"
        + "C38: 4♣\n"
        + "C39: 4♥\n"
        + "C40: 4♦\n"
        + "C41: 3♠\n"
        + "C42: 3♣\n"
        + "C43: 3♥, 2♣, A♥\n"
        + "C44: 3♦\n"
        + "C45: 2♠\n"
        + "C46:\n"
        + "C47: 2♥\n"
        + "C48: 2♦\n"
        + "C49: A♠\n"
        + "C50: A♣\n"
        + "C51:\n"
        + "C52: A♦\n"
        + "\n"
        + "Game quit prematurely.\n", gameLog.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  // tests an invalid multi move of 2 cards from one cascade pile to another due to insufficient
  // available piles (1 empty open pile and 0 empty cascade piles)
  public void testMove2CardsSomeCascades1EmptyInvalid() {
    freecell.startGame(deck, 26, 1, false);
    freecell.move(PileType.CASCADE, 25, 1, PileType.CASCADE, 19);
    freecell.move(PileType.CASCADE, 19, 1, PileType.CASCADE, 16);
    freecell.move(PileType.CASCADE, 16, 1, PileType.CASCADE, 11);
    System.out.println(freecell.getGameState());
  }

  @Test (expected = IllegalArgumentException.class)
  // tests if the list of cards is one greater than the allowed cards to move due to the max cards
  // moved formula
  public void testMoveCardsOneGreaterThanMaxAllowed() {
    freecell.startGame(deck, 26, 1, false);
    freecell.move(PileType.CASCADE, 25, 1, PileType.CASCADE, 19);
    freecell.move(PileType.CASCADE, 19, 1, PileType.CASCADE, 17);
    freecell.move(PileType.CASCADE, 25, 0, PileType.OPEN, 0);
    freecell.move(PileType.CASCADE, 17, 1, PileType.CASCADE, 11);
  }

  @Test (expected = IllegalArgumentException.class)
  // tests that the Freecell model can't multi move cards, only single cards
  public void testFreecellModelMultimove() {
    FreecellModel m = new FreecellModel();
    m.startGame(deck, 52, 4, false);
    m.move(PileType.CASCADE, 50, 0, PileType.CASCADE, 45);
    m.move(PileType.CASCADE, 45, 0, PileType.CASCADE, 42);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests if the list of cards is a valid build
  public void testValidBuild() {
    freecell.startGame(deck, 52, 4, false);
    freecell.move(PileType.CASCADE, 51, 0, PileType.CASCADE, 45);
    freecell.move(PileType.CASCADE, 45, 0, PileType.CASCADE, 38);
  }

  @Test(expected = IllegalArgumentException.class)
  // tests if the destination pile's last card is one rank higher and opposite suit color to the
  // first card in the list of cards being moved
  public void testValidMultimove() {
    freecell.startGame(deck, 52, 4, false);
    freecell.move(PileType.CASCADE, 10, 0, PileType.CASCADE, 5);
    freecell.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 5);
    freecell.move(PileType.CASCADE, 5, 0, PileType.CASCADE, 10);
    freecell.move(PileType.CASCADE, 10, 0, PileType.CASCADE, 6);
  }
}