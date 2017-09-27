/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueensproblem;

import java.util.LinkedList;

/**
 *
 * @author jconti
 */
public class Queens {
    
    private class Board 
    {
        private char[][] array;
        private int rows;
        
        private Board(int size) 
        {
            array = new char[size][size];
            
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    array[i][j] = ' ';
            
            rows = 0;
        }
    }
    
    private int size;
    
    public Queens(int size)
    {
        this.size = size;
    }
    
    public void solve() 
    {
        LinkedList<Board> list = new LinkedList<Board>();
        
        Board board = new Board(size);
        list.addFirst(board);
        
        while (!list.isEmpty())
        {
            board = list.removeFirst();
            printList(list);
            if (complete(board))
            {
                display(board);
                return;
            }
            else
            {
                LinkedList<Board> children = generate(board);
                
                for (int i = 0; i < children.size(); i++)
                    list.addFirst(children.get(i));
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
    
    private LinkedList<Board> generate(Board board)
    {
        LinkedList<Board> children = new LinkedList<Board>();
        
        for (int i = 0; i < size; i++)
        {
            Board child = copy(board);
            
            child.array[child.rows][i] = 'Q';
            
            child.rows += 1;
            
            if (check(child, child.rows - 1, i))
                children.addLast(child);
            printList(children);
        }
        return children;
    }
    
    private boolean check(Board board, int x, int y)
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
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
    
    private boolean complete(Board board) 
    {
        return (board.rows == size);
    }
    
    private Board copy(Board board)
    {
        Board result = new Board(size);
        
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result.array[i][j] = board.array[i][j];
            
        result.rows = board.rows;
        
        return result;
    }
    
    private void display(Board board)
    {
        for (int j = 0; j < size; j++)
            System.out.print("--");
        
        System.out.println();
        
        for (int i = 0; i < size; i++)
        {
            System.out.print("|");
            
            for (int j = 0; j < size; j++)
                System.out.print(board.array[i][j] + "|");
            
            System.out.println();
            
            for (int j = 0; j < size; j++)
                System.out.print("--");
            
            System.out.println();
        }
    }
}
