package org.test.org.test.bankapp.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.test.ClientExistsException;
import org.test.bankapp.model.Account;
import org.test.bankapp.model.Bank;
import org.test.bankapp.model.Client;

import java.util.List;

public class BankServiceImpl implements BankService {
    private static final Logger log = LogManager.getLogger(BankServiceImpl.class);

    public void addClient(Bank bank, Client client) {
        try {
            bank.addClient(client);
        } catch (ClientExistsException exception) {
            log.log(Level.ERROR, "Duplicate client name: \"" + client.getName() + "\"");
        }
    }

    public void removeClient(Bank bank, Client client) {
        List<Client> clients = bank.getClients();
        for (int i = 0; i < clients.size(); i++) {
            if (client.getName().equals(clients.get(i).getName())) {
                clients.remove(i);
                break;
            }
        }
    }

    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }
}
