package cs3500.freecell.hw04;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw02.Suit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an instance of a Freecell game with multi move. A multi move is one that
 * allows the player to move several cards at once from one cascade pile to another while
 * also obeying the conditions of a valid move and build. Utilizes some of the methods from the
 * FreecellModel.
 */
public class FreecellMultimoveModel extends FreecellModel {

  private List<Card> cards = new ArrayList<Card>();

  /**
   * This is a constructor for FreecellMultimoveModel.
   */
  public FreecellMultimoveModel() {
    // Nothing happens in this constructor and it has no arguments.
  }

  /**
   * Determines if the multiple cards can be moved to another pile or not.
   *
   * @param cards list of cards
   * @return true if the list of cards can be moved; false otherwise
   */
  public boolean canMove(List<Card> cards) {
    int emptyOpenPiles = 0;
    int emptyCascadePiles = 0;

    // determine the number of empty open piles
    for (int i = 0; i < openPile.size(); i++) {
      if (openPile.get(i).size() == 0) {
        emptyOpenPiles++;
      }
    }

    // determine the number of empty cascade piles
    for (int j = 0; j < cascadePile.size(); j++) {
      if (cascadePile.get(j).size() == 0) {
        emptyCascadePiles++;
      }
    }

    double maxNumCards = (emptyOpenPiles + 1) * Math.pow(2, emptyCascadePiles);
    return (cards.size() <= maxNumCards);
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) {

    validMove(source, pileNumber, cardIndex, destPileNumber);
    cards = multiCardList(source, pileNumber, cardIndex);
    if (!validBuild(cards) || !validMultimove(cards, destPileNumber, destination)) {
      throw new IllegalArgumentException("The multi-card build is not valid");
    }

    if (!canMove(cards)) {
      throw new IllegalArgumentException("The multi-card move is not legal. Not enough "
          + "intermediate slots.");
    }

    if (source == PileType.CASCADE) {
      if (cardIndex == cascadePile.get(pileNumber).size() - 1) {
        super.move(source, pileNumber, cardIndex, destination, destPileNumber);
      } else if (canMove(cards)) {
        if (destination == PileType.CASCADE) {
          if (cascadePile.get(destPileNumber).size() == 0) {
            cascadePile.get(destPileNumber).addAll(cardIndex, cards);
            cascadePile.get(pileNumber).removeAll(cards);
          } else {
            cascadePile.get(destPileNumber).addAll(cardIndex + 1, cards);
            cascadePile.get(pileNumber).removeAll(cards);
          }
        }
      }
    } else if (source == PileType.OPEN) {
      super.move(source, pileNumber, cardIndex, destination, destPileNumber);
    } else {
      throw new IllegalArgumentException("Illegal multimove.");
    }
  }

  /**
   * Gets the list of cards from the given card index to the end of the pile.
   *
   * @param source     the type of the source pile see {@link PileType}
   * @param pileNumber the pile number of the given type, starting at 0
   * @param cardIndex  the index of the card to be moved from the source pile, starting at 0
   * @throws IllegalArgumentException if the source is not valid
   */
  private List<Card> multiCardList(PileType source, int pileNumber, int cardIndex) {
    switch (source) {
      case CASCADE:
        cards = cascadePile.get(pileNumber)
            .subList(cardIndex, cascadePile.get(pileNumber).size());
        break;
      case OPEN:
        cards = openPile.get(pileNumber)
            .subList(cardIndex, openPile.get(pileNumber).size());
        break;
      case FOUNDATION:
        cards = foundationPile.get(pileNumber)
            .subList(cardIndex, foundationPile.get(pileNumber).size());
        break;
      default:
        throw new IllegalArgumentException("The source is not valid.");
    }
    return cards;
  }

  /**
   * Determines if the list of cards is a valid list, meaning that the prior card in the list is one
   * rank higher and the opposite suit color.
   *
   * @param cards list of cards
   * @return true if the list of cards are valid, false otherwise
   */
  private boolean validBuild(List<Card> cards) {
    boolean isValid = false;
    // if the list of cards is one card
    if (cards.size() == 1) {
      isValid = true;
    }
    for (int i = 0; i < cards.size() - 1; i++) {
      if (cards.get(i).getRank().ordinal() + 1 == cards.get(i + 1).getRank().ordinal()) {
        if (cards.get(i).getSuit() == Suit.D || cards.get(i).getSuit() == Suit.H) {
          if (cards.get(i + 1).getSuit() == Suit.S || cards.get(i + 1).getSuit() == Suit.C) {
            isValid = true;
          }
        } else if (cards.get(i).getSuit() == Suit.S || cards.get(i).getSuit() == Suit.C) {
          if (cards.get(i + 1).getSuit() == Suit.D || cards.get(i + 1).getSuit() == Suit.H) {
            isValid = true;
          }
        }
      } else {
        return isValid;
      }
    }
    return isValid;
  }

  /**
   * Determines if the multi move is valid, meaning that the card in the destination pile is
   * one rank higher and the opposite suit color compared to the first card in the list of cards.
   *
   * @param cards list of cards
   * @param destPileNumber the pile number of the given type, starting at 0
   * @param destination    the type of the destination pile (see {@link PileType})
   * @return true if the multi move is valid, false otherwise
   */
  private boolean validMultimove(List<Card> cards, int destPileNumber, PileType destination) {
    if (destination == PileType.CASCADE) {
      List<Card> destPile = cascadePile.get(destPileNumber);

      if (destPile.size() > 0) {
        // determines if the rank of the destination card is one rank higher than the rank
        // of the first card in the list of cards
        if (cards.get(0).getRank().ordinal()
            != destPile.get(destPile.size() - 1).getRank().ordinal() + 1) {
          return false;
        }

        // determines if the suit is the opposite suit color
        if (cards.get(0).getSuit() == Suit.H || cards.get(0).getSuit() == Suit.D) {
          if (destPile.get(destPile.size() - 1).getSuit() == Suit.H
              || destPile.get(destPile.size() - 1).getSuit() == Suit.D) {
            return false;
          }
        } else if (cards.get(0).getSuit() == Suit.C || cards.get(0).getSuit() == Suit.S) {
          if (destPile.get(destPile.size() - 1).getSuit() == Suit.C
              || destPile.get(destPile.size() - 1).getSuit() == Suit.S) {
            return false;
          }
        }
      }
    }
    return true;
  }
}

