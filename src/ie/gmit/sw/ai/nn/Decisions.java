package ie.gmit.sw.ai.nn;

import ie.gmit.sw.ai.nn.activator.Activator;

public class Decisions {

	private NeuralNetwork nn = null;

//	public Decisions{
//		nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 4, 3, 4);
//		Trainator trainer = new BackpropagationTrainer(nn);
//			trainer.train(data, expected, 0.2, 3000000);
//	}
	private double[][] data =

			{ //Health, Sword, Bomb, Enemies

					// No Sword, No Bomb
					{ 1, 0, 0, 0 }, { 1, 0, 0, 0.5 }, { 1, 0, 0, 1 }, // full health, enemies covered
					{ 0.5, 0, 0, 0 }, { 0.5, 0, 0, 0.5 }, { 0.5, 0, 0, 1 }, // minior injuries, enemies covered
					{ 0, 0, 0, 0 }, { 0, 0, 0, 0.5 }, { 0, 0, 0, 1 }, // serious injuries, enemies covered

					// Sword, No Bomb
					{ 1, 1, 0, 0 }, { 1, 1, 0, 0.5 }, { 1, 1, 0, 1 }, // full health, enemies covered
					{ 0.5, 1, 0, 0 }, { 0.5, 1, 0, 0.5 }, { 0.5, 1, 0, 1 }, // minior injuries, enemies covered
					{ 0, 1, 0, 0 }, { 0, 1, 0, 0.5 }, { 0, 1, 0, 1 }, // serious injuries, enemies covered

					// No Sword, Bomb
					{ 1, 0, 1, 0 }, { 1, 0, 1, 0.5 }, { 1, 0, 1, 1 }, // full health, enemies covered
					{ 0.5, 0, 1, 0 }, { 0.5, 0, 1, 0.5 }, { 0.5, 0, 1, 1 }, // minior injuries, enemies covered
					{ 0, 0, 1, 0 }, { 0, 0, 1, 0.5 }, { 0, 0, 1, 1 }, // serious injuries, enemies covered

					// Sword, Bomb
					{ 1, 1, 1, 0 }, { 1, 1, 1, 0.5 }, { 1, 1, 1, 1 }, // full health, enemies covered
					{ 0.5, 1, 1, 0 }, { 0.5, 1, 1, 0.5 }, { 0.5, 1, 1, 1 }, // minior injuries, enemies covered
					{ 0, 1, 1, 0 }, { 0, 1, 1, 0.5 }, { 0, 1, 1, 1 } // serious injuries, enemies covered
			};

	private double[][] expected =

			{ // Attack, Panic, Heal,  Run

					// No Sword, No Bomb
					{ 1, 0, 0, 0}, { 1, 0, 0, 0 }, { 0, 0, 1, 0 }, // full health, enemies covered
					{ 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 }, // minior injuries, enemies covered
					{ 0, 0, 1, 0 }, { 0, 0, 0, 1 }, { 0, 0, 0, 1 }, // serious injuries, enemies covered

					// Sword, No Bomb
					{ 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, // full health, enemies covered
					{ 1, 0, 0, 0 }, { 0, 0, 1, 0 }, { 0, 1, 0, 0 }, // minior injuries, enemies covered
					{ 0, 0, 1, 0 }, { 0, 0, 0, 1 }, { 0, 0, 0, 1 }, // serious injuries, enemies covered

					// No Sword, Bomb
					{ 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, // full health, enemies covered
					{ 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, // minior injuries, enemies covered
					{ 0, 0, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 }, // serious injuries, enemies covered

					// Sword, Bomb
					{ 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, // full health, enemies covered
					{ 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, // minior injuries, enemies covered
					{ 1, 0, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 1, 0 } // serious injuries, enemies covered
			};

	public void panic(){
		System.out.println("Panic");
	}

	public void attack(){
		System.out.println("Attack");
	}

	public void hide(){
		System.out.println("Hide");
	}

	public void runAway(){
		System.out.println("Run Away");
	}

	public int action(double health, double sword, double bomb, double enemies) throws Exception{

		double[] params = {health, sword, bomb, enemies};
		int action = 0;

		NeuralNetwork nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 4, 3, 4);
		Trainator trainer = new BackpropagationTrainer(nn);
		trainer.train(data, expected, 0.01, 100000);

		double[] result = nn.process(params);

		for(double val : result){
			System.out.println(val);
		}

		System.out.println("==>" + (Utils.getMaxIndex(result) + 1));

		int output = (Utils.getMaxIndex(result) + 1);

		switch(output){
			case 1:
				panic();
				action = 1;
				break;
			case 2:
				attack();
				action = 2;
				break;
			case 3:
				hide();
				action = 3;
				break;
			default:
				runAway();
				action = 4;
		}
		System.out.println("action: " + action);
		return action;
	}
} // class