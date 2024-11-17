package org.poo.gameplay;

import org.poo.cards.HeroCard;
import org.poo.cards.MinionCard;
import org.poo.cards.cardGroups.GameBoard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.fileio.Input;
import org.poo.fileio.StartGameInput;

import java.util.ArrayList;
import java.util.Random;

public final class Game {
    private GameBoard gameBoard;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player opponentPlayer;
    private int nrDecks;
    private int nrCardsInDeck;
    private int round;
    private int totalGamesPlayed;

    private static final int ERR_MANA = 1;
    private static final int ERR_FULL_ROW = 2;
    private static final int ERR_NOT_ENEMY = 3;
    private static final int ERR_HAS_ATTCKD = 4;
    private static final int ERR_FROZEN = 5;
    private static final int ERR_TANK = 6;
    private static final int ERR_ENEMY = 7;
    private static final int ERR_MANA_HERO = 8;
    private static final int ERR_HERO_HAS_ATTCKD = 9;
    private static final int ERR_ROW_NOT_ENEMY = 10;
    private static final int ERR_ROW_ENEMY = 11;

    private static final int MANA_CAP = 10;

    public Game(final Input input) {
        nrDecks = input.getPlayerOneDecks().getNrDecks();
        nrCardsInDeck = input.getPlayerTwoDecks().getNrCardsInDeck();
        player1 = new Player(1, input.getPlayerOneDecks().getDecks(), nrDecks, nrCardsInDeck);
        player2 = new Player(2, input.getPlayerTwoDecks().getDecks(), nrDecks, nrCardsInDeck);
    }

    /**
     * @param deckIdx1 index of player1's deck
     * @param deckIdx2 index of player2's deck
     * @param shuffleSeed to randomise each deck
     * @param hero1 player1's hero
     * @param hero2 player2's hero
     * @param startingPlayer index of the player that starts the game
     */
    public void prepareNewGame(final int deckIdx1, final int deckIdx2,
                               final int shuffleSeed, final CardInput hero1,
                               final CardInput hero2, final int startingPlayer) {
        gameBoard = new GameBoard();
        player1.renew();
        player2.renew();
        player1.chooseDeck(deckIdx1, new Random(shuffleSeed));
        player2.chooseDeck(deckIdx2, new Random(shuffleSeed));
        if (startingPlayer == 1) {
            currentPlayer = player1;
            opponentPlayer = player2;
        } else {
            currentPlayer = player2;
            opponentPlayer = player1;
        }
        player1.setHero(new HeroCard(player1, hero1));
        player2.setHero(new HeroCard(player2, hero2));
        round = 0;
    }

    /**
     * @param input stores all the data required to begin a game
     */
    public void prepareNewGame(final StartGameInput input) {
        prepareNewGame(input.getPlayerOneDeckIdx(), input.getPlayerTwoDeckIdx(),
                input.getShuffleSeed(), input.getPlayerOneHero(),
                input.getPlayerTwoHero(), input.getStartingPlayer());
    }

    /**
     * Prepares the players and the cards on the board for a new round.
     */
    public void prepareNewRound() {
        round++;
        gameBoard.renewCards();
        player1.setFinishedRound(false);
        player2.setFinishedRound(false);
        player1.resetHero();
        player2.resetHero();
        if (round <= MANA_CAP) {
            player1.addMana(round);
            player2.addMana(round);
        } else {
            player1.addMana(MANA_CAP);
            player2.addMana(MANA_CAP);
        }
        player1.getHand().addCard(player1.getCurrentDeck().getFirstCard());
        player1.getCurrentDeck().removeCard(0);

        player2.getHand().addCard(player2.getCurrentDeck().getFirstCard());
        player2.getCurrentDeck().removeCard(0);
    }

    /**
     * Used after a player has killed the enemy's hero.
     * @param output stores the message for a player winning
     */
    public void endGame(final ArrayNode output) {
        if (currentPlayer == player1) {
            output.addObject().put("gameEnded", "Player one killed the enemy hero.");
        } else {
            output.addObject().put("gameEnded", "Player two killed the enemy hero.");
        }
        currentPlayer.setWins(currentPlayer.getWins() + 1);
        totalGamesPlayed++;
    }

    /**
     * Ends the current player's turn.
     * Checks if a new round must begin.
     */
    public void endPlayerTurn() {
        currentPlayer.setFinishedRound(true);
        changeCurrentPlayer();
        if (player1.isFinishedRound() && player2.isFinishedRound()) {
            prepareNewRound();
        }
    }

