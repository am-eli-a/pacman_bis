package fr.univartois.butinfo.ihm.model;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Un fantôme, qui se déplace au hasard d'une case toutes les 0,35 seconde.
 */
public class Ghost extends AbstractCharacter {

    private static final Random RANDOM = new Random();

    private Timeline timeline;

    public Ghost(PacmanGame game) {
        super(game);
    }

    private void moveRandomly() {
        switch (RANDOM.nextInt(4)) {
            case 0 -> game.moveDown(this);
            case 1 -> game.moveUp(this);
            case 2 -> game.moveLeft(this);
            default -> game.moveRight(this);
        }
    }

    /**
     * Démarre le déplacement automatique du fantôme (en arrêtant l'éventuel précédent).
     */
    public void animate() {
        stop();
        this.timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.35), e -> moveRandomly()));
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * Arrête le déplacement automatique du fantôme.
     * À appeler quand le fantôme n'est plus utilisé (fin de partie, redémarrage...),
     * sinon son animation continue de tourner en arrière-plan.
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

}
