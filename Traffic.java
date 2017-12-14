
package traffictester;

import java.util.Random;


public class Traffic {
    private final int EMPTY = 0; 
    private final int PLUS = 1;
    private final int MINUS = 2;
    
    private int[][] array;
    private int size;
    private int iterations;
    private double density;
    private Random random;
    private TrafficDrawer drawer;
    
    //Consturtor of Traffic class
    public Traffic(int size, int iterations, double density, int seed)
    {
        this.array = new int[size][size];
        this.size = size;
        this.iterations = iterations;
        this.density = density;
        this.random = new Random(seed);
        this.drawer = new TrafficDrawer(array, size);
    }
    
    //Method runs simulation
    public void run()
    {
        //intialize vehicles
        initialize();
        
        //run iterations
        for (int n = 0; n < iterations; n++)
        {
            //draw array
            draw();
            
            //update vehicles
            for (int m = 0; m < size * size; m++)
            {
                //pick a location randomly
                int i = random.nextInt(size);
                int j = random.nextInt(size);
                
                //if down moving vehicle 
                if (array[i][j] == PLUS)
                {
                    if (array[(i + 1) % size][j] == EMPTY)
                    {
                        array[i][j] = EMPTY;
                        array[(i + 1) % size][j] = PLUS;
                    }
                    
                    else if (array[i][(j + 1) % size] == EMPTY)
                    {
                        array[i][j] = EMPTY;
                        array[i][(j + 1) % size] = PLUS;
                    } 
                    else if (j != 0)
                    {
                        if (array[i][(j - 1) % size] == EMPTY)
                        {
                            array[i][j] = EMPTY;
                            array[i][(j - 1) % size] = PLUS;
                        } 
                    }
                    
                }
                
                //if right moving vehicle
                if (array[i][j] == MINUS)
                {
                    if (array[i][(j + 1) % size] == EMPTY)
                    {
                        array[i][j] = EMPTY;
                        array[i][(j + 1) % size] = MINUS;
                    }
                    else if (array[(i + 1) % size][j] == EMPTY)
                    {
                        array[i][j] = EMPTY;
                        array[(i + 1) % size][j] = MINUS;
                    }
                    else if (j != 0 ) {
                    if (array[i][(j - 1) % size] == EMPTY)
                    {
                        array[i][j] = EMPTY;
                        array[i][(j - 1) % size] = MINUS;
                    }
                    }
                   
                    
                }
                
            }
            
        }
    }
     
    //Method initialize vehicles
    private void initialize()
    {
        //go through al locations
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (random.nextDouble() < density)
                {
                    if (random.nextDouble() < 0.5)
                        array[i][j] = PLUS;
                    else
                        array[i][j] = MINUS;
                }
                else
                    array[i][j] = EMPTY;
            }
    }
    
    //Method draws array of vehicles
    private void draw()
    {
        drawer.repaint();                           //repaint
        
        try {Thread.sleep(100);} catch(Exception e){} //pause
    }
}
