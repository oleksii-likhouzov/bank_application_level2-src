package org.test;

/**
 * Created by alykhouzov on 03.03.2016.
 */
public class BankServiceImpl implements  BankService {

    public void addClient(Bank bank, Client client) throws ClientExistsException {
        bank.addClient(client);
    }

    public void removeClient(Bank bank, Client client) {

    }

    public void addAccount(Client client, Account account) {

    }

    public void setActiveAccount(Client client, Account account) {

    }
}
