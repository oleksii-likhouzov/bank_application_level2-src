package org.test.org.test.bankapp.service;

import org.test.bankapp.model.Account;
import org.test.bankapp.model.Bank;
import org.test.bankapp.model.Client;

/**
 * Created by alykhouzov on 03.03.2016.
 * который будет осуществлять добавление клиента и прочие сервисные операции
 */
public interface BankService {
    void addClient(Bank bank, Client client);
    void removeClient(Bank bank,Client client);
    void addAccount(Client client, Account account);
    void setActiveAccount(Client client, Account account);
}
