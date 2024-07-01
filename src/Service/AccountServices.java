package Service;

import Entity.Account;

import java.util.List;

public class AccountServices {
    public static double queryBalance(List<Account> accounts, int accountId) {
        return accounts.stream()
                .filter(a -> a.getId() == accountId)
                .map(Account::getBalance)
                .findFirst()
                .orElse(0.0);
    }
}
