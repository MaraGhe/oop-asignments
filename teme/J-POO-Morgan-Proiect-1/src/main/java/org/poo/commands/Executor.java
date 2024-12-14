package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankdata.Bank;
import org.poo.commands.debug.*;
import org.poo.commands.workflow.*;
import org.poo.fileio.CommandInput;

public class Executor {
    Bank bank;

    public Executor(Bank bank) {
        this.bank = bank;
    }

    public Command createCommand(CommandInput input, ArrayNode output) {
        switch (input.getCommand()) {
            case "printUsers":
                return new printUsers(bank, output, input.getTimestamp());
            case "printTransactions":
                return new printTransactions(bank.getUserByEmail(input.getEmail()), output, input.getTimestamp());
            case "addAccount":
                return new addAccount(bank.getUserByEmail(input.getEmail()), input);
            case "addFunds":
                return new addFunds(bank.getAccountByIBAN(input.getAccount()), input.getAmount());
            case "createCard":
                if (bank.UserOwnsAccount(input.getEmail(), input.getAccount()))
                    return new createCard(bank.getAccountByIBAN(input.getAccount()), input.getCommand());
                else
                    return null;
            default:
                return null;
        }
    }
}
