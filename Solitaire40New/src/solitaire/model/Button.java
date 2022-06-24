package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.Serializable;
import java.util.Objects;

public class Button implements Serializable {
    static final long serialVersionUID = 1L;
    private final String name;
    private double positionXOnBoard;
    private double positionYOnBoard;
    private int numberOfColumn;
    private final WritableImage imageButton;
    private final int WIDTHOFBUTTON = 80;
    private final int HEIGHTOFBUTTON = 70;

    public Button(String name,
                  PixelReader imageOfButton,
                  int positionXOnBoard,
                  int positionYOnBoard,
                  int numberOfColumn){
        this.name = name;
        this.positionXOnBoard = positionXOnBoard;
        this.positionYOnBoard = positionYOnBoard;
        this.numberOfColumn = numberOfColumn;

        imageButton = new WritableImage(imageOfButton, (numberOfColumn - 1) * WIDTHOFBUTTON, 0, WIDTHOFBUTTON, HEIGHTOFBUTTON);

    }

    public String getName() {
        return name;
    }

    public boolean isMyActiveArea(double x, double y) {
        if ((x > positionXOnBoard)
                && (x < positionXOnBoard + WIDTHOFBUTTON)
                && (y > positionYOnBoard)
                && (y  < positionYOnBoard + HEIGHTOFBUTTON))
            return true;

        return false;
    }

    public void drawButton(GraphicsContext gc) {
        gc.drawImage(imageButton, positionXOnBoard, positionYOnBoard);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Button)) return false;
        Button button = (Button) o;
        return getName().equals(button.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
