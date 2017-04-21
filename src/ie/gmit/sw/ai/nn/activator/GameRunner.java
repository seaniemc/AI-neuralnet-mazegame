package ie.gmit.sw.ai.nn.activator;

import ie.gmit.sw.ai.nn.BackpropagationTrainer;
import ie.gmit.sw.ai.nn.NeuralNetwork;
import ie.gmit.sw.ai.nn.Trainator;
import ie.gmit.sw.ai.nn.Utils;

public class GameRunner {
	
	private double[][] data = { //Health, Sword, Gun, Enemies
			{ 2, 0, 0, 0 }, { 2, 0, 0, 1 }, { 2, 0, 1, 1 }, { 2, 0, 1, 2 }, { 2, 1, 0, 2 },
			{ 2, 1, 0, 1 }, { 1, 0, 0, 0 }, { 1, 0, 0, 1 }, { 1, 0, 1, 1 }, { 1, 0, 1, 2 }, 
			{ 1, 1, 0, 2 }, { 1, 1, 0, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 1 }, { 0, 0, 1, 1 }, 
			{ 0, 0, 1, 2 }, { 0, 1, 0, 2 }, { 0, 1, 0, 1 } };

	private double[][] expected = { //Panic, Attack, Hide, Run
			{ 0.0, 0.0, 1.0, 0.0 }, { 0.0, 0.0, 1.0, 0.0 }, { 1.0, 0.0, 0.0, 0.0 }, { 1.0, 0.0, 0.0, 0.0 }, 
			{ 0.0, 0.0, 0.0, 1.0 }, { 1.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 1.0, 0.0 }, { 0.0, 0.0, 0.0, 1.0 }, 
			{ 1.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0, 1.0 }, 
			{ 0.0, 0.0, 1.0, 0.0 }, { 0.0, 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0, 1.0 }, { 0.0, 1.0, 0.0, 0.0 }, 
			{ 0.0, 1.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 1.0 } };
	
	public void panic(){
		System.out.println("panic");
	}
	public void attack(){
		System.out.println("attack");
	}
	public void hide(){
		System.out.println("hide");
	}
	public void runAway(){
		System.out.println("run away");
	}
	public void action(double health, double sword, double gun, double enemies) throws Exception{
		
		double[] params = {health, sword, gun, enemies};
		
		NeuralNetwork nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 4, 3, 4);
		Trainator trainer = new BackpropagationTrainer(nn);
		trainer.train(data, expected, 0.01, 10000);
		
//		int testIndex = 11;
		double[] result = nn.process(params);
		System.out.println("==>" + (Utils.getMaxIndex(result) + 1));
		
		for(double val: result){
			System.out.println();
		}
		int output = (Utils.getMaxIndex(result) + 1);
		
		switch (output) {
		case 1:
			panic();
			break;
		case 2:
			attack();
			break;
		case 3:
			hide();
			break;
		case 4:
			runAway();
			break;

		default:
			break;
		}
	}
	public static void main(String[] args) throws Exception{
		
		double health = 2.0;//Double.parseDouble(args[0]);
		double sword = 1.9;  //Double.parseDouble(args[1]);
		double gun = 3.9; //Double.parseDouble(args[2]);
		double enemies = 1.2;//Double.parseDouble(args[3]);
		
		new GameRunner().action(health, sword,gun, enemies );
	}
	
	


}
