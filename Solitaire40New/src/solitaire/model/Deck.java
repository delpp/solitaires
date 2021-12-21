package solitaire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.PixelReader;

public class Deck {

	private List<Card> cards;

	public Deck(PixelReader imageCard, int numberOfDeck) {
		cards = new ArrayList<>();
		for (int deck = 0; deck < numberOfDeck; deck++)
			for (int type = 0; type < 4; type++) {
				for (int number = 0; number < 13; number++)
					cards.add(new Card(imageCard, CardNumber.values()[number], CardType.values()[type], true));
			}

//		cards.stream().forEach(x -> {
//			System.out.println(x.getCode() + "---" + x.getCardType() + " " + x.getCardNumber());
//		});
	}

	public void collectCards(List<Card> cardsToAdd){
		cards.addAll(cardsToAdd);
	}

	public List<Card> readCards (){
		return cards;
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card handOutCard() {
		return cards.remove(cards.size()-1);
	}

	public int size() {
		return cards.size();
	}

	@Override
	public String toString() {
		return "deck: " + cards.toString();
	}

}
