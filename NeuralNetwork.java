
package assignment3part4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class NeuralNetwork {
    
    private class Record
    {
        private double[] input;     //iputs of record
        private double[] output;    //outputs of record
        
        //Construcotr of record
        private Record(double[] input, double[] output)
        {
            this.input = input;
            this.output = output;
        }
        
    }
    
    private int numberRecords;     //number of training records
    private int numberInputs;       //number of inputs
    private int numberOutputs;      //number of outputs
    
    private int numberMiddle;       //numbner of hidden nodes
    private int numberIterations;   //number of iterations
    private double rate;            //learning rate
    
    private ArrayList<Record> records;  //list of training records
    
    private double[] input;          //inputs
    private double[] output;       //outputs at output nodes
    private double[] middle;        //outputs at hidden nodes
    
    private double[] errorMiddle;   //error at hidden nodes
    private double[] errorOut;      //errors at output nodes
    
    private double[]thetaMiddle;       //thetas at hidden nodes
    private double[] thetaOut;          //thetas at output nodes
    
    private double[][] matrixMiddle;    //weights between input/hidden nodes
    private double[][] matrixOut;       //meights between hidden / ouput nodes
    
    private final int COLUMN1MAX = 880;
    private final int COLUMN1MIN = 510;
    private final int COLUMN2MAX = 89;
    private final int COLUMN2MIN = 31;
    private final int COLUMN3MAX = 79;
    private final int COLUMN3MIN = 30;
    
    //Constructor of neural network
    public NeuralNetwork()
    {
        //parameters are zero
        numberRecords = 0;
        numberInputs = 0;
        numberOutputs = 0;
        numberMiddle = 0;
        numberIterations = 0;
        rate = 0;
        
        //array are empty
        records = null;
        input = null;
        middle = null;
        output = null;
        errorMiddle = null;
        errorOut = null;
        thetaMiddle = null;
        thetaOut = null;
        matrixMiddle = null;
        matrixOut = null;
    }
    
    
    //Method loads training recotrds from training file
    public void loadTrainingData(String trainingFile) throws IOException
    {
        Scanner inFile = new Scanner(new File(trainingFile));
        
        //read number of records, inputs, outputs
        numberRecords = inFile.nextInt();
        numberInputs = inFile.nextInt();
        numberOutputs = inFile.nextInt();
        
        //empty list of records
        records = new ArrayList<Record>();
        
        //for each training record
        for (int i  = 0; i < numberRecords; i++)
        {
            //read inputs
            double[] input = new double[numberInputs];
            String[] inputS = new String[numberInputs];
            for (int j = 0; j < numberInputs; j++)
            {
                if (0 <= j && j < 3) {
                    input[j] = inFile.nextDouble();
                    input[j] = normalize(input[j], j);
                }
                else 
                {
                    inputS[j] = inFile.next();
                    if (inputS[j].equals("male"))
                        input[j] = 1;
                    else if (inputS[j].equals("female"))
                        input[j] = 0;
                    else if (inputS[j].equals("single"))
                        input[j] = 0;
                    else if (inputS[j].equals("divorced"))
                        input[j] = .5;
                    else if (inputS[j].equals("married"))
                        input[j] = 1;
                }                    
                    

            }
            //read ouputs
            double[] output = new double[numberOutputs];
            String[] outputS = new String[numberOutputs];
            for (int j = 0; j < numberOutputs; j++){
                 outputS[j] = inFile.next();
                 if (outputS[j].equals("high"))
                     output[j] = .25;
                 else if (outputS[j].equals("low"))
                     output[j] = .5;
                 else if (outputS[j].equals("medium"))
                     output[j] = .75;
                 else if (outputS[j].equals("undetermined"))
                     output[j] = 1;
            }
            
            //create training record record
            Record record = new Record(input, output);
            
            //add record to list
            records.add(record);
        }
        
        inFile.close();
    }
    
    
    //Method for normalizing data from input file
    private double normalize(double value, int column)
    {
        double temp = value;
        if (column == 0)
            value = (value - COLUMN1MIN)/(COLUMN1MAX - COLUMN1MIN); 
        if (column == 1)
            value = (value - COLUMN2MIN)/(COLUMN2MAX - COLUMN2MIN); 
        if (column == 2)
            value = (value - COLUMN3MIN)/(COLUMN3MAX - COLUMN3MIN); 
        if (value < 0 || value > 1)
            System.out.println();
        return value;
    }
    
    //Method sets parameters of neural network
    public void setParameters(int numberMiddle, int numberIterations, int seed, double rate)
    {
        //set hidden nodes, iterations, rate
        this.numberMiddle = numberMiddle;
        this.numberIterations = numberIterations;
        this.rate = rate;
        
        //initialize random number generation
        Random rand = new Random(seed);
        
        //create input/output arrays
        input = new double[numberInputs];
        output = new double[numberOutputs];
        middle = new double[numberMiddle];
        
        //create error arrays
        errorMiddle = new double[numberMiddle];
        errorOut = new double[numberOutputs];
        
        //initialize thetas at hedden nodes
        thetaMiddle = new double[numberMiddle];
        
        for (int i = 0; i < numberMiddle; i++)
            thetaMiddle[i] = 2 * rand.nextDouble() - 1;
        
        //initialize thetas at output nodes
        thetaOut = new double[numberOutputs];
        for (int i = 0; i < numberOutputs; i++)
            thetaOut[i] = 2 * rand.nextDouble() - 1;
        
        //initialize weights between input/hidden nodes
        matrixMiddle = new double[numberInputs][numberMiddle];
        for (int i = 0; i < numberInputs; i++)
            for (int j = 0; j < numberMiddle; j++)
                matrixMiddle[i][j] = 2 * rand.nextDouble() - 1;
        
        //initialize weights between hidden/ouput nodes
        matrixOut = new double[numberMiddle][numberOutputs];
        for (int i = 0; i < numberMiddle; i++)
            for (int j = 0; j < numberOutputs; j++)
                matrixOut[i][j] = 2 * rand.nextDouble() - 1;
    }
    
    //Method trains neural network
    public void train()
    {
        //repeat iteration number of times
        for (int i = 0; i < numberIterations; i++)
            //for each training record
            for (int j = 0; j < numberRecords; j++)
            {
                //calculate input/output
                forwardCalculation(records.get(j).input);
                
                //compute errors, pudate weights/thetas
                backwardCalculation(records.get(j).output);
            }
    }
    
    //Method performs forward pass - computes input / output
//    private void forwardCalculation(double[] trainingInput)
//    {
//        //feed inputs of record
//        for (int i = 0; i < numberInputs; i++)
//            input[i] = trainingInput[i];
//        
//        //for each hidden node
//        for (int i = 0; i < numberMiddle; i++)
//        {
//            double sum = 0;
//            
//            //compute input at hidden node
//            for (int j = 0; j < numberInputs; j++)
//                sum += input[j] * matrixMiddle[j][i];
//            
//            //add theta
//            sum += thetaMiddle[i];
//            
//            //compute output at hedden node
//            middle[i] = 1/(1 + Math.exp(-sum));
//        }
//        
//        //for each output node
//        for (int i = 0; i < numberOutputs; i++)
//        {
//            double sum = 0;
//            
//            //compute input at output node
//            for (int j = 0; j < numberMiddle; j++)
//                sum += middle[j] * matrixOut[j][i];
//            
//            //add theta 
//            sum += thetaOut[i];
//            
//            //compute output at output node
//            output[i] = 1/(1 + Math.exp(-sum));
//        }
//    }
    
    private double forwardCalculation(double[] trainingInput)
    {
        for (int i = 0; i < numberInputs; i++)
        {
            input[i]  = trainingInput[i];
        }
        
        for (int i = 0; i < numberMiddle; i++)
        {
            double sum = 0;
            
            for (int j = 0; j < numberInputs; j++)
            {
                sum += input[j]*matrixMiddle[j][i];
            }
            
            sum += thetaMiddle[i];
            
            middle[i] = 1/(1 + Math.exp(-sum));
                   
        }
        
        for (int i = 0; i < numberOutputs; i++)
        {
            
        }
    }
    //Method performs backward pass - computes errors, updates weights / thetas
    private void backwardCalculation(double[] trainingOutput)
    {
        //compute error at each output node 
        for (int i = 0; i < numberOutputs; i++)
            errorOut[i] = output[i] * (1 - output[i]) * (trainingOutput[i] - output[i]);
        
        //compute error at each hidden node
        for (int i = 0; i < numberMiddle; i++)
        {
            double sum = 0; 
            
            for (int j = 0; j < numberOutputs; j++)
                sum += matrixOut[i][j] * errorOut[j];
            
            errorMiddle[i] = middle[i] * (1 - middle[i]) * sum;
        }
        
        //update weights between hidden / output nodes
        for (int i = 0; i < numberMiddle; i++)
            for (int j = 0; j < numberOutputs; j++)
                matrixOut[i][j] += rate * middle[i] * errorOut[j];
        
        //update weights between input / hidden nodes
        for (int i = 0; i < numberInputs; i++)
            for (int j = 0; j < numberMiddle; j++)
                matrixMiddle[i][j] += rate * input[i] * errorMiddle[j];
        
        //update thetas at output nodes
        for (int i = 0; i < numberOutputs; i++)
            thetaOut[i] += rate * errorOut[i];
        
        //update thetas at hedden nodes
        for (int i = 0; i < numberMiddle; i++)
            thetaMiddle[i] += rate * errorMiddle[i];
    }
    
    //Method computes output of an input
    private double[] test(double[] input)
    {
        //forward pass input
        forwardCalculation(input);
        
        //return output produced
        return output;
    }
    
    
    //Method reads inputs from input file and writes outputs to output file
    public void testData(String inputFile, String outputFile) throws IOException
    {
        Scanner inFile = new Scanner(new File(inputFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
        
        int numberRecords = inFile.nextInt();
        
        //for each record
        for (int i = 0; i < numberRecords; i++)
        {
            //read inputs
            double[] input = new double[numberInputs];
            String[] inputS = new String[numberInputs];
                for (int j = 0; j < numberInputs; j++)
                {
                    if (0 <= j && j < 3) {
                        input[j] = inFile.nextDouble();
                        input[j] = normalize(input[j], j);
                    }
                    else 
                    {
                        inputS[j] = inFile.next();
                        if (inputS[j].equals("male"))
                            input[j] = 1;
                        else if (inputS[j].equals("female"))
                            input[j] = 0;
                        else if (inputS[j].equals("single"))
                            input[j] = 0;
                        else if (inputS[j].equals("divorced"))
                            input[j] = .5;
                        else if (inputS[j].equals("married"))
                            input[j] = 1;
                    }                    
                }
                  
            //find ouput using neural network
            double[] output = test(input);
            
            //write ouput to ouput file
            for (int j = 0; j < numberOutputs; j++)
            {
                output[j] = output[j] * (1 - .25) + .25;
                outFile.print(output[j] + " ");
            }
                
            outFile.println();
        }
        
        inFile.close();
        outFile.close();
    }
    
    //method calidates the network using the data from a file
    public void validate(String validationFile) throws IOException
    {
        Scanner inFile = new Scanner(new File(validationFile));
        
        int numberRecords = inFile.nextInt();
        
        //for each record
        for (int i = 0; i < numberRecords; i++)
        {
            double[] input = new double[numberInputs];
            String[] inputS = new String[numberInputs];
            for (int j = 0; j < numberInputs; j++)
            {
                if (0 <= j && j < 3) {
                    input[j] = inFile.nextDouble();
                    input[j] = normalize(input[j], j);
                }
                else 
                {
                    inputS[j] = inFile.next();
                    if (inputS[j].equals("male"))
                        input[j] = 1;
                    else if (inputS[j].equals("female"))
                        input[j] = 0;
                    else if (inputS[j].equals("single"))
                        input[j] = 0;
                    else if (inputS[j].equals("divorced"))
                        input[j] = .5;
                    else if (inputS[j].equals("married"))
                        input[j] = 1;
                }                    

            }
            //read ouputs
            double[] actualOutput = new double[numberOutputs];
            String[] outputS = new String[numberOutputs];
            for (int j = 0; j < numberOutputs; j++){
                 outputS[j] = inFile.next();
                 if (outputS[j].equals("high"))
                     actualOutput[j] = 0;
                 else if (outputS[j].equals("low"))
                     actualOutput[j] = .25;
                 else if (outputS[j].equals("medium"))
                     actualOutput[j] = .5;
                 else if (outputS[j].equals("undetermined"))
                     actualOutput[j] = .75;
            }
         
            //read inputs
//            double[] input = new double[numberInputs];
//            for (int j = 0; j < numberInputs; j++)
//                input[j] = inFile.nextDouble();
//            
//            //read actual outputs
//            double[] actualOutput = new double[numberOutputs];
//            for (int j = 0; j < numberOutputs; j++)
//                actualOutput[j] = inFile.nextDouble();
//            
//            if (i == 11)
//                System.out.println();
            //find predicted output
            double[] predictedOutput = test(input);
            
//            for (int  j = 0; j < numberOutputs; j++) {
//                predictedOutput[j] = predictedOutput[j] * (1 - .25) + .25;
//                actualOutput[j] = predictedOutput[j] * (1 - .25) + .25;
//                
//                if (predictedOutput[j] > )
//            }

            //write actual and predicted outputs
            for (int  j = 0; j < numberOutputs; j++)
                System.out.println(actualOutput[j] + " " + predictedOutput[j]);
            
        }
        
        inFile.close();
    }
    
    //Method finds root mean square error between actual and predicted output
    private double computeError(double[] actualOutput, double[] predictedOutput)
    {
        double error = 0; 
        
        //sum of squares of errors
        for (int i = 0; i < actualOutput.length; i++)
            error += Math.pow(actualOutput[i] - predictedOutput[i], 2);
        
        //root mean square error
        return Math.sqrt(error / actualOutput.length);
    }
}
