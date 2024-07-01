package Ultility;

import Entity.Account;
import Entity.Currency;
import Entity.Customer;
import Entity.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    public static List<Customer> readCustomers(String filePath) {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() > 0) {
                    String[] parts = line.split("; ");
                    Customer customer = new Customer(Integer.parseInt(parts[0]), parts[1], parts[2]);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static List<Account> readAccounts(String filePath) {
        List<Account> accounts = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() > 0) {
                    String[] parts = line.split("; ");
                    Account account = new Account(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                            Double.parseDouble(parts[2]), Currency.valueOf(parts[3]));
                    accounts.add(account);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() > 0) {
                    String[] parts = line.split("; ");
                    Transaction transaction = new Transaction(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                            Double.parseDouble(parts[2]), Transaction.Type.valueOf(parts[3]),
                            LocalDateTime.parse(parts[4], formatter), Transaction.Status.valueOf(parts[5]));
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
