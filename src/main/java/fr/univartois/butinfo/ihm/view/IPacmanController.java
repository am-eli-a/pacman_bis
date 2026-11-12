package fr.univartois.butinfo.ihm.view;

import fr.univartois.butinfo.ihm.model.*;
import javafx.scene.image.Image;

public interface IPacmanController {
    void init(GameMap map);
    void bindCharacter(AbstractCharacter character, Image image);
    void updateScore(int score);
    void updateLives(int lives);
    void showGameOver();
    Image getPacmanImage();
    Image getGhostImage(int index);
    void setPacmanPowerMode(boolean powered);
    void restart();
}