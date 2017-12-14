
package kmeanstester;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


public class Kmeans {
    
    private int numberRecords;
    private int numberAttributes;
    private int numberClusters;
    private int numberIterations;
    
    double col1Min = 20;
    double col1Max = 100;
    double col2Min = 20;
    double col2Max = 100;
    double col3Min = 500;
    double col3Max = 900;
    
    private double[][] records;
    private double[][] centroids;
    private int[] clusters;
    private Random rand;
    
    public Kmeans()
    {
        //parameters are zero 
        numberRecords = 0;
        numberAttributes = 0;
        numberClusters = 0;
        numberIterations = 0;
        
        //array are empty
        records = null;
        centroids = null;
        clusters = null;
        rand = null;
    }
    
    //Method loads recordsd from input file
    public void load(String inputFile) throws IOException
    {
        Scanner inFile = new Scanner(new File(inputFile));
        
        //read number of records, attributres
        numberRecords = inFile.nextInt();
        numberAttributes = inFile.nextInt();
        
        //create array of records
        records = new double[numberRecords][numberAttributes];
        
        //for each record
        for (int i = 0; i < numberRecords; i++)
        {
            //read attributes
            for (int j = 0; j< numberAttributes; j++)
                records[i][j] = inFile.nextDouble();
        }
        
        inFile.close();
        
        normalize();
    }
    
    //Method is used to normalize the input data knowing the max and mins
    public void normalize()
    {
        for (int i = 0; i < numberRecords; i++)
        {
            for (int j = 0; j < numberAttributes; j++)
            {
                if (j == 0)
                    records[i][j] = (records[i][j] - col1Min)/(col1Max - col1Min); 
                else if (j == 1)
                    records[i][j] = (records[i][j] - col2Min)/(col2Max - col2Min); 
                else 
                    records[i][j] = (records[i][j] - col3Min)/(col3Max - col3Min);
            }
        }
    }
    
    //Method is used to normalize the input data knowing the max and mins
    public void unnormalize()
    {    
        for (int i = 0; i < numberRecords; i++)
        {
            for (int j = 0; j < numberAttributes; j++)
            {
                if (j == 0)
                    records[i][j] = records[i][j]*(col1Max - col1Min) + col1Min; 
                else if (j == 1)
                    records[i][j] = records[i][j]*(col2Max - col2Min) + col2Min; 
                else 
                    records[i][j] = records[i][j]*(col3Max - col3Min) + col3Min;
            }
        }
    }
    
    //Method sets parameters of clustering
    public void setParameters(int numberClusters, int numberIterations, int seed)
    {
        //set number of clusters
        this.numberClusters = numberClusters;
        //set number of iterations
        this.numberIterations = numberIterations;
        
        //create
        this.rand = new Random(seed);
    }
    
    //Method performs kmeans clustering
    public void cluster()
    {
        //initialize clusters of recordsd
        initializeClusters();
        
        //initialize centroids of clusters
        initializeCentroids();
                
        //While stop condition is not reached
        for (int i = 0; i < numberIterations; i++)
        {
            //assign clusters to records
            assignClusters();
            
            //update centroids of clusters
            updateCentroids();
        }
    }
    

    
    //Method initializes clusters of records
    private void initializeClusters()
    {
        //create array of cluster labels 
        clusters = new int[numberRecords];
        
        //assign cluster -1 to all records
        for (int i = 0; i < numberRecords; i++)
            clusters[i] = -1;
    }
    
    //Method initializes centroids of clusters
    private void initializeCentroids()
    {
        //create array of centroids 
        centroids = new double[numberClusters][numberAttributes];
        
        //for each cluster
        for (int i = 0; i < numberClusters; i++)
        {
            //randomly pick a record
            int index = rand.nextInt(numberRecords);
            
            //use record as ccentroid
            for (int j = 0;j < numberAttributes; j++)
                centroids[i][j] = records[index][j];
        }
    }
    
    //Method assigns cluster to records
    private void assignClusters()
    {
        //go through recoreds and assign clusters to them 
        for (int i = 0; i < numberRecords; i++)
        {
            double minDistance = distance(records[i], centroids[0]);
            int minIndex = 0;
            
            //go tyhrough centroids and find closest centroid
            for (int j = 0; j < numberClusters; j++)
            {
                //find distance between record and centroid
                double distance = distance(records[i], centroids[j]);
                
                //if distance is less than minimum, update minimuym
                if (distance < minDistance)
                {
                    minDistance = distance;
                    minIndex = j;
                }
            }
            
            clusters[i] = minIndex;
        }
    }
    
    //Method updates centroids of clusters
    private void updateCentroids()
    {
        //creawte array of cluster sums and initialize
        double[][] clusterSum = new double[numberClusters][numberAttributes];
        for (int i = 0; i < numberClusters; i++)
            for (int j = 0; j < numberAttributes; j++)
                clusterSum[i][j] = 0;
        
        //create array of cluster sizes and initialize
        int[] clusterSize = new int[numberClusters];
        for (int i = 0; i < numberClusters; i++)
            clusterSize[i] = 0;
        
        //for each record 
        for (int i = 0; i < numberRecords; i++)
        {
            //find cluster of record
            int cluster = clusters[i];
            
            //add record to cluster sum
            clusterSum[cluster] = sum(clusterSum[cluster], records[i]);
            
            //increment cluster size
            clusterSize[cluster] += 1;
        }
        
        //find centroids of each clustyer 
        for (int i = 0; i < numberClusters; i++)
            if (clusterSize[i] > 0)
                centroids[i] = scale(clusterSum[i], 1.0/clusterSize[i]);
    }
    
    //Method finds distance between two records
    private double distance(double[] u, double[] v)
    {
        double sum = 0;
        
        //find euclidean distance square between two records
        for (int i = 0; i< u.length; i++)
            sum += (u[i] - v[i]) * (u[i] - v[i]);
        
        return sum;
    }
    
    //Method finds sum of two records
    private double[] sum(double[] u, double[] v)
    {
        double[] result = new double[u.length];
        
        //add corresponding attributes of records
        for (int i = 0; i < u.length; i++)
            result[i] = u[i] + v[i];
        
        return result;
    }
    
    //Method finds scaler multiple of a record
    private double[] scale(double[] u, double k)
    {
        double[] result = new double[u.length];
        
        //multiply attributes of record by scaler
        for (int i = 0; i < u.length; i++)
            result[i] = u[i] * k;
        
        return result;
    }
    
    private void printSumSquaredError()
    {
        double sum = 0;
        for (int i = 0; i < numberRecords; i++)
        {
            sum += distance(centroids[clusters[i]], records[i]);
        }
        System.out.println(sum);
    }
    
    //Method writes records and their cluster to output file
    public void display(String outputFile) throws IOException
    {
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
        
        printSumSquaredError();
        
        unnormalize();
        
        //for each record
        for (int i = 0; i < numberRecords; i++)
        {
            //write attributes of record
            for (int j = 0; j < numberAttributes; j++)
                outFile.print(records[i][j] + " ");
            
            //write cluster label
            outFile.println(clusters[i] + 1);
        }
        
        outFile.close();
    }
}
