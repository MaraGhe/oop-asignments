package org.poo.commands.debug;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.commands.Command;
import org.poo.people.User;

public class printTransactions implements Command {
    private User user;
    private ArrayNode output;
    private int timestamp;

    public printTransactions (User user, ArrayNode output, int timestamp) {
        this.user = user;
        this.output = output;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        output.addObject().put("command", "printTransactions")
                .putPOJO("output", user.getTransactionsJSON())
                .put("timestamp", timestamp);
    }
}
