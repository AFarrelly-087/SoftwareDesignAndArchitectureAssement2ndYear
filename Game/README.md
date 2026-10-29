# Software Design and Architecture Coursework

#### Alexander Farrelly 23665714

## The Game
Due to the specifications of the project shown in the coursework brief, the basic game is designed to allow two opposing
players to move from opposite ends of a 5x5 grid. The game requires each player to roll die to move toward their goal of 
the other player's start point. The game is won when one player reaches or surpasses the target goal.

### Variations
The game utilises a state machine to determine which variation (using `Runner` class and `GameVariation` enum)
the game is going to be run in, this is done through selecting the variation from the user's input. Each Variation has #
its own logic in the `GameSimulation` class that is only called when that variation is selected.
#### Exact End
Exact End prevents the user from moving past the target goal, and if they roll higher than the target goal, the amount 
rolled above the target goal is space moved back from the target goal. To figure out whether the user has rolled higher
than the target goal, the game utilises a boolean that is set to true after checking if the roll is greater than the
target goal, the excess is then subtracted from the target goal and becomes the players' new position.
#### Hit
Hit variation utilises a check to see if the player roll lands onto another player's position, if they do land on 
another player's position, the player doesn't move, and the turn is over, otherwise the player moves as normal.
#### Wormhole
Wormhole variation assigns two positions (Fifteen and Five in this case) to be the wormhole, if a player lands on the
wormhole, their new position becomes the output position of the position of they landed on (Player lands on 15 and is
moved to 5).
#### Large Board
Large Board works the same as the basic game but with a larger board size and two additional players. The distance a 
player needs to traverse is larger, being 30 spaces instead of 24 but without needing the player to traverse the whole
board to maintain fairness. This is done by hard coding the start and ending positions into each player for the basic 
game and large board variation. This is done through a simple if statement to check if the variation is active and
building the players in the positions assigned to them.

#### Single Die
The Single Die variation works differently to the other variations as you are able to run it alongside each of them 
(i.e. wormhole but with one die instead of two). This decision is made after the user decides on what variation to play,
they can opt into using a single dice by typing y or n. The double and single die variations are concrete
implementations of the `DiceShaker` interface using `FixedDiceShaker` and `SingleDiceShaker` respectively.

### Advanced Features
In this game, I have implemented the ability to save a game and for a state machine to switch between variations of the 
game while the program is running.

#### Saving and Replaying
The game incorporates the ability to store the gamemode and dice rolls of a game, allowing the user to replay a game at
any time they want. I have stored the data as a simple text file, which is added to when a game finishes. The dice rolls
incorporate a `FixedDiceShaker` concrete implementation of `DiceShaker` interface which is used to repeat the dice rolls 
of the previous games.

#### State Machine
The game uses a state machine to switch between different variations of the game while the program is running, this is
done in the `Runner` class where the state is changed depending on the user's input. The state changes 
are handled by the `Runner` class and the `GameVariation` enum, and switch after each game is played depending on
if the user wishes to play a different variation or to exit the program.

### Patterns

#### Factory Pattern
I have used the Factory pattern when creating the player characteds (as shown in `PlayerFactory`, `BluePlayerFactory`,
`RedPlayerFactory`, etc.) to make the creation of players much simpler and would only need to send in specific values 
like the starting and ending positions and the size of the board they are playing on. It allows the game to have 
different player types without having to hard code the different variations into the program.

Another use of the Factory Pattern was the `GameFactory` class, which is used to set up the game with the correct 
variation and player count.

#### Strategy Pattern
The game uses the Strategy pattern in the `DiceShaker` interface and its concrete implementations, such as 
`FixedDiceShaker`, `RandomDoubleDiceShaker`, and `RandomSingleDiceShaker`. This allows the game to switch between the 
different dice shakers at runtime, to either replay a game or showcase different variations function using the 
`FixedDiceShaker` or to play the game with randomly rolled die using `RandomDoubleDiceShaker` or 
`RandomSingleDiceShaker`.

#### Iterator Pattern
The `FixedDiceShaker` also utilises the iterator pattern to go through a pre-determined dicerolls for the 
`replayGameUsecase` and the Fixed rolls to showcase the functionality of Hit and Wormhole variations.

### SOLID Principles

#### Single Responsibility
`GameSimulation`, `FileResultAdapter` and `Runner` each adhere to the Single Responsibility principle as they 
each adhere to one function. `Runner` is only used to handle user interaction with the system, 
`FileResultAdapter` makes controls the persistence of the savegames in `game_results.txt`, and `GameSimulation` handles
all of the logic for the game appropriately.

#### Open/Closed Principle
The `DiceShaker` and `PlayerFactory` interfaces adhere to the Open/Closed Principle by allowing for the ability to add 
new behaviors to the game without having to modify any of the core logic surronding the game.

#### Liskov Substitution Principle
`FixedDiceShaker`, `RandomDoubleDiceShaker` and `RandomSingleDiceShaker` are interchangable concrete implementations of
the `DiceShaker` interface, allowing for the game to be run with different kinds of die depending on the variation.

#### Interface Separation Principle
I use the `Required` and `Provided` interfaces to ensure that, depending on the use cases, minimal dependencies are used
for the `newgame` and `replaygame` packages.

#### Dependency Inversion Principle
The Game adheres to the Dependency Inversion Principle as the use cases depend on the interfaces rather than hardcoded 
functionality that would require large changes to the codebase to alter.

### Ports and Adapters
The project makes use of ports and adapters via separating the project into the `applicationCode`  and 
`infrastructureCode` packages. `infrastructureCode` is aware of the applicationCode through its adapters `Runner`
and `FileResultAdapter`, while the `applicationCode` is unaware of the `infrastructureCode` and is only aware of its
own interfaces. This allows for the system to be easily maintained and testable.

#### Driven Adapters
`FileResultAdapter` implements the `Required` interfaces from `newgame` and `replaygame`. It allows for the reading and
writing of game results to a file.

#### Driving Adapters
`Runner` implements `CommandLineRunner` from Spring Boot, allowing for the game to collect user input from the 
console and calls the `Provided` ports from `replaygame` and `newgame` to run the game.

### Evaluation
I believe my implementation of the Project Specification adheres particularly well. I have incorporated all variations
of the game and have followed the SOLID principles throughout. To improve on the system, I would incorporate the 
ability to have multiple game states running simultaneously and further expand on introducing new variations into the
game. I would consider reworking each game variation to utilise a strategy pattern to allow for more 
customization of the game mechanics.