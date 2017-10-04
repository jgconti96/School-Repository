
package queeens;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Queens {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   //scanner for user input
        System.out.println("Please enter the file name: ");
        String fileName = scanner.nextLine();

        try
        {
            Scanner read = new Scanner(new File(fileName)); //scanner for file
            int n = read.nextInt();     //read number of rows
            int m = read.nextInt();     //read number of columns

            QueensProblem queens = new QueensProblem(n, m);
            long startTime = System.currentTimeMillis();
            queens.solve();
            long endTime = System.currentTimeMillis();
            System.out.println("Duration: " + (endTime - startTime) + " milliseconds");
        }
        catch (IOException e)   //catch IO exception from the file input
        {
                System.out.println(e);
        }  
    }
}
