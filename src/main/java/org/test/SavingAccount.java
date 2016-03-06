package org.test;

import org.apache.logging.log4j.*;

public final class SavingAccount extends AbstractAccount {
    private static final Logger log = LogManager.getLogger(SavingAccount.class);

    public void withdraft(float x) {
        if (x < 0.) {
            log.log(Level.ERROR, "Value of \"withdraft\" = " + x + "  < 0. ");
            throw new IllegalArgumentException("Value of \"balance\" = " + x + "  < 0. ");
        };
        if ((getBalance() - x) < 0) {
            log.log(Level.ERROR,
                    "Withdraw is not possible. \n" +
                            "Withdraft with value" +
                            x +
                            "  for account is not possible. Account balance: " +
                            getBalance());
            throw new RuntimeException("Withdraw is not possible. \n" +
                    "Withdraft with value" +
                    x +
                    "  for account is not possible. Account balance: " +
                    getBalance());
        }
        setBalance(getBalance() - x);
    }

    @Override
    public String toString() {
        return "SavingAccount{" +
                super.toString() +
                "}";
    }
}
