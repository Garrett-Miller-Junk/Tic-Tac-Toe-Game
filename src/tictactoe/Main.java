/*
Main Tic Tac Toe File
This File is used for the booting of the tic tac toe FXML file. It is also used to keep the program constantly running
Garrett Miller-Junk
April 16, 2020
All that is required to is for the user to use this file to boot the Tic Tac Toe FXML file. all other user inputs are on the FXML file

For the FXML Tic Tac Toe File, 9 buttons will appear along with menu labels. the user will select the button correlating to the move they want to make in tic tac toe. From this the handler will go through all necessary computations
and change the screen to reflect the choices made. The user can press on the menu labels to navigate to different parts. These include playing again, quitting the program, reading instructions, learning about the program, and changing the settings

 */
package tictactoe;
//import the starting libraries	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	//when the program starts
	@Override
	public void start(Stage primaryStage) {
		//try this code and catch any errors that show up
		try {
			//open the tic tac toe FXML file
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("TicTacToe.fxml"));
			//create a new scene
			Scene scene = new Scene(root,300,320);
			//add css to the scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//set the scene to a stage and show the stage
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		//if an error occurs, show the error
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//while the program is running launch that arguments
	public static void main(String[] args) {
		launch(args);
	}
}