    /**
     * Switches the current player and the opponent.
     */
    public void changeCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
            opponentPlayer = player1;
        } else {
            currentPlayer = player1;
            opponentPlayer = player2;
        }
    }

    /**
     * Executes a list of actions.
     * @param actions contains the actions
     * @param output stores any output messages
     */
    public void executeActions(final ArrayList<ActionsInput> actions, final ArrayNode output) {
        for (ActionsInput action : actions) {
            if (action.getCommand().contains("get")) {
                executeDebugAction(action, output);
            } else {
                executeGameplayAction(action, output);
            }
        }
    }

    /**
     * Executes a debugging action.
     * @param action is executed
     * @param output stores the output message
     */
    public void executeDebugAction(final ActionsInput action, final ArrayNode output) {
        Player actionPlayer = null;
        int pIdx = action.getPlayerIdx();
        if (pIdx == 1) {
            actionPlayer = player1;
        }
        if (pIdx == 2) {
            actionPlayer = player2;
        }
        switch (action.getCommand()) {
            case "getCardsInHand":
                output.addObject().put("command", action.getCommand()).
                        put("playerIdx", pIdx)
                        .putPOJO("output", actionPlayer.getCardsInHand());
                break;
            case "getPlayerDeck":
                output.addObject().put("command", action.getCommand()).
                        put("playerIdx", pIdx).
                        putPOJO("output", actionPlayer.getPlayerDeck());
                break;
            case "getPlayerHero":
                output.addObject().put("command", action.getCommand()).
                        put("playerIdx", pIdx).
                        put("output", actionPlayer.getPlayerHero());
                break;
            case "getPlayerMana":
                output.addObject().put("command", action.getCommand()).
                        put("playerIdx", pIdx).
                        put("output", actionPlayer.getMana());
                break;
            case "getPlayerOneWins":
                output.addObject().put("command", action.getCommand()).
                        put("output", player1.getWins());
                break;
            case "getPlayerTwoWins":
                output.addObject().put("command", action.getCommand()).
                        put("output", player2.getWins());
                break;
            case "getCardsOnTable":
                output.addObject().put("command", action.getCommand()).
                        putPOJO("output", gameBoard.getCardsOnTable());
                break;
            case "getCardAtPosition":
                MinionCard minion = gameBoard.
                        getCardAtPosition(action.getX(), action.getY());
                if (minion != null) {
                    output.addObject().put("command", action.getCommand()).
                            put("x", action.getX()).
                            put("y", action.getY()).
                            putPOJO("output", minion.showJSON());
                } else {
                    output.addObject().put("command", action.getCommand()).
                            put("x", action.getX()).
                            put("y", action.getY()).
                            put("output", "No card available at that position.");
                }
                break;
            case "getFrozenCardsOnTable":
                output.addObject().put("command", action.getCommand()).
                        putPOJO("output", gameBoard.getFrozenCardsOnTable());
                break;
            case "getPlayerTurn":
                output.addObject().put("command", action.getCommand()).
                        put("output", currentPlayer.getIndex());
                break;
            case "getTotalGamesPlayed":
                output.addObject().put("command", action.getCommand()).
                        put("output", totalGamesPlayed);
                break;
            default:
                break;
        }
    }

    /**
     * Executes a gameplay action.
     * @param action is executed
     * @param output stores potential error or "player has won" messages
     */
    public void executeGameplayAction(final ActionsInput action, final ArrayNode output) {
        if (action.getCommand().equals("endPlayerTurn")) {
            endPlayerTurn();
        } else if (action.getCommand().equals("placeCard")) {
            error(currentPlayer.placeCard(action.getHandIdx(), gameBoard), action, output);
        } else if (action.getCommand().equals("cardUsesAttack")) {
            MinionCard attacker = gameBoard.getCards()[action.getCardAttacker().getX()].
                    getCards()[action.getCardAttacker().getY()];
            MinionCard attacked = gameBoard.getCards()[action.getCardAttacked().getX()].
                    getCards()[action.getCardAttacked().getY()];
            if (attacker != null && attacked != null) {
                if (attacked.getPlayer().equals(currentPlayer)) {
                    error(ERR_NOT_ENEMY, action, output);
                    return;
                }
                if (attacker.isUsedAttack()) {
                    error(ERR_HAS_ATTCKD, action, output);
                    return;
                }
                if (attacker.isFrozen()) {
                    error(ERR_FROZEN, action, output);
                    return;
                }
                if (!attacked.isTank() && gameBoard.hasTanks(opponentPlayer)) {
                    error(ERR_TANK, action, output);
                    return;
                }
                attacker.attack(attacked);
                if (attacked.getHealth() <= 0) {
                    gameBoard.getCards()[action.getCardAttacked().getX()].removeCard(attacked);
                }
            }
        } else if (action.getCommand().equals("cardUsesAbility")) {
            MinionCard attacker = gameBoard.getCards()[action.getCardAttacker().getX()].
                    getCards()[action.getCardAttacker().getY()];
            MinionCard attacked = gameBoard.getCards()[action.getCardAttacked().getX()].
                    getCards()[action.getCardAttacked().getY()];
            if (attacker == null || attacked == null) {
                return;
            }
            if (attacker.isFrozen()) {
                error(ERR_FROZEN, action, output);
                return;
            }
            if (attacker.isUsedAttack()) {
                error(ERR_HAS_ATTCKD, action, output);
                return;
            }
            if (attacker.getName().equals("Disciple")
                    && !attacked.getPlayer().equals(currentPlayer)) {
                error(ERR_ENEMY, action, output);
                return;
            }
            if (!attacker.getName().equals("Disciple")) {
                if (attacked.getPlayer().equals(currentPlayer)) {
                    error(ERR_NOT_ENEMY, action, output);
                    return;
                } else if (!attacked.isTank() && gameBoard.hasTanks(opponentPlayer)) {
                    error(ERR_TANK, action, output);
                    return;
                }
            }
            attacker.abilityAttack(attacked);
            if (attacked.getHealth() <= 0) {
                gameBoard.getCards()[action.getCardAttacked().getX()].removeCard(attacked);
            }
        } else if (action.getCommand().equals("useAttackHero")) {
            MinionCard attacker = gameBoard.getCards()[action.getCardAttacker().getX()].
                    getCards()[action.getCardAttacker().getY()];
            if (attacker != null) {
                if (attacker.isFrozen()) {
                    error(ERR_FROZEN, action, output);
                    return;
                }
                if (attacker.isUsedAttack()) {
                    error(ERR_HAS_ATTCKD, action, output);
                    return;
                }
                if (gameBoard.hasTanks(opponentPlayer)) {
                    error(ERR_TANK, action, output);
                    return;
                }
                attacker.attack(opponentPlayer.getHero());
                if (opponentPlayer.getHero().getHealth() <= 0) {
                    endGame(output);
                }
            }
        } else if (action.getCommand().equals("useHeroAbility")) {
            if (currentPlayer.getMana() < currentPlayer.getHero().getMana()) {
                error(ERR_MANA_HERO, action, output);
                return;
            }
            if (currentPlayer.getHero().isUsedAttack()) {
                error(ERR_HERO_HAS_ATTCKD, action, output);
                return;
            }
            int row = action.getAffectedRow();
            int currentLine;
            if (currentPlayer.getIndex() == 1) {
                currentLine = 2;
            } else {
                currentLine = 0;
            }
            if (currentPlayer.getHero().isOffensive()
                    && (row == currentLine || row == currentLine + 1)) {
                error(ERR_ROW_NOT_ENEMY, action, output);
                return;
            }
            if (!currentPlayer.getHero().isOffensive()
                    && row != currentLine && row != currentLine + 1) {
                error(ERR_ROW_ENEMY, action, output);
                return;
            }

            currentPlayer.getHero().abilityAttack(gameBoard.getCards()[row]);
            currentPlayer.reduceMana(currentPlayer.getHero().getMana());
        }
    }

    /**
     * Adds error texts to the output node.
     * @param errorCode one for each possible error
     * @param action that generated the error
     * @param output stores the error message
     */
    public void error(final int errorCode, final ActionsInput action, final ArrayNode output) {
        if (errorCode == 0) {
            return;
        }
        String errorText = "";
        switch (errorCode) {
            case ERR_MANA:
                errorText = "Not enough mana to place card on table.";
                break;
            case ERR_FULL_ROW:
                errorText = "Cannot place card on table since row is full.";
                break;
            case ERR_NOT_ENEMY:
                errorText = "Attacked card does not belong to the enemy.";
                break;
            case ERR_HAS_ATTCKD:
                errorText = "Attacker card has already attacked this turn.";
                break;
            case ERR_FROZEN:
                errorText = "Attacker card is frozen.";
                break;
            case ERR_TANK:
                errorText = "Attacked card is not of type 'Tank'.";
                break;
            case ERR_ENEMY:
                errorText = "Attacked card does not belong to the current player.";
                break;
            case ERR_MANA_HERO:
                errorText = "Not enough mana to use hero's ability.";
                break;
            case ERR_HERO_HAS_ATTCKD:
                errorText = "Hero has already attacked this turn.";
                break;
            case ERR_ROW_NOT_ENEMY:
                errorText = "Selected row does not belong to the enemy.";
                break;
            case ERR_ROW_ENEMY:
                errorText = "Selected row does not belong to the current player.";
                break;
            default:
                break;
        }

        if (errorCode <= ERR_FULL_ROW) {
            output.addObject().put("command", action.getCommand()).
                    put("handIdx", action.getHandIdx()).
                    put("error", errorText);
        } else if (errorCode <= ERR_ENEMY) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode coord1 = mapper.createObjectNode();
            coord1.put("x", action.getCardAttacker().getX());
            coord1.put("y", action.getCardAttacker().getY());
            if (!action.getCommand().equals("useAttackHero")) {
                ObjectNode coord2 = mapper.createObjectNode();
                coord2.put("x", action.getCardAttacked().getX());
                coord2.put("y", action.getCardAttacked().getY());
                output.addObject().put("command", action.getCommand()).
                        putPOJO("cardAttacker", coord1).
                        putPOJO("cardAttacked", coord2).
                        put("error", errorText);
            } else {
                output.addObject().put("command", action.getCommand()).
                        putPOJO("cardAttacker", coord1).
                        put("error", errorText);
            }
        } else {
            output.addObject().put("command", action.getCommand()).
                    put("affectedRow", action.getAffectedRow()).
                    put("error", errorText);
        }
    }

}
