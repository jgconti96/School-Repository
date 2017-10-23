/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2p3;

import java.util.LinkedList;
import java.util.Scanner;


public class AlphaBeta {
    
    private final char EMPTY = ' ';
    private final char COMPUTER = 'X';
    private final char PLAYER = 'O';
    private final int MIN = 0;
    private final int MAX = 1;
    private final int LIMIT = 5;
    
    //Board class (inner class)
    private class Board
    {
        private char[][] array;
        
        //Constructor of Board class
        private Board(int size)
        {
            array = new char[size][size];
            
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    array[i][j] = EMPTY;
        }
    }
    
    private Board board; 
    private int size;
    
    //Constructor of AlphaBeta class
    public  AlphaBeta(int size)
    {
        this.board = new Board(size);
        this.size = size;
    }
    
    //Method plays game
    public void play()
    {
        while (true)
        {
            board = playerMove(board);
            
            if (playerWin(board))
            {
                System.out.println("Player Win");                
                break;
            }
            
            if (draw(board))
            {
                System.out.println("Draw");
                break;
            }
            
            board = computerMove(board);
            
            if (computerWin(board))
            {
                System.out.println("Computer Wins");
                break;
            }
            
            if (draw(board))
            {
                System.out.println("Draw");
            }
        }
    }
    
    //Method performs player move
    private Board playerMove(Board board)
    {
        System.out.println("Player Move");
        
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        
        board.array[i][j] = PLAYER;
        
        displayBoard(board);
        
        return board;
    }
    
    //Method determines computer move
    private Board computerMove(Board board)
    {
        LinkedList<Board> children = generate(board, COMPUTER);
        
        int maxIndex = 0;
        int maxValue = minmax(children.get(0), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        for (int i = 1; i < children.size(); i ++)
        {
            int currentValue = minmax(children.get(i), MIN, 1, Integer.MAX_VALUE, Integer.MAX_VALUE);
            
            if (currentValue > maxValue)
            {
                maxIndex = i;
                maxValue = currentValue;                
            }
            
        }
        
        Board result = children.get(maxIndex);
        
        System.out.println("Computer move:");
        
        displayBoard(result);
        
        return result;
        
    }
    
    //Method compytes minmax value of a board
    private int minmax(Board board, int level, int depth, int alpha, int beta)      
    {
        if (computerWin(board) || playerWin(board) || draw(board) || depth >= LIMIT)
            return evaluate(board);
        
        else if (level == MAX)
        {
           int maxValue = Integer.MIN_VALUE;
           
           LinkedList<Board> children = generate(board, COMPUTER);
           
           for (int i = 0; i < children.size(); i++)
           {
               int currentValue = minmax(children.get(i), MIN, depth + 1, alpha, beta);
               
               if (currentValue > maxValue)
                   maxValue = currentValue;
               if (maxValue >= beta)
                   return maxValue;
               if (maxValue > alpha)
                   alpha = maxValue;
           }
           return maxValue;
        }
        else 
        {
            int minValue = Integer.MAX_VALUE;
            
            LinkedList<Board> children = generate(board, PLAYER);
            
            for (int i = 0; i < children.size(); i++)
            {
                int currentValue = minmax(children.get(i), MAX, depth + 1, alpha, beta);
                
                if (currentValue < minValue)
                        minValue = currentValue;
                if (minValue <= alpha)
                    return minValue;
                if (minValue < beta)
                    beta = minValue;
            }
            return minValue;
        }
    }
    
    private LinkedList<Board> generate(Board board, char symbol)
    {
        LinkedList<Board> children = new LinkedList<>();
        
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.array[i][j] == EMPTY)
                {
                    Board child = copy(board);
                    child.array[i][j] = symbol;
                    children.addLast(child);
                }
        return children;
    }
    
    //Method checks whether compyter mins
    private boolean computerWin(Board board)
    {
        return check(board, COMPUTER);
    }
    
    //Method checks whether player wins
    private boolean playerWin(Board board)
    {
        return check(board, PLAYER);
    }
    
