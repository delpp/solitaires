package solitaire.model;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class Card {
	public static final int CARDWIDTH = 66;
	public static final int CARDHEIGHT = 94;

	private CardNumber cardNumber;
	private CardType cardType;

	private boolean visibleFront;

	private final WritableImage frontImage;
	private final WritableImage backImage;

	public static int[] getCode(char number){
		int[] card = new int[2];

		card[0] = (number - 33) % 13; // cardNumber
		if (card[0] == 0) card[0] = 13;

		card[1] = (number - 33 - card[0]) / 13 + 1; // cardType
		return card;
	}

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

	public char getCode(){
		return (char) (getCardNumber().getNumber() + 13 * (getCardType().getNumber() - 1) + 33);
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

/*
"---pik as
#---pik two
$---pik three
%---pik four
&---pik five
'---pik six
(---pik seven
)---pik eight
*---pik nine
+---pik ten
,---pik jack
----pik queen
.---pik king
/---karo as
0---karo two
1---karo three
2---karo four
3---karo five
4---karo six
5---karo seven
6---karo eight
7---karo nine
8---karo ten
9---karo jack
:---karo queen
;---karo king
<---trefl as
=---trefl two
>---trefl three
?---trefl four
@---trefl five
A---trefl six
B---trefl seven
C---trefl eight
D---trefl nine
E---trefl ten
F---trefl jack
G---trefl queen
H---trefl king
I---kier as
J---kier two
K---kier three
L---kier four
M---kier five
N---kier six
O---kier seven
P---kier eight
Q---kier nine
R---kier ten
S---kier jack
T---kier queen
U---kier king
 */