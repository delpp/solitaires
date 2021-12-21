package solitaire.model;

import java.io.Serializable;
import java.util.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * @author Marcin Zglenicki
 *
 */
public class Stack implements Serializable {
	static final long serialVersionUID = 1L;
	private List<Card> stack;
	private final String name;

	private WritableImage imageWhenEmpty;

	private double positionXOnBoard;
	private double positionYOnBoard;

	private final int shiftCardX;
	private final int shiftCardY;


	private int numberPossibleToTakeCards;
	private int numberPossibleVisibleCards;
	private int numberPossibleVisibleCardsInFront;

	private int numberShownCards;
	private int numberActivatedCards;

	private int acceptAscending; // 0 - accept all, 1 - accept ascending, 2 - accept descending

	private int startPositionX;
	private int startPositionY;
	private int endPositionX;
	private int endPositionY;

	private int pressedCardNumberInStack;

	private int numberOfAcceptedCardInOneMove;

	private List<CardType>  whenEmptyAcceptTypeCard;
	private List<CardNumber> whenEmptyAcceptNumberCard;

	private List<Stack> acceptableCardsFromStacks;
	private List<Card> tempStack;

	private int counter;

	public Stack() {
		name = "empty";
		shiftCardX = 0;
		shiftCardY = 0;
	}


	/**
	 * @param name – stack name
	 * @param imagesOfCards – image of all cards
	 * @param positionXOnBoard – stack's X position on Board
	 * @param positionYOnBoard – stack's X position on Board
	 * @param shiftX – shift X position next cards on stack, when shows more than one card
	 * @param shiftY – shift X position next cards on stack, when shows more than one card
	 * @param numberCardsPossibleToTake – number cards possible to take from stack
	 * @param numberPossibleVisibleCards – number visible cards on stack
	 * @param numberPossibleVisibleCardsInFront  – number visible cards on stack which shows front
	 * @param acceptAscending – 0: accept all, 1: accept ascending, 2: accept descending
	 * @param numberOfAcceptedCardInOneMove – number of accepted card in one move
	 * @param whenEmptyAcceptTypeCard – which type cards accept when stack is empty
	 * @param whenEmptyAcceptNumberCard – which number(s) card(s) accept when stack is empty
	 * @param positionXOnImage – number of column on image file
	 * @param positionYOnImage – number of row on image file
	 */
	public Stack(String name,
				 PixelReader imagesOfCards, //imageCardWhenEmpty,
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
				 int positionXOnImage,
				 int positionYOnImage) {
		this.name = name;
		this.positionXOnBoard = positionXOnBoard;
		this.positionYOnBoard = positionYOnBoard;
		this.shiftCardX = shiftX;
		this.shiftCardY = shiftY;
		stack = new ArrayList<Card>();
		this.numberPossibleToTakeCards = numberCardsPossibleToTake;
		this.numberPossibleVisibleCards = numberPossibleVisibleCards;
		this.numberPossibleVisibleCardsInFront = numberPossibleVisibleCardsInFront;
		this.acceptAscending = acceptAscending;
		this.numberOfAcceptedCardInOneMove = numberOfAcceptedCardInOneMove;
		this.whenEmptyAcceptTypeCard = whenEmptyAcceptTypeCard;
		this.whenEmptyAcceptNumberCard = whenEmptyAcceptNumberCard;

		acceptableCardsFromStacks = new ArrayList<Stack>();
		tempStack = new ArrayList<Card>();

		numberShownCards = 0;
		pressedCardNumberInStack = 0;

		numberActivatedCards = 0;
		startPositionX = positionXOnBoard;
		startPositionY = positionYOnBoard;
		endPositionX = positionXOnBoard + Card.CARDWIDTH;
		endPositionY = positionYOnBoard + Card.CARDHEIGHT;

		imageWhenEmpty = new WritableImage(imagesOfCards, positionXOnImage*Card.CARDWIDTH, positionYOnImage*Card.CARDHEIGHT, Card.CARDWIDTH, Card.CARDHEIGHT);

		counter = 0;
	}


	public List<Card> getStack() {
		return stack;
	}

	public int getStackSize() {
		return stack.size();
	}

	public double getPositionXOnBoard() {
		return positionXOnBoard;
	}

	public void setPositionXOnBoard(double positionXOnBoard) {
		this.positionXOnBoard = positionXOnBoard;
	}

	public double getPositionYOnBoard() {
		return positionYOnBoard;
	}

