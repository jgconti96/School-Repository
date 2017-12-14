
package majoritytester;

//Program simu7lates opinion dynamixs based on majority rule

import java.util.Random;

public class Majority {
    private final int PLUS = 1;
    private final int MINUS = 2;
    
    private int[][] array;      //array of opinion
    private int size;           //size of array
    private int iterations;     //number of iiterations
    private double density;     // initial density of plus opinion
    private Random random ;     //random number generator
    private OpinionDrawer drawer;//drawing object
    
    //Constructor of Majority class
    public Majority(int size, int iterations, double density, int seed)
    {
        this.array = new int[size][size];
        this.size = size;
        this.iterations = iterations;
        this.density = density;
        this.random = new Random(seed);
        this.drawer = new OpinionDrawer(array, size);
    }
    
    //Method runs simulation
    public void run()
    {
        //initial opinions 
        initialize();
        
        //Get inital percentage
        int initialBlue = numberOfBlue(array);
        
        int count = 0;
        
        //run iterations
        for (int n = 0; n < iterations; n++)
        {
            //draw array
            draw();
            
            //update population
            for (int m = 0; m < size*size; m++)
            {
                //pick an agent randomly 
                int i = random.nextInt(size);
                int j = random.nextInt(size);
                
                
                if (array[i][j] == 1)
                    array[i][j] = majority(i, j);
                else 
                {
                    if ((double) count / initialBlue < 0.11) {
                        array[i][j] = majority(i, j);
                        count++;
                    }
                        
                }
                 
                
            }
            displayVotingStatus(array);
        }
    }
    
    //Method used to get the initial percentage of blue voters vs red
    private int numberOfBlue(int[][] array)
    {
        int count = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if(array[i][j] == 2)
                    count++;
            }
        return count;
    }
    
    //Method used to count number of reds and blues
    private void displayVotingStatus(int[][] array)
    {
        int countRed = 0;
        int countBlue = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (array[i][j] == 1)
                    countRed++;
                else if (array[i][j] == 2)
                    countBlue++;
            }
        System.out.println("Red: " + countRed);
        System.out.println("Blue: " + countBlue);
    }
    
    //Method initializes opinions
    private void initialize()
    {
        //go through all agents
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                //assign plus/minus opinion
                if (random.nextDouble() < density)
                    array[i][j] = PLUS;
                else
                    array[i][j] = MINUS;
            }
        }
    }
    
    //Method finds the mojority opinion of a neighborhood
    private int majority(int i, int j)
    {
        int plus = 0;
        int minus = 0;
        
        if (array[i][j] == PLUS)
            plus++;
        else 
            minus++;
        
        if (array[(i + 1) % size][j] == PLUS)
            plus += 1;
        else 
            minus += 1;
        
        if (array[(i - 1 + size) % size][j] == PLUS)
            plus += 1;
        else 
            minus += 1;
        
        if (array[i][(j + 1) % size] == PLUS)
            plus += 1;
        else
            minus += 1;
        
        if (array[i][(j - 1 + size) % size] == PLUS)
            plus += 1;
        else
            minus += 1;
        

            
        if (plus > minus)
            return PLUS;
        else if (plus < minus)
            return MINUS;
        else
        {
            if (random.nextDouble() < 0.5)
                return PLUS;
            else 
                return MINUS;
        }
    }
    
    //Method draws array of opinions
    private void draw()
    {
        drawer.repaint();       //repaint array
        
        try {Thread.sleep(2000);} catch(Exception e){} //pause
    }
}
