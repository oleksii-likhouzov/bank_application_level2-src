package org.test.bankapp.model;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.test.NotEnoughFundsException;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final Logger log = LogManager.getLogger(Client.class);
    private String name;
    private List<Account> accounts = new ArrayList<Account>();
    private Account activeAccount;
    private float initialOverdraft;
    private Gender gender;
    private final static int INITIAL_OVERDRTAFT = 300;
    public final static String CLIENT_CHECKING_ACCOUNT_TYPE = "checking";
    public final static String CLIENT_SAVING_ACCOUNT_TYPE = "saving";

    public Client() {
        this(INITIAL_OVERDRTAFT);
    }

    public Client(float initialOverdraft) {
        Account account = createAccount(initialOverdraft == 0 ? CLIENT_SAVING_ACCOUNT_TYPE : CLIENT_CHECKING_ACCOUNT_TYPE);
        this.initialOverdraft = initialOverdraft;
        if (account != null &&
                account instanceof CheckingAccount) {
            ((CheckingAccount) account).setOverdraft(initialOverdraft);
        }
        addAccount(account);
    }

    public Client(Gender gender) {
        this(INITIAL_OVERDRTAFT);
        setGender(gender);
    }

    public Client(Gender gender, float initialOverdraft) {
        this(INITIAL_OVERDRTAFT);
        setGender(gender);
    }

    public void addAccount(Account account) {

        if (activeAccount == null) {
            setActiveAccount(account);
        }
        accounts.add(account);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public String getName() {
        return name;
    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void deposit(float x) {
        if (activeAccount != null) {
            activeAccount.deposit(x);
        } else {
            log.log(Level.ERROR, "Deposit is not possible. \n" +
                    "Ddeposit with value" +
                    x +
                    "  for active account is not possible. Account not defined. ");
            throw new RuntimeException("Deposit is not possible. \n" +
                    "Ddeposit with value" +
                    x +
                    "  for active account is not possible. Account not defined. ");
        }
    }

    public void withdraw(float x) throws NotEnoughFundsException {
        if (activeAccount != null) {
            activeAccount.withdraft(x);
        } else {
            log.log(Level.ERROR, "Withdraft( is not possible. \n" +
                    "Withdraft with value" +
                    x +
                    "  for active account is not possible. Account not defined. ");
            throw new RuntimeException("Withdraft( is not possible. \n" +
                    "Withdraft with value" +
                    x +
                    "  for active account is not possible. Account not defined. ");
        }
    }

    public Account createAccount(String accountType) {
        Account newAccout;
        if (!accountType.equals(CLIENT_CHECKING_ACCOUNT_TYPE) && !accountType.equals(CLIENT_SAVING_ACCOUNT_TYPE)) {
            log.log(Level.ERROR, "Account creation is not possible. \n" +
                    "Defined accountType: " +
                    accountType +
                    "  is not accessible. Accessible list(" +
                    CLIENT_CHECKING_ACCOUNT_TYPE + ", " +
                    CLIENT_SAVING_ACCOUNT_TYPE +
                    ". ");
            throw new RuntimeException("Account creation is not possible. \n" +
                    "Defined accountType: " +
                    accountType +
                    "  is not accessible. Accessible list(" +
                    CLIENT_CHECKING_ACCOUNT_TYPE + ", " +
                    CLIENT_SAVING_ACCOUNT_TYPE +
                    ". ");

        } else {
            if (accountType.equals(CLIENT_CHECKING_ACCOUNT_TYPE)) {
                newAccout = new CheckingAccount(initialOverdraft);
            } else {
                newAccout = new SavingAccount();
            }
        }
        return newAccout;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public float getBalance() {
        Account tmpAccount;
        float result = 0.f;
        for (int i = 0; i < accounts.size(); i++) {
            tmpAccount = accounts.get(i);
            if (tmpAccount != null) {
                result = result + tmpAccount.getBalance();
            }
        }
        return result;
    }
    public void  printReport() {
        System.out.println("  Client name       : " + getClientSalutation());
        System.out.format("  Client overdraft  : %.2f\n", getInitialOverdraft());
        System.out.format("  Client balance    : %.2f\n", getBalance());
        System.out.println("  Active account    :");
        getActiveAccount().printReport();
        List<Account> accounts = getAccounts();
        System.out.println("  Client accounts information  (accounts count " + accounts.size() + ") :");
        for (int j = 1; j <= accounts.size(); j++) {
            //System.out.println("--------------------------------------------------------------");
            System.out.println("Account # [" + j + "]");
            accounts.get(j - 1).printReport();
            System.out.println("--------------------------------------------------------------");
        }
    }
    public String getClientSalutation() {
        if (gender == null) return name;
        return gender.gender +". "+  name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", accounts=" + accounts +
                ", activeAccount=" + activeAccount +
                ", initialOverdraft=" + initialOverdraft +
                ", gender=" + gender +
                '}';
    }
}
