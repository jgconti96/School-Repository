
package shortestatester;

import java.util.LinkedList;


public class ShortestA {
    //Node class
    private class Node
    {
        private int id;
        private double gvalue;
        private double hvalue;
        private double fvalue;
        private Node parent;
        
        //Construtor of node class
        private Node(int id)
        {
            this.id = id;
            gvalue = 0;
            hvalue = heuristic(this);
            fvalue = gvalue + hvalue;
            parent = null;
        }
        
    }
    
    private double[][] matrix;
    private int size;
    private Node initial;
    private Node goal;
    
    //Constructor of ShortestA class
    public ShortestA(int vertices, double[][] edges, int initial, int goal)
    {
        size = vertices;
        
        matrix = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 0;
        
        for (int i = 0; i < edges.length; i++)
        {
            double u = edges[i][0];
            double v = edges[i][1];
            matrix[u][v] = matrix[v][u];// = edges[i][2];
        }
        
        this.initial = new Node(initial);
        this.goal = new Node(goal);
    }
    
    //Method finds shortest path
    public void solve()
    {
        LinkedList<Node> openList = new LinkedList<>();
        LinkedList<Node> closedList = new LinkedList<>();
        
        openList.addFirst(initial);
        
        while (!openList.isEmpty())
        {
            int best = selectBest(openList);
            
            Node node = openList.remove(best);
            
            closedList.addLast(node);
            
            if (complete(node))
            {
                displayPath(node);
                return;
            }
            else
            {
                LinkedList<Node> children = generate(node);

                for (int i = 0; i < children.size(); i++)
                {
                    Node child = children.get(i);
                    
                    if (!exists(child, closedList))
                    {
                        if (!exists(child, openList))
                            openList.addLast(child);
                        else
                        {
                            int index = find(child, openList);
                            if (child.fvalue < openList.get(index).fvalue)
                            {
                                openList.remove(index);
                                openList.addLast(child);
                            }
                        }
                    }

                }
            }
        }
        System.out.println("no solution");
        
    }
    
    //Method creates children of node
    private LinkedList<Node> generate(Node node)
    {
        LinkedList<Node> children = new LinkedList<Node>();
        
        for (int i = 0; i < size; i++)
        {
            if (matrix[node.id][i] != 0)
            {
                Node child = new Node(i);
                
                child.gvalue = node.gvalue + matrix[node.id][i];
                
                child.hvalue = heuristic(child);
                
                child.fvalue = child.gvalue + child.hvalue;
                
                child.parent = node;
                
                children.addLast(child);
            }
        }
        
        return children;
    }
    
    //Method computes heuristic value of node
    private double heuristic(Node node)
    {
        return 0.0;
        
    }
    
    //Method locates the node with minimum fvalue in a list of nodes
    private int selectBest(LinkedList<Node> list)
    {
        double minValue = list.get(0).fvalue;
        int minIndex = 0;
        
        for (int i = 0; i < list.size(); i++)
        {
            double value = list.get(i).fvalue;
            if (value < minValue)
            {
                minValue = value;
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    //Method decides whether a node is goal
    private boolean complete(Node node)
    {
        return identical(node, goal);
    }
    
    //Method decides whether a node is in a list
    private boolean exists(Node node, LinkedList<Node> list)
    {
        for (int i = 0; i < list.size(); i++)
            if (identical(node, list.get(i)))
                return true;
        return false;
    }
    
    //Method finds location of a node in a list
    private int find(Node node, LinkedList<Node> list)
    {
        for (int i = 0; i < list.size(); i++)
            if (identical(node, list.get(i)))
                return i;
        return -1;
    }
    
    //Method decides whether two nodes are edentical
    private boolean identical(Node p, Node q)    
    {
        return p.id == q.id;
    }
    
    //Method displays path from initial to current node
    private void displayPath(Node node)
    {
        LinkedList<Node> list = new LinkedList<>();
        
        Node pointer = node;
        
        while (pointer != null)
        {
            list.addFirst(pointer);
            
            pointer = pointer.parent;
        }
        
        for (int i = 0; i < list.size(); i++)
            displayNode(list.get(i));
        System.out.println(": " + list.getLast().gvalue);
    }
    
    //Method displays node
    private void displayNode(Node node)
    {
        System.out.print(node.id + " ");
    }
    
}
