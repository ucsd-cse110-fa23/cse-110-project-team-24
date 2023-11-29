package server;
import server.MockAccountInfo;
import java.util.*;

import org.bson.Document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AccountInfoTest {
    private MockAccountInfo mockAccountInfo;
    

    String username1 = "safiaAli";
    String password1 = "password1234567890";
    Document doc = new Document()
        .append("username", username1)
        .append("password", password1);


    String username2 = "Robin";
    String password2 = "Password@123";
    Document doc2 = new Document()
        .append("username", username2)
        .append("password", password2);

    String username3 = "Mohamed";
    String password3 = "PasswOrd!@#$%^&*()_+-=";
    Document doc3 = new Document()
    .append("username", username3)
    .append("password", password3); 
    List<Document> AccountInfo = new ArrayList<Document>();
    
    public AccountInfoTest() {
        AccountInfo = new ArrayList<Document>();
        // Add user documents to the list
        AccountInfo.add(doc);
        AccountInfo.add(doc2);
        AccountInfo.add(doc3);
    }
    
     @BeforeEach
    public void setUp() {
        mockAccountInfo = new MockAccountInfo();
    }

    @Test
    public void testInsertAndAuthenticateAccount() {
        mockAccountInfo.insertSingleAccount(username1, password1 );
        assertTrue(mockAccountInfo.authenticateUserInfo(username1, password1));
    }

    @Test
    public void testDeleteAccount() {
        mockAccountInfo.insertSingleAccount(username1, password1 );
        assertTrue(mockAccountInfo.deleteAccountByUsername(username1));
        assertNull(mockAccountInfo.findAccountByUsername(username1));
    }

    @Test
    public void testUpdateAccountPassword() {
        String newpassward = "newPass123";
        mockAccountInfo.insertSingleAccount(username2, password2);
        assertTrue(mockAccountInfo.updateAccountPassword(username2, "newPass123"));
        assertTrue(mockAccountInfo.authenticateUserInfo(username2, newpassward));
    }
}

 