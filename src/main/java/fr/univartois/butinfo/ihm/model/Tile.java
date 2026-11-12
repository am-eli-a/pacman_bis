package fr.univartois.butinfo.ihm.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Une tuile de la carte, avec son contenu (chemin ou mur) et son éventuelle gomme.
 * Le contenu et la gomme sont des propriétés, pour que la vue puisse s'y lier.
 */
public class Tile {

    private final int row;
    private final int column;
    private final ObjectProperty<TileContent> tileContent = new SimpleObjectProperty<>();
    private final ObjectProperty<Gum> gum = new SimpleObjectProperty<>();

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /**
     * Indique si cette tuile est un chemin libre (donc ni un mur, ni une tuile non initialisée).
     */
    public boolean isEmpty() {
        return tileContent.get() == TileContent.PATH;
    }

    public Gum getGum() {
        return gum.get();
    }

    public ObjectProperty<Gum> getGumProperty() {
        return gum;
    }

    public void setGum(Gum gum) {
        this.gum.set(gum);
    }

    public TileContent getContent() {
        return tileContent.getValue();
    }

    public ObjectProperty<TileContent> getContentProperty() {
        return tileContent;
    }

    public void setContent(TileContent content) {
        this.tileContent.set(content);
    }

}
