package assignment3part4;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author JOHN
 */
public class Assignment3Part4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//          String string = "male";
//          byte[] integer = string.getBytes();
//          string = integer.toString();
//          
//          System.out.println(string);
        NeuralNetwork network = new NeuralNetwork();
        try
        {
            //load training data
            network.loadTrainingData("trainingFile.txt");

            //set parameters of network
            network.setParameters(3, 1000, 2376, .7);
            
            //3, 3000, 2376, .7 works well. 5 out of 20% error
            //network.setParameters(5, 2500, 2376, .7); 9 out of 20%
            //network.setParameters(5, 2500, 2376, .65); 6 out of 20%
            
            //train network
            network.train();
            
            network.validate("validationFile.txt");
            
            //test network
            //network.testData("inputFile.txt", "outputFile.txt");
        
        } catch (IOException e)
        {
            System.out.println(e);
        }
    }
    
}