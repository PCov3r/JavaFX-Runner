# JavaFX-Runner

## Bienvenue dans le monde merveilleux de Java 👨‍💻

Notre héros devra faire face à bien des dangers pour arriver victorieux. 
Armez-vous de courage et plongez dans cette aventure !

## Description des différentes classes 
### Main

Tout simplement le corps du jeu. Classe où on retrouve tout les différents écrans du jeu et qui permet de lancer le jeu.

### MenuScene

Extend de Scene, MenuScene correspond à la page d'accueil du jeu. On y retrouve donc le titre du jeu, les boutons du menu, ainsi qu'un (superbe) visuel de notre héros courant, armure au vent.

### GameScene

Extend de Scene, GameScene contient tous les éléments composant le corps du jeu : camera, heros, ennemis, projectiles, arrière-plan et j'en passe.

### LoseScene

Extend de Scene, LoseScene représente la page de gameover du jeu. Elle indique ainsi son score final au joueur et par le biais de boutons, lui permet de tenter d'améliorer son score ou bien de quitter le jeu. Je vous souhaite de ne pas avoir affaire à cet écran !
