package org.poo.operators;

import org.poo.operands.accounts.Account;

public class PayOperator implements Operator {
    public boolean allowOperation(Account account, double amount) {
        if (account.getBalance() <= amount)
            return true;
        return false;
    }

    public void operate(Account account, double amount) {
        account.setBalance(account.getBalance() - amount);
    }
}
