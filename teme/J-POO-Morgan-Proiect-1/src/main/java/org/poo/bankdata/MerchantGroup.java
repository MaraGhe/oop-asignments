package org.poo.bankdata;

import org.poo.fileio.MerchantInput;

import java.util.List;

public class MerchantGroup {
    private int id;
    private String description;
    private List<String> merchants;

    public MerchantGroup(int id, String description, List<String> merchants) {
        this.id = id;
        this.description = description;
        this.merchants = merchants;
    }

    public MerchantGroup(MerchantInput input) {
        this(input.getId(), input.getDescription(), input.getCommerciants());
    }
}
