/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelingsalesman;

import java.util.LinkedList;

/**
 *
 * @author JOHN
 */
public class TravelBreadthFirst {
        private class Path {
        private LinkedList<Integer> list;
        private int cost; 
        
        //Constructor of path class
        private Path()
        {
            list = new LinkedList<>();
            cost = 0;
        }
        
        //Copy constructor
        private Path(Path other)
        {
            list = new LinkedList<>();
            
            for (int i = 0; i < other.list.size(); i++) {
                list.addLast(other.list.get(i));
            }
            
            cost = other.cost;
        }
        
        //Method adds vertex to path
        private void add(int vertex, int weight)
        {
            list.addLast(vertex);
            cost += weight;
        }
        
        //Method finds last vertex of path
        private int last() 
        {
            return list.getLast();
        }
        
        //Method finds cost of path
        private int cost() 
        {
            return cost;
        }
        
        //Method finds length of path
        private int size()
        {
            return list.size();
        }
        
        //Method decides whether path contains a given vertex
        private boolean contains(int vertex)
        {
            for (int i = 0; i < list.size(); i ++)
            {
                if (list.get(i) == vertex)
                    return true;
            }
            return false;
        }
        
        //Method displays path and its cost
        private void display()
        {
            for (int i = 0; i < list.size(); i++)
                System.out.print(list.get(i) + " ");
            System.out.println(": " + cost);
        }
    }  
        private int size;
        private int[][] matrix;
        private int initial;
        
        //Constructor of Travel class
        public TravelBreadthFirst(int vertices, int[][] edges)
        {
            size = vertices;
            
            matrix = new int[size][size];
            for (int i = 0; i < size; i++)//initialize matrix to 0s
                for (int j = 0; j < size; j++)
                    matrix[i][j] = 0;
            
            for (int i = 0; i < edges.length; i++)//initialize matrix with 
            {                                     //all edges and their neighbors
                int u = edges[i][0];
                int v = edges[i][1];
                int weight = edges[i][2];
                matrix[u][v] = weight;
                matrix[v][u] = weight;
            }
            
            initial = edges[0][0];
        }
        
        //Method finds shortest cycle
        public void solve()
        {
            Path shortestPath = null;
            int minimumCost = Integer.MAX_VALUE;
            
            LinkedList<Path> openList = new LinkedList<>();
            LinkedList<Path> closedList = new LinkedList<>();
            int numberOfCycles = 0;
            
            Path path = new Path();
            path.add(initial, 0); //add verticy to path
            openList.addFirst(path);
            
            while(!openList.isEmpty())
            {
                path = openList.removeFirst();
                
                closedList.addFirst(path);
                
                if (complete(path)) //if path is complete cycle
                {
                    if (path.cost() < minimumCost) //if minimum is lowest
                    {
                        minimumCost = path.cost();
                        shortestPath = path;
                    }
                    numberOfCycles++; //cylce has been created
                    path.display();
                }
                else 
                {
                    LinkedList<Path> children = generate(path); //new path based off
                                                                //previous path
                    for (int i = 0; i < children.size(); i ++) {
                        openList.addLast(children.get(i));
                    }
                }
                
            }
            
            if (shortestPath == null)
                System.out.println("no solution");
            else
            {
                System.out.println("Solution: ");
                shortestPath.display();
                System.out.println("Number of states created: " + closedList.size());
                System.out.println("Number of cylces created: " + numberOfCycles);
            }
                
        }
        
        //Method generates children of path
        private LinkedList<Path> generate(Path path)
        {
            LinkedList<Path> children = new LinkedList<>();
            
            int lastVertex = path.last();
            
            for (int i = 0; i < size; i++)
            {
                if (matrix[lastVertex][i] != 0) //if matrix has neighbor
                {
                    if (i == initial) //if verticy is same as initial
                    {
                        if (path.size() == size - 1)
                        {
                            Path child = new Path(path);
                            
                            child.add(i, matrix[lastVertex][i]);
                            
                            children.addLast(child);
                        }
                    }
                    else
                    {
                        if (!path.contains(i)) //if verticy is not already used in path
                        {
                            Path child = new Path(path);

                            child.add(i, matrix[lastVertex][i]);

                            children.addLast(child);
                        }
                    }
                }
                
            }
            return children;
        }
        
        //Method decides whether a path is complete
        boolean complete(Path path)
        {
            return path.size() == size;
        }
}
