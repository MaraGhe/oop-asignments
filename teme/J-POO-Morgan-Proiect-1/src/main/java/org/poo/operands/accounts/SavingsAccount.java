package org.poo.operands.accounts;

import org.poo.cards.Card;
import org.poo.transactions.Transaction;

import java.util.ArrayList;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String alias, String IBAN, double balance, String currency,
                          String accountType, double minBalance, ArrayList<Card> cards,
                          ArrayList<Transaction> transactions, double interestRate) {
        super(alias, IBAN, balance, currency, accountType, minBalance, cards, transactions);
        this.interestRate = interestRate;
    }
}
