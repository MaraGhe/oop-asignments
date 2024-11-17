package org.poo.gameplay;

import org.poo.cards.HeroCard;
import org.poo.cards.MinionCard;
import org.poo.cards.cardGroups.GameBoard;
import org.poo.cards.cardGroups.PacketOfMinions;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;

import java.util.List;
import java.util.Random;
import static java.util.Collections.shuffle;

public final class Player {
    private int index;
    private int mana;
    private PacketOfMinions hand;
    private PacketOfMinions currentDeck;
    private PacketOfMinions[] decks;
    private HeroCard hero;
    private int wins;
    private boolean finishedRound;

    private static final int ERR_MANA = 1;
    private static final int ERR_FULL_ROW = 2;
    private static final int COLUMNS = 5;

    public Player(final int index, final CardInput[][] decks,
                  final int nrDecks, final int nrCardsInDeck) {
        this.index = index;
        this.decks = new PacketOfMinions[nrDecks];
        this.hand = new PacketOfMinions(nrCardsInDeck);
        for (int i = 0; i < nrDecks; i++) {
            this.decks[i] = new PacketOfMinions(nrCardsInDeck);
            for (int j = 0; j < nrCardsInDeck; j++) {
                this.decks[i].addCard(new MinionCard(this, decks[i][j]));
            }
        }
    }

    /**
     * @param deckIdx index of the deck to be chosen as current deck
     * @param shuffleSeed to randomise the current deck
     */
    public void chooseDeck(final int deckIdx, final Random shuffleSeed) {
        currentDeck = new PacketOfMinions(decks[deckIdx]);
        currentDeck.setPlayer(this);
        currentDeck.setLength(decks[deckIdx].getLength());
        List<MinionCard> listDeck = currentDeck.convertToList(currentDeck);
        shuffle(listDeck, shuffleSeed);
        currentDeck = currentDeck.convertFromList(listDeck);
    }

    /**
     * @param addedMana added to the player's mana
     */
    public void addMana(final int addedMana) {
        mana += addedMana;
    }

    /**
     * @param substitutedMana from the player's mana
     */
    public void reduceMana(final int substitutedMana) {
        mana -= substitutedMana;
    }

    /**
     * Places a card in hand onto the correct row
     * @param handIdx index of the card in hand to be placed
     * @param gameBoard on which the card is placed
     * @return 0 if all is good, error code 1 if player doesn't have enough mana,
     * error code 2 if the row is full
     */
    public int placeCard(final int handIdx, final GameBoard gameBoard) {
        MinionCard card = hand.getCards()[handIdx];
        if (card != null) {
            if (mana < card.getMana()) {
                return ERR_MANA;
            }
            int frontRow, backRow;
            if (index == 1) {
                frontRow = index + 1;
                backRow = index + 2;
            } else {
                frontRow = index - 1;
                backRow = index - 2;
            }

            if (card.getName().equals("Goliath")
                    || card.getName().equals("Warden")
                    || card.getName().equals("The Ripper")
                    || card.getName().equals("Miraj")) {
                if (gameBoard.getCards()[frontRow].getLength() == COLUMNS) {
                    return ERR_FULL_ROW;
                }
                gameBoard.getCards()[frontRow].addCard(card);
                hand.removeCard(handIdx);
            }

            if (card.getName().equals("Sentinel")
                    || card.getName().equals("Berserker")
                    || card.getName().equals("The Cursed One")
                    || card.getName().equals("Disciple")) {
                if (gameBoard.getCards()[backRow].getLength() == COLUMNS) {
                    return ERR_FULL_ROW;
                }
                gameBoard.getCards()[backRow].addCard(card);
                hand.removeCard(handIdx);
            }

            mana -= card.getMana();
        }
        return 0;
    }

    /**
     * Renews a player for the start of a new game.
     */
    public void renew() {
        mana = 0;
        hand = new PacketOfMinions(decks[0].getLength());
        currentDeck = null;
        finishedRound = false;
    }

    /**
     * Resets the player's hero for a new round
     */
    public void resetHero() {
        hero.setUsedAttack(false);
    }

    /**
     * @return an ArrayNode with information about all the cards in the player's hand
     */
    public ArrayNode getCardsInHand() {
        if (hand != null) {
            return hand.showJSON();
        }
        return null;
    }

    /**
     * @return an ArrayNode with information about all the cards in the player's current deck
     */
    public ArrayNode getPlayerDeck() {
        return currentDeck.showJSON();
    }

    /**
     * @return an ObjectNode with information about the player's hero
     */
    public ObjectNode getPlayerHero() {
        return hero.showJSON();
    }


    public int getMana() {
        return mana;
    }

    public int getIndex() {
        return index;
    }

    public PacketOfMinions getHand() {
        return hand;
    }

    public PacketOfMinions getCurrentDeck() {
        return currentDeck;
    }

    public HeroCard getHero() {
        return hero;
    }

    public void setHero(final HeroCard hero) {
        this.hero = hero;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(final int wins) {
        this.wins = wins;
    }

    public boolean isFinishedRound() {
        return finishedRound;
    }

    public void setFinishedRound(final boolean finishedRound) {
        this.finishedRound = finishedRound;
    }
}
