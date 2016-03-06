package org.test.util;

import org.test.bankapp.model.Client;

public class GetAccountsCommand implements Command {
    public void execute() {
        System.out.println("--------------------------------\n" +
                "- [Active client account list] - \n" +
                "--------------------------------");
        Client currentClient = BankCommander.currentClient;
        if (currentClient == null) {
            throw new RuntimeException("Active client not defined");
        }
        if (currentClient.getActiveAccount() != null) {
            System.out.println("Active account:");
            currentClient.getActiveAccount().printReport();
        }
        System.out.println("Account list:");
        for (int i = 0; currentClient.getAccounts() != null &&
                i < currentClient.getAccounts().size(); i++) {
            System.out.println("Account[" + i + "]:");
            currentClient.getAccounts().get(i).printReport();
        }
    }

    public void printCommandInfo() {
        System.out.println("Get Client accounts list");
    }
}
