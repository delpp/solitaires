package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;

public class GameBoard implements Serializable {
	private static final long serialVersionUID = 1L;

	private Deck deck;

	final int NUMBEROFGAMESTACKS;
	final int NUMBEROFFINALSTACKS;

	private List<Stack> stacks = new ArrayList<Stack>();
	private List<Button> buttons = new ArrayList<Button>();
	private Stack startStack;
	private Stack putAwayStack;
	private Stack[] gameStacks;
	private Stack[] finalStacks;
	private HandStack handStack;

	private List<Undo> undoSteps = new ArrayList<Undo>();
	private Undo currentUndoStep;

	private Stack stackWhenPressedMouse; // the stack on which the mouse was pressed
	private Stack stackWhenReleaseMouse; // the stack over which the mouse was released
	private Button buttonPressed;
	private AnimateCards animateCards;
	private boolean animateInProgress = false;
	//Optional<Stack> areaStack;
	//Optional<Button> areaButton;
	private Card testCard;

	int numberPossibleMove;

	private GraphicsContext gc;

	public GameBoard(GraphicsContext gc, PixelReader imagesOfCards, PixelReader imagesOfButtons, int numberOfDecks, int numberOfGameStacks, int numberOfFinalStacks) {
		this.gc = gc;
		this.NUMBEROFGAMESTACKS = numberOfGameStacks;
		this.NUMBEROFFINALSTACKS = numberOfFinalStacks;

		newDeck(imagesOfCards, numberOfDecks);

		buttons.add(new Button("UNDO", imagesOfButtons, 611, 105, 4));
		buttons.add(new Button("SAVE", imagesOfButtons, 611 + 76, 105, 2));
		buttons.add(new Button("LOAD", imagesOfButtons, 611 + 2*76, 105, 3));

		List<CardType> cardTypes = new ArrayList<CardType>();
		cardTypes.add(CardType.karo);
		cardTypes.add(CardType.kier);
		cardTypes.add(CardType.pik);
		cardTypes.add(CardType.trefl);

		List<CardNumber> cardNumbers = new ArrayList<CardNumber>();
		cardNumbers.add(CardNumber.as);
		cardNumbers.add(CardNumber.two);
		cardNumbers.add(CardNumber.three);
		cardNumbers.add(CardNumber.four);
		cardNumbers.add(CardNumber.five);
		cardNumbers.add(CardNumber.six);
		cardNumbers.add(CardNumber.seven);
		cardNumbers.add(CardNumber.eight);
		cardNumbers.add(CardNumber.nine);
		cardNumbers.add(CardNumber.ten);
		cardNumbers.add(CardNumber.jack);
		cardNumbers.add(CardNumber.queen);
		cardNumbers.add(CardNumber.king);


		startStack = new Stack("startStack", imagesOfCards, 99, 10, 0, 0, 1, 1, 0, 0, 1, cardTypes, cardNumbers, 5, 0);
		putAwayStack = new Stack("putAwayStack", imagesOfCards, 9, 10, 0, 30, 1, 7, 7, 0, 1, cardTypes, cardNumbers, 5, 0);
		handStack = new HandStack("handStack", imagesOfCards, 99, 400, 0, 30, 12, 12, 12, 0, 12, cardTypes, cardNumbers, 0, 0, 10, 0, 0);

		createGameStacks(NUMBEROFGAMESTACKS, cardTypes, cardNumbers, imagesOfCards);
		createFinalStacks(NUMBEROFFINALSTACKS, imagesOfCards);

		for (int i = 0; i < NUMBEROFGAMESTACKS; i++) stacks.add(gameStacks[i]);
		for (int i = 0; i < NUMBEROFFINALSTACKS; i++) stacks.add(finalStacks[i]);

		stacks.add(startStack);
		stacks.add(putAwayStack);
		stacks.add(handStack);

		putAwayStack.addAcceptedStack(startStack);

		handStack.setSourceStack(startStack);

		animateCards = new AnimateCards();
	}

