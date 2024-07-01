package Entity;

public class Account {
    private int id;
    private int customerId;
    private double balance;
    private Currency currency;

    public Account(int id, int customerId, double balance, Currency currency) {
        this.id = id;
        this.customerId = customerId;
        this.balance = balance;
        this.currency = currency;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }
}
