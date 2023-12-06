package server;

import javafx.stage.Stage;
import server.APIResponse;
import server.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageTest {
    MockDallE dallE = new MockDallE();
    File image = new File("response.jpg");
    @BeforeEach
    public void initializeImage(){
       try (FileWriter Writer = new FileWriter("response2.jpg")) {
        try {
            Writer.write("This is a response.jpg file");
            Writer.close(); 
        } catch (IOException e) {
           
            e.printStackTrace();
        }
    } catch (IOException e) {
        
        e.printStackTrace();
    }
       
    }
    @Test
    public void testReturnImage() throws IOException{
       
        String FileReturned = MockDallE.GeneratedImage("response.jpg");
        assertTrue(Files.exists(Paths.get(FileReturned)));
    }

    @Test
    public void testImageRead() throws IOException{
   
        String FileReturned = MockDallE.GeneratedImage("response.jpg");
        FileReader Reader = new FileReader(FileReturned);
        BufferedReader bufferedReader = new BufferedReader(Reader);
        String Content = bufferedReader.readLine();
        assertEquals("This is a response.jpg file", Content);
        bufferedReader.close();

    }
}