	private void createGameStacks(int numberOfGameStacks, List<CardType> cardTypes, List<CardNumber> cardNumbers, PixelReader imageCard) {
		gameStacks = new Stack[numberOfGameStacks];
		for (int i = 0; i < numberOfGameStacks; i++)
			gameStacks[i] = new Stack("gameStack" + i, imageCard, 99 + i*75, 179, 0, 30, 2, 104, 104, 2, 3, cardTypes, cardNumbers, 5, 0);

		for (int i = 0; i < numberOfGameStacks; i++) {
			for (int j = 0; j < numberOfGameStacks; j++)
				gameStacks[i].addAcceptedStack(gameStacks[j]);
			gameStacks[i].addAcceptedStack(putAwayStack);
		}
	}

	private void createFinalStacks(int numberOfFinalStacks, PixelReader imageCard) {
		List<List<CardType>> listFinalStackAcceptTypeCard = new ArrayList<List<CardType>>();

		List<CardType> listFinalStackAcceptTypeCard1 = new ArrayList<CardType>();
		listFinalStackAcceptTypeCard1.add(CardType.trefl);
		List<CardType> listFinalStackAcceptTypeCard2 = new ArrayList<CardType>();
		listFinalStackAcceptTypeCard2.add(CardType.kier);
		List<CardType> listFinalStackAcceptTypeCard3 = new ArrayList<CardType>();
		listFinalStackAcceptTypeCard3.add(CardType.pik);
		List<CardType> listFinalStackAcceptTypeCard4 = new ArrayList<CardType>();
		listFinalStackAcceptTypeCard4.add(CardType.karo);

		listFinalStackAcceptTypeCard.add(listFinalStackAcceptTypeCard1);
		listFinalStackAcceptTypeCard.add(listFinalStackAcceptTypeCard2);
		listFinalStackAcceptTypeCard.add(listFinalStackAcceptTypeCard3);
		listFinalStackAcceptTypeCard.add(listFinalStackAcceptTypeCard4);

		List<CardNumber> setFinalStackAcceptCardNumber = new ArrayList<CardNumber>();
		setFinalStackAcceptCardNumber.add(CardNumber.as);

		finalStacks = new Stack[numberOfFinalStacks];
		for (int i = 0; i < numberOfFinalStacks; i++)
			finalStacks[i] = new Stack("finalStack" + i, imageCard, 249 + i*75, 10, 0, 0, 0, 13, 1, 1, 1, listFinalStackAcceptTypeCard.get(i%4), setFinalStackAcceptCardNumber, 4, i%4 + 1);

		for (int i = 0; i < numberOfFinalStacks; i++) {
			for (int j = 0; j < gameStacks.length; j++)
				finalStacks[i].addAcceptedStack(gameStacks[j]);
			finalStacks[i].addAcceptedStack(putAwayStack);
		}

	}

	public void newDeck(PixelReader imageCard, int decks) {
		deck = new Deck(imageCard, decks);
	}

	public void collectCardsToDeck(){
		for (Stack stack : stacks){
			if (stack.getStackSize() > 0){
				deck.collectCards(stack.getStack());
				stack.getStack().clear();
			}
		}

		System.out.println("***** STAN PO POWROCIE DO TALII *****");
		stacks.stream().forEach(x -> {
			System.out.println(x.toString());
		});
		System.out.println("DECK: " + deck.toString());
		System.out.println("rozmiar DECK: " + deck.size());
		System.out.println("*************************************");
	}

	public void shuffleDeck() {
		deck.shuffle();
	}

	public void newGame(PixelReader imageCard, int decks) {
		deck = new Deck(imageCard, decks);
		stacks.stream().forEach(x -> x.getStack().clear());
		shuffleDeck();
		distributeCards();
	}

	public void distributeCards() {
		Stack stack;
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < stacks.size(); i++) {
				stack = stacks.get(i);
				if (stack.getName().substring(0, 9).equals("gameStack")) {
					stacks.get(i).addCard(deck.handOutCard());
				}
			}

		int numerOfRestCardsToDistribute = deck.size();

		for (int i = 0; i < numerOfRestCardsToDistribute; i++)
			startStack.addCard(deck.handOutCard());

		stacks.stream().forEach(x -> x.setImageOnCards());

		stacks.stream().forEach(x -> {
			System.out.println(x.toString());
			System.out.println(x.getCode());
			System.out.println("***********************");
		});

