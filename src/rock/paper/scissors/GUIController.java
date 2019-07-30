/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rock.paper.scissors;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;                           // Imports necessary libraries
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author Yousef
 */
public class GUIController implements Initializable {
    private int check = 0, win = 0, tie = 0, lose = 0;
    private String difficulty;
    private Gameplay newGame = new Gameplay();
    
    @FXML
    private ImageView gif1, gif2, rockImageView, paperImageView, scissorImageView;
    
    @FXML
    private MenuItem startMenuItem, resetMenuItem;
    
    @FXML
    private Menu edit;
                                                        // Creates instance fields for FXML objects, newGame object, and other necessary instance fields
    @FXML
    private Label title, author, instruction, winsNumber, tiesNumber, lossesNumber, instruction1, winName, tieName, loseName, userName, compName;
    
    @FXML
    private CheckBox easyCheck, hardCheck;
    
    @FXML
    private Line line1, line2, line3, line4, line5;
    
    @FXML
    private Circle rockCir, paperCir, scissorCir;
    
    @FXML
    private Line[] lines = new Line[5];
    
    @FXML
    private void systemExit(){
        System.exit(0);     // Closes the program
    }
    
    @FXML
    private void reset(){
        rockCir.setVisible(false); paperCir.setVisible(false); scissorCir.setVisible(false); gif1.setVisible(false); gif2.setVisible(false); rockImageView.setVisible(false);
        paperImageView.setVisible(false); scissorImageView.setVisible(false); winsNumber.setVisible(false); tiesNumber.setVisible(false); lossesNumber.setVisible(false);
        rockCir.setFill(Color.TRANSPARENT); paperCir.setFill(Color.TRANSPARENT); scissorCir.setFill(Color.TRANSPARENT); easyCheck.setVisible(true);     // Resets variables so game can be played agin
        hardCheck.setVisible(true); title.setVisible(true); author.setVisible(true); instruction.setVisible(true); startMenuItem.setVisible(true);
        edit.setVisible(false); resetMenuItem.setVisible(false); instruction1.setVisible(false); gif1.setImage(null); gif2.setImage(null); winName.setVisible(false); tieName.setVisible(false);
        loseName.setVisible(false); userName.setVisible(false); compName.setVisible(false);
        for (int i = 0; i < lines.length; i++){
            lines[i].setVisible(false);     // Sets all of the lines on the screen to invisible
        }
        win = 0; tie = 0; lose = 0;
        updateScore();  // Updates the score on the screen to reflect the reset values
    }
    
    @FXML
    private void setUp(){
        rockCir.setVisible(true); paperCir.setVisible(true); scissorCir.setVisible(true); gif1.setVisible(true); gif2.setVisible(true); rockImageView.setVisible(true);
        paperImageView.setVisible(true); scissorImageView.setVisible(true); winsNumber.setVisible(true); tiesNumber.setVisible(true); lossesNumber.setVisible(true);
        rockCir.setFill(Color.TRANSPARENT); paperCir.setFill(Color.TRANSPARENT); scissorCir.setFill(Color.TRANSPARENT); easyCheck.setVisible(false);
        hardCheck.setVisible(false); title.setVisible(false); author.setVisible(false); instruction.setVisible(false); startMenuItem.setVisible(false); winName.setVisible(true);
        tieName.setVisible(true); loseName.setVisible(true); userName.setVisible(true); compName.setVisible(true);
        edit.setVisible(true); resetMenuItem.setVisible(true); instruction1.setVisible(true);
        setPictures();
        for (int i = 0; i < lines.length; i++){
            lines[i].setVisible(true);
        }
        if (easyCheck.isSelected()){
            difficulty = "easy";        // Sets the difficulty based on the checked value
        }
        else if (hardCheck.isSelected()){
            difficulty = "hard";
        }
    }
    
    @FXML
    private void getChoice(MouseEvent event){  // This method runs when an image is pressed
        String source = event.getSource().toString();  // Gets information about the FXML objecty that called this method
        String userChoice = "";
        switch(source.substring(10, source.indexOf("C", 5))){
            case "scissor":
                userChoice = "scissor";
                break;                              // Determines which FXML object called the method
            case "paper":
                userChoice = "paper";
                break;
            case "rock":
                userChoice = "rock";
                break;
            }
        displayWinner(userChoice);      // Runs method displayWinner() using the option that the user has selected
    }
    
