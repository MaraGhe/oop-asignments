package org.poo.people.accounts;

import org.poo.cards.Card;
import org.poo.fileio.CommandInput;
import org.poo.transactions.Transaction;

import java.util.ArrayList;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String alias, String IBAN, double balance, String currency,
                          String accountType, ArrayList<Card> cards,
                          ArrayList<Transaction> transactions, double interestRate) {
        super(alias, IBAN, balance, currency, accountType, cards, transactions);
        this.interestRate = interestRate;
    }

    public SavingsAccount(CommandInput input) {
        super(input);
        interestRate = input.getInterestRate();
    }
}
