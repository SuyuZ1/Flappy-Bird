# ShadowFlap

ShadowFlap is a Java-based 2D game inspired by the popular "Flappy Bird."     The project requires implementing a bird navigating through a series of obstacles (pipes) with added features such as different levels, life bars, and weapons.   The game utilizes the Basic Academic Game Engine Library (Bagel).

## Overview

ShadowFlap consists of two levels, each with distinct gameplay elements and challenges. Players control a bird that must flap its wings to navigate through a series of obstacles. The game also introduces mechanics such as life bars, weapons, and dynamic speed adjustments for increased difficulty.

- **Level 0:** Basic gameplay with pipes as obstacles and a life bar. The player must earn 10 points to level up.
- **Level 1:** Adds new elements, including weapons (rocks and bombs) and two types of pipes (plastic and steel). The player must earn 30 points to win.

## Features

### Game Elements

- **Bird:** Controlled by the player using the space bar to flap. It features animations for wing flapping and the ability to pick up and shoot weapons.
  - Life bar with hearts indicating remaining lives.
  - Spawn point at (200, 350).
- **Pipes:** Pipes come in sets and act as obstacles for the bird.
  - **Plastic Pipes:** Destroyed with any weapon.
  - **Steel Pipes:** Can only be destroyed with bombs and occasionally shoot flames.
- **Weapons:** The bird can pick up weapons (rocks or bombs) and shoot them at pipes.
  - Rocks and bombs have different ranges and target capabilities.

### Controls

- **Space Bar:** Makes the bird flap its wings.
- **'S' Key:** Shoots a weapon if the bird has picked one up.
- **'L' Key:** Increases the game speed.
- **'K' Key:** Decreases the game speed.

### Screens

- **Start Screen:** Displays when the game starts, prompting the player to press space to begin.
- **Game Over Screen:** Displays when the player loses all lives.
- **Level Up Screen:** Displays upon reaching 10 points in Level 0.
- **Win Screen:** Displays upon reaching 30 points in Level 1.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or above.
- Bagel game engine library (provided with the project skeleton).

### Installation

1. Clone the project repository:
   ```
   git clone https://github.com/SuyuZ1/Flappy-Bird.git
   ```
2. Import the project into your preferred Java IDE (e.g., IntelliJ IDEA).
3. Ensure that the Bagel library is included in your project.

### Running the Game

- Run the `ShadowFlap.java` file, which contains the main method to start the game.
- Use the space bar to control the bird and navigate through obstacles.

## Class Structure

- **ShadowFlap:** The main class that initializes and runs the game.
- **Bird:** Manages the bird's movement, life, and interaction with game elements.
- **Pipe:** Represents the obstacles that the bird must avoid.
- **Weapon:** Represents rocks and bombs that can be used to destroy pipes.
- **LifeBar:** Displays the bird's remaining lives.

The UML diagram (from Project 2A) outlines the relationship between these classes and their main attributes and methods.

## Game Mechanics

### Scoring

- **Level 0:** 1 point is earned every time the bird passes through a set of pipes.
- **Level 1:** Additional points are earned by destroying pipes using weapons.

### Life System

- The bird starts with 3 lives in Level 0 and 6 lives in Level 1.
- Lives are lost when the bird collides with pipes or goes out of bounds.

### Timescale Adjustment

- Players can adjust the game's speed by using the 'L' and 'K' keys.
- The timescale ranges from 1 to 5, with each increment or decrement adjusting the movement speed of the pipes by 50%.

## Media

Below are some screenshots from ShadowFlap gameplay:

![Start Screen](images/start_screen.png)
*The start screen prompts the player to begin.*

![Level 0 Gameplay](images/level0_gameplay.png)
*The bird navigating through pipes in Level 0.*

![Level 1 Weapon Pickup](images/level1_weapon.png)
*The bird picking up a weapon in Level 1.*


## Contributing

Contributions are welcome! If you wish to add new features or fix bugs, feel free to fork the repository and submit a pull request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- The Bagel library and course instructors for providing the foundation of this project.
- University of Melbourne, School of Computing and Information Systems.

