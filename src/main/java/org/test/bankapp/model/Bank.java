package org.test.bankapp.model;

import org.test.bankapp.ClientExistsException;
import org.test.bankapp.ClientRegistrationListener;
import org.test.bankapp.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bank implements Report {
    private List<Client> clients = new ArrayList<Client>();
    private List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();

    public class PrintClientListener implements ClientRegistrationListener {
        public void onClientAdded(Client client) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(" Client registration report ");
            System.out.println("  Client name       : " + client.getName());
            System.out.format("  Client overdraft  : %.2f\n", client.getInitialOverdraft());
            System.out.format("  Client balance    : %.2f\n", client.getBalance());
            System.out.println("  Active account    :");
            client.getActiveAccount().printReport();
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        }
    }

    public class EmailNotificationListener implements ClientRegistrationListener {
        public void onClientAdded(Client client) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("Notification email for client \"" + client.getName() + "\"… to be sent");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }

    }

    public void registerEvent(ClientRegistrationListener listener) {
        listeners.add(listener);
    }

    public Bank() {
        registerEvent(new PrintClientListener());
        registerEvent(new EmailNotificationListener());
    }

    private void checkDuplicateName(Client client) throws ClientExistsException {
        for (int i = 1; i <= clients.size(); i++) {
            if (clients.get(i - 1).getName().equals(client.getName())) {
                throw new ClientExistsException();
            }
        }
    }

    public void addClient(Client client) throws ClientExistsException {
        checkDuplicateName(client);
        clients.add(client);
        for (ClientRegistrationListener listener : listeners)
            listener.onClientAdded(client);
    }

    public List<Client> getClients() {
        return clients;
    }


    public void printReport() {

        float bankBalance = 0.f;
        for (Client client : clients) {
            bankBalance += client.getBalance();
        }

        System.out.println("\n\n\n\nBank report  :");
        System.out.println("Report date  : " + new Date());
        System.out.printf("Bank balance : %.2f\n", bankBalance);
        System.out.println("Client lists (client counts:" + clients.size() + "):");
        Client client;
        for (int i = 1; clients != null && i <= clients.size(); i++) {
            client = clients.get(i - 1);

            System.out.println("==============================================================");
            System.out.println("Clinet # [" + i + "]");
            System.out.println("==============================================================");
            client.printReport();
            System.out.println("==============================================================");
        }
    }

}
