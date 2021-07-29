package solitaire.model;

import java.io.Serializable;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class Card implements Cloneable, Serializable{
	public static final int CARDWIDTH = 66;
	public static final int CARDHEIGHT = 94;
	
	static final long serialVersionUID = 0;
	
	private CardNumber cardNumber;
	private CardType cardType;
	
	private boolean visibleFront;
	
	private final WritableImage frontImage;
	private final WritableImage backImage;
	
	public Card(PixelReader imagesOfCards, CardNumber cardNumber, CardType cardType, boolean visibleFront){
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.visibleFront = visibleFront;
		
		frontImage = new WritableImage(imagesOfCards, shiftHorizontal(), shiftVertical(), Card.CARDWIDTH, Card.CARDHEIGHT);
		backImage = new WritableImage(imagesOfCards, 4*Card.CARDWIDTH, 0, Card.CARDWIDTH, Card.CARDHEIGHT);
	}
	
	public String readCard(){
		return cardType.name() + cardNumber;
	}
	
	public CardNumber getCardNumber(){
		return cardNumber;
	}
	
	public CardType getCardType(){
		return cardType;
	}
	
	public WritableImage getImageCard() {
		if (visibleFront) return frontImage;
		else return backImage;
	}
	
	public void setVisibleFrontFlag(boolean visibleFront) {
		this.visibleFront = visibleFront;
	}
	
	public int shiftVertical() { 
		return cardNumber.getNumber()*Card.CARDHEIGHT - Card.CARDHEIGHT; 
	}
	
	public int shiftHorizontal() { 		
		if (cardType.equals(CardType.pik)) return 0;
		else if (cardType == CardType.karo) return Card.CARDWIDTH;
		else if (cardType == CardType.trefl) return 2*Card.CARDWIDTH;
		else if (cardType == CardType.kier) return 3*Card.CARDWIDTH;
		return 0;		
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
		result = prime * result + ((cardType == null) ? 0 : cardType.hashCode());
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
		Card other = (Card) obj;
		if (cardNumber != other.cardNumber)
			return false;
		if (cardType != other.cardType)
			return false;
		return true;
	}

	@Override
	public String toString(){
		return cardNumber.toString() + " " + cardType.toString();
	}
	
	@Override
	public Object clone() {
		try{
			return super.clone();
		}
		catch (Exception e){
			return null;
		}
	}
		
}