	public void setPositionYOnBoard(double positionYOnBoard) {
		this.positionYOnBoard = positionYOnBoard;
	}

	public int getNumberShownCards() {
		return numberShownCards;
	}

	public int getShiftCardX() {
		return shiftCardX;
	}

	public int getShiftCardY() {
		return shiftCardY;
	}

	public void addAcceptedStack (Stack stack){
		acceptableCardsFromStacks.add(stack);
	}

	public int getNumberOfAcceptedCardInOneMove() {
		return numberOfAcceptedCardInOneMove;
	}

	public void setNumberPossibleVisibleCardsInFront(int number) {
		this.numberPossibleVisibleCardsInFront = number;
	}

	public int getNumberPossibleVisibleCardsInFront() {
		return numberPossibleVisibleCardsInFront;
	}

	public int getPressedCardNumberInStack() {
		return pressedCardNumberInStack;
	}

	public boolean isAcceptCardsFromStack (Stack stack){
		return acceptableCardsFromStacks.contains(stack);
	}

	public boolean isAcceptCard (Card cardToAccept){
		if (isStackEmpty()) {
			if (isAcceptCardWhenEmpty(cardToAccept)) return true;
			else return false;
		}

		else {
			Card cardOnTopStack = getCard(stack.size()-1);

			System.out.println("karta na górze stosu: " + cardOnTopStack);
			System.out.println("testowana karta: " + cardToAccept);

			if (cardOnTopStack.getCardType() == cardToAccept.getCardType()) {
				System.out.println("testowana karta: " + cardToAccept + " pasuje typem do karty: " + cardOnTopStack);
				if (acceptAscending == 2)
					if (cardOnTopStack.getCardNumber().getNumber()-1 == cardToAccept.getCardNumber().getNumber())
						return true;
				if (acceptAscending == 1)
					if (cardOnTopStack.getCardNumber().getNumber()+1 == cardToAccept.getCardNumber().getNumber())
						return true;
				if (acceptAscending == 0)
					return true;
			}
			else return false;


		}
		return false;
	}

	private boolean isStackEmpty() {
		if (stack.isEmpty()) return true;
		else return false;
	}

	private boolean isAcceptCardWhenEmpty(Card cardToAccept){
		if ((isAcceptCardType(cardToAccept.getCardType()))	&& 	isAcceptCardNumber(cardToAccept.getCardNumber()) ){
			System.out.println("Stos " + name + " jest pusty i akceptuje kartę: " + cardToAccept);
			return true;
		}

		return false;
	}

	private boolean isAcceptCardType(CardType cardType) {
		if (whenEmptyAcceptTypeCard.stream().filter(x -> x.equals(cardType)).findAny().isPresent()) return true;
		else return false;
	}

	private boolean isAcceptCardNumber(CardNumber cardNumber) {
		if (whenEmptyAcceptNumberCard.stream().filter(x -> x.getNumber() == (cardNumber.getNumber())).findAny().isPresent()) return true;
		else return false;
	}

	public boolean addCard(Card card) {
		boolean addedCard = false;
		if (numberOfAcceptedCardInOneMove > 0) {
			addedCard = stack.add(card);
			numberShownCards = (stack.size() < numberPossibleVisibleCards) ?  stack.size() : numberPossibleVisibleCards;
			numberActivatedCards = (stack.size() < numberPossibleToTakeCards) ?  stack.size() : numberPossibleToTakeCards;
		}
		return addedCard;
	}

	public boolean addCards (List<Card> cards) {
		boolean addedCard = false;
		System.out.println("Liczba akceptowanych kart w jednym ruchu w stosie " + name + ": " + numberOfAcceptedCardInOneMove);

		System.out.println("Przekładam karty: " + cards + " na stos " + name);
		addedCard = stack.addAll(cards);
		updateData();
		setImageOnCards();
		return addedCard;
	}

	public void setImageOnCards() {
		stack.stream().forEach(x -> x.setVisibleFrontFlag(false));

		int numberCardsToSetFrontImage = (numberShownCards > numberPossibleVisibleCardsInFront) ? numberPossibleVisibleCardsInFront : numberShownCards;

		for (int i = stack.size(); i > stack.size() - numberCardsToSetFrontImage; i--)
			stack.get(i-1).setVisibleFrontFlag(true);
	}

	public Card getCard(int numberOfCardInStack) {
		return stack.get(numberOfCardInStack);
	}

