import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextAreaSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Frame outside = new Frame();

        // Set the title of the app
        primaryStage.setTitle("Contacts");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(outside, 500, 600));


        // Show the app
        primaryStage.show();

    }
}

class Contact extends HBox {
    private Text name;
    private String email;
    private String phoneNumber;
    private Button saveInfoButton;
    private ContactDisplay currentDisplay;

    private Image picture;
    private FileChooser fileChooser;
    private Button uploadButton;
    private Button removeButton;

    Contact() {
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;");
        this.name = new Text(); // TODO: want to set padding
        /*
         * Source: https://stackoverflow.com/questions/18363585/adding-space-between-buttons-in-vbox
         * Title: javafx 2 - https://stackoverflow.com/questions/18363585/adding-space-between-buttons-in-vbox
         * Date Accessed: 10/9/2023
         * Use: Used to discover spacing property and setSpacing() function.
         */
        this.setSpacing(50);



        this.setOnMouseClicked(e -> this.show());
        this.removeButton = new Button("Remove");
        removeButton.setPrefSize(100, 20);
        removeButton.setPrefHeight(Double.MAX_VALUE);
    
        this.getChildren().addAll(name, removeButton);
    }

    Contact(String label, String email, String phoneNumber) {
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;");
        this.name = new Text(label); // TODO: want to set padding
        this.email = email;
        this.phoneNumber = phoneNumber;
        /*
         * Source: https://stackoverflow.com/questions/18363585/adding-space-between-buttons-in-vbox
         * Title: javafx 2 - https://stackoverflow.com/questions/18363585/adding-space-between-buttons-in-vbox
         * Date Accessed: 10/9/2023
         * Use: Used to discover spacing property and setSpacing() function.
         */
        this.setSpacing(50);



        this.setOnMouseClicked(e -> this.show());
        this.removeButton = new Button("Remove");
        removeButton.setPrefSize(100, 20);
        removeButton.setPrefHeight(Double.MAX_VALUE);
    
        this.getChildren().addAll(name, removeButton);
    }


    public Text getName() {
        return this.name;
    }


    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Image getPicture() {
        return this.picture;
    }

    public Button getRemoveButton() {
        return this.removeButton;
    }

    // show all info associated with contact and allow user to modify info
    public void show() {
        Stage secondaryStage = new Stage();
        this.currentDisplay = ContactDisplay.of(this);
        // set up save button
        this.saveInfoButton = currentDisplay.getSaveInfoButton();
        this.saveInfoButton.setOnAction(e -> {      
            this.name.setText(currentDisplay.getName().getText());
            this.email =currentDisplay.getEmail().getText();
            this.phoneNumber = currentDisplay.getPhoneNumber().getText();
            this.picture = currentDisplay.getPicture();
            secondaryStage.close();
        });

        // set up upload button
        this.uploadButton = currentDisplay.getUploadButton();
        this.uploadButton.setOnAction(e -> {
            if (this.fileChooser == null) {
                this.fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            }
            
            File selectedFile = fileChooser.showOpenDialog(secondaryStage);
            if (selectedFile != null) {
                /*
                    * Source link: https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
                    * Title: java - How do I resize an imageview image in javafx?
                    * Date: 10/07/2023
                    * Use: I used the source to learn how to set the size of an Image in javafx (using the below constructor).
                    */
                Image picture = new Image(selectedFile.toURI().toString(), 100, 100, false, false);
                currentDisplay.setPicture(picture);
                currentDisplay.getImageView().setImage(picture);
            }
        });

        // show contact display
        Scene toShow = new Scene(this.currentDisplay, 100, 200);
        secondaryStage.setTitle("Contact");
        secondaryStage.setScene(toShow);
        secondaryStage.show();
        
    }
}

class ContactDisplay extends VBox {
    private TextField name;
    private TextField email;
    private TextField phoneNumber;
    private Button saveInfoButton;
    private Image picture;
    private Button uploadButton;
    private ImageView imageView;


    ContactDisplay() {
        this.name = new TextField();
        this.email = new TextField();
        this.phoneNumber = new TextField();
        this.saveInfoButton = new Button();
        this.picture = null;
        this.saveInfoButton.setText("Save");
        this.uploadButton = new Button();
        this.uploadButton.setText("Upload");

        this.imageView = new ImageView();

        HBox nameBox = new HBox(new Text("Name: "), this.name);
        HBox emailBox = new HBox(new Text("Email Address: "), this.email);
        HBox phoneNumberBox = new HBox(new Text("Phone Number: "), this.phoneNumber);


        this.getChildren().addAll(imageView, uploadButton, nameBox, emailBox, phoneNumberBox, saveInfoButton);
    }


    public static ContactDisplay of (Contact contact) {
        ContactDisplay display = new ContactDisplay();
        display.setName(contact.getName().getText());
        display.setEmail(contact.getEmail());
        display.setPhoneNumber(contact.getPhoneNumber());
        display.setPicture(contact.getPicture());
        return display;
    }

