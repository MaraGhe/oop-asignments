package org.poo.moneyHandling;

import org.poo.people.accounts.Account;

public class PayOperator implements MoneyHandler {
    public boolean allowOperation(Account account, double amount) {
        if (account.getBalance() <= amount)
            return true;
        return false;
    }

    public void operate(Account account, double amount) {
        account.setBalance(account.getBalance() - amount);
    }
}
