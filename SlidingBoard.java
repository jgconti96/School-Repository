package slidingboard;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SlidingBoard {


    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);   //scanner for user input
            System.out.println("Please enter the file name: ");
            String fileName = scanner.nextLine();

            try
            {
                Scanner read = new Scanner(new File(fileName)); //scanner for file
                int n = read.nextInt();     //read number of rows
                int m = read.nextInt();     //read number of columns
                int[][] initial = new int[n][m];    //initial board
                int[][] goal = new int[n][m];       //initial board

                for(int i = 0; i < n; i++){         //step through file to
                    for(int j = 0; j < m; j++)      //find every integer for
                    {                               //the initial board
                        initial[i][j] = read.nextInt();
                    }
                }
                
                for(int i = 0; i < n; i++){         //step through file to
                    for(int j = 0; j < m; j++)      //find every integer for
                    {                               //the initial baord
                        goal[i][j] = read.nextInt();
                    }
                }

                SlidingDepthFirst s = new SlidingDepthFirst(initial, goal, n, m);  
                long startTime = System.currentTimeMillis();
                s.solve();      //solve board
                long endTime = System.currentTimeMillis();
                System.out.println("Duration: " + (endTime - startTime) + " milliseconds using depth first");
            }
            catch (IOException e)   //catch IO exception from the file input
            {
                    System.out.println(e);
            }  
        }
}