    public Button getSaveInfoButton() {
        return saveInfoButton;
    }

    public TextField getName() {
        return name;
    }

    public TextField getEmail() {
        return email;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextField getPhoneNumber() {
        return phoneNumber;
    }

    public Image getPicture() {
        return this.picture;
    }

    public Button getUploadButton() {
        return this.uploadButton;
    }

    



    public void setPicture(Image picture) {
        this.picture = picture;
        imageView.setImage(picture);
    }

    public void setSaveInfoButton(Button b) {
        this.saveInfoButton = b;
    }

    public void setName(String text) {
       this.name.setText(text);
    }

    public void setEmail(String text) {
       this.email.setText(text);
    }

    public void setPhoneNumber(String text) {
       this.phoneNumber.setText(text);
    }

    public void setUploadButton(Button button) {
        this.uploadButton = button;
    }

    

    
}

class ContactList extends VBox {

    /*
     * Save current contacts to CSV file
     */
    public void saveContacts() {
        try {
            FileWriter fr = new FileWriter("contacts.txt");
            fr.write("name, email, phone number\n");
            for (Object current: this.getChildren()) {
                Contact contact = (Contact) current;
                String name = contact.getName().getText();
                String email = contact.getEmail();
                String phoneNumber = contact.getPhoneNumber();

                fr.write(String.format("%s,%s,%s\n", name, email, phoneNumber));
            }
            fr.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    /*
     * Add contacts from CSV file to this ContactList
     */
    public void loadContacts() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("contacts.txt"));
            String nextLine;
            String name = "";
            String email = "";
            String phoneNumber = "";

            while ((nextLine = br.readLine()) != null) {
                String[] sections = nextLine.split(",");
                name = sections[0];
                email = sections[1];
                phoneNumber = sections[2];

                Contact toAdd = new Contact(name, email, phoneNumber);
                this.getChildren().add(toAdd);
            }

            br.close();

        } catch (Exception e) {e.printStackTrace();}
    }

}

class Footer extends HBox {
    private Button addButton;
    private Button sortButton;
    private Button saveContactsButton;
    private Button loadContactsButton;

    Footer() {
        this.addButton = new Button();
        this.sortButton = new Button();
        this.saveContactsButton = new Button("Save Contacts");
        this.loadContactsButton = new Button("Load Contacts");

        sortButton.setText("Sort Contacts");
        addButton.setText("Add Contact");


        this.getChildren().addAll(addButton, sortButton, saveContactsButton, loadContactsButton);
    }

    public Button getAddButton() {
        return this.addButton;
    }

    public Button getSaveContactsButton() {
        return this.saveContactsButton;
    }

    public Button getSortButton() {
        return this.sortButton;
    }

    public Button getLoadContactsButton() {
        return this.loadContactsButton;
    }
}

class Frame extends BorderPane {
    
    private ContactList contacts;
    private Footer footer;
    
    private Button addButton;
    private Button sortButton;
    private Button saveContactsButton;
    private Button loadContactsButton;
    
    Frame() {
        this.contacts = new ContactList();
        this.footer = new Footer();
        this.addButton = footer.getAddButton();
        this.sortButton = footer.getSortButton();
        this.saveContactsButton = footer.getSaveContactsButton();
        this.loadContactsButton = footer.getLoadContactsButton();

        ScrollPane s1 = new ScrollPane(contacts);
        s1.setFitToHeight(true);
        s1.setFitToWidth(true);

        HBox header = new HBox();
        header.setStyle("-fx-background-color: #F0F8FF;");
        header.setAlignment(Pos.CENTER);
        Text title = new Text("Contacts");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        header.getChildren().add(title);

        this.setCenter(s1);
        this.setBottom(this.footer);
        this.setTop(header);


        addListeners();
    }

    private void addListeners() {
        addButton.setOnAction(e -> {
            Contact toAdd = new Contact();
            toAdd.getRemoveButton().setOnAction(a -> {
                this.contacts.getChildren().remove(toAdd);
            });
            toAdd.show();
            this.contacts.getChildren().add(toAdd);
        });

        sortButton.setOnAction(e -> {
            sortContacts();
        });

        saveContactsButton.setOnAction (e -> {
            this.contacts.saveContacts();
        });

        loadContactsButton.setOnAction(e -> {
            this.contacts.loadContacts();
        });
    }

    /*
     * Sort contacts in alphabetical order
     */
    public void sortContacts() {
        ObservableList<Node> children = contacts.getChildren();
        Object[] sortedArray = children.toArray();
        Arrays.sort(sortedArray, new ContactComparator());
        children.removeIf(contact -> true);
        for (Object child:sortedArray) {
            children.add((Node) child);
        }
    
    }

    class ContactComparator implements Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {
            Contact t1 = (Contact) o1;
            Contact t2 = (Contact) o2;

            String label1 = t1.getName().getText();
            String label2 = t2.getName().getText();

            return label1.compareTo(label2);
        }
    
    }
}
