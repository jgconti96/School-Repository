/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
          String string = "male";
          byte[] integer = string.getBytes();
          string = integer.toString();
          
          System.out.println(string);
//        NeuralNetwork network = new NeuralNetwork();
//        try
//        {
//            //load training data
//            network.loadTrainingData("trainingFile1.txt");
//
//            //set parameters of network
//            network.setParameters(3, 1000, 2376, .6);
//
//            //train network
//            network.train();
//
//            //test network
//            network.testData("inputFile1.txt", "outputFile1.txt");
//        
//        } catch (IOException e)
//        {
//            System.out.println(e);
//        }
    }
    
}
