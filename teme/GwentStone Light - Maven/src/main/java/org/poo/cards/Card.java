package org.poo.cards;

import org.poo.fileio.CardInput;
import org.poo.gameplay.Player;

import java.util.ArrayList;

public class Card {
    protected int mana;
    protected int health;
    protected String description;
    protected String[] colors;
    protected String name;
    protected boolean usedAttack;
    protected Player player;

    public Card(final Card other) {
        this.mana = other.mana;
        this.health = other.health;
        this.description = other.description;
        this.colors = other.colors;
        this.name = other.name;
        this.player = other.player;
        this.usedAttack = other.usedAttack;
    }

    public Card(final Player player, final int mana, final int health,
                   final String description, final ArrayList<String> colors, final String name) {
        this.player = player;
        this.mana = mana;
        this.health = health;
        this.description = description;
        this.colors = new String[colors.size()];
        for (int i = 0; i < colors.size(); i++) {
            this.colors[i] = colors.get(i);
        }
        this.name = name;
    }


    public Card(final Player player, final CardInput inputCard) {
        this(player, inputCard.getMana(), inputCard.getHealth(), inputCard.getDescription(),
                inputCard.getColors(), inputCard.getName());
    }

    /**
     * @param damage for how much damage this card takes
     */
    public final void getAttacked(final int damage) {
        this.health -= damage;
    }

    public final int getMana() {
        return mana;
    }

    public final int getHealth() {
        return health;
    }

    public final String getName() {
        return name;
    }

    public final boolean isUsedAttack() {
        return usedAttack;
    }

    public final void setUsedAttack(final boolean usedAttack) {
        this.usedAttack = usedAttack;
    }

    public final Player getPlayer() {
        return player;
    }
}
