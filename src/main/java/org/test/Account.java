package org.test;

public interface Account extends Report {
    // Получить баланс
    public float getBalance();

    // Положить деньги на счет
    public void deposit(float x);

    // Снять деньги со счета
    public void withdraft(float x) throws NotEnoughFundsException;

}
