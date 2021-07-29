package solitaire.controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Start extends Application{
	
	@Override
	public void start(Stage primaryStage) throws CloneNotSupportedException{
		SolitaireGame game = new SolitaireGame(primaryStage);
		game.run();
	} 
	
	public static void main(String[] args) {
		launch(args);

	}
}

