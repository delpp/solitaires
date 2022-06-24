package model;

import java.io.*;

public class IOGame {

	public static void savaGame(GameBoard gameBoard) throws IOException{

		DataOutputStream output = new DataOutputStream(new FileOutputStream("saveGame.pas"));

		output.writeUTF(gameBoard.getCode());

		output.close();
	}

	public static String loadGame() throws IOException {
		String readString;
		DataInputStream input = new DataInputStream(new FileInputStream("saveGame.pas"));
		readString = input.readUTF();
		input.close();
		System.out.println("***************** ODCZYT *******************");
		System.out.println("odczytano");
		System.out.println(readString);
		System.out.println("********************************************");
		return readString;
	}

}
