package org.poo.moneyHandling;

import org.poo.people.accounts.Account;

public interface MoneyHandler {
    public boolean allowOperation(Account account, double amount);
    public void operate(Account account, double amount);
}
