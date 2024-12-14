package org.poo.commands.workflow;

import org.poo.cards.Card;
import org.poo.cards.OneTimeCard;
import org.poo.commands.Command;
import org.poo.people.accounts.Account;

public class createCard implements Command {
    private Account account;
    private Card card;

    public createCard(Account account, String command) {
        this.account = account;
        if (command.equals("createCard"))
            card = new Card();
        else
            card = new OneTimeCard();
    }

    @Override
    public void execute() {
        account.getCards().add(card);
    }
}
