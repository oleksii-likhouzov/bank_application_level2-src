package org.test.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.test.bankapp.model.Bank;
import org.test.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BankCommander {
    private static final Logger log = LogManager.getLogger(BankCommander.class);
    public static Bank currentBank = new Bank();
    public static Client currentClient;


    static Command[] commands = {
            new FindClientCommand(), // 1 Находить клиента по имени
            new GetAccountsCommand(), // 2   Получать список счетов клиента и остаток на счетах
            new DepositCommand(), // 3 Пополнять счет клиента (DepositCommand)
            new WithdrawCommand(), //4 Снимать средства со счета клиента
            new TransferCommand(), //5 Осуществлять перевод со счета клиента на счет другого клиента банка
            new AddClientCommand(),  //7 -  AddClientCommand

            new Command() { // 7 - Exit Command
                public void execute() {
                    System.exit(0);
                }

                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int commndNumber;
        while (true) {
            for (int i = 1; i <= 3; i++) {
                System.out.println("...................................");
            }
            if (currentClient != null) {
                System.out.println("Active client:");
                currentClient.printReport();
            }
            System.out.println("--------------\nCommand line list:");
            for (int i = 0; i < commands.length; i++) { // show menu
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            System.out.print("Enter command number:\n");
            try {
                commndNumber = Integer.parseInt(br.readLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid Format!");
                continue;
            }
            if (commndNumber < 0 || commndNumber >= commands.length) {
                System.out.println("Not a valid command number!");
                continue;
            }
            try {
                commands[commndNumber].execute();
            } catch (Exception e) {
                log.log(Level.ERROR, e);

            }
        }
    }
}
