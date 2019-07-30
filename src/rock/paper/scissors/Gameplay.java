/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rock.paper.scissors;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;            // Imports necessary libraries
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmed1045
 */
public class Gameplay {
    private boolean ran = false, ran2 = false;
    private String choice = "";
    private ArrayList<Integer> allHistory = new ArrayList();        // Creates instance fields
    private ArrayList<Integer> userHistory = new ArrayList();
    public String returnMove(String difficulty){
        if (difficulty.equals("easy")){
            int temp = 1 + (int) (Math.random() * 3);
            switch(temp){
                case 1:                 // If the difficulty is easy, it will return a random value
                    return "rock";
                case 2:
                    return "paper";
                case 3:
                    return "scissors";
            }
        }
        else if (difficulty.equals("hard")){
            int weight1 = 0, weight2 = 0, weight3 = 0;      // Initializes weights
            if (!ran2){
                allHistory = load();        // Loads the text document only once
                ran2 = true;
            }    
            if (!ran){
                for (int i = 0; i < 8; i++){
                    userHistory.add(1 + (int) (Math.random() * 3));     // Populates the array with random values once
                }
                ran = true;
            }
            for (int x = 2; x <= 8; x++){    // Runs this loop to check repitions of 2 values through 6 values
                for (int j = 0; j < allHistory.size() - x; j++){        // Runs this code to sift through all of the values in the history
                    boolean match = true;       // Assumes that there will be a match
                    int k = 0;
                    for (k = 0; k < x; k++){ // This loop will check the last values in the userHistory and compare them to corresponding values in all history (frameshift)
                        if (userHistory.get(8 - x + k) != allHistory.get(j + k)){       
                            match = false;      // If any of the values do not match, match becomes false
                        }
                    } 
                    if (match){     // If all of the values were equal, it adds to weight based on the length of the corresponding values
                        switch (allHistory.get(j + k)){
                            case 1:
                                weight1 += Math.pow(x, 2);
                                break;
                            case 2:
                                weight2 += Math.pow(x, 2);
                                break;
                            case 3:
                                weight3 += Math.pow(x, 2);
                                break;
                        }
                    }
                }               
            }
            int largest = Collections.max(Arrays.asList(weight1, weight2, weight3));   // Determines the largest of these three values (finds the greatest weight)
            String name = "";
            if (largest == weight1){
                name = "weight1";
            }
            else if (largest == weight2){
                name = "weight2";           // Sets variable name to the name of the largest weight
            }
            else if (largest == weight3){
                name = "weight3";
            }
            String nextMove = calcNextReturn(name);  // Calls calcNextReturn with the passed thorugh name to get the best move to return
            save();  // Runs save to save all of the history
            return nextMove;   // Returns the best move
        }
        return returnMove("easy");  // If there is no match, it will return the a random number (happens once on the first match)
    }
    
    public String calcNextReturn (String name){
        if (name.equals("weight1")){
            return "paper";
        }
        else if (name.equals("weight2")){       // Determinmes the winning move based on the most likely next move made by the user
            return "scissors";
        }    
        else if (name.equals("weight3")){
            return "rock";
        }
        else {
            return "paper";
        }
    }
    
    
    public void updateArray(String userChoice){
        int numToAdd = 0;                           
        if (userChoice.equals("rock")){
            numToAdd = 1;
        }
        else if (userChoice.equals("paper")){           // Adds the userValue to teh array of userHistory and to all of the history
            numToAdd = 2;   
        }
        else if (userChoice.equals("scissor")){
            numToAdd = 3;
        }
        userHistory.add(numToAdd);
        userHistory.remove(0);  // Removes the first value in the array
        allHistory.add(numToAdd); 
    }
    
    public ArrayList load()
    {
        try{
            FileReader reader = new FileReader("Resources/moves.txt");
            ArrayList<Integer> moves = new ArrayList();                 // Prepares to load a file
            Scanner in = new Scanner(reader);
            while(in.hasNextLine())
            {
                String temp = in.nextLine();            // Scans the file and adds all of the values to moves
                moves.add(Integer.parseInt(temp));
            }
            return moves;  // Returns all of the moves
        } catch (FileNotFoundException ex) {
            return null;
        }
    }
    
    public void save(){
        String outFile = "Resources/moves.txt";  // Creates a path to an outfile
        try {
                PrintWriter out = new PrintWriter(outFile);  
                for (int i = 0; i < allHistory.size(); i++){
                    out.println(allHistory.get(i));             // Adds all of the values in allHistory to this file
                }   
                out.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex); // Gets a logger of the error that occured
        }
    }
}
