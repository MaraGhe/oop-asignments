package org.poo.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.gameplay.Player;

public final class MinionCard extends Card {
    private int attackDamage;
    private boolean isFrozen;

    public MinionCard(final MinionCard other) {
        super(other);
        this.attackDamage = other.attackDamage;
        this.isFrozen = other.isFrozen;
    }

    public MinionCard(final Player player, final CardInput inputCard) {
        super(player, inputCard);
        this.attackDamage = inputCard.getAttackDamage();
    }

    /**
     * @return true if this is a special minion, false if it isn't
     */
    public boolean isSpecialMinion() {
        String[] specialNames = {"The Ripper", "Miraj", "The Cursed One", "Disciple"};
        for (String name : specialNames) {
            if (this.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTank() {
        return name.equals("Goliath") || name.equals("Warden");
    }

    /**
     * @param enemy that gets attacked
     */
    public void attack(final Card enemy) {
        enemy.getAttacked(this.attackDamage);
        this.usedAttack = true;
    }

    /**
     * @return an ObjectNode with all the information about the minion
     */
    public ObjectNode showJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", mana);
        objectNode.put("attackDamage", attackDamage);
        objectNode.put("health", health);
        objectNode.put("description", description);
        ArrayNode colorsArray = objectNode.putArray("colors");
        for (String color : colors) {
            colorsArray.add(color);
        }
        objectNode.put("name", name);
        return objectNode;
    }

    private void weakKnees(final MinionCard minion) {
        if (minion.attackDamage < 2) {
            minion.attackDamage = 0;
        } else {
            minion.attackDamage -= 2;
        }
    }

    private void skyjack(final MinionCard minion) {
        int aux;
        aux = this.health;
        this.health = minion.health;
        minion.health = aux;
    }

    private void shapeshift(final MinionCard minion) {
        int aux;
        aux = minion.health;
        minion.health = minion.attackDamage;
        minion.attackDamage = aux;
    }

    private void godsPlan(final MinionCard minion) {
        minion.health += 2;
    }

    /**
     * @param minion that the ability is used on
     */
    public void abilityAttack(final MinionCard minion) {
        if (!this.isSpecialMinion()) {
            return;
        }
        this.usedAttack = true;
        switch (this.name) {
            case "The Ripper":
                this.weakKnees(minion);
                break;
            case "Miraj":
                this.skyjack(minion);
                break;
            case "The Cursed One":
                this.shapeshift(minion);
                break;
            case "Disciple":
                this.godsPlan(minion);
                break;
            default:
                break;
        }
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(final boolean frozen) {
        isFrozen = frozen;
    }
}
