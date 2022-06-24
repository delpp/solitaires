package model;

public enum CardType {
	pik(1),
	karo(2),
	trefl(3),
	kier(4);

	private final int number;

	private CardType(int number) {
		this.number = number;
	}

	public int getNumber(){
		return number;
	}
}
