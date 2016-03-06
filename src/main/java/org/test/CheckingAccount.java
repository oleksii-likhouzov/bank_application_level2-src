package org.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CheckingAccount extends AbstractAccount {
    private static final Logger log = LogManager.getLogger(CheckingAccount.class);
    private float overdraft;

    public float getOverdraft() {
        return overdraft;
    }

    public CheckingAccount(float overdraft) {
        setOverdraft(overdraft);
    }

    public void setOverdraft(float x) {
        overdraft = x;
    }


    public void withdraft(float x) throws NotEnoughFundsException {
        if (x < 0.) {
            log.log(Level.ERROR, "Value of \"withdraft\" = " + x + "  < 0. ");
            throw new IllegalArgumentException("Value of \"balance\" = " + x + "  < 0. ");
        }
        if ((getBalance() + overdraft - x) < 0) {
            log.log(Level.INFO, "Withdraw is not possible. \n" +
                    "Withdraft with value " +
                    x +
                    "  for account is not possible. Account balance: " +
                    getBalance() +
                    ". Account overdraft:" + overdraft);
            throw new OverDraftLimitExceededException (this, x);
        }
        setBalance(getBalance() - x);
    }

    @Override
    public void printReport() {
        super.printReport();
        System.out.println("  Overdraft: " + overdraft);
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                super.toString() +
                ", overdraft=" + overdraft +
                '}';
    }
}