		getCode();
	}

	public void drawGameBoard() {
		buttons.stream().forEach((x -> x.drawButton(gc)));
		stacks.stream().forEach(x -> x.drawStack(gc));
		animateCards.drawStack(gc);
	}

	public void updatePosition() {
		animateInProgress = animateCards.updatePosition();

		if ((!animateInProgress) && (animateCards.getStack().size() > 0)){
			animateCards.getSourceStack().addCards(animateCards.getStack());
			animateCards.getStack().clear();
			countPossibleMoves();
		}

	}

	private boolean moveCards(Stack sourceStack, Stack destinationStack) {
		boolean movedCards = false;
		List<Card> cards = new ArrayList<Card> (sourceStack.takeCards());

		if (destinationStack.getNumberOfAcceptedCardInOneMove() >= cards.size()) {
			movedCards = destinationStack.addCards(cards);
			sourceStack.updateData();
			sourceStack.setImageOnCards();
		}
		else sourceStack.addCards(cards);

		return movedCards;
	}

	private void setAnimateMoveBackCards(HandStack handStack, Stack destinationStack, int numberFrames) {
		animateCards.setSourceStack(destinationStack);
		animateCards.setStack(handStack.takeCards());
		animateCards.setPositionXOnBoard(handStack.getPositionXOnBoard() - handStack.getDeltaX());
		animateCards.setPositionYOnBoard(handStack.getPositionYOnBoard() - handStack.getDeltaY());

		animateCards.setShiftCardX(handStack.getShiftCardX());
		animateCards.setShiftCardY(handStack.getShiftCardY());

		animateCards.setNumberShownCards(handStack.getNumberShownCards());

		animateCards.setTargetXPosition(handStack.getTargetPositionXOnBoard());
		animateCards.setTargetYPosition(handStack.getTargetPositionYOnBoard());

		animateCards.setNumberAnimatedFrames(numberFrames);
		animateCards.setSteps();

		handStack.updateData();
	}

	public int countPossibleMoves() {
		numberPossibleMove = 0;

		if (!animateInProgress) {
			System.out.println("SPRAWDZAM MOŻLIWE RUCHY");

			System.out.println("––––––––––––––––––");
			System.out.println("Sprawdzam startStack");
			if (startStack.getStackSize() > 0) {
				numberPossibleMove = 1;
				System.out.println("Można wziąć kartę ze stosu startowego");
			}

			System.out.println("––––––––––––––––––");
			System.out.println("Sprawdzam putAwayStack. Rozmiar: " + putAwayStack.getStackSize());

			if (putAwayStack.getStackSize() > 0) {

				testCard = putAwayStack.getCard(putAwayStack.getStackSize()-1);

				System.out.println("Testuję kartę: " + testCard + " z putAwayStack");
				numberPossibleMove += calculatePossibleMatchesCardToStack(testCard, -1);
			}


			for (int i = 0; i < gameStacks.length; i++) {
				if (gameStacks[i].getStackSize() > 0) {

					testCard = gameStacks[i].getCard(gameStacks[i].getStackSize()-1);

					System.out.println("Testuję kartę: " + testCard + " z gameStacks" + gameStacks[i].getName());
					numberPossibleMove += calculatePossibleMatchesCardToStack(testCard, i);
				}
			}

			System.out.println("LICZBA MOŻLIWYCH RUCHÓW: " + numberPossibleMove);
		}

		return numberPossibleMove;
	}

	public int calculatePossibleMatchesCardToStack(Card testCard, int numberOfSourceGameStack) {
		int counter = 0;

		counter = calculatePossibleMatchesCardToGameStack(testCard, numberOfSourceGameStack);
		counter += calculatePossibleMatchesCardToFinalStack(testCard);

		return counter;
	}

	public int calculatePossibleMatchesCardToGameStack(Card testCard, int numberOfSourceGameStack) {
		System.out.println("");
		System.out.println("numberOfSourceGameStack: " + numberOfSourceGameStack);
		int counter = 0;
		for (int i = 0; i < gameStacks.length; i++) {
			if (i == numberOfSourceGameStack) continue;
			if (isCardFitsToStack(testCard, gameStacks[i])) {
				System.out.println("karta: " + testCard + " pasuje do stosu " + gameStacks[i].getName());
//				if (numberOfSourceGameStack > -1)
//					if (!isPossibleToBackToSource(numberOfSourceGameStack))
//						counter++;
//					else;
//
//				else
					counter++;
			}
		}
		System.out.println("counter: " + counter);
		System.out.println("");
		return counter;
	}

	private boolean isPossibleToBackToSource(int numberOfSourceGameStack) {
		boolean isPossibleToBack = false;

		if (gameStacks[numberOfSourceGameStack].getStackSize() >= 2) {

			handStack.addCard(gameStacks[numberOfSourceGameStack].takeCardFromTop());
			Card cardToTest = handStack.takeCardFromTop();

			System.out.println("**********************");
			System.out.println("Testowana karta w ręku: " + cardToTest);
			System.out.println("Stos, z którgo była zabrana karta: " + gameStacks[numberOfSourceGameStack].toString());
			System.out.println("**********************");

			if (isCardFitsToStack(cardToTest, gameStacks[numberOfSourceGameStack])) {
				gameStacks[numberOfSourceGameStack].addCard(cardToTest);
				isPossibleToBack =  true;
			}
			else {
				gameStacks[numberOfSourceGameStack].addCard(cardToTest);
				isPossibleToBack = false;
			}
		}
		// else isPossibleToBack = true;

		System.out.println("Czy karta może wrócić na miejsce? :" + isPossibleToBack);
		System.out.println("Stos, z którgo była zabrana karta i z powrotem odlożona: " + gameStacks[numberOfSourceGameStack].toString());
		System.out.println("**********************");
		System.out.println("* KONIEC TESTU POWROTU *");
		System.out.println("**********************");
		return isPossibleToBack;
	}

	public int calculatePossibleMatchesCardToFinalStack(Card testCard) {
		int counter = 0;
		for (int i = 0; i < finalStacks.length; i++) {
			if (isCardFitsToStack(testCard, finalStacks[i])) counter++;
		}
		return counter;
	}

	public boolean isCardFitsToStack(Card testCard, Stack stack) {
		if (stack.isAcceptCard(testCard)) return true;
		else return false;
	}

	public void actionOnPressedMouse(double x, double y) throws IOException {

		System.out.println("naciśnięto klawisz myszki. Poz X: " + x + ". Poz Y: " + y);

		for (Stack stack : stacks){
			if (stack.isMyActiveArea(x, y)){
				stackWhenPressedMouse = stack;
				break;
			}
		}

		if (stackWhenPressedMouse != null) {
			//stackWhenPressedMouse = areaStack.get();

			if (stackWhenPressedMouse.getName().equals("startStack")) {

				currentUndoStep = new Undo(stackWhenPressedMouse, putAwayStack, 1);
				undoSteps.add(currentUndoStep);

				moveCards(stackWhenPressedMouse, putAwayStack);
				countPossibleMoves();
			}
			else {
				double deltaX = x
						- (stackWhenPressedMouse.getPositionXOnBoard()
						+ stackWhenPressedMouse.getShiftCardX() * (stackWhenPressedMouse.getNumberShownCards() - (stackWhenPressedMouse.getStackSize() - stackWhenPressedMouse.getPressedCardNumberInStack())));
				double deltaY = y
						- (stackWhenPressedMouse.getPositionYOnBoard()
						+ stackWhenPressedMouse.getShiftCardY() * (stackWhenPressedMouse.getNumberShownCards() - (stackWhenPressedMouse.getStackSize() - stackWhenPressedMouse.getPressedCardNumberInStack())));

				if (moveCards(stackWhenPressedMouse, handStack)) {
					handStack.setSourceStack(stackWhenPressedMouse);

					handStack.setDeltaX(deltaX);
					handStack.setDeltaY(deltaY);

					handStack.setPositionXOnBoard((int)x);
					handStack.setPositionYOnBoard((int)y);
					handStack.setNumberPossibleVisibleCardsInFront(stackWhenPressedMouse.getNumberPossibleVisibleCardsInFront());
					handStack.setImageOnCards();

					handStack.setTargetPositionXOnBoard(x - deltaX);
					handStack.setTargetPositionYOnBoard(y - deltaY);
				}
			}
			stackWhenPressedMouse = null;
		}

		for (Button button : buttons){
			if (button.isMyActiveArea(x, y)){
				buttonPressed = button;
				break;
			}
		}

		if (buttonPressed != null){
			System.out.println("Naciśnięto button: " + buttonPressed.getName());
			if (buttonPressed.getName().equals("SAVE")){
				IOGame.savaGame(this);
			}
			if (buttonPressed.getName().equals("LOAD")){
				String gameBoardAsString = IOGame.loadGame();

				collectCardsToDeck();

				System.out.println(gameBoardAsString);

				String[] undos = gameBoardAsString.split(" ");

				String[] stackAsString = undos[0].split("!");

				System.out.println(stackAsString.length);

				System.out.println(Arrays.stream(stackAsString));

				for (int i = 0; i < stackAsString.length; i++){
					stacks.get(i).takeCardsFromDeck(stackAsString[i], deck);
				}

				if (undos.length > 1) {
					String undoStep[];
					for (int i = 1; i < undos.length; i++) {
						undoStep = undos[i].split("-");
						undoSteps.add(new Undo(undoStep[0], undoStep[1], Integer.parseInt(undoStep[2])));
					}
				}

			}

			if (buttonPressed.getName().equals("UNDO") && undoSteps.size() > 0){
				System.out.println("akcja - UNDO");

				currentUndoStep = undoSteps.get(undoSteps.size()-1);
				undoSteps.remove(undoSteps.size()-1);

				System.out.println("UNDO: Przeniesienie "
						+ currentUndoStep.getNumberMovedCards()
						+ " kart ze stosu "
						+ currentUndoStep.getDestinationStackName()
						+ " na stos "
						+ currentUndoStep.getSourceStackName());


				for (Stack stack : stacks){
					if (stack.getName().equals(currentUndoStep.getDestinationStackName())){
						stackWhenPressedMouse = stack;
						break;
					}
				}

				for (Stack stack : stacks){
					if (stack.getName().equals(currentUndoStep.getSourceStackName())){
						stackWhenReleaseMouse = stack;
						break;
					}
				}

				stackWhenPressedMouse.setPressedCardNumberInStack(stackWhenPressedMouse.getStackSize() - currentUndoStep.getNumberMovedCards());
				moveCards(stackWhenPressedMouse, stackWhenReleaseMouse);

			}
			buttonPressed = null;
		}
	}

	public void actionOnDraggedMouse(double x, double y) {
		if (handStack.getStackSize() > 0) {
			handStack.setPositionXOnBoard((int)x);
			handStack.setPositionYOnBoard((int)y);
		}
	}

	public void actionOnReleasedMouse(double x, double y) {
		if (handStack.getStackSize() > 0) {

			for (Stack stack : stacks){
				if (stack.isMyArea(x, y)){
					stackWhenReleaseMouse = stack;
					break;
				}
			}

			boolean cardsMoved = false;

			if (stackWhenReleaseMouse != null) {
				System.out.println("odłożono myszkę na stosie: " + stackWhenReleaseMouse.getName());
				System.out.println("Stan w handStack przed próbą operacji przeniesienia: " + handStack);

				if (isAcceptCard(stackWhenReleaseMouse, handStack)) {
					currentUndoStep = new Undo(handStack.getSourceStack(), stackWhenReleaseMouse, handStack.getStackSize());
					undoSteps.add(currentUndoStep);

					cardsMoved = moveCards(handStack, stackWhenReleaseMouse);

					countPossibleMoves();
					handStack.getSourceStack().setImageOnCards();
				}
			}
			if (!cardsMoved) setAnimateMoveBackCards(handStack, handStack.getSourceStack(), 10);

		}
		stackWhenReleaseMouse = null;
	}

	private boolean isAcceptCard(Stack destinationStack, HandStack handStack) {
		if ((destinationStack.isAcceptCardsFromStack(handStack.getSourceStack()))
				&& (destinationStack.isAcceptCard(handStack.getCard(0)))) return true;
		else return false;
	}

	public String getCode(){
		StringBuilder gameBoardAsString = new StringBuilder();
		stacks.stream().forEach(x -> {
			gameBoardAsString.append(x.getCode());
		});

		undoSteps.stream().forEach(x -> {
			gameBoardAsString.append(x.getCode());
		});

		System.out.println(gameBoardAsString);
		return gameBoardAsString.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + Arrays.hashCode(stacks.toArray());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameBoard other = (GameBoard) obj;
		if (stacks.equals(other.stacks))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Arrays.toString(stacks.toArray());
	}
}
