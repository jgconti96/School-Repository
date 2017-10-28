/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author JOHN
 */
public class ChessAlphaBeta {
     private final char EMPTY = ' ';
    private final char CROOK = 'R';
    private final char CBISHOP = 'B';
    private final char CKING = 'K';
    private final char PROOK = 'r';
    private final char PBISHOP = 'b';
    private final char PKING = 'k';
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
    
    //Constructor of AphaBeta class
    public ChessAlphaBeta(int size, char[][] board)
    {
        this.board = new Board(size);
        for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    this.board.array[i][j] = board[i][j];
        this.size = size;
        displayBoard(this.board);
    }
    
    //Method plays game
    public void play()
    {
        while (true)
        {
            board = playerMove(board);
            
            if (playerWin(board))
            {
                System.out.println("Player Win!");
                break;
            }
            
            board = computerMove(board);
            
            if (computerWin(board))
            {
                System.out.println("Computer Win!");
                break;
            }
        }
    }
    
    //method performs player move
    private Board playerMove(Board board)
    {
        System.out.println("Player move: ");
        
        Scanner scanner = new Scanner(System.in);
        int fromi = scanner.nextInt();
        int fromj = scanner.nextInt();
        int toi = scanner.nextInt();
        int toj = scanner.nextInt();
        
        board.array[toi][toj] = board.array[fromi][fromj];
        board.array[fromi][fromj] = EMPTY;
        
        displayBoard(board);
        
        return board;
    }
    
