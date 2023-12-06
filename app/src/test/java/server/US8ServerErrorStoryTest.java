package server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Label;
import pantrypal.ServerErrorView;
import pantrypal.ServerStatusCheck;

public class US8ServerErrorStoryTest {
    @Test
    public void ServerIsAvailableTest() throws Exception{
        try {
            ServerErrorView view = new ServerErrorView();
            boolean serverStatus = ServerStatusCheck.isServerAvailable();
            // serverStatus = true;
            Label expectedMessage =null;
            Label actualMessage = view.getErrorMessage();
            assertTrue(serverStatus);
            assertEquals(expectedMessage, actualMessage);
        } catch (ExceptionInInitializerError | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // @Test
    // public void ServerNotAvailableTest(){
    //     try{
    //         ServerErrorView view = new ServerErrorView();
    //         boolean serverStatus = ServerStatusCheck.isServerAvailable();
    //         //serverStatus = false;
    //         String message = "Unable to connect to Server. Please try again later.";
    //         Label expectedMessage = new Label(message);
    //         Label actualMessage = view.getErrorMessage();
    //         assertFalse(serverStatus);
    //         assertEquals(expectedMessage, actualMessage);
    //     } catch (ExceptionInInitializerError | IllegalStateException e) {
    //         e.printStackTrace();
    //     }
    // }
}