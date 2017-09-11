/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuprogram;

public class Sudoku {
    
    protected static int[][] board = new int[8][8];
    
    public Sudoku(int[][] board) {
        this.board = board;
    }
    
    public void solve() {
        if (fill(0))
            display();
        else
                System.out.println("No solution");
    }
    
    public static boolean fill(int location) {
        int x = location / 9;
        int y = location % 9;
        int value;
        
        if (location > 80)
            return true;
        
        else if (board[x][y] != 0)
            return fill(location + 1);
        
        else
        {
            for (value = 1; value <=9; value++) {
                board[x][y] = value;
                
                if (check(y, x) && fill(location + 1))
                    return true;
            }
            
            board[x][y] = 0;
            return false;
        }
    }
    
    public static boolean check(int column , int row) {
        int rowMultiple = (row/3)*3;
        int columnMultiple = (column/3)*3;
        for (int i = 0; i < 9; i++) {
            if (i != column) {
                if (board[row][i] == board[row][column])
                    return false;
            }
            if (i != row) {
                if (board[i][column] == board[row][column])
                    return false;
            }
        }
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if ((j + rowMultiple) != row && 
                    (i + columnMultiple) != column &&
                    board[j + rowMultiple][i + columnMultiple] == board[row][column]) 
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void display()
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
