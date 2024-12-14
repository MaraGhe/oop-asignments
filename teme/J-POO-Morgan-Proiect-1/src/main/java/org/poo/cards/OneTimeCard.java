package org.poo.cards;

public class OneTimeCard extends Card {
    private boolean hasBeenUsed;

    public OneTimeCard() {
        super();
        hasBeenUsed = false;
    }

    public boolean isHasBeenUsed() {
        return hasBeenUsed;
    }

    public void setHasBeenUsed(boolean hasBeenUsed) {
        this.hasBeenUsed = hasBeenUsed;
    }
}
