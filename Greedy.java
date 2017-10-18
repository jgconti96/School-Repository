
package greedytester;

import java.util.LinkedList;


public class Greedy {
    
    private class Path
    {
        private LinkedList<Integer> list;
        private int cost;
        
        //Constructor of path class
        private Path()
        {
            list = new LinkedList<Integer>();
            cost = 0;
        }
        
        //Copy constructor 
        private Path(Path other)
        {
            list = new LinkedList<Integer>();
            
            for (int i = 0; i < other.list.size(); i++)
                list.addLast(other.list.get(i));
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
        
        private int size()
        {
            return list.size();
        }
        
        //Method decides whether pathcontains a given vertex
        private boolean contains(int vertex)
        {
            for (int i = 0; i < list.size(); i ++)
                if (list.get(i) == vertex)
                    return true;
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
    
    //Constructor of Greedy class
    public Greedy(int vertices, int[][] edges)
    {
        size = vertices;
        
        matrix = new int[size][size];
        for (int i = 0;i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 0;
        
        for (int i = 0; i < edges.length; i++)
        {
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
        LinkedList<Path> list = new LinkedList<>();
        
        Path path = new Path();
        path.add(initial, 0);
        list.addFirst(path);
        
        while (!list.isEmpty())
        {
            path = list.removeFirst();
            
            if (complete(path))
            {
                if (path.cost() < minimumCost)
                {
                    minimumCost = path.cost();
                    shortestPath = path;
                }
            }
            else
            {
                LinkedList<Path> children = generate(path);
                
                if (children != null)
                {
                    int best = selectBest(children);
                    
                    list.addFirst(children.get(best));
                }
            }
        }
        
        if (shortestPath == null)
            System.out.println("no solution");
        else
            shortestPath.display();
    }
    
    //Method generates children of path
    private LinkedList<Path> generate(Path path)
    {
        LinkedList<Path> children = new LinkedList<>();
        
        int lastVertex = path.last();
        
        for (int i = 0; i < size; i++)
        {
            if (matrix[lastVertex][i] != 0)
            {
                if (i == initial)
                {
                    if (path.size() == size)
                    {
                        Path child = new Path(path);
                        
                        child.add(i, matrix[lastVertex][i]);
                        
                        children.addLast(child);
                    }
                }
                else
                {
                    if (!path.contains(i))
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
    
    //Method locates the path with minimum cost in a list of paths
    private int selectBest(LinkedList<Path> list)
    {
        int minValue = list.get(0).cost;
        int minIndex = 0;
        
        for (int i = 0; i < list.size(); i++)
        {
            int value = list.get(i).cost;
            if (value < minValue)
            {
                minValue = value;
                minIndex = i;
            }
        }
        
        return minIndex;
    }
    
    //Method decides whether a path is complete
    boolean complete(Path path)
    {
        return path.size() == size + 1;
    }
 
}
