
package shortestatester;


public class ShortestATester {


    public static void main(String[] args) {
        double[][] edges = {{1.0, 4.1, 1.2}, 
                         {2.0, 0.5, 2.1},
                         {3.0, 1.9, 4.5}, 
                         {4.0, 1.2, 0.4}, 
                         {5.0, 3.8, 1.7}};
        int[][] coordinates =   {{1, 5}, 
                                 {2, 3},
                                 {1, 3}, 
                                 {2, 4},
                                 {3, 5},
                                 {1, 4}};
    
        
        ShortestA s = new ShortestA(5, edges, coordinates, 3, 4);
        s.solve();
        
    }
        
}