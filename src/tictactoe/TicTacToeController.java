/*
Tic Tac Toe Controller
This File Processes all actions the user makes in the FXML files. It is responsible for changing the text of buttons, checking to see if a winner is found, and calculating the AI's moves
Garrett Miller-Junk
April 16, 2020
This File does not require any direct user input. The user input is performed on the FXML files. This file serves to process these inputs made
 */
package tictactoe;

//import necessary starting libraries
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.util.Random;
import java.math.*;
import javafx.scene.input.MouseEvent;

public class TicTacToeController {
	//declare starting variables
	private static boolean singlePlayer = true;
	private static boolean setStart = false;
	private boolean isFirstPlayer = true;
	private boolean hasWon = false;
	static Stage secondaryStage;
	private boolean thinking = false;
	private static int difficulty = 9;
	private boolean aiWentFirst = false;
	private static boolean playerHasWent = false;
	
	//import FXML components
	@FXML Button b1;
	@FXML Button b2;
	@FXML Button b3;
	@FXML Button b4;
	@FXML Button b5;
	@FXML Button b6;
	@FXML Button b7;
	@FXML Button b8;
	@FXML Button b9;
	
	@FXML CheckBox single;
	@FXML CheckBox first;
	
	@FXML Slider howDifficult;
	
	@FXML
	private TextField difficultyText;
	
	@FXML GridPane gameBoard;
	
	//declare the buttons array
	private Button buttons [];
	
