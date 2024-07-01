package Service;

import Entity.Account;

import Entity.Transaction;
import Ultility.DataWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionServices {
    static String rootPath = System.getProperty("user.dir");
    static String CustomerFilePath = rootPath.replace('\\', '/') + "/data/Customer.txt";
    static String AccountFilePath = rootPath.replace('\\', '/') + "/data/Account.txt";
    static String TransactionFilePath = rootPath.replace('\\', '/') + "/data/Transaction.txt";
    public static String withdraw(List<Account> accounts, List<Transaction> transactions, int accountId, double amount) {
        Account account = accounts.stream()
                .filter(a -> a.getId() == accountId)
                .findFirst()
                .orElse(null);

        if (account == null) {
            return "Account not found.";
        }

        if (amount % 10 != 0 || amount > account.getBalance()) {
            return "Invalid amount. Amount must be a multiple of 10 and less than the current balance.";
        }

        account.setBalance(account.getBalance() - amount);
        transactions.add(new Transaction(transactions.size() + 1, accountId, amount, Transaction.Type.WITHDRAWAL,
                LocalDateTime.now(), Transaction.Status.C));
        DataWriter.updateAccounts(accounts, AccountFilePath);
        DataWriter.updateTransactions(transactions, TransactionFilePath);
        return "Transaction successful. Current balance: " + account.getBalance();
    }


    public static List<Transaction> queryTransactions(List<Transaction> transactions, LocalDate from, LocalDate to) {
        return transactions.stream()
                .filter(t -> !t.getDateTime().toLocalDate().isBefore(from) && !t.getDateTime().toLocalDate().isAfter(to))
                .collect(Collectors.toList());
    }

    public static void saveTransactionsToFile(List<Transaction> transactions, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Transaction t : transactions) {
                writer.write(t.toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
