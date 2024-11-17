package org.poo.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.cards.cardGroups.PacketOfMinions;
import org.poo.gameplay.Player;

public class HeroCard extends Card {
    private static final int HERO_LIFE = 30;

    public HeroCard(final Player player, final CardInput inputCard) {
        super(player, inputCard);
        this.health = HERO_LIFE;
    }

    public boolean isOffensive() {
        if (name.equals("Lord Royce") || name.equals("Empress Thorina")) {
            return true;
        }
        return false;
    }

    private void subZero(final PacketOfMinions row) {
        for (MinionCard minion : row.getCards()) {
            if (minion != null) {
                minion.setFrozen(true);
            }
        }
    }

    private void lowBlow(final PacketOfMinions row) {
        if (row.getCards().length == 0) {
            return;
        }
        MinionCard victim = row.getCards()[0];
        for (MinionCard minion : row.getCards()) {
            if (minion != null) {
                if (minion.health > victim.health) {
                    victim = minion;
                }
            }
        }
        row.removeCard(victim);
    }

    private void earthBorn(final PacketOfMinions row) {
        for (MinionCard minion : row.getCards()) {
            if (minion != null) {
                minion.health++;
            }
        }
    }

    private void bloodThirst(final PacketOfMinions row) {
        for (MinionCard minion : row.getCards()) {
            if (minion != null) {
                minion.setAttackDamage(minion.getAttackDamage() + 1);
            }
        }
    }

    /**
     * Uses the hero's ability on a given row of minions
     * @param row the ability will be used on
     */
    public void abilityAttack(final PacketOfMinions row) {
        usedAttack = true;
        switch (this.name) {
            case "Lord Royce":
                subZero(row);
                break;
            case "Empress Thorina":
                lowBlow(row);
                break;
            case "King Mudface":
                earthBorn(row);
                break;
            case "General Kocioraw":
                bloodThirst(row);
                break;
            default:
                break;
        }
    }

     /**
     * @return an ObjectNode with all the information about the hero
     */
    public ObjectNode showJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", mana);
        objectNode.put("description", description);
        ArrayNode colorsArray = objectNode.putArray("colors");
        for (String color : colors) {
            colorsArray.add(color);
        }
        objectNode.put("name", name);
        objectNode.put("health", health);
        return objectNode;
    }
}
