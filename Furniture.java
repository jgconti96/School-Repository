/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package furnituretransport;

import java.util.LinkedList;


public class Furniture {
    
    private class Path {
        private LinkedList<Integer> list;
        private int cost;
        private int value;
        
        //Constructor of path class
        private Path()
        {
            list = new LinkedList<>();
            cost = 0;
            value = 0;
        }
        
        //Copy constructor
        private Path(Path other)
        {
            list = new LinkedList<>();
            
            for (int i = 0; i < other.list.size(); i++) {
                list.addLast(other.list.get(i));
            }
            
            cost = other.cost;
            value = other.value;
        }
        
        //Method adds vertex to path
        private void add(int vertex, int weight, int value)
        {
            list.addLast(vertex);
            cost += weight;
            this.value += value;
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
        
        //Method gets the current value of the path
        private int value()
        {
            return value;
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
            for (int i = 0; i < list.size(); i++){
                System.out.print(list.get(i) + " ");                
            }
            System.out.println();
            System.out.println("Value: " + value);
            System.out.println("Cost: " + cost);
        }
    }
    
    private int size; //identifies the number of furniture pieces
    private int[][] edges;  //array of furniture piecies, its cost and value
    private int goal;   //how many pieces of furniture we want to select
    private int max;       //the max cost of what we can select

    public Furniture(int vertices, int[][] edges, int max, int goal)
    {
        size = vertices + 1;
        this.goal = goal;
        this.max = max;
        this.edges = edges;
    }
    
    //Method takes in pieces of furniture, thier cost, and thier value
    //and produces the best selection of "goal" it offers
    public void solve()
    {
        Path bestPath = null;   //stores the best path
        int maximum = 0;    //current max of best path
        
        LinkedList<Path> openList = new LinkedList<>(); //open list of paths
        LinkedList<Path> closedList = new LinkedList<>(); //open list of paths
        
       for (int i = 1; i < size; i++){ //for each possible starting piece of furniture
            Path path = new Path(); //new path for the starting piece
            path.add(i, edges[i][1], edges[i][2]); //add cost and value
            openList.addFirst(path); //add piece to openList
            while(!openList.isEmpty())
            {
                path = openList.removeFirst();
                closedList.addLast(path);
                if(complete(path))  //if path contians the most it can handle (max)
                {
                    if (path.value() > maximum) //if path contains them most value
                    {
                        maximum = path.value();
                        bestPath = path;
                    }
                }
                else
                {
                    LinkedList<Path> children = generate(path); //new children from path

                    for (int j = 0; j < children.size(); j++)
                        openList.addFirst(children.get(j));//add to openlist
                }
            }
       }
        if (bestPath == null)
            System.out.println("no solution");
        else
            bestPath.display(); //display best path
    }
    
    //Method selects new pieces of furniture based on what already exists in the path
    private LinkedList<Path> generate(Path path)
    {
        LinkedList<Path> children = new LinkedList<>();
                
        for (int i = 0; i < size - 1; i++)
        {
            int temp = i + 1;
            if (path.size() < goal && !path.contains(temp))//if path can select 
            {                                              //more furniture that 
                                                           //doesn't already exist in path
                Path child = new Path(path);
                child.add(temp, edges[temp][1], edges[temp][2]);
                if (child.cost() <= max)  //if new path is less than the max cost
                    children.addLast(child);
            }
        }
        return children;
    }
    
    //method checks if path contains enough pieces of furniture
    boolean complete(Path path)
    {
        return path.last() == goal;
    }
}
