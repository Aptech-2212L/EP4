package Ultility;


import Entity.Account;
import Entity.Customer;
import Entity.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataWriter {

    public static void writeCustomers(List<Customer> customers, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Customer customer : customers) {
                writer.write(customer.getId() + "; " + customer.getName() + "; " + customer.getPhone());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeAccounts(List<Account> accounts, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Account account : accounts) {
                writer.write(account.getId() + "; " + account.getCustomerId() + "; " + account.getBalance() + "; " + account.getCurrency());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTransactions(List<Transaction> transactions, String filePath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.getId() + "; " + transaction.getAccountId() + "; " + transaction.getAmount() + "; " + transaction.getType() + "; " + transaction.getDateTime().format(formatter) + "; " + transaction.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateAccounts(List<Account> accounts, String filePath) {
        writeAccounts(accounts, filePath);
    }

    public static void updateTransactions(List<Transaction> transactions, String filePath) {
        writeTransactions(transactions, filePath);
    }
}
