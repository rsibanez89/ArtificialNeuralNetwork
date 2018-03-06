package algorithms.supervised.artificialneuralnetwork;

import java.util.Vector;

public class Perceptron {
	private Vector<Double> weights;
	private double convergence;
	
	public Perceptron (int inputs){
		this.convergence = 0.1; //default value
		weights = new Vector <Double>(inputs);
		for (int i = 0; i<inputs; i++){
			Double w = new Double (Math.random()); //random weights
			weights.add(w);
		}
	}
	
	public int predict(Vector<Double> inputs){
		Double weightedAverage = 0.0;
		for (int i = 0; i < inputs.size(); i++){
			weightedAverage = weightedAverage+ inputs.get(i)* weights.get(i);
		}
		if (weightedAverage>0)
			return 1;
		return 0;
	}

	public int train (Vector<Double> inputs, int expectedOutput){
		int output = this.predict(inputs);
		int error = expectedOutput - output;
		if (error!=0){
			for (int i = 0; i < weights.size(); i++){
				Double w = weights.get(i);
				w = w + this.convergence * error * inputs.get(i);
				weights.set(i,w);
			}
		}
		return error;
	}
	
}
