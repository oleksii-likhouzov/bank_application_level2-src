package org.test;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BankApplication {
    private static final Logger log = LogManager.getLogger(BankApplication.class);
    private Bank bank;


    private void addClient(String clientName ) {
        BankService bankService = new BankServiceImpl();

        Client client = new Client();
        client.setName(clientName);

        try {
            bankService.addClient(bank, client);

        }   catch (ClientExistsException exception)
        {
            log.log(Level.ERROR, "Duplicate client name: \""+clientName+"\"");
        }
    }
    private void addClient(String clientName, float initialOverdraft ) {
        Client client = new Client(initialOverdraft);
        try {
            client.setName(clientName);
            bank.addClient(client);

        }   catch (ClientExistsException exception)
        {
            log.log(Level.ERROR, "Duplicate client name: \""+clientName+"\"");
        }
    }
    public void initialize()
    {
        bank = new Bank();
        addClient("First Client");
        addClient("Second Client",100);
        addClient("Third Client",0);
        addClient("Third Client",-1);
        List<Client> clients =  bank.getClients();
        Client tmpClient = clients.get(2);
        tmpClient.addAccount( tmpClient.createAccount(Client.CLIENT_SAVING_ACCOUNT_TYPE));
        tmpClient.addAccount( tmpClient.createAccount(Client.CLIENT_CHECKING_ACCOUNT_TYPE));

        for(Client client: clients) {
            List<Account> accounts = client.getAccounts();
            for (Account account : accounts) {
                account.deposit(((int) (Math.random() * 100 * 100)) / 100.f);
            }
            for (Account account : accounts) {
                if (account != null &&
                        account instanceof CheckingAccount) {
                    try {
                        account.withdraft(
                                2* account.getBalance() + (int) (((CheckingAccount) account).getOverdraft()));
                    }
                    catch (OverDraftLimitExceededException exception) {
                        log.log(Level.WARN,
                        "Withdraft with value " +
                                exception.getWithdraft() +
                                "  for account is not possible. Account balance: " +
                                exception.getBalance() +
                                ". Account overdraft:" + exception.getOverdraft());

                        try {
                            account.withdraft(
                                    account.getBalance() + (int) (((CheckingAccount) account).getOverdraft()  / 2 ));
                        } catch (NotEnoughFundsException innerException) {
                            log.log(Level.ERROR, exception);
                        }
                    }
                    catch (NotEnoughFundsException exception ) {
                        try {
                            account.withdraft(
                                    account.getBalance() + (int) (((CheckingAccount) account).getOverdraft() / 2) );
                        } catch (NotEnoughFundsException innerException) {
                            log.log(Level.ERROR, exception);
                        }

                    }
                }

            }
        }
    }
    // оторый изменяет значение баланса (методы deposit() и withdraw()) для некоторых счетов клиентов банка.
    public void  modifyBank()
    {
        if (bank!=null) {
            List<Client> clients = bank.getClients();
            for (int i = 1; clients != null && i <= clients.size(); i++) {
                Client client = clients.get(i - 1);
                Account clientActiveAccount = client.getActiveAccount();
                float clientBalance = clientActiveAccount.getBalance();
                if (clientBalance > 0) {
                    try {
                        clientActiveAccount.withdraft(Math.round(clientBalance / 2.f*100)/100);
                    } catch (NotEnoughFundsException exception) {
                        log.log(Level.WARN,
                                "Withdraft with value " +
                                        Math.round((clientBalance / 2.f*100)/100) +
                                        "  for account is not possible. Account balance: " +
                                        clientBalance);
                    }
                }
                else{
                    clientActiveAccount.deposit(-Math.round(clientBalance/2.f*100)/100);
                }
            }
        }
    }
    public void printBankReport()
    {
       if (bank!=null) {
           bank.printReport();
       } else {
           log.log(Level.ERROR, "Bank application is not initialized.");
           throw new RuntimeException("Bank application is not initialized.");
       }

    }
    public static void main(String [] argv)
    {
        BankApplication bankApplication = new BankApplication();
        bankApplication.initialize();
        bankApplication.printBankReport();
        bankApplication.modifyBank();
        bankApplication.printBankReport();
    }
}
