package org.poo.bankdata;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.commands.Command;
import org.poo.commands.Executor;
import org.poo.fileio.*;
import org.poo.people.User;
import org.poo.people.accounts.Account;

import java.util.ArrayList;

public class Bank {
    ArrayList <User> users;
    ArrayList <ExchangeRate> exchangeRates;
    ArrayList <MerchantGroup> merchantGroups;
    Executor executor = new Executor(this);

    public Bank(ObjectInput input) {
        this.users = new ArrayList<>();
        this.exchangeRates = new ArrayList<>();
        this.merchantGroups = new ArrayList<>();
        for (UserInput userInput : input.getUsers())
            this.users.add(new User(userInput));
        for (ExchangeInput exchangeInput : input.getExchangeRates())
            this.exchangeRates.add(new ExchangeRate(exchangeInput));
        if (input.getMerchants() != null)
            for (MerchantInput merchantInput : input.getMerchants())
                this.merchantGroups.add(new MerchantGroup(merchantInput));
    }

    public User getUserByEmail(String email) {
        for (User user : users)
            if (user.getEmail().equals(email))
                return user;
        return null;
    }

    public User getUserByIBAN(String iban) {
        for (User user : users)
            for (Account account : user.getAccounts())
                if (account.getIBAN().equals(iban))
                    return user;
        return null;
    }

    public Account getAccountByIBAN(String iban) {
        for (User user : users)
            for (Account account : user.getAccounts())
                if (account.getIBAN().equals(iban))
                    return account;
        return null;
    }

    public boolean UserOwnsAccount(String email, String iban) {
        User user1 = getUserByEmail(email);
        User user2 = getUserByIBAN(iban);
        if (user1 != null && user2 != null && user1.equals(user2))
            return true;
        return false;
    }

    public void executeCommands(ArrayNode output, ArrayList<CommandInput> input) {
        Command command;
        for (CommandInput commandInput : input) {
            command = executor.createCommand(commandInput, output);
            if (command == null) {
                /// TODO tranzactie eronata
            } else {
                command.execute();
            }
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(ArrayList<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public ArrayList<MerchantGroup> getMerchantGroups() {
        return merchantGroups;
    }

    public void setMerchantGroups(ArrayList<MerchantGroup> merchantGroups) {
        this.merchantGroups = merchantGroups;
    }
}
