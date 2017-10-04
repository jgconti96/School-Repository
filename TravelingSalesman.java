/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelingsalesman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author JOHN
 */
public class TravelingSalesman {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   //scanner for user input
        System.out.println("Please enter the file name: ");
        String fileName = scanner.nextLine();

        try
        {
        Scanner read = new Scanner(new File(fileName)); //scanner for file
        int verticies = read.nextInt();     //read number of rows
        int numberOfEdges = read.nextInt();     //read number of columns
        int[][] edges = new int[numberOfEdges][3];
        for (int i = 0; i < numberOfEdges; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                edges[i][j] = read.nextInt();
            }
        }
       
        TravelDepthFirst s = new TravelDepthFirst(verticies + 1, edges);
        long startTime = System.currentTimeMillis();
        s.solve();
        long endTime = System.currentTimeMillis();
        System.out.println("Duration: " + (endTime - startTime) + " milliseconds using depthFirst");
        
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
