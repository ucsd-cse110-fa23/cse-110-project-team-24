package server;

import java.io.FileWriter;
import java.io.IOException;

public class MockDallE implements ImageGenerator{
   public static String GeneratedImage(String File) {
   try (FileWriter Writer = new FileWriter(File)) {
        try {
            Writer.write("This is a "+ File + " file");
            Writer.close(); 
        } catch (IOException e) {
           
            e.printStackTrace();
        }
    } catch (IOException e) {
        
        e.printStackTrace();
    }
    return File;
 }
}
