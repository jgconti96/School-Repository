
package queeens;

import static java.util.Collections.list;
import java.util.LinkedList;

/**
 *
 * @author JOHN
 */
public class QueensProblem {
    private class Board 
    {
        private char[][] array;
        private int rows;
        
        private Board(int n, int m) 
        {
            array = new char[n][m];
            
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    array[i][j] = ' ';
            
            rows = 0;
        }
    }
    
    private int n;
    private int m;
    
    public QueensProblem(int n, int m)
    {
        this.n = n;
        this.m = m;
    }
    
    public void solve() 
    {
        LinkedList<Board> list = new LinkedList<Board>();
        
        Board board = new Board(n, m);
        list.addFirst(board);
        LinkedList<Board> children = generate(list);
        while (!list.isEmpty())
        {
            //board = list.removeFirst();
            //printList(list);
            if (complete(children))
            {
                display(board);
                return;
            }
            else
            {
                LinkedList<Board> newChildren = generate(children);
                children.addAll(newChildren);
                
                for (int i = 0; i < newChildren.size(); i++)
                    list.addFirst(newChildren.get(i));
                printList(list);
            }
        }
        
        System.out.println("no solution");
    }
    
    
    private void printList(LinkedList<Board> list) 
    {
        System.out.println("Start List");
        for (Board child : list )
        {
            display(child);
        }
        System.out.println("End List");
    }
    
    private LinkedList<Board> generate(LinkedList<Board> children)
    {
        LinkedList<Board> newChildren = new LinkedList<Board>();
        
        for (Board child : children) {
            for (int i = 0; i < m; i++)
            {
                Board newChild = copy(child);

                newChild.array[newChild.rows][i] = 'Q';

                newChild.rows += 1;

                if (check(newChild, newChild.rows - 1, i))
                    newChildren.addLast(newChild);
                //printList(newChildren);
                //System.out.println("Generate");
            }
        }
        
        return newChildren;
    }
    
    private boolean check(Board board, int x, int y)
    {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
            {
                if (board.array[i][j] == ' ')
                    ;
                else if (x == i && y == j)
                    ;
                else if (x == i || y == j || x + y == i + j || x - y == i - j)
                    return false;
            }
        return true;
    }
    
    private boolean complete(LinkedList<Board> children) 
    {
        boolean complete = false;
        for (Board child : children )
        {
            if (child.rows == n)
                complete = true;
            else
                complete = false;
        }    
        return complete;
    }
    
    private Board copy(Board board)
    {
        Board result = new Board(n, m);
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                result.array[i][j] = board.array[i][j];
            
        result.rows = board.rows;
        
        return result;
    }
    
    private void display(Board board)
    {
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