	//when the program is first loaded
	public void initialize() {
		//add the FXML buttons to the buttons array
		buttons =  new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9};
	}
	//create a function to control calculator button clicks
	public void buttonClickHandler(ActionEvent evt) {
		//if the game is not over
		if (!hasWon) {
			//get the event target
			Button clickedButton = (Button) evt.getTarget();
			//based off the event target, get the string that corresponds to it.
			String buttonLabel = clickedButton.getText();
			//if the button is empty and it is the first players turn
			if ("".contentEquals(buttonLabel) && isFirstPlayer) {
				//set the button's text to X
				clickedButton.setText("X");
				//make it the second players turn
				isFirstPlayer = false;
				//indicate that the player has made their move
				playerHasWent = true;
			//if it is not the first player's turn
			} else if ("".contentEquals(buttonLabel) && !isFirstPlayer) {
				//set the button's text to O
				clickedButton.setText("O");
				//indicate that it is now the first players turn
				isFirstPlayer = true;
				//show that a player has went
				playerHasWent = true;
			}
			//check to see if the game has been won
			hasWon = find3InARow();
			//check to see if the board is full
			boolean isFull = true;
			for (int i=0; i< buttons.length ; i++) {
				if (buttons[i].getText() =="") {
					isFull = false;
				}
			}
			//if the game has now been won, the board is not full, the single player mode is enabled, and the player has made their move
			if (!hasWon && !isFull && singlePlayer && playerHasWent) {
				//reset the boolean showing if the player has made their move
				playerHasWent = false;
				//if the AI difficulty is not set to 0
				if (difficulty != 0) {
					//show that all 3 in a row check actions here are not actual moves but the AI thinking
					thinking = true;
					//if the AI is the first player
					if (isFirstPlayer) {
						//call the best move function, then set the found Button's text to X
						bestMove().setText("X");
						//make it the second player's turn
						isFirstPlayer = false;
					//if the AI is the second player
					} else {
						//call the best move functi9on and set the found Button's text to O
						bestMove().setText("O");
						// make it the first players turn
						isFirstPlayer = true;
					}
					// turn off the thinking boolean
					thinking = false;
				//if the difficulty is 0
				} else {
					//if it is the first player's turn
					if (isFirstPlayer) {
						//make the AI find a random empty button and set it to X
						randomMove().setText("X");
						// make it the second player's turn
						isFirstPlayer = false;
					// if it is the second player's turn
					}else {
						//make the AI find a random empty button and set it to O
						randomMove().setText("O");
						// make it the first player's turn
						isFirstPlayer = true;
					}
				}
			// check to see if the game is now won
			hasWon = find3InARow();
			}
		}
	}
	//create a function for when the user moves the slider in the settings popup window
	public void changeTextHandler(MouseEvent e) {
		// find the value of the slider and save it to an integer
		difficulty = (int) howDifficult.getValue();
		// Based off the value of the slider, set the text box to show what difficulty the AI opponent will have.
		if (difficulty == 0) {
			difficultyText.setText("Very Easy");
		}else if (difficulty == 1) {
			difficultyText.setText("Easy");
		}else if (difficulty == 2) {
			difficultyText.setText("Moderate");
		}else if (difficulty == 3) {
			difficultyText.setText("Somewhat Difficult");
		}else if (difficulty == 4) {
			difficultyText.setText("Difficult");
		}else if (difficulty == 5) {
			difficultyText.setText("Quite Difficult");
		}else if (difficulty == 6) {
			difficultyText.setText("Very Difficult");
		}else if (difficulty == 7) {
			difficultyText.setText("Extremely Difficult");
		}else if (difficulty == 7) {
			difficultyText.setText("Impossible");
		}
	}
	// create a function that checks to see if the game is won
	private boolean find3InARow() {
		//for every button in the array
		for (int i = 0; i < buttons.length; i++) {
			//check to see if it is in the left most column
			if (i%3 == 0) {
				//if it is, check the other buttons in the row going to the right and see if they have the same letter as that button
				if (""!=buttons[i].getText() && buttons[i].getText() == buttons[i+1].getText() && buttons[i].getText() == buttons[i+2].getText()) {
					//if they do, check to make sure this is not a test situation
					if (!thinking) {
						//if it is not, highlight the winning combination of buttons
						highlightWinningCombo(buttons[i],buttons[i+1],buttons[i+2]);
					}
					//return that the game has been won
					return true;
				}
			}
			//if the button is in the first row
			if (i < 3) {
				//check the other buttons in its column and see if they all have the same letter
				if (""!=buttons[i].getText() && buttons[i].getText() == buttons[i+3].getText() && buttons[i].getText() == buttons[i+6].getText()) {
					// if they do, check to make sure this is not a test situation
					if (!thinking) {
						// if it isn't highlight the winning combination
						highlightWinningCombo(buttons[i],buttons[i+3],buttons[i+6]);
					}
					//return that the game has been won
					return true;
				}
			}
		}
		//check the top left to bottom right diagonal
		if(""!=b1.getText()&& b1.getText() == b5.getText() && b5.getText() == b9.getText()) {
			// if they do, check to make sure this is not a test situation
			if (!thinking) {
				// if it isn't highlight the winning combination
				highlightWinningCombo(b1,b5,b9);
			}
			//return that the game has been won
			return true;
		}
		//check to if the top right to bottom left diagonal
		if(""!=b3.getText()&& b3.getText() == b5.getText() && b5.getText() == b7.getText()) {
			// if they do, check to make sure this is not a test situation
			if (!thinking) {
				// if it isn't highlight the winning combination
				highlightWinningCombo(b3,b5,b7);
			}
			//return that the game has been won
			return true;
		}
		// if none of the above tests worked, return that the game has not been won
		return false;
	}
	//create a function to highlight the winning buttons based off the 3 buttons sent to it
	private void highlightWinningCombo(Button first, Button second, Button third) {
		// for each of the buttons, add the style class "winning-button" to give it the extra style created for the winning buttons
		first.getStyleClass().add("winning-button");
		second.getStyleClass().add("winning-button");
		third.getStyleClass().add("winning-button");
	}
	//create a function for when the user saves their settings
	public void settingsSaveHandler (final ActionEvent evt) {
		//change the setting variables according to the user's choices
		singlePlayer = single.isSelected();
		setStart = first.isSelected();
		difficulty = (int)howDifficult.getValue();
		//find the source of the action event and find the stage that corresponds to it
		final Node source = (Node) evt.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		//use the stage to close the settings popup
		stage.close();
	}
	// create a handler for the menu
	public void menuClickHandler(ActionEvent evt) {
		//when the menu is clicked find which part of the menu was clicked and get its name
        MenuItem clickedMenu = (MenuItem) evt.getTarget();
        String menuLabel = clickedMenu.getText();
        //if the user chose to play again
        if ("Play".equals(menuLabel)) {
            ObservableList<Node> buttons = gameBoard.getChildren();
            //reset all of the button names remove any wining button styles on them
            buttons.forEach(btn -> {
                ((Button) btn).setText("");
                
                btn.getStyleClass().remove("winning-button");
            });
            //reset the starting variables
            isFirstPlayer = true; //new game starts with X
            hasWon = false;
            aiWentFirst = false;
            //if the user is choosing to play single player without a set start, flip a coin
    		if (singlePlayer && !setStart && (Math.random() < 0.5)) {
    			//if the coin indicates the AI should go first
    			//make the AI go in the top left (what the AI algorithm would cause it to do anyway (saves time))
        		b1.setText("X");
        		//make it the second player's turn
        		isFirstPlayer = false;
        		//show that the AI went first
        		aiWentFirst = true;
    		}
        }
        
        //if the user chose to find out about the program
        if ("About".equals(menuLabel)) {
        	//open the about FXML
        	openNewWindow("About.fxml");
        }
        
        //if the user chose to find out how to play
        if ("How to Play".equals(menuLabel)) {
        	//open the how to play FXML
        	openNewWindow("HowToPlay.fxml");
        }
        
        //if the user chose to quit the program
        if ("Quit".equals(menuLabel)) {
        	//exit the platform and system, shutting down the entire program
        	Platform.exit();
        	System.exit(0);
        }
        
        //if the user chose to change their settings
        if ("Settings".equals(menuLabel)) {
        	//open the settings FXML file
        	openNewWindow("Settings.fxml");
        }
    }
	//create a function for if the AI is to make a random move (difficulty 0)
	private Button randomMove() {
		//create a loop that continues until a random button is found
		boolean findingButton = true;
		while (findingButton) {
			//pick a random button by generating a random num that correlates to a button
			int randomNum = (int)(Math.random()*9);
			//if the button is empty
			if (buttons[randomNum].getText()=="") {
				//end the loop and return the empty button
				findingButton = false;
				return buttons[randomNum];
			}
		}
		//return nothing (won't ever come up, the function just likes there being a return that is not imbedded in an if statement return)
		return null;
	}
	//create a function for when the user wants to open a new window based off the window's name
	private void openNewWindow(String windowName) {
		//create a try statement for if an error occurs
		try {
			//load the pop up you created based off of the name given
			Pane howTo = (Pane)FXMLLoader.load(getClass().getResource(windowName));
			
			//create a new scene
			Scene howToScene = new Scene (howTo,600,400);
			
			//add css to the new scene
			howToScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//create new stage to put scene in
			secondaryStage = new Stage();
			secondaryStage.setScene(howToScene);
			secondaryStage.setResizable(false);
			secondaryStage.showAndWait();
		//if an error occurs print the error	
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//close the window that is currently open
	public void closeCurrentWindow(final ActionEvent evt) {
		final Node source = (Node) evt.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
	//create a function to find the best move based for the AI
	private Button bestMove() {
		//declare starting variables
		int scoreTop = -10000;
		int score;
		Button move = null;
		//go through each button
		for (int i=0; i< buttons.length ; i++) {
			//if the button is empty
			if (buttons[i].getText() == "") {
				//if its the first player's turn. set it to X
				if (isFirstPlayer) {
				buttons[i].setText("X");
				}else {
				//if its the second player's turn, set it to O
				buttons[i].setText("O");	
				}
				//call the minimax algorithm to find the score for the given situation, have it start with minimizing
				score = minimax(false , 1);
				//reset the button's text
				buttons[i].setText("");
				//if the score from the minimax algorithm is greater than the current highest score
				if (score > scoreTop) {
					//make the score the new highscore
					scoreTop = score;
					//save the button as the best move to make
					move = buttons[i];
				}
			}
		}
	//return the best move to make
	return move;
	}
	//create a minimax algorithm that goes through all game outcomes and chooses the best path
	private int minimax(boolean whosPlaying, int depth) {
		//check to see if the game has been won
		if (find3InARow()) {
			//if it is, check to see if it has been won by the minimizing or maximising player
			if (whosPlaying) {
				//if it is the minimizing player, return a negative score divided by the depth this was achieved at (leads to the computer wanting to draw out games it looses)
				return (-2520/depth);
			} else {
				//if it is the maximizing player, return a positive score divided by the depth this was achieved at (leads to the computer wanting to win as fast as possible)
				return (2520/depth);
			}
		}
		//if the depth the algorithm has achieved is equal to its difficulty, instead return a score 0
		if (depth == difficulty) {
			return 0;
		}
		//check to see if the game is a tie
		boolean isFull = true;
		for (int i=0; i< buttons.length ; i++) {
			if (buttons[i].getText() =="") {
				isFull = false;
			}
		}
		// if it is, return a score of 0
		if (isFull) {
			return 0;
		}
		//if it is the maximizing player's turn
		if (whosPlaying) {
			//start with a low score
			int scoreTop = -10000;
			//go through every button
			for (int i=0; i< buttons.length ; i++) {
				//check to see if the button is empty
				if (buttons[i].getText() =="") {
					//if the AI went first
					if (aiWentFirst) {
						//set the button's text to X
						buttons[i].setText("X");
					// if the AI instead went second
					}else {
						//set the button's text to O
						buttons[i].setText("O");	
					}
					//call the minimax algorithm on this new board state, now with the minimizing player and with 1 greater of a depth
					int score = minimax(false, depth + 1);
					//reset the button
					buttons[i].setText("");
					//hold onto the score if it is greater than the best score achieved up to this point.
					if (score > scoreTop) {
						scoreTop = score;
					}
				}
			}
			//return the top score
			return scoreTop;
		// if it is instead the minimizing player's turn
		} else {
			//start with a high score
			int scoreBottom = 10000;
			//go through every button
			for (int i=0; i< buttons.length ; i++) {
				//check to see if the button is empty
				if (buttons[i].getText() =="") {
					//if the AI went first
					if (aiWentFirst) {
						//set the button's text to O
						buttons[i].setText("O");
					//if the AI went second
					}else {
						//set the button's text to X
						buttons[i].setText("X");	
					}
					//call the minimax algorithm, set for the maximizing player and increase the depth by 1
					int score = minimax(true, depth + 1);	
					//reset the button picked
					buttons[i].setText("");
					//if the score is less that the lowest score achieved, save this new score.
					if (score < scoreBottom) {
						scoreBottom = score;
					}
				}
			}
			//return the lowest score
			return scoreBottom;
		}
	}
}
