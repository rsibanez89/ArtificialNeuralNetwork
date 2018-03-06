package algorithms.supervised.artificialneuralnetwork;

import java.util.Vector;

public class ArtificialNeuralNetwork {

	public static void main(String[] args) {
		
		Tripla[] input_data = new Tripla[] { 
				new Tripla(1.7, 56.0, 1),
				new Tripla(1.72, 63.0, 0),
				new Tripla(1.6, 50.0, 1),		
				new Tripla(1.7, 63.0, 0),
				new Tripla(1.74, 66.0, 0),
				new Tripla(1.72, 63.0, 0),
				new Tripla(1.58, 55.0, 1),
				new Tripla(1.83, 80.0, 0),
				new Tripla(1.82, 70.0, 0),
				new Tripla(1.65, 54.0, 1),			
		};
		
		Perceptron perceptron = new Perceptron(3);
		
		//training phase
		for (int i = 0; i < 1000; i++){
			for (Tripla t:input_data){
				int output = t.Gender;
				Vector<Double> inputs = new Vector<Double>();
				inputs.add(1.0);
				inputs.add(t.Height);
				inputs.add(t.Weight);
				int error = perceptron.train(inputs, output);
				System.out.println(error);
			}
		}
		
		Vector<Double> testMasc = new Vector<>();
		testMasc.add(1.0);
		testMasc.add(1.72);
		testMasc.add(82.0);
		
		Vector <Double> testFem = new Vector<>();
		testFem.add(1.0);
		testFem.add(1.53);
		testFem.add(45.0);
				
		if (perceptron.predict(testMasc) == 1)
			System.out.println("Woman");
		else 
			System.out.println("Men");
		
		if (perceptron.predict(testFem) == 1)
			System.out.println("Woman");
		else 
			System.out.println("Men");
		
		
	}

}
