package model;

public class Undo {
    private String sourceStackName;
    private String destinationStackName;
    private int numberMovedCards;

    public Undo(Stack sourceStack, Stack destinationStack, int numberMovedCards) {
        sourceStackName = sourceStack.getName();
        destinationStackName = destinationStack.getName();
        this.numberMovedCards = numberMovedCards;

        System.out.println("Dodano do undo source stack: " + sourceStackName + ", destination stack: " + destinationStackName + ". Liczba przenesionych kart: " + numberMovedCards);
    }

    public Undo(String sourceStack, String destinationStack, int numberMovedCards) {
        sourceStackName = sourceStack;
        destinationStackName = destinationStack;
        this.numberMovedCards = numberMovedCards;

        System.out.println("Dodano do undo source stack: " + sourceStackName + ", destination stack: " + destinationStackName + ". Liczba przenesionych kart: " + numberMovedCards);
    }

    public String getSourceStackName() {
        return sourceStackName;
    }

    public String getDestinationStackName() {
        return destinationStackName;
    }

    public int getNumberMovedCards() {
        return numberMovedCards;
    }

    public String getCode(){
        return " " + sourceStackName + "-" + destinationStackName + "-" + numberMovedCards;
    }

}
