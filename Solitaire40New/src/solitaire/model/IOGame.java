package solitaire.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOGame {
	
	public static void savaGame(GameBoard gameBoard) throws IOException{

		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("saveGame.pas"));
		
		output.writeObject(gameBoard);
		
		output.close();
	}
	
	public static GameBoard loadGame() throws IOException, ClassNotFoundException{
		GameBoard gameBoard = null;
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("saveGame.pas"));
		gameBoard = (GameBoard) input.readObject();
		input.close();
		return gameBoard;
	}
	
}
