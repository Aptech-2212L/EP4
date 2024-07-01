import Entity.Account;
import Entity.Customer;
import Entity.Transaction;
import Service.AccountServices;
import Service.TransactionServices;
import Ultility.DataReader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String rootPath = System.getProperty("user.dir");
        String CustomerFilePath = rootPath.replace('\\', '/') + "/data/Customer.txt";
        String AccountFilePath = rootPath.replace('\\', '/') + "/data/Account.txt";
        String TransactionFilePath = rootPath.replace('\\', '/') + "/data/Transaction.txt";
        try {
            List<Customer> customers = DataReader.readCustomers(CustomerFilePath);
            List<Account> accounts = DataReader.readAccounts(AccountFilePath);
            List<Transaction> transactions = DataReader.readTransactions(TransactionFilePath);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Select an option:");
                System.out.println("1. Perform a withdrawal transaction");
                System.out.println("2. Query account balance");
                System.out.println("3. Query transactions by date range");
                System.out.println("4. Exit");
                int option = scanner.nextInt();
                int accountId;
                switch (option) {
                    case 1:
                        System.out.println("Enter the customer ID, customer name, or account ID for withdrawal:");
                        String input = scanner.nextLine();
                        inputDataWithdraw(input, customers, accounts, transactions);
                        break;

                    case 2:
                        System.out.println("Enter the account ID to query balance:");
                        accountId = scanner.nextInt();
                        double balance = AccountServices.queryBalance(accounts, accountId);
                        System.out.println("Current balance: " + balance);
                        break;

                    case 3:
                        System.out.println("Enter the account ID:");
                        accountId = scanner.nextInt();
                        System.out.println("Enter the start date (yyyy-MM-dd):");
                        String fromDate = scanner.next();
                        System.out.println("Enter the end date (yyyy-MM-dd):");
                        String toDate = scanner.next();
                        LocalDate from = LocalDate.parse(fromDate, DateTimeFormatter.ISO_DATE);
                        LocalDate to = LocalDate.parse(toDate, DateTimeFormatter.ISO_DATE);

                        List<Transaction> resultTransactions = TransactionServices.queryTransactions(transactions, from, to);
                        for (Transaction t : resultTransactions) {
                            System.out.println(t);
                        }

                        System.out.println("Do you want to save the transactions to a file? (yes/no):");
                        String saveToFile = scanner.next();
                        if (saveToFile.equalsIgnoreCase("yes")) {
                            String fileName = accountId + "_transaction_history.txt";
                            TransactionServices.saveTransactionsToFile(resultTransactions, fileName);
                            System.out.println("Transactions saved to " + fileName);
                        }
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inputDataWithdraw(String input, List<Customer> customers, List<Account> accounts, List<Transaction> transactions) {
        Scanner scanner = new Scanner(System.in);
        List<Account> matchedAccounts = null;

        try {
            int accountId = Integer.parseInt(input);
            matchedAccounts = accounts.stream()
                    .filter(account -> account.getId() == accountId)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            // Not an account ID, check if it's a customer ID or name
            try {
                int customerId = Integer.parseInt(input);
                matchedAccounts = accounts.stream()
                        .filter(account -> account.getCustomerId() == customerId)
                        .collect(Collectors.toList());
            } catch (NumberFormatException ex) {
                // Not a customer ID, check if it's a customer name
                matchedAccounts = customers.stream()
                        .filter(customer -> customer.getName().equalsIgnoreCase(input))
                        .flatMap(customer -> accounts.stream()
                                .filter(account -> account.getCustomerId() == customer.getId()))
                        .collect(Collectors.toList());
            }
        }

        if (matchedAccounts == null || matchedAccounts.isEmpty()) {
            System.out.println("No accounts found matching the input.");
            return;
        }

        System.out.println("Matched accounts:");
        matchedAccounts.forEach(account -> System.out.println("Account ID: " + account.getId() + ", Balance: " + account.getBalance() + ", Currency: " + account.getCurrency()));

        System.out.println("Enter the account ID to perform withdrawal:");
        int selectedAccountId = scanner.nextInt();

        Account selectedAccount = matchedAccounts.stream()
                .filter(account -> account.getId() == selectedAccountId)
                .findFirst()
                .orElse(null);

        if (selectedAccount == null) {
            System.out.println("Invalid account ID selected.");
            return;
        }

        System.out.println("Enter the amount to withdraw:");
        double amount = scanner.nextDouble();
        String result = TransactionServices.withdraw(accounts, transactions, selectedAccountId, amount);
        System.out.println(result);
    }
}