    //Method determines computer move
    private Board computerMove(Board board)
    {
        LinkedList<Board> children = new LinkedList<Board>();
        LinkedList<Board> newChildren = null;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (board.array[i][j] == CBISHOP || board.array[i][j] == CROOK || board.array[i][j] == CKING) {
                    newChildren = generate(board, board.array[i][j], i, j);
                    for (Board child : newChildren) {
                        children.addLast(child);
                    }                       
                }
                    
            }
//        for (Board child : children)
//            displayBoard(child);
        int maxIndex = 0;
        int maxValue = minmax(children.get(0), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        for (int i = 0; i < children.size(); i++)
        {
            int currentValue = minmax(children.get(i), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            
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
    
    
    private int minmax(Board board, int level, int depth, int alpha, int beta)
    {
        
        if (computerWin(board) || playerWin(board) || depth >=LIMIT) {
            int evaluation = evaluate(board);
            return evaluation;
        }
        
        else if (level == MAX)
        {
            int maxValue = Integer.MIN_VALUE;
            
            LinkedList<Board> children = new LinkedList<Board>();
            LinkedList<Board> newChildren = null;
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                {
                    if (board.array[i][j] == CBISHOP || board.array[i][j] == CROOK || board.array[i][j] == CKING){
                        newChildren = generate(board, board.array[i][j], i, j);
                        for (Board child : newChildren)
                            children.addLast(child);
                    }
                }
            
            for (int i = 0; i < children.size(); i++)
            {
                int currentValue = minmax(children.get(i), MIN, depth+1, alpha, beta);
                
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
            
            LinkedList<Board> children = new LinkedList<Board>();
            LinkedList<Board> newChildren = null;
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                {
                    if (board.array[i][j] == PBISHOP || board.array[i][j] == PROOK || board.array[i][j] == PKING){
                        newChildren = generate(board, board.array[i][j], i, j);
                        for (Board child : newChildren)
                            children.addLast(child);
                    }
                       
                }
//            System.out.println("________________Player Children________________");
//            for (Board child : children) {
//                System.out.println();
//                displayBoard(child);
//                System.out.println();
//            }
//                
//            System.out.println("_________________________________________________");
            for (int i = 0; i < children.size(); i++)
            {
                int currentValue = minmax(children.get(i), MAX, depth+1, alpha, beta);
                
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
    
    private LinkedList<Character> listOfExistingComputerPieces(Board board)
    {
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (board.array[i][j] == CBISHOP || board.array[i][j] == CROOK || board.array[i][j] == CKING)
                    list.addFirst(board.array[i][j]);
            }
        return list;
    }
    
    private LinkedList<Character> listOfExistingPlayerPieces(Board board)
    {
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (board.array[i][j] == PBISHOP || board.array[i][j] == PROOK || board.array[i][j] == PKING)
                    list.addFirst(board.array[i][j]);
            }
        return list;
    }
    
    //Method generates children of baord using a symbol
    private LinkedList<Board> generate(Board board, char symbol, int i, int j)
    {
        LinkedList<Board> children = new LinkedList<Board>();

        boolean north = true;
        boolean south = true;
        boolean east = true;
        boolean west = true;

        boolean northWest = true;
        boolean northEast = true;
        boolean southEast = true;
        boolean southWest = true;

        northEast = checkNorthEast(board, i, j);
        northWest = checkNorthWest(board, i, j);
        southEast = checkSouthEast(board, i, j);
        southWest = checkSouthWest(board, i, j);
        north = checkNorth(board, i, j);
        south = checkSouth(board, i, j);
        east = checkEast(board, i, j);
        west = checkWest(board, i, j);
        if (board.array[i][j] == PROOK || board.array[i][j] == CROOK)
        {
            if (north == true) children.addLast(createBoard(board, i, j, "N"));
            if (south == true) children.addLast(createBoard(board, i, j, "S"));
            if (east == true) children.addLast(createBoard(board, i, j, "E"));
            if (west == true) children.addLast(createBoard(board, i, j, "W"));
        }
        else if (board.array[i][j] == PBISHOP || board.array[i][j] == CBISHOP)
        {
            if (northEast == true) children.addLast(createBoard(board, i, j, "NE"));
            if (northWest == true) children.addLast(createBoard(board, i, j, "NW"));
            if (southEast == true) children.addLast(createBoard(board, i, j, "SE"));
            if (southWest == true) children.addLast(createBoard(board, i, j, "SW"));

        }
        else if (board.array[i][j] == PKING || board.array[i][j] == CKING)
        {
            if (north == true) children.addLast(createBoard(board, i, j, "N"));
            if (south == true) children.addLast(createBoard(board, i, j, "S"));
            if (northEast == true) children.addLast(createBoard(board, i, j, "NE"));
            if (northWest == true) children.addLast(createBoard(board, i, j, "NW"));
            if (southEast == true) children.addLast(createBoard(board, i, j, "SE"));
            if (southWest == true) children.addLast(createBoard(board, i, j, "SW"));
            if (east == true) children.addLast(createBoard(board, i, j, "E"));
            if (west == true) children.addLast(createBoard(board, i, j, "W"));
        }

        return children;
    }
    
    private boolean notOwnPiece(Board board, char symbol, int i, int j)
    {
        //System.out.println("i and j: " + i + " " + j);
        if (symbol == CROOK || symbol == CBISHOP || symbol == CKING)
            if (board.array[i][j] == CROOK || board.array[i][j] == CBISHOP || board.array[i][j] == CKING)
                return false;
        if (symbol == PROOK || symbol == PBISHOP || symbol == PKING)
            if (board.array[i][j] == PROOK || board.array[i][j] == PBISHOP || board.array[i][j] == PKING)
                return false;
        return true;
    }
    
    private boolean checkNorthWest(Board board, int i, int j)
    {
        int tempi = i - 1;
        int tempj = j - 1;
        if (i == 0)
            return false;
        if (j == 0)
            return false;
        if (notOwnPiece(board, board.array[i][j], tempi, tempj))
            return true;
        return false;
    }
    
    private boolean checkNorthEast(Board board, int i, int j)
    {
        int tempi = i - 1;
        int tempj = j + 1;
        if (i == 0)
            return false;
        if (j == size - 1)
            return false;
        if (notOwnPiece(board, board.array[i][j], tempi, tempj))
            return true;
        return false;
    }
    
    private boolean checkSouthWest(Board board, int i, int j)
    {
        int tempi = i + 1;
        int tempj = j - 1;
        if (i == size - 1)
            return false;
        if (j == 0)
            return false;
        if (notOwnPiece(board, board.array[i][j], tempi, tempj))
            return true;
        return false;
    }
    
    private boolean checkSouthEast(Board board, int i, int j)
    {
        int tempi = i + 1;
        int tempj = j + 1;
        if (i == size - 1)
            return false;
        if (j == size - 1)
            return false;
        if (notOwnPiece(board, board.array[i][j], tempi, tempj))
            return true;
        return false;
    }
    
    private boolean checkNorth(Board board, int i, int j)
    {
        int tempi = i - 1;
        int tempj = j;
        if (i == 0)
            return false;
        if (notOwnPiece(board, board.array[i][j], tempi, tempj))
            return true;
        return false;
    }
    
    private boolean checkEast(Board board, int i, int j)
    {
        int tempi = i;
        int tempj = j + 1;
        if (j == size - 1)
            return false;
        if (notOwnPiece(board, board.array[i][j],tempi, tempj))
            return true;
        return false;
    }
    
    private boolean checkWest(Board board, int i, int j)
    {
        int tempi = i;
        int tempj = j - 1;
        if (j == 0)
            return false;
        if (notOwnPiece(board, board.array[i][j], tempi, tempj))
            return true;
        return false;
    }
    
    private boolean checkSouth(Board board, int i, int j)
    {
        int tempi = i + 1;
        int tempj = j;
        if (i == size - 1)
            return false;
        if (notOwnPiece(board, board.array[i][j], tempi, tempj))
            return true;
        return false;
    }
    
    private Board createBoard(Board board, int fromi, int fromj, String direction)
    {
        Board child = copy(board);
        int toi = fromi, toj = fromj;
        if (direction.equals("N"))
            toi = fromi - 1;
        else if (direction.equals("NE")) 
        {
            toi = fromi - 1;
            toj = fromj + 1;
        }
        else if (direction.equals("NW")) 
        {
            toi = fromi - 1;
            toj = fromj - 1;
        }
        else if (direction.equals("S"))
            toi = fromi + 1;
        else if (direction.equals("SW")) 
        {
            toi = fromi + 1;
            toj = fromj - 1;
        }
        else if (direction.equals("SE")) 
        {
            toi = fromi + 1;
            toj = fromj + 1;
        }
        else if (direction.equals("E"))
            toj = fromj + 1;
        else if (direction.equals("W"))
            toj = fromj - 1;
        

        child.array[toi][toj] = child.array[fromi][fromj];
        child.array[fromi][fromj] = EMPTY;
        
        return child;
    }
    
    //Mehod checks whether compuyter wins
    private boolean computerWin(Board board)
    {
        return checkForWin(board, PKING);
    }
    
    private boolean playerWin(Board board)
    {
        return checkForWin(board, CKING);
    }
    
    private boolean checkForWin(Board board, char king)
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (board.array[i][j] == king)
                    return false;
            }
        return true;
    }
    
    private int evaluate(Board board)
    {
        if (computerWin(board))
            return 4*size;
        else if (playerWin(board))
            return -100*size;
        else 
            return differenceOfPieces(board);
    }
    
    private int differenceOfPieces(Board board){
        int playerPiece = 0;
        int computerPiece = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (board.array[i][j] == CBISHOP || board.array[i][j] == CROOK || board.array[i][j] == CKING)
                    computerPiece++;
                else if (board.array[i][j] == PBISHOP || board.array[i][j] == PROOK || board.array[i][j] == PKING)
                    playerPiece++;
            }
        
        return computerPiece - playerPiece;
    }
    
    private Board copy(Board board)
    {
        Board result = new Board(size);
        
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result.array[i][j] = board.array[i][j];
        return result;
    }
    
    private void displayBoard(Board board)
    {
        for (int i = 0; i < size; i++)
        {
            System.out.println("-------------------------");
            System.out.print("| ");
            for (int j = 0; j < size; j++)
                System.out.print(board.array[i][j] + " | ");
            System.out.println();
        }
    }
}
