package org.poo.cards.cardGroups;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.gameplay.Player;
import org.poo.cards.MinionCard;

public final class GameBoard {
    private PacketOfMinions[] cards;

    private static final int LINES = 4;
    private static final int COLUMNS = 5;

    public PacketOfMinions[] getCards() {
        return cards;
    }

    public GameBoard() {
        cards = new PacketOfMinions[LINES];
        for (int i = 0; i < LINES; i++) {
            cards[i] = new PacketOfMinions(COLUMNS);
        }
    }

    /**
     * Unfreezes all cards and sets their usedAttack value to false.
     */
    public void renewCards() {
        for (PacketOfMinions row : cards) {
            for (MinionCard card : row.getCards()) {
                if (card != null) {
                    card.setUsedAttack(false);
                }
            }
        }
        unfreezeCards();
    }

    /**
     * Unfreezes all cards.
     */
    public void unfreezeCards() {
        for (PacketOfMinions row : cards) {
            for (MinionCard card : row.getCards()) {
                if (card != null) {
                    card.setFrozen(false);
                }
            }
        }
    }

    /**
     * @return an ArrayNode with all cards on the game board
     */
    public ArrayNode getCardsOnTable() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode table = mapper.createArrayNode();
        for (PacketOfMinions row : cards) {
            table.add(row.showJSON());
        }
        return table;
    }

    /**
     * @param x the line
     * @param y the column
     * @return the card on x line and y column
     */
    public MinionCard getCardAtPosition(final int x, final int y) {
        return cards[x].getCards()[y];
    }

    /**
     * @return an ArrayNode with all frozen cards on the game board
     */
    public ArrayNode getFrozenCardsOnTable() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode frozen = mapper.createArrayNode();
        for (PacketOfMinions row : cards) {
            for (MinionCard card : row.getCards()) {
                if (card != null) {
                    if (card.isFrozen()) {
                        frozen.add(card.showJSON());
                    }
                }
            }
        }
        return frozen;
    }

    /**
     * @param player that is checked for tank minion cards
     * @return true if the player has tanks placed, false otherwise
     */
    public boolean hasTanks(final Player player) {
        for (int i = 1; i <= 2; i++) {
            for (MinionCard card : cards[i].getCards()) {
                if (card != null) {
                    if (card.isTank() && card.getPlayer().getIndex() == player.getIndex()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
