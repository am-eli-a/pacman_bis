package fr.univartois.butinfo.ihm.model;

import fr.univartois.butinfo.ihm.view.IPacmanController;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import static fr.univartois.butinfo.ihm.model.GameMapFactory.createMapWithRandomWalls;

/**
 * La façade du modèle : elle contient l'état du jeu (carte, joueur, fantômes, score, vies)
 * et fournit une méthode pour chaque action possible.
 */
public class PacmanGame {

    private static final Random RANDOM = new Random();
    private static final int NB_GHOST = 4;
    private static final int MAP_SIZE = 30;
    private static final double MEGAGUM_DURATION_SECONDS = 30;

    private GameMap map;
    private Player player;
    private int score = 0;
    private int lives = 3;
    private boolean peutMangerFantome = false;
    private Timeline megagumTimer;

    private final List<Ghost> ghosts = new ArrayList<>();

    private IPacmanController controller;

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void setControlleur(IPacmanController controlleur) {
        this.controller = controlleur;
    }

    /**
     * Démarre (ou redémarre) une partie : nouvelle carte, nouveaux personnages,
     * score à 0 et 3 vies. La vue est prévenue du nouvel état.
     */
    public void initMap() {
        // Une éventuelle partie précédente ne doit plus animer quoi que ce soit.
        stopGhosts();
        ghosts.clear();
        stopMegagumTimer();

        score = 0;
        lives = 3;
        peutMangerFantome = false;
        this.map = createMapWithRandomWalls(MAP_SIZE, MAP_SIZE);

        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Tile tuile = map.get(i, j);
                if (tuile.getContent() == TileContent.PATH) {
                    if (RANDOM.nextDouble() < 0.05) {
                        tuile.setGum(Gum.MEGAGUM);
                    } else {
                        tuile.setGum(Gum.PACGUM);
                    }
                }
            }
        }

        controller.init(this.map);
        player = new Player(this);
        placeCharacter(player);
        controller.bindCharacter(player, controller.getPacmanImage());

        for (int i = 0; i < NB_GHOST; i++) {
            Ghost ghost = new Ghost(this);
            ghosts.add(ghost);
            placeCharacter(ghost);
            controller.bindCharacter(ghost, controller.getGhostImage(i));
            ghost.animate();
        }

        // Le score et les vies repartent de zéro : la vue doit l'afficher.
        controller.updateScore(score);
        controller.updateLives(lives);
    }

    public void placeCharacter(AbstractCharacter character) {
        List<Tile> emptyTiles = map.getEmptyTiles();
        Tile tile = emptyTiles.get(RANDOM.nextInt(emptyTiles.size()));
        character.setPosition(tile.getRow(), tile.getColumn());
    }

    private void stopGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.stop();
        }
    }

    private void stopMegagumTimer() {
        if (megagumTimer != null) {
            megagumTimer.stop();
            megagumTimer = null;
        }
    }

    private void playerLosesLife() {
        if (lives > 0) lives--;
        controller.updateLives(lives);
        if (lives <= 0) {
            stopGhosts();
            controller.showGameOver();
        } else {
            placeCharacter(player);
        }
    }

    /**
     * Active le mode puissance pendant 30 secondes.
     * Si une méga-gum est mangée alors que le mode est déjà actif, la durée repart de zéro
     * (l'ancienne minuterie ne doit pas interrompre le nouveau mode trop tôt).
     */
    private void startMegagumTimer() {
        stopMegagumTimer();
        peutMangerFantome = true;
        controller.setPacmanPowerMode(true);
        megagumTimer = new Timeline(new KeyFrame(Duration.seconds(MEGAGUM_DURATION_SECONDS), e -> {
            peutMangerFantome = false;
            controller.setPacmanPowerMode(false);
        }));
        megagumTimer.play();
    }

    /**
     * Déplace un personnage d'une case dans la direction donnée.
     * Si la case cible est un mur ou sort de la carte, le déplacement est ignoré.
     *
     * @param character Le personnage à déplacer.
     * @param dRow Le déplacement en ligne.
     * @param dCol Le déplacement en colonne.
     */
    public void move(AbstractCharacter character, int dRow, int dCol) {
        int rowCharacter = character.getRow();
        int columnCharacter = character.getColumn();

        if (lives <= 0) return;

        if (map.isOnMap(rowCharacter + dRow, columnCharacter + dCol)) {
            Tile newPosition = map.get(rowCharacter + dRow, columnCharacter + dCol);
            if (newPosition.isEmpty()) {
                character.setPosition(rowCharacter + dRow, columnCharacter + dCol);

                if (character == player && newPosition.getGum() != null) {
                    if (newPosition.getGum() == Gum.MEGAGUM) {
                        score += 10;
                        startMegagumTimer();
                    } else {
                        score++;
                    }
                    newPosition.setGum(null);
                    controller.updateScore(score);
                }

                for (Ghost ghost : ghosts) {
                    if (ghost.getRow() == player.getRow()
                            && ghost.getColumn() == player.getColumn()) {
                        if (peutMangerFantome) {
                            score += 50;
                            controller.updateScore(score);
                            placeCharacter(ghost);
                        } else {
                            playerLosesLife();
                            return;
                        }
                    }
                }
            }
        }
    }

    public void moveUp(AbstractCharacter character)    { move(character, -1,  0); }
    public void moveDown(AbstractCharacter character)  { move(character,  1,  0); }
    public void moveLeft(AbstractCharacter character)  { move(character,  0, -1); }
    public void moveRight(AbstractCharacter character) { move(character,  0,  1); }

    public void movePlayerUp()    { moveUp(player); }
    public void movePlayerDown()  { moveDown(player); }
    public void movePlayerLeft()  { moveLeft(player); }
    public void movePlayerRight() { moveRight(player); }

}
