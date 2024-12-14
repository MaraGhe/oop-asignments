package org.poo.commands.workflow;

import org.poo.commands.Command;
import org.poo.moneyHandling.MoneyHandler;
import org.poo.moneyHandling.ReceiveOperator;
import org.poo.people.User;
import org.poo.people.accounts.Account;

public class addFunds implements Command {
    private Account account;
    private double amount;
    private MoneyHandler operator = new ReceiveOperator();

    public addFunds(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        operator.operate(account, amount);
    }
}
