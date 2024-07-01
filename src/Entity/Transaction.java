package Entity;

import java.time.LocalDateTime;

public class Transaction {
    public enum Type {
        WITHDRAWAL, DEPOSIT
    }

    public enum Status {
        C("Completed"), P("Pending"), R("Reject");

        private String description;

        Status(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    private int id;
    private int accountId;
    private double amount;
    private Type type;
    private LocalDateTime dateTime;
    private Status status;

    public Transaction(int id, int accountId, double amount, Type type, LocalDateTime dateTime, Status status) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", type=" + type +
                ", dateTime=" + dateTime +
                ", status=" + status +
                '}';
    }
}
