package org.poo.people;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.UserInput;
import org.poo.people.accounts.Account;
import org.poo.transactions.Transaction;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;

    public User(String firstName, String lastName, String email, ArrayList<Account> accounts, ArrayList<Transaction> transactions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accounts = accounts;
        this.transactions = transactions;
    }

    public User (UserInput input) {
        this(input.getFirstName(), input.getLastName(), input.getEmail(), new ArrayList<>(), new ArrayList<>());
    }

    public ObjectNode convertJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode out = mapper.createObjectNode();
        ArrayNode accountsNode = mapper.createArrayNode();
        for (Account account : accounts) {
            accountsNode.add(account.convertJSON());
        }
        out.put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("accounts", accountsNode);
        return out;
    }

    public ArrayNode getTransactionsJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode out = mapper.createArrayNode();
        for (Transaction transaction : transactions) {
            out.add(transaction.convertJSON());
        }
        return out;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}