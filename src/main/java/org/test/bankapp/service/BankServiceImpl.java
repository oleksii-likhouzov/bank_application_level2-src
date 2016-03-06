package org.test.bankapp.service;

import org.test.bankapp.ClientExistsException;
import org.test.bankapp.model.Account;
import org.test.bankapp.model.Bank;
import org.test.bankapp.model.Client;

import java.util.Iterator;
import java.util.List;

public class BankServiceImpl implements BankService {

    public void addClient(Bank bank, Client client) throws ClientExistsException {

            bank.addClient(client);
    }

    public void removeClient(Bank bank, Client client) {

        List<Client> clients = bank.getClients();
        Iterator iter = bank.getClients().iterator();
        while (iter.hasNext()) {
            Client tempClient = (Client) iter.next();
            if (tempClient.getName().equals(client.getName())) {
                iter.remove();
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

    public Client findClientByName(Bank bank, String clientName) {

        List<Client> clients = bank.getClients();
        for (int i = 0; clients !=null &&  i < clients.size(); i++) {
            if (clientName.equals(clients.get(i).getName())) {
                return clients.get(i);
            }
        }
        return null;
    }
}
