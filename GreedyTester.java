/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greedytester;

/**
 *
 * @author jconti
 */
public class GreedyTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] edges = {{3, 4, 7},
                         {2, 1, 6},
                         {0, 3, 9},
                         {2, 4, 4},
                         {4, 0, 10},
                         {2, 3, 5},
                         {1, 3, 3},
                         {0, 1, 2}};
        Greedy g = new Greedy(5, edges);
        g.solve();
        }
    
}