    //Method checks whether board is draw
    private boolean draw(Board board)
    {
        return full(board) && !computerWin(board) && playerWin(board);
    }
    
    //Method checks whether row column or diagnol is occupied by a symbol
    private boolean check(Board board, char symbol)
    {
        for (int i = 0; i < size; i ++)
            if (checkRow(board, i, symbol))
                return true;
        for (int i = 0; i < size; i++)
            if (checkColumn(board, i , symbol))
                return true;
        if (checkLeftDiagonal(board, symbol))
            return true;
        if (checkRightDiagonal(board, symbol))
            return false;
        return false;
    }
    
    //Method checks whether a row is occupied by a sombol
    private boolean checkRow(Board board, int i, char symbol)
    {
        for (int j = 0; j < size; j++)
            if (board.array[i][j] != symbol)
                return false;
        
        return true;
    }
    
    //Method checks whether a column si occupied by a symbol
    private boolean checkColumn(Board board, int i, char symbol)
    {
        for (int j = 0; j < size; j++)
            if (board.array[j][i] != symbol)
                return false;
        return true;
    }
    
    //Method checks whether left diagonal is occupied by a symbol
    private boolean checkLeftDiagonal(Board board, char symbol)
    {
        for (int i = 0; i < size; i++)
            if (board.array[i][i] != symbol)
                return false;
        return true;
    }
    
    //Method checks whether right diagonal is occupied by a symbol
    private boolean checkRightDiagonal(Board board, char symbol)
    {
        for (int i = 0; i < size; i++)
            if (board.array[i][size - 1 - i] != symbol)
                return false;
        return true;
    }
    
    //Method checks whether a bard is full
    private boolean full(Board board)
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
             if (board.array[i][j] == EMPTY)
                 return false;
        return true;
    }
    
    //Method evaluates a board
    private int evaluate(Board board)
    {
        if (computerWin(board))
            return 4*size;
        else if (playerWin(board))
            return -4*size;
        else if (draw(board))
            return 3*size;
        else
            return count(board, COMPUTER);
    }
    
    //Method counts possible ways a symbol can win
    private int count(Board board, char symbol)
    {
        int answer = 0;
        
        for (int i = 0; i < size; i++)
            if (testRow(board, i, symbol))
                answer++;
        
        for (int i = 0; i < size; i++)
            if (testColumn(board, i, symbol))
                answer++;
        
        if (testLeftDiagonal(board, symbol))
            answer++;
        
        if (testRightDiagonal(board, symbol))
            answer++;
        
        return answer;
    }
    
    //Method checks whether a row is occupied by a symbol or empty
    private boolean testRow(Board board, int i, char symbol)
    {
        for (int j = 0; j < size; j++)
            if (board.array[i][j] != symbol && board.array[i][j] != EMPTY)
                return false;
        
        return true;
    }
    
    private boolean testColumn(Board board, int i, char symbol)
    {
        for (int j = 0; j < size; j ++)
            if (board.array[j][i] != symbol && board.array[j][i] != EMPTY)
                return false;
        return true;
    }
    
    //Method checks whether a left diagonal is occupied by a symbol or empty
    private boolean testLeftDiagonal(Board board, char symbol)
    {
        for (int i = 0; i < size; i++)
            if (board.array[i][i] != symbol && board.array[i][i] != EMPTY)
                return false;
        return true;
    }
    
    //Method checks whether a right diagonal is occupied by a symbol or empty
    private boolean testRightDiagonal(Board board, char symbol)
    {
        for (int i = 0; i < size; i++)
        {
            if (board.array[i][size - 1 - i] != symbol && board.array[i][size - 1 - i] != EMPTY)
                return false;
        }
        return true;
    }
    
    //Method makes copy of a baord
    private Board copy(Board board)
    {
        Board result = new Board(size);
        
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result.array[i][j] = board.array[i][j];
        
        return result;
        
    }
    
    //Method displays a board
    private void displayBoard(Board board)
    {
        for (int i = 0; i < size; i++)
        {
            System.out.print("|");
            for (int j = 0; j < size; j++)
                
                System.out.print(board.array[i][j] + "|");
            System.out.println();
        }
    }
}
