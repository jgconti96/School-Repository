
package coursegenerator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class CourseGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   //scanner for user input
        System.out.println("Please enter the file name: ");
        String fileName = scanner.nextLine();

        try
        {
            Scanner read = new Scanner(new File(fileName)); //scanner for file
            int professors = read.nextInt();   //read in how many prof
            int submitted = read.nextInt();     //read in how many prefered
            int recieve = read.nextInt();     //read in how many prof will recieve
            int[][] arr = new int[professors][submitted];
            for (int i = 0; i < professors; i++)
            {
                read.nextInt(); //read which professor we are on 
                read.next(":"); //read the colon separating prof and submitted
                for (int j = 0; j < submitted; j++)
                {
                    arr[i][j] = read.nextInt();
                }
            }   
            
            Generate generator = new Generate(arr, professors, submitted, recieve);
            long startTime = System.currentTimeMillis();
            generator.solve();
            long endTime = System.currentTimeMillis();
            System.out.println("Duration: " + (endTime - startTime) + " milliseconds");
        }
        catch (IOException e)   //catch IO exception from the file input
        {
                System.out.println(e);
        }  

    }
    
}
