package queeens;

import java.util.LinkedList;
import java.util.Random;


public class QueensProblem {
    private class Board 
    {
        private char[][] array;     //array
        private int rows;               //filled rows
        
        private Board(int n, int m) 
        {
            array = new char[n][m];         //create array
            
            for (int i = 0; i < n; i++)     //initialize array
                for (int j = 0; j < m; j++) //to blanks
                    array[i][j] = ' ';
            
            rows = 0;                   //xero filled rows
        }
    }
    
    private int n;      //number of rows
    private int m;      //number of columns
    
    public QueensProblem(int n, int m)
    {
        this.n = n; //assign number of rows
        this.m = m; //assign number of columns
    }
    
    public void solve() 
    {
	LinkedList<Board> openList = new LinkedList<>(); //open linked list
	LinkedList<Board> closedList = new LinkedList<>(); //closed linked list
        
        Random rand = new Random(); //random number generator
        rand.setSeed(System.currentTimeMillis()); //set seed as current time
        
	Board board = new Board(n, m); //initialize board
	openList.addFirst(board); //add initial board to open list
	while (!openList.isEmpty())
	{
		board = openList.removeFirst(); //remove current board from openList
		closedList.addLast(board);  //add board to closed list
                
		if (complete(board))    //if first board is solution
		{
			display(openList); //display openlist (leafs of tree)
                        System.out.println("Solution:");
                        int element = rand.nextInt(openList.size());//get random element
                        displayBoard(openList.get(element));//display random solution
                        System.out.println("Total number of solutions:" + openList.size());
			return;
		}
		else
		{
			LinkedList<Board> children = generate(board); //get all children of board
			
                        for (int i = 0; i < children.size(); i++) //add children to openList
                        {
                            Board child = children.get(i);  //for each child
                            openList.addLast(child);   
                        }           
		}
		
	}
	
	System.out.println("no solution");
    }
    
    
    //Method generates children of a board
    private LinkedList<Board> generate(Board board)
    {
        LinkedList<Board> newChildren = new LinkedList<>();
        
            for (int i = 0; i < m; i++) //generate children of board
            {
                Board newChild = copy(board);   //create copy of parent

                newChild.array[newChild.rows][i] = 'Q'; //put queen in next row

                newChild.rows += 1;

                if (check(newChild, newChild.rows - 1, i)) //if the added queen does not
                    newChildren.addLast(newChild);         //cause conflict add board to 
            }                                              //children list
        
        return newChildren; //return list of children
    }
    
    //Method checks whether queen at a given location causses conflict
    private boolean check(Board board, int x, int y)
    {
        for (int i = 0; i < n; i++) //go through all locations of board
            for (int j = 0; j < m; j++)
            {
                if (board.array[i][j] == ' ') //if empty loocation ignore
                    ;
                else if (x == i && y == j) //if same location ignore
                    ;
                else if (x == i || y == j || x + y == i + j || x - y == i - j)
                    return false; //conflict
            }
        return true; //return conflict
    }
    
    //Method checks whether baord is complete
    private boolean complete(Board board) 
    {
        return (board.rows == n);
    }
    
    //Method makes copy of board
    private Board copy(Board board)
    {
        Board result = new Board(n, m);
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                result.array[i][j] = board.array[i][j];
            
        result.rows = board.rows;
        
        return result;
    }
    
    //Method displays list of boards
    private void display(LinkedList<Board> list)
    {
       for (Board child : list) {
           displayBoard(child);
       }
  
    }
    
    //Method displays single board
    private void displayBoard(Board board) {
           for (int j = 0; j < m; j++)
                System.out.print("--");

            System.out.println();

            for (int i = 0; i < n; i++)
            {
                System.out.print("|");

                for (int j = 0; j < m; j++)
                    System.out.print(board.array[i][j] + "|");

                System.out.println();

                for (int j = 0; j < m; j++)
                    System.out.print("--");

                System.out.println();
            }
       }
}
