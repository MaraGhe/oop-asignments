package org.poo.operators;

import org.poo.operands.accounts.Account;

public interface Operator {
    public boolean allowOperation(Account account, double amount);
    public void operate(Account account, double amount);
}
