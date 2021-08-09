package com.easyept.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Credit {

    @Id
    @GeneratedValue
    private UUID uuid;

    private int creditLimit;

    private float creditRate;

    public Credit(int creditLimit, float creditRate) {
        this.creditLimit = creditLimit;
        this.creditRate = creditRate;

    }

    public String creditNameToString() {
        return "" + creditRate + "%, Limit: " + creditLimit;
    }

}
