package ie.gmit.sw.ai.gui;

import ie.gmit.sw.ai.maze.*;
import ie.gmit.sw.ai.searchAlgos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame implements KeyListener {

	public static final int MAZE_DIMENSION = 80;
	private JFrame gameFrame;
	private JPanel coverPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;

	// Left panel elements
	private JLabel lblCurScore;
	private JLabel lblCurStepstoExit;
	private JLabel lblCurSteps;
	private JLabel lblHealthPoints;
	private JLabel lblArmorPoints;
	private JLabel lblCurWeapon;
	private JLabel lblCurWeaponStr;
	private JLabel lblCurSpecial;
	private JLabel lblEnemiesAmount;
	private JLabel lblCommonAmount;
	private JLabel lblBossesAmount;
	private JLabel lblCurDifficulty;

	// Right panel elements
	private JLabel lblCurExits;
	private JComboBox<String> gameDifficulty1;
	private JComboBox<String> gameDifficulty2;

	// The game controller object instance & game view panel
	private GameView gamePanel;
	private GameController game;

	public GameFrame() {
		setupFrame();
	}

	/**
	 * Setup of the window frame that surrounds the game
	 */
	private void setupFrame() {
		newFrame();
		setupCoverPanel();
		gameFrame.repaint();
	}

	/**
	 * Creating a new window frame
	 */

	//main frame for game
	private void newFrame() {
		gameFrame = new JFrame("Maze Game - AI Project - ");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.addKeyListener(this);
		gameFrame.getContentPane().setLayout(null);
		gameFrame.getContentPane().setBackground(Color.black);
		gameFrame.setSize(1200, 700);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
	}

	/**
	 * Setup of the cover panel that greets the player when they first launch the game
	 */

	//main menu frame
	private void setupCoverPanel() {
		// Setting up cover panel when game first starts
		coverPanel = new JPanel();
		coverPanel.setBounds(204, 0, 797, 670);
		coverPanel.setBackground(Color.blue);
		coverPanel.setLayout(null);
		gameFrame.getContentPane().add(coverPanel);



		JLabel label1 = new JLabel("AI Maze Game");
		label1.setForeground(Color.WHITE);
		label1.setFont(new Font("Serif", Font.BOLD, 40));
		label1.setBounds(255, 200, 750, 100);
		coverPanel.add(label1);

		JLabel label = new JLabel("Difficulty:");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Serif", Font.BOLD, 24));
		label.setBounds(307, 350, 126, 24);
		coverPanel.add(label);

		gameDifficulty1 = new JComboBox<String>();
		gameDifficulty1.setFont(new Font("Serif", Font.BOLD, 24));
		gameDifficulty1.setBounds(435, 350, 120, 30);
		gameDifficulty1.addItem("Easy");
		gameDifficulty1.addItem("Normal");
		gameDifficulty1.addItem("Hard");
		gameDifficulty1.setSelectedIndex(1);
		gameDifficulty1.setFocusable(false);
		coverPanel.add(gameDifficulty1);

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameFrame.getContentPane().remove(coverPanel);
				//setupLeftPanel();
				//setupRightPanel();
				initNewGame(gameDifficulty1.getSelectedItem().toString());
			}
		});
		btnNewGame.setFont(new Font("Serif", Font.BOLD, 24));
		btnNewGame.setBounds(332, 430, 211, 33);
		btnNewGame.setFocusable(false);
		coverPanel.add(btnNewGame);
	}

	/**
	 * Setup of the left panel in the game window frame that contains labels and information for the player
	 */


	/**
	 * Initializing the game to start
	 *
	 * @param gameDifficulty
	 */
	private void initNewGame(String gameDifficulty) {
		// Initializing the game, panel and killing old enemy threads if they exist
		if (game != null)
			game.killEnemyThreads();

		if (gamePanel != null)
			gameFrame.getContentPane().remove(gamePanel);

		game = new GameController();

		newGame(gameDifficulty);
		lblCurDifficulty.setText(gameDifficulty);
		gameDifficulty2.setSelectedItem(gameDifficulty);

		gameFrame.repaint();
		// Making sure if the player block is suitable to search for the amount of steps to goal
		if (game.getPlayer().getStepsToExit() <= 0)
			initNewGame(gameDifficulty);
	}

	/**
	 * Starts a new game by re-generating the maze and call the enemy and player creation methods
	 *
	 * @param gameDifficulty
	 */
	private void newGame(String gameDifficulty) {
		// Create new player & enemy instances here
		Maze model = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		Node[][] maze = model.getMaze();
		Warrior player = new Warrior(100, 100);
		ArrayList<Spider> enemies = new ArrayList<Spider>();
		Dimension d = new Dimension(800, 800);

		game.setModel(model);
		game.setMaze(maze);
		game.setPlayer(player);
		game.setEnemies(enemies);
		game.placePlayer(model.getGoalPos());
		game.setupEnemies(gameDifficulty);

		gamePanel = new GameView(game);
		gamePanel.setBounds(204, 0, 800, 700);
		gamePanel.setPreferredSize(d);
		gamePanel.setMinimumSize(d);
		gamePanel.setMaximumSize(d);
		gameFrame.getContentPane().add(gamePanel);

		updateView();
	}



	/**
	 * Update the view by calling the game panel set position methods
	 */
	private void updateView() {
		gamePanel.setCurrentRow(game.getPlayer().getRowPos());
		gamePanel.setCurrentCol(game.getPlayer().getColPos());

	}

	/**
	 * Check what key the user presses and executes the code / methods if a valid button is pressed
	 */
	public void keyPressed(KeyEvent e) {
		if (game.getPlayer() == null || game.getPlayer().isGameOver()) return;

		// Check here if the block is a bomb or weapon etc
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && game.getPlayer().getColPos() < MAZE_DIMENSION - 1) {
			if (isValidMove(game.getPlayer().getRowPos(), game.getPlayer().getColPos() + 1) && game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos() + 1].isWalkable()) {
				gamePanel.setPlayerState(5);
				game.getPlayer().setColPos(game.getPlayer().getColPos() + 1);
				game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && game.getPlayer().getColPos() > 0) {
			if (isValidMove(game.getPlayer().getRowPos(), game.getPlayer().getColPos() - 1) && game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos() - 1].isWalkable()) {
				gamePanel.setPlayerState(16);
				game.getPlayer().setColPos(game.getPlayer().getColPos() - 1);
				game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP && game.getPlayer().getRowPos() > 0) {
			if (isValidMove(game.getPlayer().getRowPos() - 1, game.getPlayer().getColPos()) && game.getMaze()[game.getPlayer().getRowPos() - 1][game.getPlayer().getColPos()].isWalkable()) {
				gamePanel.setPlayerState(5);
				game.getPlayer().setRowPos(game.getPlayer().getRowPos() - 1);
				game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && game.getPlayer().getRowPos() < MAZE_DIMENSION - 1) {
			if (isValidMove(game.getPlayer().getRowPos() + 1, game.getPlayer().getColPos()) && game.getMaze()[game.getPlayer().getRowPos() + 1][game.getPlayer().getColPos()].isWalkable()) {
				gamePanel.setPlayerState(5);
				game.getPlayer().setRowPos(game.getPlayer().getRowPos() + 1);
				game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			gamePanel.toggleZoom();
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			// If the player hasn't picked up any special pickup items then return
			if (game.getPlayer().getSpecial() <= 0) return;
			// Creates a new search algorithm object to find the goal node, randomly
			Traversator traverse = randomSearch(new Random().nextInt((3 - 0) + 1) + 0);
			traverse.traverse(game.getMaze(), game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()]);
			game.getPlayer().setSearchCount(game.getPlayer().getSearchCount() + 1);
			game.getPlayer().setSpecial(game.getPlayer().getSpecial() - 1);
		}

		updateView();
	}

	public void keyReleased(KeyEvent e) {
		gamePanel.setPlayerState(6);
	}

	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Checks if the player is making a valid move
	 *
	 * @param r
	 * @param c
	 * @return Returns true if its a valid move
	 */
	private boolean isValidMove(int r, int c) {
		// Error checking the move position
		if (!(r <= game.getMaze().length - 1 && c <= game.getMaze()[r].length - 1)) return false;

		switch (game.getMaze()[r][c].getNodeType()) {
			case ' ':
				// Health pick up,
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
				game.getMaze()[r][c].setNodeType('P');
				game.getMaze()[r][c].setGoalNode(true);
				return true;
			case 'W':
				// Sword pick up, the least powerful weapon in the game
				if (!game.getPlayer().getWeapon().equals("Sword")) {
					game.getPlayer().setWeapon("Sword");
					game.getPlayer().setWeaponStrength(45);
					game.getMaze()[r][c].setNodeType('X');
					//PlaySound.play("res/power_up.wav");
				}
				return true;
			case '?':
				// Help me pick up, shows the player a possible route to the goal
				game.getPlayer().setSpecial(game.getPlayer().getSpecial() + 1);
				game.getMaze()[r][c].setNodeType('X');
				//PlaySound.play("res/power_up.wav");
				return true;
			case 'B':
				// A bomb pick up, very powerful bomb that kills enemies very well
				if (!game.getPlayer().getWeapon().equals("Shotgun")) {
					game.getPlayer().setWeapon("Shotgun");
					game.getPlayer().setWeaponStrength(65);
					game.getMaze()[r][c].setNodeType('X');
					//PlaySound.play("res/power_up.wav");
				}
				return true;
			case 'H':
				// A hydrogen bomb pick up, extremely powerful and deadly weapon
				if (!game.getPlayer().getWeapon().equals("AK-47")) {
					game.getPlayer().setWeapon("AK-47");
					game.getPlayer().setWeaponStrength(85);
					game.getMaze()[r][c].setNodeType('X');
					//PlaySound.play("res/power_up.wav");
				}
				return true;
			case 'M':
				// Health pick up, adds 50 health to players character
				if (game.getPlayer().getHealth() < 100) {
					game.getPlayer().setHealth(game.getPlayer().getHealth() + 50);
					if (game.getPlayer().getHealth() > 100)
						game.getPlayer().setHealth(100);
					game.getMaze()[r][c].setNodeType('X');
					//PlaySound.play("res/power_up.wav");
				}
				return true;
			case 'A':
				// Health pick up, adds 50 health to players character
				if (game.getPlayer().getArmor() < 100) {
					game.getPlayer().setArmor(game.getPlayer().getArmor() + 50);
					if (game.getPlayer().getArmor() > 100)
						game.getPlayer().setArmor(100);
					game.getMaze()[r][c].setNodeType('X');
					//PlaySound.play("res/power_up.wav");
				}
				return true;
			case 'T':
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
				game.getMaze()[r][c].setNodeType('P');
				game.getMaze()[r][c].setGoalNode(true);
				return true;
			case 'G':
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
				game.getMaze()[r][c].setNodeType('Z');
				game.getPlayer().setGameOver(true);
				//PlaySound.play("res/win_game.wav");
				return true;
			case 'N':
				if (!game.getPlayer().getWeapon().equals("Pipe Bomb")) {
					game.getPlayer().setWeapon("Pipe Bomb");
					game.getPlayer().setWeaponStrength(100);
					game.getMaze()[r][c].setNodeType('X');
					//PlaySound.play("res/power_up.wav");
				}
				return true;
			case 'E':
				BattleLogic fuzzyBattle1 = new BattleLogic();
				boolean enemyWon1 = fuzzyBattle1.startBattle(game.getPlayer(), game.getEnemies().get(game.getMaze()[r][c].getEnemyID()), "fcl/fuzzyFight.fcl");
				if (enemyWon1 == true) {
					// The player has lost the game!
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setEnemyID(0);
					game.getPlayer().setGameOver(true);
					game.getMaze()[r][c].setNodeType('L');
					//PlaySound.play("res/lose_game.wav");
					return false;
				} else {
					game.getEnemies().get(game.getMaze()[r][c].getEnemyID()).setHealth(0);
					game.getMaze()[r][c].setNodeType('D');
					game.getMaze()[r][c].setEnemyID(0);
					//PlaySound.play("res/win_fight.wav");
					return false;
				}
			case 'F':
				BattleLogic fuzzyBattle2 = new BattleLogic();
				boolean enemyWon2 = fuzzyBattle2.startBattle(game.getPlayer(), game.getEnemies().get(game.getMaze()[r][c].getEnemyID()), "fcl/fuzzyFight.fcl");
				if (enemyWon2 == true) {
					// The player has lost the game!
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setEnemyID(0);
					game.getPlayer().setGameOver(true);
					game.getMaze()[r][c].setNodeType('D');
					//PlaySound.play("res/lose_game.wav");
					return false;
				} else {
					game.getEnemies().get(game.getMaze()[r][c].getEnemyID()).setHealth(0);
					game.getMaze()[r][c].setNodeType('L');
					game.getMaze()[r][c].setEnemyID(0);
					//PlaySound.play("res/win_fight.wav");
					return false;
				}
			default:
				return false;
		}
	}

	private Traversator randomSearch(int randNum) {
		// Selecting a random algorithm to be created and returned
		switch (randNum) {
			case 0:
				return new AStarTraversator(game.getModel().getGoalNode(), false);
			case 1:
				return new BeamTraversator(game.getModel().getGoalNode(), 50);
			case 2:
				return new BruteForceTraversator(true);
			case 3:
				//return new BestFirstTraversator(game.getModel().getGoalNode());
			case 4:
				//return new BasicHillClimbingTraversator(game.getModel().getGoalNode());
			case 5:
				//return new DepthLimitedDFSTraversator(game.getMaze().length / 2);
			case 6:
				//return new IDAStarTraversator(game.getModel().getGoalNode());
			case 7:
				//return new IDDFSTraversator();
			default:
				return new AStarTraversator(game.getModel().getGoalNode(), false);
		}
	}

	public static void main(String[] args) throws Exception {
		new GameFrame();
	}

}
