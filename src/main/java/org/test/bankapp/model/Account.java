package org.test.bankapp.model;

import org.test.NotEnoughFundsException;
import org.test.Report;

public interface Account extends Report {
    // Получить баланс
    float getBalance();

    // Положить деньги на счет
    void deposit(float x);

    // Снять деньги со счета
    void withdraft(float x) throws NotEnoughFundsException;

}
