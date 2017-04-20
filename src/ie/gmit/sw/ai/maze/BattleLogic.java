package ie.gmit.sw.ai.maze;

/**
 * Created by Sean on 20/04/2017.
 */import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;
public class BattleLogic {
    public BattleLogic(){

    }

    public boolean startBattle(Warrior player, Spider enemy, String fclFilePath){

        FIS fis = FIS.load(fclFilePath, true);

        if(fis == null){
            System.err.println("Error loading: '" + fclFilePath + "'");
            return true;
        }

        FunctionBlock functionBlock = fis.getFunctionBlock("battle");

        fis.setVariable("health", player.getHealth());
        fis.setVariable("armor", player.getArmor());
        fis.setVariable("weapon", player.getWeaponStrength());
        fis.evaluate();

        Variable survivability = functionBlock.getVariable("survivability");
        System.out.println("Survivability Percentage: " + (int)survivability.getValue() + "%\n");

        boolean enemyWon = false;

        player.setHealth((int)(player.getHealth() - (100 - survivability.getValue())));
        player.setArmor((int)(player.getArmor() - (100 - survivability.getValue() + 10)));
        player.setWeaponStrength((int)(player.getWeaponStrength() * (survivability.getValue() / 100)));

        // If the weapon was damaged enough then destroy the weapon
        if(player.getWeaponStrength() < 35){
            player.setWeaponStrength(0);
            player.setWeapon("Unarmed");
        }

        // Checking armor
        if(player.getArmor() <= 0){
            player.setArmor(0);
        }

        // Checking health below zero, if true then the game is over
        if(player.getHealth() <= 0){
            player.setHealth(0);
            player.setGameOver(true);
            System.out.println("Game Over!");
            System.out.println("Player Score: " + player.getScore() + "\n");
            enemyWon = true;
        }

        // If the player wins the fight then update the player object variables
        if(!enemyWon){
            player.setScore(player.getScore() + 25);
            System.out.println("Player Health: " + player.getHealth());
            System.out.println("Player Armor: " + player.getArmor() + "\n");
        }

        return enemyWon;
    }
}
