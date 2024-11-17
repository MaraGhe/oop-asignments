package org.poo.cards.cardGroups;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.cards.MinionCard;
import org.poo.gameplay.Player;

import java.util.ArrayList;
import java.util.List;

public final class PacketOfMinions {
    private MinionCard[] cards;
    private Player player;
    private int length;

    public PacketOfMinions() {
        cards = new MinionCard[0];
    }

    public PacketOfMinions(final int length) {
        cards = new MinionCard[length];
    }

    public PacketOfMinions(final int length, final Player player) {
        this(length);
        this.player = player;
    }

    public PacketOfMinions(final PacketOfMinions packet) {
        this(packet.length);
        for (int i = 0; i < packet.length; i++) {
            this.cards[i] = new MinionCard(packet.cards[i]);
        }
        this.player = packet.player;
    }

    /**
     * Converts a PacketOfMinions object to an equivalent List<MinionCard> object
     * @param packet of class PacketOfMinions
     * @return the List<MinionCard> object
     */
    public List<MinionCard> convertToList(final PacketOfMinions packet) {
        List<MinionCard> list = new ArrayList(packet.length);
        for (int i = 0; i < packet.length; i++) {
            list.add(packet.cards[i]);
        }
        return list;
    }

    /**
     * Converts a List<MinionCard> object to an equivalent PacketOfMinions object
     * @param list of class List<MinionCard>
     * @return the PacketOfMinions object
     */
    public PacketOfMinions convertFromList(final List<MinionCard> list) {
        PacketOfMinions packet = new PacketOfMinions(list.size());
        packet.length = list.size();
        for (int i = 0; i < list.size(); i++) {
            packet.cards[i] = list.get(i);
        }
        return packet;
    }

    /**
     * Adds a card to the packet. If packet is full, a new memory area
     * double the original's size is allocated and the contents are copied there
     * @param card added to the packet
     */
    public void addCard(final MinionCard card) {
        if (card != null) {
            try {
                cards[length] = card;
            } catch (ArrayIndexOutOfBoundsException error) {
                MinionCard[] newCards = new MinionCard[length * 2];
                System.arraycopy(cards, 0, newCards, 0, length);
                cards = newCards;
            }
            length++;
        }
    }

    /**
     * @return the first card in the packet
     */
    public MinionCard getFirstCard() {
        if (this.length > 0) {
            return this.cards[0];
        }
        return null;
    }

    /**
     * @param card removed from the packet
     */
    public void removeCard(final MinionCard card) {
        if (card != null) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i].equals(card)) {
                    for (int j = i; j < cards.length - 1; j++) {
                        cards[j] = cards[j + 1];
                    }
                    cards[length - 1] = null;
                    length--;
                    return;
                }
            }
        }
    }

    /**
     * @param position of the removed card
     */
    public void removeCard(final int position) {
        removeCard(cards[position]);
    }

    /**
     * @return and ArrayNode with information on all the cards in the packet
     */
    public ArrayNode showJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        for (MinionCard card : cards) {
            if (card != null) {
                arrayNode.add(card.showJSON());
            }
        }
        return arrayNode;

    }

    public Player getPlayer() {
        return player;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public MinionCard[] getCards() {
        return cards;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }
}
