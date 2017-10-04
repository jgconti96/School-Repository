package furnituretransport;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class FurnitureTransport {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   //scanner for user input
        System.out.println("Please enter the file name: ");
        String fileName = scanner.nextLine();

        try
        {
        Scanner read = new Scanner(new File(fileName)); //scanner for file
        int pieces = read.nextInt();
        int[][] edges = new int[pieces + 1][3]; //i am using an extra row in order 
                                                //to print out the correct number 
                                                //for the corresponding piece
                                                //of furtiture.
        for (int i = 1; i <= pieces; i++)
            for (int j = 0; j < 3; j++)
                edges[i][j] = read.nextInt();
        
        int max = read.nextInt();
                        
        Furniture s = new Furniture(pieces, edges, max, 3);
        long startTime = System.currentTimeMillis();
        s.solve();
        long endTime = System.currentTimeMillis();
        System.out.println("Duration: " + (endTime - startTime) + " milliseconds");
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    
}
