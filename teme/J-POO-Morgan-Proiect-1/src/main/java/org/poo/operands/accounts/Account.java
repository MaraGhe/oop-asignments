package org.poo.operands.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.cards.Card;
import org.poo.transactions.Transaction;

import java.util.ArrayList;

public class Account {
    private String alias;
    private String IBAN;
    private double balance;
    private String currency;
    private String accountType;
    private double minBalance;
    private ArrayList<Card> cards;
    private ArrayList<Transaction> transactions;

    public Account(String alias, String IBAN, double balance, String currency,
                   String accountType, double minBalance, ArrayList<Card> cards,
                   ArrayList<Transaction> transactions) {
        this.alias = alias;
        this.IBAN = IBAN;
        this.balance = balance;
        this.currency = currency;
        this.accountType = accountType;
        this.minBalance = minBalance;
        this.cards = cards;
        this.transactions = transactions;
    }

    public ObjectNode convertJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode out = mapper.createObjectNode();
        ArrayNode cardsArray = mapper.createArrayNode();
        for (Card card : cards) {
            cardsArray.addPOJO(card.toJson());
        }
        out.put("IBAN", IBAN)
                .put("balance", balance)
                .put("currency", currency)
                .put("type", accountType)
                .putPOJO("cards", cardsArray);
        return out;
    }

    public boolean containsCard(String cardNumber) {
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return true;
            }
        }
        return false;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

}