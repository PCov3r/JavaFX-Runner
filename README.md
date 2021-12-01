# JavaFX-Runner

## 👨‍💻 Bienvenue dans le monde merveilleux de Java

Notre valeureux Link se retrouve assailli par sa version maléfique: Dark Link.

Survivez le plus longtemps possible, en parcourant les terres de la Vallée Gerudo (en tout cas ce qui s'en rapproche).


⚠️ Afin que la MV puisse jouer les musiques, il faut mettre à jour sa configuration avec : --add-modules=javafx.controls,javafx.media ⚠️

<br>

## [🧾 Description des différentes classes](/src/com/runner/)
### Main

Tout simplement le corps du jeu. Classe où on retrouve tout les différents écrans du jeu et qui permet de lancer le jeu.

### MenuScene

Extend de Scene, MenuScene correspond à la page d'accueil du jeu. On y retrouve donc le titre du jeu, les boutons du menu, ainsi qu'un (superbe) visuel de notre héros courant, armure au vent.

### OptionScene

Extend de Scene, OptionScene permet de configurer les contrôles du jeu (saut et tir).

### LoseScene

Extend de Scene, LoseScene représente la page de gameover du jeu. Elle indique ainsi son score final au joueur et par le biais de boutons, lui permet de tenter d'améliorer son score ou bien de quitter le jeu. Je vous souhaite de ne pas avoir affaire à cet écran !

### GameScene

Extend de Scene, GameScene contient tous les éléments composant le corps du jeu : camera, heros, ennemis, projectiles, arrière-plan et j'en passe.

### StaticThing

Classe permettant de gérer les éléments non mobiles de notre jeu, tels que les arrières-plans.

### AnimatedThing

Classe permettant de gérer les éléments mobiles de notre jeu, tels que le héros, ennemis et projectiles.

* Hero : Ajoute une caméra et un effet qui modifie la couleur du héros lorsque celui-ci prend des dégâts.
* Foe : Ajoute peu de choses en elle-même, puisque la gestion des ennemis se fait principalement dans la classe GameScene. Permet cependant de mettre en place l'état d'un ennemi (ie         mort ou vivant).
* Fireball : Là encore, la gestion des projectiles est inhérente à la classe GameScene pour plus de simplicité. La classe est donc réduite à son strict minimum avec simplement un jeu              de coordonnées.

### Item

Classe utilisée pour la gestion des bonus octroyés à notre héros. Consiste en une ImageView et un jeu de coordonnées. D'autres méthodes permettent aussi de gérer nos bonus dans la GameScene : par exemple, éviter que le joueur ne puisse ramasser plusieurs fois d'affilées le même bonus.

### Camera

Classe permettant de gérer l'articulation entre héros, background et surtout rendu à l'écran. On associe une caméra au héros, qui par un jeu d'équation va suivre ce dernier dans ses déplacements, avec un effet de ressort. De là nous allons pouvoir faire le rendu dans la GameScene de notre background et de notre héros. <br> <br>
Il est à noter que seul le jeu d'équations en x est ici implémenté. Après avoir écrit une implémentation en y, il est apparu une détérioration du gameplay. En effet, seul le haut des ennemis apparaissait visible et ces derniers étaient donc difficiles à éviter. Ainsi, les équations en y ont été abandonnées.

### Classes de gestion d'effet
* MusicPlayer : Le nom parle de lui-même. Cette classe permet de gérer l'ambiance sonore du jeu, en définissant notamment le fichier à jouer ainsi que sa répétition.
* PauseScreen : Permet depuis GameScene de mettre le jeu en pause lors de l'appui sur la touche Echap. Allège surtout le constructeur de GameScene ainsi que son corps.
* Fadingrectangle : Implémentation des transitions visuelles entre 2 écrans. Permet simplement de construire un rectangle dont l'opacité va évoluer au cours du temps, avant de passer                     à la scène suivante.

## 📋 Les prochains objectifs 
- Ajouter des options, telles que la possibilité de redéfinir les touches du jeu ✔️, ou encore la taille de la fenêtre
- Sauvegarde du meilleur score et des nouveaux contrôles
- Proposer plus de thèmes pour le héros ou pour l'arrière-plan
- Ajouter une animation lors de la collision ✔️
