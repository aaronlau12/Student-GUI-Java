/**
* Class: CIST 2372 Java Programming II
* Term: Spring 2026
* Instructor: Jianmin Wang
* Description: Solution to Lab 10
* Due: 04/10/2026
* @author Aaron Lau
* @version 1.0
*
* By turning in this code, I Pledge:
* 1. That I have completed the programming assignment independently.
* 2. I have not copied the code from a student or any source.
* 3. I have not given my code to any student.
* 4. I have not used any AI tools (e.g., ChatGPT) to generate this code
* 
* * References:
* Liang, Y. Daniel. Introduction to Java Programming, 10th ed., Pearson, 2013.
* Used for Java syntax and JavaFX examples.
 */
package lab.pkg10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class StudentGUI extends Application {
     
    private Label studentIdLbl;
    private TextField idTextField;
    
    private Label firstNameLbl;
    private TextField firstNameTextField;
    
    private Label lastNameLbl;
    private TextField lastNameTextField;
    
    private Label emailLbl;
    private TextField emailTextField;
    
    private Label gpaLbl;
    private TextField gpaTextField;
    
    
    @Override
    public void start(Stage primaryStage) {
        //pane and set properties
        GridPane gPane = new GridPane();
        gPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(5, 5, 5, 5));
        gPane.setHgap(5);
        gPane.setVgap(5);
        
        //put labels and textboxes in pane
        //Student ID 
        studentIdLbl = new Label("Student ID: ");
        studentIdLbl.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 14));
        gPane.add(studentIdLbl, 0, 0);
        idTextField = new TextField();//textbox
        gPane.add(idTextField, 1, 0);
        
        //first Name
        firstNameLbl = new Label("First Name: ");
        firstNameLbl.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 14));
        gPane.add(firstNameLbl, 0, 1);
        firstNameTextField = new TextField();//textbox
        gPane.add(firstNameTextField, 1, 1);
        
        //last Name
        lastNameLbl = new Label("Last Name: ");
        lastNameLbl.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 14));
        gPane.add(lastNameLbl, 0, 2);
        lastNameTextField = new TextField(); //textbox
        gPane.add(lastNameTextField, 1, 2);
        
        //email
        emailLbl = new Label("        Email: ");
        emailLbl.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 14));
        gPane.add(emailLbl, 0, 3);
        emailTextField = new TextField();
        gPane.add(emailTextField, 1, 3);
        
        //gpa
        gpaLbl = new Label("          GPA: ");
        gpaLbl.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 14));
        gPane.add(gpaLbl, 0, 4);
        gpaTextField = new TextField();
        gPane.add(gpaTextField, 1, 4);
        
        //buttons, action handlers, and horizontal panel for buttons
        HBox buttonHBox = new HBox();
        buttonHBox.setPadding(new Insets(5, 5, 5, 5));
        buttonHBox.setStyle("-fx-border-style: solid; -fx-border-color: grey; -fx-border-width: 1; -fx-background-color: lightgrey");
        
        Alert alert = new Alert(Alert.AlertType.NONE);
        
        //find button and action
        Button findBtn = new Button("Find");
        findBtn.setOnAction((ActionEvent event) -> {
            search();
        });
        
        Button insertBtn = new Button("Insert");
        insertBtn.setOnAction((ActionEvent event) -> {
            insert();
        });
        
        Button updateBtn = new Button("Update");
        updateBtn.setOnAction((ActionEvent event) -> {            
            update();
        });
        
        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyle("-fx-background-color: pink");
        deleteBtn.setOnAction((ActionEvent event) -> {
            delete();
        });
        
        
        Button clearBtn = new Button("Clear");
        clearBtn.setOnAction((ActionEvent event)-> {
            idTextField.setText(null);
            firstNameTextField.setText(null);
            lastNameTextField.setText(null);
            emailTextField.setText(null);
            gpaTextField.setText(null);
        });
        
        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Platform.exit();
                }
            }
        });
        
        buttonHBox.getChildren().addAll(findBtn, insertBtn, updateBtn, deleteBtn, clearBtn, exitBtn);
        gPane.add(buttonHBox, 1, 6);
        
        //create scene and put on stage
        Scene scene = new Scene(gPane, 500, 350);
        
        primaryStage.setTitle("Student Information");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void search() {
        if(idTextField.getText().isEmpty()) {
        errorMessage("Error", "Please enter a student ID.");
        return;
    }
        try {
            int sid = Integer.parseInt(idTextField.getText());

            Student s1 = new Student();
            s1.selectDB(sid);

            firstNameTextField.setText(s1.getFirstName());
            lastNameTextField.setText(s1.getLastName());
            emailTextField.setText(s1.getEmail());
            gpaTextField.setText(String.valueOf(s1.getGpa()));
        }
        catch(NumberFormatException ex) {
            ex.printStackTrace();
        }
    }//end search
    
    public void insert() {
        if (idTextField.getText().isEmpty() ||
        firstNameTextField.getText().isEmpty() ||
        lastNameTextField.getText().isEmpty() ||
        emailTextField.getText().isEmpty() ||
        gpaTextField.getText().isEmpty()) {

        errorMessage("Error", "Please fill in all fields.");
        return;
    }
        int sid = Integer.parseInt(idTextField.getText());
        String fName = firstNameTextField.getText();
        String lName = lastNameTextField.getText();
        String email = emailTextField.getText();
        double gpa = Double.parseDouble(gpaTextField.getText());
        
        Student stu = new Student();
        stu.insertDB(sid, fName, lName, email, gpa);
    }//end insert
    
    public void update() {
        if (idTextField.getText().isEmpty() ||
            firstNameTextField.getText().isEmpty() ||
            lastNameTextField.getText().isEmpty() ||
            emailTextField.getText().isEmpty() ||
            gpaTextField.getText().isEmpty()) {
            
            errorMessage("Error", "Please fill in all fields.");
            return;
        }
        
        int sid = Integer.parseInt(idTextField.getText());
        String fName = firstNameTextField.getText();
        String lName = lastNameTextField.getText();
        String email = emailTextField.getText();
        double gpa = Double.parseDouble(gpaTextField.getText());
        
        Student st1 = new Student(sid, fName, lName, email, gpa);
        st1.updateDB();
    }//end update
    
    public void delete() {
        if (idTextField.getText().isEmpty()) {
        errorMessage("Error", "Please enter a student ID.");
        return;
    }
        int sid = Integer.parseInt(idTextField.getText());
        
        Student st2 = new Student();
        st2.setSid(sid);
        
        st2.deleteDB();
    }
    
    
    
    private void errorMessage(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }//end show error
    
    private boolean confirmMessage(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setContentText(body);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }//end show error
    
    private Integer getId() {
        try {
            return Integer.parseInt(idTextField.getText().trim());
        }//end try
            
        catch (NumberFormatException ex) {
            errorMessage("Invalid Entry", "Enter Valid Student ID");
            return null;
            }//end catch//end catch
    }//end getId
    
    private String getFirstName() {
            return firstNameTextField.getText().trim();
    }//end getFirstName
    
    private String getLastName() {
        return lastNameTextField.getText().trim();
    }
    
    private String getEmail() {
        return emailTextField.getText().trim();
    }
    
    private Double getGpa() {
        try {
            return Double.parseDouble(gpaTextField.getText().trim());
        }//end try
            
        catch (NumberFormatException ex) {
            errorMessage("Invalid Entry", "Enter valid GPA.");
            return null;
            }//end catch//end catch
    }//end getId
    
    public static void main(String[] args) {
        launch(args);
    }
}
