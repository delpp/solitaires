package solitaire.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class AnimateCards {
	private List<Card> stack;
	private Stack sourceStack;
	private double positionXOnBoard;
	private double positionYOnBoard;	
	private double targetXPosition;
	private double targetYPosition;	
	private int numberShownCards;
	private int shiftCardX;
	private int shiftCardY;
	private int numberFrames;
	private int counterDoneFrames;
	private double stepX;
	private double stepY;
	
	public AnimateCards() {
		positionXOnBoard = 0;
		positionYOnBoard = 0;
		targetXPosition = 0;
		targetYPosition = 0;
		numberShownCards = 1;
		shiftCardX = 0;
		shiftCardY = 0;
		stack = new ArrayList<Card>();
		sourceStack = new Stack(); 
		counterDoneFrames = 0;
	}

	public void drawStack(GraphicsContext gc) {	
		int counter = 0;
		if (stack.size() > 0)
			for (int i = stack.size() - numberShownCards; i < stack.size(); i++) {
				gc.drawImage(stack.get(i).getImageCard(), positionXOnBoard + counter * shiftCardX, positionYOnBoard + counter * shiftCardY);
				counter++;
			}		
	}
	
	public void setStack(List<Card> cards) {
		stack.addAll(cards);
	}
	
	public List<Card> getStack() {
		return stack;
	}
	
	public void setSourceStack(Stack sourceStack) {
		this.sourceStack = sourceStack;
	}
	
	public Stack getSourceStack() {
		return sourceStack;
	}
	
	public void setPositionXOnBoard(double positionXOnBoard) {
		this.positionXOnBoard = positionXOnBoard;
	}
	
	public void setPositionYOnBoard(double positionYOnBoard) {
		this.positionYOnBoard = positionYOnBoard;
	}
	
	public void setTargetXPosition(double targetXPosition) {
		this.targetXPosition = targetXPosition;
	}
	
	public void setTargetYPosition(double targetYPosition) {
		this.targetYPosition = targetYPosition;
	}
	
	public void setNumberAnimatedFrames(int numberFrames) {
		this.numberFrames = numberFrames;
	}
	
	public int getNumberAnimatedFrames() {
		return numberFrames;
	}
	
	public void setSteps() {
		stepX = (positionXOnBoard - targetXPosition) / numberFrames;
		stepY = (positionYOnBoard - targetYPosition) / numberFrames;
	}
	
	public void setShiftCardX(int shiftCardX) {
		this.shiftCardX = shiftCardX;
	}
	
	public void setShiftCardY(int shiftCardY) {
		this.shiftCardY = shiftCardY;
	}
	
	public void setNumberShownCards(int numberShownCards) {
		this.numberShownCards = numberShownCards;
	}
	
	public boolean updatePosition() {
		if (stack.size() > 0) {		
			if (counterDoneFrames == numberFrames) 
				counterDoneFrames = 0;				
			else
			{
				positionXOnBoard = positionXOnBoard - stepX;
				positionYOnBoard = positionYOnBoard - stepY;
				counterDoneFrames = counterDoneFrames + 1;
				return true;
			}
		}
		return false;
	}
}
