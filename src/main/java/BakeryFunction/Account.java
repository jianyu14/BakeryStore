package BakeryFunction;

public class Account {
    private String accID;
    private String password;
    private double balance;

    public Account() {}

    public Account(String accID, String password, double balance) {
        this.accID = accID;
        this.password = password;
        this.balance = balance;
    }

    public String getAccID() {
        return accID;
    }

    public void setAccID(String accID) {
        this.accID = accID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
