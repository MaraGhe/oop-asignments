package org.poo.bankdata;

import org.poo.fileio.MerchantInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;
import org.poo.operands.User;

import java.util.ArrayList;

public class Bank {
    ArrayList <User> users;
    ArrayList <ExchangeRate> exchangeRates;
    ArrayList <MerchantGroup> merchantGroups;

    public Bank(ObjectInput input) {
        this.users = new ArrayList<>();
        this.exchangeRates = new ArrayList<>();
        this.merchantGroups = new ArrayList<>();
        for (UserInput userInput : input.getUsers())
            this.users.add(new User(userInput));
        for (ExchangeInput exchangeInput : input.getExchangeRates())
            this.exchangeRates.add(new ExchangeRate(exchangeInput));
        for (MerchantInput merchantInput : input.getMerchants())
            this.merchantGroups.add(new MerchantGroup(merchantInput));
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
