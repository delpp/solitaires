//package solitaire.model;
//
//import java.util.List;
//
//import javafx.scene.canvas.GraphicsContext;
//
//public class BackStack extends Stack{
//	private int destinationPositionXOnBoard;
//	private int destinationPositionYOnBoard;
//	private double stepX;
//	private double stepY;
//	
//	public BackStack(String name, 
//			int positionXOnBoard, 
//			int positionYOnBoard, 
//			int shiftX, 
//			int shiftY,
//			int numberCardsPossibleToTake, 
//			int numberPossibleVisibleCards,
//			int numberPossibleVisibleCardsInFront,
//			int acceptAscending,
//			int numberOfAcceptedCardInOneMove,
//			List<CardType> whenEmptyAcceptTypeCard,
//			List<CardNumber> whenEmptyAcceptNumberCard,
//			int destinationPositionXOnBoard,
//			int destinationPositionYOnBoard,
//			int stepsToDestiantion) {
//		super(	name, 
//				positionXOnBoard, 
//				positionYOnBoard, 
//				shiftX, 
//				shiftY, 
//				numberCardsPossibleToTake, 
//				numberPossibleVisibleCards, 
//				numberPossibleVisibleCardsInFront,
//				acceptAscending, 
//				numberOfAcceptedCardInOneMove,
//				whenEmptyAcceptTypeCard,
//				whenEmptyAcceptNumberCard);
//		this.destinationPositionXOnBoard = destinationPositionXOnBoard;
//		this.destinationPositionYOnBoard = destinationPositionYOnBoard;
//		stepX = (destinationPositionXOnBoard - positionXOnBoard) / stepsToDestiantion;
//		stepY = (destinationPositionYOnBoard - positionYOnBoard) / stepsToDestiantion;
//	}
//	
//	public int getDestinationPositionXOnBoard() {
//		return destinationPositionXOnBoard;
//	}
//
//	public void setDestinationPositionXOnBoard(int destinationPositionXOnBoard) {
//		this.destinationPositionXOnBoard = destinationPositionXOnBoard;
//	}
//
//	public int getDestinationPositionYOnBoard() {
//		return destinationPositionYOnBoard;
//	}
//
//	public void setDestinationPositionYOnBoard(int destinationPositionYOnBoard) {
//		this.destinationPositionYOnBoard = destinationPositionYOnBoard;
//	}
//	
//	public void reducePositionToDestination() {
//		super.setPositionXOnBoard(super.getPositionXOnBoard() - stepX);
//		super.setPositionYOnBoard(super.getPositionYOnBoard() - stepY);
//		
//		System.out.print("Pozycja " + super.getName() + " X: " + super.getPositionXOnBoard());
//		System.out.println(", Y: " + super.getPositionYOnBoard());
//	}
//	
//	public void drawStack(GraphicsContext gc) {	
//		if ((super.getPositionXOnBoard() != destinationPositionXOnBoard) || 
//			(super.getPositionYOnBoard() != destinationPositionYOnBoard))	
//				reducePositionToDestination();
//		super.drawStack(gc);
//	}
//}
