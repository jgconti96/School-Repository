package slidingboard;

import java.util.LinkedList;


public class SlidingDepthFirst {
     private class Board {
        
        private int[][] array;  //board array
        private Board parent;   //parent board
    
        private Board(int[][] array, int n, int m) {
        this.array = new int[n][m]; //create board array
        
        for (int i = 0; i < n; i++)     //copy given array
            for (int j = 0; j < m; j++)
                this.array[i][j] = array[i][j];
        
        this.parent = null; //no parent
        }
    }
    
    private final Board initial; //initial board
    private final Board goal;   //goal board
    private final int n;    //number of rows
    private final int m;    //number of columns
    
    public SlidingDepthFirst(int[][] initial, int[][] goal, int n, int m) {
        this.initial = new Board(initial, n, m);    //create initial board
        this.goal = new Board(goal, n, m);      //create final board
        this.n = n;
        this.m = m;
    }
    
    public void solve() {
        LinkedList<Board> openList = new LinkedList<>();    //open list
        LinkedList<Board> closedList = new LinkedList<>();  //closed list
        boolean newchild = false;
        
        openList.addFirst(initial); //add initial board to open list
        
        while (!openList.isEmpty()) {   //while open list has more boards
            
            Board board = openList.getFirst();   //get first board from openlist
            
            closedList.addLast(board);  //add board to closed list
            
            newchild = false;
            
            if (complete(board))    //if board is goal
            {
                System.out.println("Final path: \n");
                displayPath(board);
                System.out.println("States explored: " + closedList.size());
                System.out.println("States waiting to be explored: " + openList.size());
                return;
            }
            else
            {
                    LinkedList<Board> children = generate(board);   //create children

                    //adds single valid child
                    for (int i = 0; i < children.size(); i++) 
                    {
                        Board child = children.get(i);  //for each child

                        if (!exists(child, openList) && !exists(child, closedList)){    
                            openList.addFirst(child);   //if it is not in open or
                            newchild = true;            //closed list then add to 
                        }                               //beginning of open list
                        if (newchild)   //if new child is found, exit
                            break;
                    } 
                    if (!newchild) {    //if no child is valid, we found leaf so remove.
                        openList.removeFirst();
                    }
            }
        }
        
        System.out.println("no solution");  //no solution if there are 
    }                                       //no boards in open list
    
    
    //Method creates children of a board
    private LinkedList<Board> generate(Board board) 
    {
        int i = 0, j = 0;
        boolean found = false;
        
        for (i = 0; i < n; i ++)    //find location of empty slot
            {                       //of board
                for (j = 0; j < m; j++){
                    if (board.array[i][j] == 0)
                    {
                        found = true;
                        break;
                    }
                }

                if (found)
                    break;
            }
        boolean north, south, east, west;
        north = i == 0 ? false : true;          //decide whether empty slot
        south = i == n - 1 ? false : true;      //has N, S, E, Q neighbors
        east = j == m - 1 ? false : true;
        west = j == 0 ? false : true;
        
        LinkedList<Board> children = new LinkedList<>();
        
        if (north) children.addLast(createChild(board, i, j, 'N')); //add N, S
        if (south) children.addLast(createChild(board, i, j, 'S')); //E, W
        if (east) children.addLast(createChild(board, i, j, 'E'));  //children
        if (west) children.addLast(createChild(board, i, j, 'W'));  //if they 
                                                                    //exist
        return children;        //return children
    }
    
    //Method creates a child of a board byb swaping empty slot in a 
    //given direction
    private Board createChild(Board board, int i , int j, char direction)
    {
        Board child = copy(board);      //create copy of board
        
        if (direction == 'N')           //swap empty slot to north
        {
            child.array[i][j] = child.array[i-1][j];
            child.array[i - 1][j] = 0;
        }
        else if (direction == 'S')      //swap empty slot to south
        {
            child.array[i][j] = child.array[i + 1][j];
            child.array[i + 1][j] = 0;
        }
        else if (direction == 'E')      //swap empty slot to East
        {
            child.array[i][j] = child.array[i][j + 1];
            child.array[i][j + 1] = 0;
        }
        else                            //swap empty slot to West
        {
            child.array[i][j] = child.array[i][j-1];
            child.array[i][j - 1] = 0;
        }
        
        child.parent = board;   //assign parent to child
        
        return child;           //return child
    }
    
    //Method creates copy of a board
    private Board copy(Board board)
    {
        return new Board(board.array, n, m);
    }
    
    //Method decides whether a board is complete 
    private boolean complete(Board board)
    {
        return identical(board, goal);  //compare baord with
    }                                   //goal
    
    //Method decides whether a board exists in a list
    private boolean exists(Board board, LinkedList<Board> list)
    {
        for (int i = 0; i < list.size(); i++)   //compare baord eith each
            if (identical(board, list.get(i)))  //element of list
                return true;    
        return false;
    }
    
    //Method decides whether two are identical
    private boolean identical(Board p, Board q)
    {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (p.array[i][j] != q.array[i][j])
                    return false;       //if there is a mismatch then false
       
        return true;                //otherwise true
    }
    
    //Method displays path from initial to current board
    private void displayPath(Board board)
    {
        LinkedList<Board> list = new LinkedList<>();
        
        Board pointer = board;  //start at current board
        int count = 0;      //count the number of boards in path
        
        while (pointer != null)     //go back towards initial board
        {
            list.addFirst(pointer); //add boards to list
            count++;        //count boards
            pointer = pointer.parent;   //keep going back
        }
        
        for (int i = 0; i < list.size(); i++)   //print boards in list
            displayBoard(list.get(i));
        System.out.println("Length of the swap: " + count); //print # of boards
    }
    
    //Method displays board
    private void displayBoard(Board board)
    {
        for (int j = 0; j < n; j++)
        {
            for (int i = 0; i < m; i ++)
                System.out.print(board.array[j][i] + " ");
            System.out.println();
        }
        System.out.println();
    }
    
}
