package cs3500.freecell.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the FreecellModel which implements the {@code FreecellOperations<Card>} interface.
 */
public class FreecellModel implements FreecellOperations<Card> {

  protected ArrayList<ArrayList<Card>> cascadePile;
  protected ArrayList<ArrayList<Card>> openPile;
  protected ArrayList<ArrayList<Card>> foundationPile;
  protected boolean gameStarted = false;

  /**
   * This is a constructor for FreecellModel.
   */
  public FreecellModel() {
    // Nothing should happen in the constructor. The game will start when startGame is called.
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (Rank r : Rank.values()) {
      for (Suit s : Suit.values()) {
        deck.add(new Card(s, r));
      }
    }

    if (!isValid(deck)) {
      throw new IllegalArgumentException("Error: The deck is invalid!");
    }

    return deck;
  }

  /**
   * Determines if a deck of cards is valid, meaning that it is invalid if there are not 52 cards,
   * it has duplicate cards, or it has at least one invalid card (invalid suit or invalid number).
   *
   * @param deck the deck to be dealt
   * @return true if the deck is valid, false otherwise
   */
  public boolean isValid(List<Card> deck) {
    // checks if the deck size is not 52
    if (deck.size() != 52) {
      return false;
    }

    // checks if there are any duplicate cards
    for (int i = 0; i < 52; i++) {
      if (i == 51) {
        break;
      }
      for (int j = i + 1; j <= 52; j++) {
        if (j == 51) {
          break;
        }
        if (deck.get(i) == deck.get(j)) {
          return false;
        }
      }
    }

    // checks if there is at least one invalid card (invalid suit or number)
    for (Suit s : Suit.values()) {
      for (Rank r : Rank.values()) {
        if (!deck.contains(new Card(s, r))) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    cascadePile = new ArrayList<ArrayList<Card>>(numCascadePiles);
    openPile = new ArrayList<ArrayList<Card>>();
    foundationPile = new ArrayList<ArrayList<Card>>(4);

    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("There can't be less than 4 cascade piles!");
    }

    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("There can't be less than 1 open pile!");
    }

    if (isValid(deck)) {
      if (shuffle) {
        Collections.shuffle(deck);
      }

      for (int i = 0; i < numCascadePiles; i++) {
        cascadePile.add(new ArrayList<>());
      }

      for (int i = 0; i < 52; i++) {
        cascadePile.get(i % numCascadePiles).add(deck.get(i));
      }

      for (int i = 0; i < numOpenPiles; i++) {
        openPile.add(new ArrayList<>());
      }

      for (int i = 0; i < 4; i++) {
        foundationPile.add(new ArrayList<>());
      }

      gameStarted = true;

    } else {
      throw new IllegalArgumentException("The deck is invalid!");
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) {

    validMove(source, pileNumber, cardIndex, destPileNumber);

    switch (source) {
      case OPEN:
        if (pileNumber >= openPile.size()) {
          throw new IllegalArgumentException("Index out of bounds");
        }
        if (openPile.get(pileNumber).size() - 1 != cardIndex) {
          throw new IllegalArgumentException("Illegal index.");
        }
        Card openCard = openPile.get(pileNumber).get(openPile.get(pileNumber).size() - 1);

        if (destination == source && pileNumber == destPileNumber) {
          throw new IllegalArgumentException("The destination can't be the same as the source!");
        }

        moveHelper(openCard, destination, destPileNumber);
        openPile.get(pileNumber).remove(openCard);
        break;
      case CASCADE:
        if (pileNumber >= cascadePile.size()) {
          throw new IllegalArgumentException("Index out of bounds");
        }
        if (cascadePile.get(pileNumber).size() - 1 != cardIndex) {
          throw new IllegalArgumentException("Illegal index.");
        }
        Card cascadeCard = cascadePile.get(pileNumber)
            .get(cascadePile.get(pileNumber).size() - 1);

        // makes sure if card in the destination pile above the card being moved to the destination
        // is one rank higher and opposite suit color
        if (destination == PileType.CASCADE) {
          List<Card> pile = cascadePile.get(pileNumber);
          List<Card> destPile = cascadePile.get(destPileNumber);
          if (pile.get(pile.size() - 1).getSuit() == Suit.D
              || pile.get(pile.size() - 1).getSuit() == Suit.H) {
            if (destPile.get(destPile.size() - 1).getSuit() == Suit.D
                || destPile.get(destPile.size() - 1).getSuit() == Suit.H) {
              throw new IllegalArgumentException(
                  "The card above the moved card is not one rank higher and "
                      + "the opposite suit color");
            }
          }
          if (pile.get(pile.size() - 1).getSuit() == Suit.S
              || pile.get(pile.size() - 1).getSuit() == Suit.C) {
            if (destPile.get(destPile.size() - 1).getSuit() == Suit.S
                || destPile.get(destPile.size() - 1).getSuit() == Suit.C) {
              throw new IllegalArgumentException(
                  "The card above the moved card is not hh one rank higher and "
                      + "the opposite suit color");
            }
          }
        }
        if (destination == PileType.FOUNDATION) {
          // makes sure foundation pile card is one rank higher and same suit than the last card
          if (foundationPile.get(destPileNumber).size() > 0) {
            if (cascadePile.get(pileNumber).get(cascadePile.get(pileNumber).size() - 1).getSuit()
                != foundationPile
                .get(destPileNumber).get(foundationPile.get(destPileNumber).size() - 1).getSuit()) {
              throw new IllegalArgumentException(
                  "The card above the moved card does not have the same suit");
            }
          } else if (cascadePile
              .get(pileNumber).get(cascadePile.get(pileNumber).size() - 1).getRank()
              != Rank.ACE) {
            throw new IllegalArgumentException("The first card in foundation pile must be an Ace!");
          }
        }
        if (destination == source && pileNumber == destPileNumber) {
          throw new IllegalArgumentException("The destination can't be the same as the source!");
        }
        moveHelper(cascadeCard, destination, destPileNumber);
        cascadePile.get(pileNumber).remove(cascadeCard);
        break;
      case FOUNDATION:
        if (pileNumber >= 4) {
          throw new IllegalArgumentException("Index out of bounds");
        }

        Card foundationCard = foundationPile.get(pileNumber)
            .get(foundationPile.get(pileNumber).size() - 1);

        if (destination == source && pileNumber == destPileNumber) {
          throw new IllegalArgumentException("The destination can't be the same as the source!");
        }
        moveHelper(foundationCard, destination, destPileNumber);
        foundationPile.get(pileNumber).remove(foundationCard);
        break;
      default:
        throw new IllegalArgumentException("That move is not possible!");
    }

    // makes sure only one card is in each open pile
    for (ArrayList<Card> cards : openPile) {
      if (cards.size() > 1) {
        throw new IllegalArgumentException(
            "Open piles cannot have more than one card in a pile!");
      }
    }
  }

  /**
   * Helps the move method by moving the a card from the given pile to the given new destination
   * element.
   *
   * @param sourceCard     The card that is to be moved
   * @param destination    The pile type of the destination for the moved card
   * @param destPileNumber the index of the destination where the card will be moved
   */
  protected void moveHelper(Card sourceCard, PileType destination, int destPileNumber) {
    if (destination == PileType.OPEN) {
      if (destPileNumber >= openPile.size()) {
        throw new IllegalArgumentException("Index out of bounds");
      }
      openPile.get(destPileNumber).add(sourceCard);
    } else if (destination == PileType.CASCADE) {
      if (destPileNumber >= cascadePile.size()) {
        throw new IllegalArgumentException("Index out of bounds");
      }
      cascadePile.get(destPileNumber).add(sourceCard);
    } else if (destination == PileType.FOUNDATION) {
      if (destPileNumber >= 4) {
        throw new IllegalArgumentException("Index out of bounds");
      }
      foundationPile.get(destPileNumber).add(sourceCard);
    }
  }

  /**
   * Determines if the move is valid based on the given parameters and set rules of Freecell.
   *
   * @param source         the type of the source pile see {@link PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source pile, starting at 0
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if one or more parameters are out of bounds
   * @throws IllegalStateException    if the game hasn't started
   * @throws IllegalArgumentException if a given foundation pile doesn't start with an ace or the
   *                                  correct suit
   */
  protected void validMove(PileType source, int pileNumber, int cardIndex, int destPileNumber) {
    if (pileNumber < 0 || destPileNumber < 0 || cardIndex < 0) {
      throw new IllegalArgumentException("out of bounds");
    }

    if (!gameStarted) {
      throw new IllegalStateException("The game has not started!");
    }

    // makes sure foundation pile starts with an ace card and the correct suit
    if (source == PileType.FOUNDATION) {
      for (int i = 0; i < foundationPile.size() - 1; i++) {
        if (i == 0) {
          if (foundationPile.get(i).get(0).getSuit() != Suit.S) {
            throw new IllegalArgumentException(
                "The first card in the first foundation pile should be an ace of spades!");
          }
        } else if (i == 1) {
          if (foundationPile.get(i).get(0).getSuit() != Suit.C) {
            throw new IllegalArgumentException(
                "The first card in the second foundation pile should be an ace of clubs!");
          }
        } else if (i == 2) {
          if (foundationPile.get(i).get(0).getSuit() != Suit.H) {
            throw new IllegalArgumentException(
                "The first card in the third foundation pile should be an ace of hearts!");
          }
        } else if (i == 3) {
          if (foundationPile.get(i).get(0).getSuit() != Suit.D) {
            throw new IllegalArgumentException(
                "The first card in the fourth foundation pile should be an ace of diamonds!");
          }
        }
      }
    }
  }

  @Override
  public boolean isGameOver() {
    if (!gameStarted) {
      return false;
    }

    for (ArrayList<Card> cards : foundationPile) {
      if (cards.size() != 13) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Card getCard(PileType pile, int pileNumber, int cardIndex) {
    if (!gameStarted) {
      throw new IllegalStateException("The game has not started!");
    }
    if (cardIndex < 0 || pileNumber < 0) {
      throw new IllegalArgumentException("The card index is negative");
    }
    if (pile == PileType.OPEN) {
      if (pileNumber > openPile.size()) {
        throw new IllegalArgumentException(("Index out of bounds for open pile!"));
      }
      if (cardIndex > 1) {
        throw new IllegalArgumentException(("Index out of bounds for open pile!"));
      }
      if (openPile.get(pileNumber).get(cardIndex) == null) {
        throw new IllegalArgumentException("There is no such card!");
      }
      return openPile.get(pileNumber).get(cardIndex);
    } else if (pile == PileType.CASCADE) {
      if (pileNumber >= cascadePile.size()) {
        throw new IllegalArgumentException(("Index out of bounds for open pile!"));
      }
      if (cardIndex >= cascadePile.get(pileNumber).size()) {
        throw new IllegalArgumentException(("Index out of bounds for open pile!"));
      }
      if (cascadePile.get(pileNumber).get(cardIndex) == null) {
        throw new IllegalArgumentException("There is no such card!");
      }
      return cascadePile.get(pileNumber).get(cardIndex);
    } else if (pile == PileType.FOUNDATION) {
      if (pileNumber >= 4) {
        throw new IllegalArgumentException(("Index out of bounds for open pile!"));
      }
      if (cardIndex >= foundationPile.get(pileNumber).size()) {
        throw new IllegalArgumentException(("Index out of bounds for open pile!"));
      }
      if (foundationPile.get(pileNumber).get(cardIndex) == null) {
        throw new IllegalArgumentException("There is no such card!");
      }
      return foundationPile.get(pileNumber).get(cardIndex);
    } else {
      throw new IllegalArgumentException("There is no such card!");
    }
  }

  @Override
  public String getGameState() {
    String s = "";
    if (!gameStarted) {
      return "";
    }
    for (int i = 0; i < foundationPile.size(); i++) {
      s += "F" + (i + 1) + ":";
      if (foundationPile.get(i).size() == 0) {
        s += "\n";
        continue;
      }
      s += " ";
      for (int j = 0; j < foundationPile.get(i).size(); j++) {
        s += foundationPile.get(i).get(j).toString();

        if (j != foundationPile.get(i).size() - 1) {
          s += ", ";
        }
      }
      s += "\n";
    }

    for (int i = 0; i < openPile.size(); i++) {
      s += "O" + (i + 1) + ":";
      if (openPile.get(i).size() == 0) {
        s += "\n";
        continue;
      }
      s += " ";
      for (int j = 0; j < openPile.get(i).size(); j++) {
        s += openPile.get(i).get(j).toString();

        if (j != openPile.get(i).size() - 1) {
          s += ", ";
        }
      }
      s += "\n";
    }

    for (int i = 0; i < cascadePile.size(); i++) {
      s += "C" + (i + 1) + ":";
      if (cascadePile.get(i).size() == 0) {
        s += "\n";
        continue;
      }
      s += " ";
      for (int j = 0; j < cascadePile.get(i).size(); j++) {
        s += cascadePile.get(i).get(j).toString();

        if (j != cascadePile.get(i).size() - 1) {
          s += ", ";
        }
      }
      if (cascadePile.size() - 1 != i) {
        s += "\n";
      }
    }
    return s;
  }
}