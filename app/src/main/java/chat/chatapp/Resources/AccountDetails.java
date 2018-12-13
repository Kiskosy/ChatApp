package chat.chatapp.Resources;

import java.util.Date;

public class AccountDetails {

    int token;
    String accountName;
    String saltValue;
    String whenCreated;

    public AccountDetails(int token, String accountName, String saltValue, String whenCreated) {
        this.token = token;
        this.accountName = accountName;
        this.saltValue = saltValue;
        this.whenCreated = whenCreated;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSaltValue() {
        return saltValue;
    }

    public void setSaltValue(String saltValue) {
        this.saltValue = saltValue;
    }

    public String getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(String whenCreated) {
        this.whenCreated = whenCreated;
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "token=" + token +
                ", accountName='" + accountName + '\'' +
                ", saltValue='" + saltValue + '\'' +
                ", whenCreated='" + whenCreated + '\'' +
                '}';
    }
}
