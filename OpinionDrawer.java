
package majoritytester;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;


public class OpinionDrawer extends JFrame {
    private final int PLUS = 1;
    private final int MINUS = 2;
    
    private int[][] array;
    private int size;
    
    //Constructor of Opinion Drawer class
    public OpinionDrawer(int[][] array, int size)
    {
        setSize(5 * size, 5 * size);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
        
        this.array = array;
        
        this.size = size;
    }
    
    //Method paints window
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
                else
                {
                    g.setColor(Color.BLUE);
                    g.fillRect(5 * j, 5 * i, 5, 5);
                }
            }
    }
}
