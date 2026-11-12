package fr.univartois.butinfo.ihm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Un personnage du jeu (Pac-Man ou un fantôme), repéré par sa ligne et sa colonne.
 * La position est stockée dans des propriétés, pour que la vue puisse suivre ses déplacements.
 */
public abstract class AbstractCharacter {

    protected PacmanGame game;

    private final IntegerProperty row = new SimpleIntegerProperty();
    private final IntegerProperty column = new SimpleIntegerProperty();

    public AbstractCharacter(PacmanGame game) {
        this.game = game;
    }

    public int getRow() {
        return row.get();
    }

    public int getColumn() {
        return column.get();
    }

    public IntegerProperty getRowProperty() {
        return row;
    }

    public IntegerProperty getColumnProperty() {
        return column;
    }

    public void setPosition(Integer row, Integer column) {
        this.row.set(row);
        this.column.set(column);
    }

}
