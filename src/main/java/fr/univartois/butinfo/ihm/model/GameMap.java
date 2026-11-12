package fr.univartois.butinfo.ihm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * La carte du jeu : une grille de tuiles.
 */
public class GameMap {

    private final int width;
    private final int height;
    private final Tile[][] tiles;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOnMap(int row, int column) {
        return ((0 <= row) && (row < height))
                && ((0 <= column) && (column < width));
    }

    /**
     * Donne une tuile à une position donnée.
     *
     * @param row La ligne de la tuile à récupérer.
     * @param column La colonne de la tuile à récupérer.
     *
     * @return La tuile à la position donnée.
     */
    public Tile get(int row, int column) {
        return tiles[row][column];
    }

    /**
     * Donne la liste des tuiles qui sont vides sur cette carte.
     *
     * @return La liste des tuiles vides.
     *
     * @see Tile#isEmpty()
     */
    public List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (tiles[i][j].isEmpty()) {
                    emptyTiles.add(tiles[i][j]);
                }
            }
        }

        return emptyTiles;
    }

}
