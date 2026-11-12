package fr.univartois.butinfo.ihm.model;

import java.util.Random;

/**
 * Une fabrique de cartes pour le jeu.
 */
public final class GameMapFactory {

    private static final Random RANDOM = new Random();

    private GameMapFactory() {
        // Classe utilitaire : pas d'instance.
    }

    /**
     * Crée une carte entourée de murs, dont l'intérieur est entièrement libre.
     */
    public static GameMap createEmptyMap(int height, int width) {
        GameMap map = new GameMap(width, height);
        for (int i = 0; i < width; i++) {
            map.get(0, i).setContent(TileContent.WALL);
            map.get(height - 1, i).setContent(TileContent.WALL);
        }

        for (int i = 0; i < height; i++) {
            map.get(i, 0).setContent(TileContent.WALL);
            map.get(i, width - 1).setContent(TileContent.WALL);
        }

        for (int i = 1; i < (height - 1); i++) {
            for (int j = 1; j < (width - 1); j++) {
                map.get(i, j).setContent(TileContent.PATH);
            }
        }

        return map;
    }

    /**
     * Crée une carte entourée de murs, avec environ 20 % de murs placés au hasard à l'intérieur.
     */
    public static GameMap createMapWithRandomWalls(int height, int width) {
        GameMap map = createEmptyMap(height, width);
        for (int i = 1; i < (height - 1); i++) {
            for (int j = 1; j < (width - 1); j++) {
                if (RANDOM.nextDouble() < 0.20) {
                    map.get(i, j).setContent(TileContent.WALL);
                }
            }
        }
        return map;
    }

}
