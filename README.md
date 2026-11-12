# 👻 Pacman

> Reproduction du jeu **Pacman** en Java et JavaFX : le joueur déplace Pacman dans un labyrinthe, mange les gommes, et peut manger les fantômes pour gagner la partie.

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](#-stack-technique)
[![JavaFX](https://img.shields.io/badge/JavaFX-FF0000?style=for-the-badge&logo=openjdk&logoColor=white)](#-stack-technique)
[![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)](#-stack-technique)
[![Statut](https://img.shields.io/badge/statut-terminé-2EA44F?style=for-the-badge)](#)


---

## 🎓 Contexte du projet

Projet universitaire réalisé **en solo** dans le cadre du **BUT Informatique** (1re année, semestre 2) à l'IUT de Lens, pour la ressource **R2.02 : Développement d'applications avec IHM**, au printemps 2026.

L'objectif : concevoir une application graphique complète en Java avec **JavaFX**, en séparant clairement le modèle, la vue et le contrôleur.

---

## ✨ Fonctionnalités

- 🎮 Déplacement de Pacman dans le labyrinthe au clavier
- 🟡 Gommes à manger
- 👻 Fantômes, que le joueur peut manger
- 🖼️ Interface graphique JavaFX (fichier FXML et sprites)

---

## 🛠️ Stack technique

| Domaine | Technologies |
|---------|--------------|
| Langage | Java |
| Interface graphique | JavaFX, FXML |
| Build | Gradle (wrapper inclus) |
| Architecture | MVC |

### Organisation du code

Le projet suit le patron **MVC** :

- **Modèle** (`model`) : la logique du jeu, avec la carte (`GameMap`, construite par `GameMapFactory`), les personnages (`AbstractCharacter`, `Ghost`) et les gommes (`Gum`)
- **Vue** : le fichier `pacman.fxml` et les images du jeu
- **Contrôleur** : `PacmanController`, qui relie l'interface au modèle

```
pacman/
├── src/main/java/…/ihm/
│   ├── model/        # logique du jeu
│   └── …             # contrôleur et lancement
├── src/main/resources/   # FXML, images
├── images_bis/           # sprites supplémentaires
├── build.gradle
└── gradlew
```

<!-- TODO : vérifier l'arborescence et adapter les noms de dossiers à ton projet réel -->

---

## 🚀 Lancer le jeu

**Prérequis** : un **JDK** installé (Java 17 ou plus récent).
<!-- TODO : indiquer la version de Java réellement utilisée par le projet -->

```bash
# 1. Cloner le dépôt
git clone https://github.com/am-eli-a/pacman_bis.git
cd pacman

# 2. Lancer le jeu (Gradle télécharge JavaFX automatiquement)
./gradlew run
```

Sous Windows, utilise `gradlew.bat run`. Tu peux aussi ouvrir le projet dans **IntelliJ IDEA** et lancer la tâche Gradle `application > run`.

---

## 🕹️ Commandes

| Touche | Action |
|--------|--------|
| Z, Q, S, D| Déplacer Pacman |

---

## 📚 Ce que j'ai appris

- Concevoir une application **JavaFX** avec FXML et un contrôleur
- Appliquer le **MVC** pour séparer l'affichage de la logique du jeu
- Structurer un projet Java avec **Gradle**
- Modéliser des personnages avec l'**héritage** et les classes abstraites

---

## 👩‍💻 Auteure

**Amelia Dufour-Lazrek**, étudiante en BUT Informatique
[GitHub](https://github.com/am-eli-a) · [LinkedIn](https://www.linkedin.com/in/amelia-dufour-lazrek-159101375/)
