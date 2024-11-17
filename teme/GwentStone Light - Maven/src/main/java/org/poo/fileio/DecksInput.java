package org.poo.fileio;

public final class DecksInput {
    private int nrCardsInDeck;
    private int nrDecks;
    private CardInput[][] decks;

    public DecksInput() {
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public CardInput[][] getDecks() {
        return decks;
    }

    public void setDecks(final CardInput[][] decks) {
        this.decks = decks;
    }

    @Override
    public String toString() {
        return "InfoInput{"
                + "nr_cards_in_deck="
                + nrCardsInDeck
                +  ", nr_decks="
                + nrDecks
                + ", decks="
                + decks
                + '}';
    }
}
