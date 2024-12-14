package org.poo.commands.workflow;

import org.poo.commands.Command;
import org.poo.fileio.CommandInput;
import org.poo.people.User;
import org.poo.people.accounts.Account;
import org.poo.people.accounts.SavingsAccount;

public class addAccount implements Command {
    private User user;
    private Account account;

    public addAccount(User user, CommandInput accountInput) {
        this.user = user;
        if (accountInput.getAccountType().equals("classic"))
            account = new Account(accountInput);
        if (accountInput.getAccountType().equals("savings"))
            account = new SavingsAccount(accountInput);
    }

    @Override
    public void execute() {
        user.getAccounts().add(account);
    }
}
