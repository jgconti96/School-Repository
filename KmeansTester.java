
package kmeanstester;

import java.io.IOException;

public class KmeansTester {

    public static void main(String[] args) throws IOException {
        //creawte clustering object
        Kmeans k = new Kmeans();
        
        //load data records
        //k.load("file1");
        k.load("file1");
        
        //set parameters
            k.setParameters(3, 100, 42333);
        
        //perform clustering
        k.cluster();
        
        //display records and cluster
        k.display("outputfile");
    }
    
}
