package server;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MockAccountInfo {
    private List<Document> mockDatabase = new ArrayList<>();

    public MockAccountInfo(){
        this.mockDatabase = mockDatabase;
    }

    public void insertSingleAccount(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }

        if (findAccountByUsername(username) != null) {
            throw new IllegalStateException("An account with this username already exists.");
        }

        Document newAccount = new Document("_id", new ObjectId())
            .append("username", username)
            .append("password", password);
        mockDatabase.add(newAccount);
    }

    public Document findAccountByUsername(String username) {
        return mockDatabase.stream()
            .filter(account -> account.getString("username").equals(username))
            .findFirst()
            .orElse(null);
    }

      public boolean updateAccountDetails(String username, Document updatedDetails) {
        if (username == null || username.isEmpty() || updatedDetails == null) {
            throw new IllegalArgumentException("Username and updates cannot be empty");
        }
    
        Document account = findAccountByUsername(username);
        if (account != null) {
            updatedDetails.entrySet().forEach(entry -> account.put(entry.getKey(), entry.getValue()));
            return true;
        }
        return false;
    }

    public boolean updateAccountPassword(String username, String newPassword) {
        if (username == null || username.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }
    
        Document account = findAccountByUsername(username);
        if (account != null) {
            account.put("password", newPassword);
            return true;
        }
        return false;
    }

    public boolean deleteAccountByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
    
        boolean accountExists = mockDatabase.stream()
            .anyMatch(account -> account.getString("username").equals(username));
    
        if ((accountExists)) {
            mockDatabase.removeIf(account -> account.getString("username").equals(username));
            return true;  // Account found and removed
        } else {
            return false; // Account not found
        }
    }
    
     public boolean authenticateUserInfo(String username, String password) {
        Document userAccount = findAccountByUsername(username);
        if (userAccount == null) {
            return false;
        }
        return userAccount.getString("password").equals(password);
    }
}
