package com.easyept.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Bank {

    @Id
    @GeneratedValue
    @Column(name = "bank_uuid")
    private UUID uuid;

    private String bankName;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "bank_uuid")
    @ToString.Exclude
    private Set<Credit> credits = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "bank_uuid")
    @ToString.Exclude
    private Set<Client> clients = new HashSet<>();

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    public void addClient(Client addClient){
        if (clients == null) {
            clients = new HashSet<>();
        }
        clients.add(addClient);
    }

    public void addCredit(Credit addCredit){
        if (credits == null) {
            credits = new HashSet<>();
        }
        credits.add(addCredit);
    }

    @Override
    public String toString() {
        return  bankName + ": " + uuid +" ||" + clients;

    }
}
