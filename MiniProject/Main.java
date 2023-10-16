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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;


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
        this.setStyle("-fx-background-color: #f5cf52; -fx-border-width: 1; -fx-border-color:#000000; -fx-font-weight: bold;");
        this.name = new Text(); // TODO: want to set padding
        this.email = "";
        this.phoneNumber = "";


        this.setOnMouseClicked(e -> this.show());
        this.removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color:#ffed28");
        
    
        /*
         * Source: https://stackoverflow.com/questions/41654333/how-to-align-children-in-a-hbox-left-center-and-right
         * Title: java - How to align children in a HBox Left, Center and Right
         * Date Accessed: 10/15/2023
         * Use: I used this source to figure out how to align children of an HBox to different sides.
         */
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);

        this.getChildren().addAll(name, filler, removeButton);
    }

    Contact(String label, String email, String phoneNumber) {
        this();
        this.name.setText(label); // TODO: want to set padding
        this.email = email;
        this.phoneNumber = phoneNumber;
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
            if (currentDisplay.getName().getText().equals("")) {
                System.err.println("Must fill out name");
                return;
            } 
            this.name.setText(currentDisplay.getName().getText());
            this.email = currentDisplay.getEmail().getText();
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
        Scene toShow = new Scene(this.currentDisplay, 250, 400);
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
        this.setStyle("-fx-background-color: #eea80d");
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setSpacing(20);
        this.name = new TextField();
        this.email = new TextField();
        this.phoneNumber = new TextField();
        this.saveInfoButton = new Button();
        this.picture = null;
        this.saveInfoButton.setText("Save");
        saveInfoButton.setStyle("-fx-background-color:#ffed28");
        this.uploadButton = new Button("Upload Image");
        uploadButton.setStyle("-fx-background-color:#ffed28");

        this.imageView = new ImageView();

        
        Label l1 = new Label("Name: ");
        l1.setStyle("-fx-font-weight: bold;");
        Label l2 = new Label("Email Address: ");
        l2.setStyle("-fx-font-weight: bold;");
        Label l3 = new Label("Phone Number: ");
        l3.setStyle("-fx-font-weight: bold;");

        HBox nameBox = new HBox(l1, this.name);
        HBox emailBox = new HBox(l2, this.email);
        HBox phoneNumberBox = new HBox(l3, this.phoneNumber);


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
            // delete previous entries
            File toUpdate = new File("contacts.txt");
            toUpdate.delete();

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
            br.readLine(); // ignore first line
            String nextLine;
            String name;
            String email;
            String phoneNumber;

            while ((nextLine = br.readLine()) != null) {
                name = getSection(nextLine, 0);
                email = getSection(nextLine, 1);
                phoneNumber = getSection(nextLine, 2);


                Contact toAdd = new Contact(name, email, phoneNumber);
                toAdd.getRemoveButton().setOnAction(e->{
                    this.getChildren().remove(toAdd);
                });
                this.getChildren().add(toAdd);
            }

            br.close();

        } catch (Exception e) {e.printStackTrace();}
    }

    // get value at position index from line in CSV file
    private static String getSection (String line, int index) {
        int currentIndex = 0;
        String result = "";
        // navigate to section
        for (int i = 0; i < index; i++) {
            while (line.charAt(currentIndex) != ',') {
                currentIndex++;
            }
            currentIndex++;
        }

        // get section
        while (currentIndex < line.length() && line.charAt(currentIndex) != ',') {
            result += line.charAt(currentIndex++);
        }

        return result;
    }

}

class Footer extends HBox {
    private Button addButton;
    private Button sortButton;
    private Button saveContactsButton;
    private Button loadContactsButton;

    Footer() {
        this.addButton = new Button();
        Region filler1 = new Region();
        HBox.setHgrow(filler1, Priority.ALWAYS);
        this.sortButton = new Button();
        Region filler2 = new Region();
        HBox.setHgrow(filler2, Priority.ALWAYS);
        this.saveContactsButton = new Button("Save Contacts");
        Region filler3 = new Region();
        HBox.setHgrow(filler3, Priority.ALWAYS);
        this.loadContactsButton = new Button("Load Contacts");

        sortButton.setText("Sort Contacts");
        addButton.setText("Add Contact");


        this.getChildren().addAll(addButton, filler1, sortButton, filler2, 
                saveContactsButton, filler3, loadContactsButton);
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
        contacts.setStyle("-fx-background-color: #eea80d");
        this.footer = new Footer();
        footer.setStyle("-fx-background-color: #c3550a");
        this.addButton = footer.getAddButton();
        addButton.setStyle("-fx-background-color:#ffed28");
        this.sortButton = footer.getSortButton();
        sortButton.setStyle("-fx-background-color:#ffed28");
        this.saveContactsButton = footer.getSaveContactsButton();
        saveContactsButton.setStyle("-fx-background-color:#ffed28");
        this.loadContactsButton = footer.getLoadContactsButton();
        loadContactsButton.setStyle("-fx-background-color:#ffed28");

        ScrollPane scroller = new ScrollPane(contacts);
        scroller.setFitToHeight(true);
        scroller.setFitToWidth(true);

        HBox header = new HBox();
        header.setStyle("-fx-background-color: #c3550a;");
        header.setAlignment(Pos.CENTER);
        Text title = new Text("Contacts");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        header.getChildren().add(title);

        this.setCenter(scroller);
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
