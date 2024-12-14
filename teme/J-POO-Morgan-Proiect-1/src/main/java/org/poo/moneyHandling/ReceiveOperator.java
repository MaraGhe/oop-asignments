package org.poo.moneyHandling;

import org.poo.people.accounts.Account;

public class ReceiveOperator implements MoneyHandler {
    public boolean allowOperation(Account account, double amount) {
        if (account == null)
            return false;
        return true;
    }

    public void operate(Account account, double amount) {
        if (allowOperation(account, amount))
            account.setBalance(account.getBalance() + amount);
    }
}
