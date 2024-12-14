package org.poo.commands.debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankdata.Bank;
import org.poo.commands.Command;
import org.poo.people.User;

public class printUsers implements Command {
    private Bank bank;
    private ArrayNode output;
    private int timestamp;

    public printUsers(Bank bank, ArrayNode output, int timestamp) {
        this.bank = bank;
        this.output = output;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode usersArray = mapper.createArrayNode();
        for (User user : bank.getUsers()) {
            usersArray.add(user.convertJSON());
        }
        output.addObject().put("command", "printUsers")
                .putPOJO("output", usersArray)
                .put("timestamp", timestamp);
    }
}
