package org.poo.commands.debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankdata.Bank;
import org.poo.operands.User;

public class printUsers {
    public void execute(Bank bank, ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode usersArray = mapper.createArrayNode();
        for (User user : bank.getUsers()) {
            usersArray.add(user.convertJSON());
        }
        output.addObject().put("command", "printUsers")
                .putPOJO("output", usersArray);
    }
}