    @FXML
    private void displayWinner(String userChoice){
        String compChoice = newGame.returnMove(difficulty);     // Gets the computers next choice
        if (difficulty.equals("hard")){
            newGame.updateArray(userChoice);        // Runs a method to update the array of user moves with his most recent move 
        }
        if (userChoice.equals("rock") || compChoice.equals("rock")){
            if (compChoice.equals("rock")){
                gif2.setImage(new Image("Resources/rightrock.gif"));        // Sets imageview to the correct gif based on the userchoice and the computerchoice
            }
            if (userChoice.equals("rock")){
                gif1.setImage(new Image("Resources/leftrock.gif"));
            }
        }
        if (userChoice.equals("paper") || compChoice.equals("paper")){
            if (compChoice.equals("paper")){
                gif2.setImage(new Image("Resources/rightpaper.gif"));
            }
            if (userChoice.equals("paper")){
                gif1.setImage(new Image("Resources/leftpaper.gif"));
            }
        }
        if (userChoice.equals("scissor") || compChoice.equals("scissors")){
            if (compChoice.equals("scissors")){
                gif2.setImage(new Image("Resources/rightscissors.gif"));
            }
            if (userChoice.equals("scissor")){
                gif1.setImage(new Image("Resources/leftscissors.gif"));
            }
        }
        clearImage(userChoice, compChoice);     // Runs clearImage() and passes througth the choices
    }
    
    @FXML
    private void calcWinner(String userChoice, String compChoice){
        int choice1 = 0;
        int choice2 = 0;
        switch (userChoice) {
            case "rock":
                choice1 = 1;
                break;
            case "paper":       // Sets choice1 based on what the user has input
                choice1 = 2;
                break;
            case "scissors":
                choice1 = 3;
                break;
        }
        switch (compChoice) {
            case "rock":
                choice2 = 1;
                break;
            case "paper":
                choice2 = 2;
                break;
            case "scissors":
                choice2 = 3;
                break;
        }
        switch (choice1 - choice2) {
            case 0:
                tie += 1;
                break;
            case 2:
                lose += 1;
                break;
            case 1:
                win += 1;
                break;            
            case -1:                // Determines the winner by substracting the two values
                lose += 1;
                break;
            case -2:
                win += 1;
                break;
            case -3:
                tie += 1;
                break;    
        }
        updateScore();      // Updates the score to reflect the winner of the match
    }
    
    @FXML
    private void updateScore(){
        winsNumber.setText(String.valueOf(win));
        tiesNumber.setText(String.valueOf(tie));        // Sets labels to reflect wins, ties, and losses
        lossesNumber.setText(String.valueOf(lose));
    }
    
    @FXML
    public void clearImage(String userChoice, String compChoice){
        rockCir.setVisible(false); paperCir.setVisible(false); scissorCir.setVisible(false);                    // Sets options to invisible so that the user cannot pick anothe roption before the animation is finished
        paperImageView.setVisible(false); scissorImageView.setVisible(false); rockImageView.setVisible(false); userName.setVisible(false); compName.setVisible(false);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {      // Starts a new FXML timer that will last ~ 1 second
            gif1.setImage(gif1.snapshot(null, null));
            gif2.setImage(gif2.snapshot(null, null));       // Sets the two imageviews to a creenshot of their current state
            rockCir.setVisible(true); paperCir.setVisible(true); scissorCir.setVisible(true); rockImageView.setVisible(true);       // Makes all the options visible again
            paperImageView.setVisible(true); scissorImageView.setVisible(true);
            userName.setVisible(true); compName.setVisible(true);
            calcWinner(userChoice, compChoice);     // Calculates the winner of the match
            
        }));
        timeline.setCycleCount(1);      // Runs the timer once only
        timeline.play();  // Begins the timer
    }
    
    @FXML
    private void setPictures(){
        rockImageView.setImage(new Image("Resources/rockImage.png"));
        scissorImageView.setImage(new Image("Resources/scissorImage.png"));     // Sets the pictures of the choices
        paperImageView.setImage(new Image("Resources/paperImage.png"));
    }
    
    @FXML
    private void fillCircle(MouseEvent event){
        ((Circle) event.getSource()).setFill(Color.BLACK);      // Fills the circle black if the users mouse enters the circle
    }
    
    @FXML
    private void clearCircle(MouseEvent event){
        ((Circle) event.getSource()).setFill(Color.TRANSPARENT);       // Makes the circle transparent if the mouse leaves the circle
    }
    
    @FXML
    private void switchCheck(){ 
        if (easyCheck.isSelected() && check == 0){
            hardCheck.setSelected(true);
            easyCheck.setSelected(false);
            check = 1;
        }
        else if (hardCheck.isSelected() && check == 1){
            easyCheck.setSelected(true);
            hardCheck.setSelected(false);                   // This method will always ensure that the user has selected one of the two difficulties
            check = 0;
        }
        else if (check == 0){
            hardCheck.setSelected(true);
            easyCheck.setSelected(false);
            check = 1;
        }
        else if (check == 1){
            easyCheck.setSelected(true);
            hardCheck.setSelected(false);
            check = 0;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lines[0] = line1;
        lines[1] = line2;       // Initializes all of the values in the object array
        lines[2] = line3;
        lines[3] = line4;
        lines[4] = line5;
    }
}
