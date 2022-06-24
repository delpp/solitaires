package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.GameBoard;

import java.io.IOException;


public class SolitaireGame {
	private final int WIDTH = 900;
	private final int HEIGHT = 580;
	
	final int NUMBEROFGAMESTACKS = 10;
	final int NUMBEROFFINALSTACKS = 8;
	
	private Stage stage;
	private GameAnimationTimer animationTimer;
	private Group root;
	private Canvas canvas;
	private Scene scene;
	private PixelReader cardsImagesPixelReader;
	private PixelReader buttonsImagesPixelReader;
	private Image cardsImages;
	private Image boardImage;
	private Image buttonImages;
	private GraphicsContext gc;
	private GameBoard gameBoard;
	
	public SolitaireGame(Stage primaryStage){
		stage = primaryStage;
		stage.setTitle("Pasjans");
	}
	
	private class GameAnimationTimer extends AnimationTimer{
		@Override
		public void handle(long currentNanoTime) { 
			update(currentNanoTime);
			draw(currentNanoTime);
		}
	}
		
	public void run() {
		initialize();	
		stage.show();	
		animationTimer = new GameAnimationTimer();
		animationTimer.start();
	}
	
	public void initialize() {		
		cardsImages = new Image("/deck.png");
		boardImage = new Image("/background.png");
		buttonImages = new Image("/buttons.png");
		cardsImagesPixelReader = cardsImages.getPixelReader();
		buttonsImagesPixelReader = buttonImages.getPixelReader();
		root = new Group();
		canvas = new Canvas(WIDTH, HEIGHT);		
		root.getChildren().add(canvas);
		scene = new Scene(root);
		gc = canvas.getGraphicsContext2D();
		stage.setScene(scene);
		stage.setResizable(true);
		stage.sizeToScene();
			
		gameBoard = new GameBoard(gc, cardsImagesPixelReader, buttonsImagesPixelReader,2, NUMBEROFGAMESTACKS, NUMBEROFFINALSTACKS);

		gameBoard.shuffleDeck();				
		gameBoard.distributeCards();
		
		scene.setOnMousePressed(mouseEvent -> {
			try {
				pressedMouse(mouseEvent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		scene.setOnMouseDragged(mouseEvent -> draggedMouse(mouseEvent));
		scene.setOnMouseReleased(mouseEvent -> releasedMouse(mouseEvent));
	}
	
	public void update(long currentNanoTime) {
		gameBoard.updatePosition();
	}
	
	public void draw(long currentNanoTime) {
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		gc.drawImage(boardImage, 0, 0);
		
		gameBoard.drawGameBoard();
	}
	
	public void pressedMouse(MouseEvent mouseEvent) throws IOException {
		double x = mouseEvent.getSceneX();
		double y = mouseEvent.getSceneY();
		
		gameBoard.actionOnPressedMouse(x, y);	
	}
	
	public void draggedMouse(MouseEvent mouseEvent){
		double x = mouseEvent.getSceneX();
		double y = mouseEvent.getSceneY();
		
		gameBoard.actionOnDraggedMouse(x, y);	
	}
	
	public void releasedMouse(MouseEvent mouseEvent){
		double x = mouseEvent.getSceneX();
		double y = mouseEvent.getSceneY();
		
		gameBoard.actionOnReleasedMouse(x, y);
	}
	
}
