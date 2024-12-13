package org.poo.commands.debug;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.operands.User;

public class printTransactions {
    public void execute(User user, ArrayNode output) {
        output.addObject().put("command", "printTransactions")
                .putPOJO("output", user.getTransactionsJSON());
    }
}