	public List<Card> takeCards() {
		tempStack.clear();

		int stackSize = stack.size();

		if (stackSize > 0) {
			for (int i = pressedCardNumberInStack; i < stackSize; i++)
				tempStack.add(stack.get(i));
			int counter = 0;
			for (int i = pressedCardNumberInStack; i < stackSize; i++) {
				counter = stack.size();
				stack.remove(counter-1);
			}
		}
		return tempStack;
	}

	public Card takeCardFromTop() {
		Card card = stack.get(stack.size()-1);
		stack.remove(stack.size()-1);

		return card;
	}

	public void updateData() {
		numberShownCards = (stack.size() < numberPossibleVisibleCards) ?  stack.size() : numberPossibleVisibleCards;
		numberActivatedCards = (stack.size() < numberPossibleToTakeCards) ?  stack.size() : numberPossibleToTakeCards;
	}

	public boolean isMyArea(double x, double y) {
		if (stack.size() == 0) {
			if ((x > startPositionX)
					&& (x < endPositionX)
					&& (y > startPositionY)
					&& (y  < endPositionY))
				return true;
		}
		else
			for (int i = numberShownCards - 1; i >= 0 ; i--) {
				if ((x > startPositionX + i * shiftCardX)
						&& (x < endPositionX + i * shiftCardX)
						&& (y > startPositionY + i * shiftCardY)
						&& (y  < endPositionY + i * shiftCardY)) {
					System.out.println("to jest obszar stosu: " + name);
					System.out.println("karta numer: " + i);
					return true;
				}
			}
		return false;
	}

	public boolean isMyActiveArea(double x, double y) {
		if (stack.size() == 0) {
			if ((x > startPositionX)
					&& (x < endPositionX)
					&& (y > startPositionY)
					&& (y  < endPositionY))
				return true;
		}
		else
			for (int i = numberShownCards - 1; i >= 0 ; i--) {
				if ((x > startPositionX + i * shiftCardX)
						&& (x < endPositionX + i * shiftCardX)
						&& (y > startPositionY + i * shiftCardY)
						&& (y  < endPositionY + i * shiftCardY)) {
					System.out.println("to jest obszar stosu: " + name);
					System.out.println("karta numer: " + i);
					if (numberShownCards - i <= numberActivatedCards) {
						pressedCardNumberInStack = i + (stack.size() - numberShownCards);
						System.out.println("naciśnięto na " + pressedCardNumberInStack  + " kartę od dołu stosu " + name);
						System.out.println("rozmiar stosu " + name + ": " + stack.size());
						return true;
					}
				}
			}
		return false;
	}

	public void drawStack(GraphicsContext gc) {
		counter = 0;
		if (stack.size() > 0)
			for (int i = stack.size() - numberShownCards; i < stack.size(); i++) {
				gc.drawImage(stack.get(i).getImageCard(), positionXOnBoard + counter * shiftCardX, positionYOnBoard + counter * shiftCardY);
				counter++;
			}
		else {
			gc.drawImage(imageWhenEmpty, positionXOnBoard, positionYOnBoard);

		}
	}

	public String getName() {
		return name;
	}

	public String getCode(){
		StringBuilder stackAsString = new StringBuilder();
		stack.stream().forEach(x -> {
			stackAsString.append(x.getCode());
		});
		stackAsString.append('!');
		return stackAsString.toString();
	}

	public void takeCardsFromDeck(String stackAsString, Deck deck){
		char[] charCards = stackAsString.toCharArray();
		Card card;
		for (char charCard : charCards){
			int[] cardCode = Card.getCode(charCard);

			System.out.println(cardCode[0] + ", " + cardCode[1]);
			Iterator<Card> iter = deck.readCards().iterator();
			while(iter.hasNext()){
				card = iter.next();
				if ((card.getCardNumber().getNumber() == cardCode[0]) && (card.getCardType().getNumber() == cardCode[1])){
					System.out.println(cardCode[0] + ", " + cardCode[1] + " pasuje do karty: " + card.toString() + " więc dodaję do stosu " + this.name);
					if (this.name.equals("startStack")) card.setVisibleFrontFlag(false);
					else card.setVisibleFrontFlag(true);
					addCard(card);
					iter.remove();
					break;
				}
			}

		}
		//updateData();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Stack)) return false;
		Stack stack1 = (Stack) o;
		return Objects.equals(getStack(), stack1.getStack()) && getName().equals(stack1.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getStack(), getName());
	}

	@Override
	public String toString() {
		return name + ": " + "size: " + stack.size() + stack.toString() + "\n";
	}
}
