package org.poo.operators;

import org.poo.operands.accounts.Account;

public class ReceiveOperator implements Operator {
    public boolean allowOperation(Account account, double amount) {
        if (!account.getCards().isEmpty())
            return true;
        return false;
    }

    public void operate(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }
}
