package solitaire.model;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;

public class HandStack extends Stack{
	private Stack sourceStack;
	private double deltaX;
	private double deltaY;
	private double targetPositionXOnBoard;
	private double targetPositionYOnBoard;

	public HandStack(String name, 
			PixelReader imageCardWhenEmpty,
			int positionXOnBoard, 
			int positionYOnBoard, 
			int shiftX, 
			int shiftY,
			int numberCardsPossibleToTake, 
			int numberPossibleVisibleCards,
			int numberPossibleVisibleCardsInFront,
			int acceptAscending,
			int numberOfAcceptedCardInOneMove,
			List<CardType> whenEmptyAcceptTypeCard,
			List<CardNumber> whenEmptyAcceptNumberCard,
			int destinationPositionXOnBoard,
			int destinationPositionYOnBoard,
			int stepsToDestiantion,
			int positionXOnImage,
			int positionYOnImage) {
		super(	name, 
				imageCardWhenEmpty,
				positionXOnBoard, 
				positionYOnBoard, 
				shiftX, 
				shiftY, 
				numberCardsPossibleToTake, 
				numberPossibleVisibleCards, 
				numberPossibleVisibleCardsInFront,
				acceptAscending, 
				numberOfAcceptedCardInOneMove,
				whenEmptyAcceptTypeCard,
				whenEmptyAcceptNumberCard,
				positionXOnImage,
				positionYOnImage);
		this.targetPositionXOnBoard = destinationPositionXOnBoard;
		this.targetPositionYOnBoard = destinationPositionYOnBoard;
	}
	
	public double getTargetPositionXOnBoard() {
		return targetPositionXOnBoard;
	}

	public void setTargetPositionXOnBoard(double targetPositionXOnBoard) {
		this.targetPositionXOnBoard = targetPositionXOnBoard;
	}

	public double getTargetPositionYOnBoard() {
		return targetPositionYOnBoard;
	}

	public void setTargetPositionYOnBoard(double targetPositionYOnBoard) {
		this.targetPositionYOnBoard = targetPositionYOnBoard;
	}
	
	public void setSourceStack(Stack stack) {
		sourceStack = stack;
	}
	
	
	public Stack getSourceStack() { 
		return sourceStack; 
	}
	
	public double getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}

	public double getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}
	
	
	public void drawStack(GraphicsContext gc) {		
		int counter = 0;
		if (this.getStack().size() > 0)
			for (int i = this.getStack().size() - this.getNumberShownCards(); i < this.getStack().size(); i++) {
				gc.drawImage(this.getStack().get(i).getImageCard(), 
						this.getPositionXOnBoard() + counter * this.getShiftCardX() - deltaX, 
						this.getPositionYOnBoard() + counter * this.getShiftCardY() - deltaY);
				counter++;
			}	
	}
	
}
