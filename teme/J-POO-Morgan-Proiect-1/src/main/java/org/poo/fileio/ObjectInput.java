package org.poo.fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public final class ObjectInput {
    private ArrayList<UserInput> users;
    private ArrayList<ExchangeInput> exchangeRates;
    private ArrayList<CommandInput> commands;
    private ArrayList<MerchantInput> merchants;
}
