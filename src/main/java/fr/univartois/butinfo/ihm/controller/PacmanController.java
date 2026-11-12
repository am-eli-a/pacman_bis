/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d'aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d'adéquation
 * à un usage particulier et d'absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d'auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d'un contrat, d'un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d'autres éléments du logiciel.
 *
 * (c) 2022-2026 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.ihm.controller;

import fr.univartois.butinfo.ihm.model.*;
import fr.univartois.butinfo.ihm.view.IPacmanController;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Le contrôleur de la vue du jeu Pac-Man : il affiche la carte, le score et les vies,
 * et transmet les actions de l'utilisateur au modèle.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class PacmanController implements IPacmanController {

    private static final int TILE_SIZE = 20;

    private ImageView playerView;

    private Image wallImage;
    private Image pacmanImage;
    private Image pacmanPowerUpImage;
    private Image pathImage;
    private Image gumImage;
    private Image megagumImage;
    private Image[] ghostImages;
    private Scene scene;

    private PacmanGame game;

    /**
     * Le label du score. Initialisé automatiquement par JavaFX grâce à l'annotation {@link FXML}.
     */
    @FXML private Label scoreLabel;

    /**
     * Le label des vies restantes.
     */
    @FXML private Label livesLabel;

    /**
     * La grille dans laquelle est affichée la carte.
     */
    @FXML private GridPane mapGrid;

    private void initImage() {
        this.wallImage = loadResource("/images/wall.png");
        this.pathImage = loadResource("/images/path.png");
        this.gumImage = loadResource("/images/pacgum.png");
        this.megagumImage = loadResource("/images/megagum.png");
        this.pacmanImage = loadResource("/images/pacman.png");
        this.pacmanPowerUpImage = loadResource("/images/pacman-powerup.png");

        ghostImages = new Image[] {
                loadResource("/images/red.png"),
                loadResource("/images/pink.png"),
                loadResource("/images/blue.png"),
                loadResource("/images/orange.png")
        };
    }

    private Image loadResource(String path) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }

    @Override
    public Image getGhostImage(int index) {
        return ghostImages[index];
    }

    @Override
    public Image getPacmanImage() {
        return pacmanImage;
    }

    @Override
    public void setPacmanPowerMode(boolean powered) {
        if (playerView != null) {
            playerView.setImage(powered ? pacmanPowerUpImage : pacmanImage);
        }
    }

    public void setGame(PacmanGame game) {
        this.game = game;
    }

    @Override
    public void updateScore(int score) {
        scoreLabel.setText("Score : " + score);
    }

    @Override
    public void updateLives(int lives) {
        livesLabel.setText("❤ ".repeat(Math.max(lives, 0)));
        if (lives <= 0 && scene != null) {
            // Plus de vie : les touches ne déplacent plus Pac-Man.
            scene.setOnKeyPressed(event -> { });
        }
    }

    @Override
    public void showGameOver() {
        scoreLabel.setText("GAME OVER - Score final : " + game.getScore());
        livesLabel.setText("");
    }

    private Image loadImage(TileContent content) {
        if (content == null) {
            return null;
        }
        return switch (content) {
            case WALL -> wallImage;
            case PATH -> pathImage;
        };
    }

    private Image loadGumImage(Gum gum) {
        if (gum == null) {
            return null;
        }
        return switch (gum) {
            case PACGUM -> gumImage;
            case MEGAGUM -> megagumImage;
        };
    }

    private ImageView makeImageView(Image img) {
        ImageView iv = new ImageView(img);
        iv.setFitWidth(TILE_SIZE);
        iv.setFitHeight(TILE_SIZE);
        return iv;
    }

    /**
     * Construit l'affichage de la carte : chaque tuile est liée à son contenu
     * (mur ou chemin) et à sa gomme, pour que l'affichage suive les changements du modèle.
     *
     * @param map La carte à afficher.
     */
    @Override
    public void init(GameMap map) {
        initImage();
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                StackPane sp = new StackPane();
                Tile tuile = map.get(i, j);

                ImageView iv = new ImageView();
                iv.setFitWidth(TILE_SIZE);
                iv.setFitHeight(TILE_SIZE);
                iv.imageProperty().bind(
                        Bindings.createObjectBinding(
                                () -> loadImage(tuile.getContent()),
                                tuile.getContentProperty()
                        )
                );
                sp.getChildren().add(iv);

                if (tuile.getContent() == TileContent.PATH) {
                    ImageView gumView = new ImageView();
                    gumView.setFitWidth(TILE_SIZE);
                    gumView.setFitHeight(TILE_SIZE);
                    gumView.imageProperty().bind(
                            Bindings.createObjectBinding(
                                    () -> loadGumImage(tuile.getGum()),
                                    tuile.getGumProperty()
                            )
                    );
                    sp.getChildren().add(gumView);
                }

                mapGrid.add(sp, j, i);
            }
        }
    }

    /**
     * Cette méthode est appelée lorsque l'utilisateur clique sur le bouton « Restart ».
     * Le lien avec le bouton est réalisé par le FXML (attribut {@code onAction}).
     */
    @Override
    @FXML
    public void restart() {
        mapGrid.getChildren().clear();
        playerView = null;
        game.initMap();
        setupKeyboard();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        setupKeyboard();
    }

    /**
     * Relie les touches aux déplacements : Z/Q/S/D ou les flèches du clavier.
     */
    private void setupKeyboard() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z, UP -> game.movePlayerUp();
                case S, DOWN -> game.movePlayerDown();
                case Q, LEFT -> game.movePlayerLeft();
                case D, RIGHT -> game.movePlayerRight();
                default -> { }
            }
        });
    }

    @Override
    public void bindCharacter(AbstractCharacter character, Image image) {
        ImageView view = makeImageView(image);

        if (character instanceof Player) {
            playerView = view;
        }

        GridPane.setConstraints(view, character.getColumn(), character.getRow());
        mapGrid.getChildren().add(view);

        character.getRowProperty().addListener((obs, oldVal, newVal) ->
                GridPane.setConstraints(view, character.getColumn(), newVal.intValue())
        );
        character.getColumnProperty().addListener((obs, oldVal, newVal) ->
                GridPane.setConstraints(view, newVal.intValue(), character.getRow())
        );
    }

}
