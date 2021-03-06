**AI Maze Game**
===================

**Students:**<br>
*Dara Starr - G00209787*<br>
*Sean McGrath - G00316649*<br>
**Course:** *Software Development*<br>
**Module:** *Artificial Intelligence*<br>
**Lecturer:** *Dr. John Healy*<br>
**Galway-Mayo Institute of Technology**
****

**Maze Game**
As part of our fourth year module Artificial Intelligence we were tasked with controlling a maze game's characters using Fuzzy Logic and Neural Networks. The game itself is a maze game where you are a Spartan warrior and have to get to a goal node randomly generated on the maze. Also in the maze are two types of spiders. The red spiders move in random directions around the maze but the black spiders uses the A* search algorithm to hunt you down and try annihilate your character all interactions between the the Warrior and the spider are decided by Fuzzy Logic. We also have used other search algorithms to help find your way to the winning pint at the end of the game. 

**Main Character**
![Spartan Warrior](https://cloud.githubusercontent.com/assets/8806515/25294893/19178f34-26d9-11e7-9d17-25e07479eeee.png)<br>
Our Main Hero is a Spartan Warrior.<br>

**Black Hunter Spider**
![Hunts down the Spartan warrior](https://cloud.githubusercontent.com/assets/8806515/25294936/5840fca4-26d9-11e7-9388-eff64310e7ec.png)<br>
This is our black hunter spider who uses the A*(A-star) algorithm to hunt down the Spartan warrior before he reaches his destination.<br>
**Red Spider**
![Red Threaded Spider](https://cloud.githubusercontent.com/assets/8806515/25295007/a91e889e-26d9-11e7-99ef-0f7151aa9b57.png)<br>
The Red spiders are threaded and randomly move around the map so the Spartan must avoid them.<br>

**Goal node**
![Where the spartan wants to get to for his reward](https://cloud.githubusercontent.com/assets/8806515/25295055/de178172-26d9-11e7-8fd0-c2a24e89b525.png)<br>
The location which the spartan warrior is trying to get to.<br>

**Winners Prize**
![Spartans reward](https://cloud.githubusercontent.com/assets/8806515/25295104/187decac-26da-11e7-97ce-1058a5cd3c58.jpg)<br>
The prize at the end of the arduous journey.<br>

**Algorithms Used**

**A* Search:** is a computer algorithm that is widely used in pathfinding and graph traversal. It is widely used due to its performance and accuracy. <br>

**Beam Search:** is a heuristic search algorithm that explores a graph by expanding the most promising node in a limited set. Beam search is an optimization of best-first search that reduces its memory requirements. <br>

**BruteForce Search:** is a very general problem-solving technique that consists of systematically enumerating all possible candidates for the solution and checking whether each candidate satisfies the problem's statement.

**Fuzzy Logic:**
The game uses Fuzzy logic to decide if a player wins a battle with the spider enemys. When the warrior encounters a spider the game uses 
fuzzy logic to decide if the player or the spider wins the battle. 
```
VAR_INPUT				// Define input variables
	health : REAL;
	weapon : REAL;
	armor : REAL;
END_VAR
```
The program uses the variables listed above to decide if the player is strong enough, or if the spider wins the day.

```
RULE 1 : IF health IS poor AND armor IS poor THEN annihilation IS bad;
```
If the player conditions are poor then the game is over. 

**Game Modes**

The game has 3 modes.  

+ Easy: Has 35 normal spiders and 3 Hunting Spiders and Warrior has full health.
+ Medium: Has 50 normal spiders 5 hunting spiders. Warrior has full health.
+ Hard: Has 60 normal spiders and 10 hunting. Warrior has full health. 

The enemys health is randomly generated and the paramters for the values change depending on the difficulty selected by the user.  
All hunting spiders use the Astar Algorithm. 

**Game Clues**

The game allows users to collect and use clues, to speed up the search for the goal. As the game is started it randomly selects one of the algorithms stated above.<br>
![Icon](https://cloud.githubusercontent.com/assets/9398358/25298364/07ff3afe-26ec-11e7-8662-d4ad025a2c27.png)






