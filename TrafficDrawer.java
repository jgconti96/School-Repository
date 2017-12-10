
package traffictester;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

public class TrafficDrawer extends JFrame {
    private final int PLUS = 1;
    private final int MINUS = 2;
    
    private int[][] array;
    private int size;
    
    //Constructor of Traffic Drawer class
    public TrafficDrawer(int[][] array, int size)
    {
        setSize(5 * size, 5 * size);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
        
        this.array = array;
        
        this.size = size;
    }
    
    public void paint(Graphics g)
    {
        //go through array 
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (array[i][j] == PLUS)
                {
                    g.setColor(Color.RED);
                    g.fillRect(5 * j, 5 * i, 5, 5);
                }
                else if (array[i][j] == MINUS)
                {
                    g.setColor(Color.GREEN);
                    g.fillRect(5 * j, 5 * i, 5, 5);
                }
                else
                {
                    g.setColor(Color.BLACK);
                    g.fillRect(5 * j, 5 * i, 5, 5);
                }
            }
    }
    
}
