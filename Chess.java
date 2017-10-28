/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;


public class Chess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char[][] array = {{'R', 'K', ' ', 'B', 'R', 'B'},
                          {' ', ' ', ' ', ' ', ' ', ' '},
                          {' ', ' ', ' ', ' ', ' ', ' '},
                          {' ', ' ', ' ', ' ', ' ', ' '},
                          {' ', ' ', ' ', ' ', ' ', ' '},
                          {'b', 'r', 'r', 'k', ' ', 'b'}};
        ChessAlphaBeta cab = new ChessAlphaBeta(6, array);
        cab.play();
    }
    
}
