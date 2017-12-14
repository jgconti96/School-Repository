
package traffictester;



public class TrafficTester {

    public static void main(String[] args) {
        
        //create a traffic object
        Traffic t = new Traffic(100, 1000, .99, 46235);
        
        //run simulation
        t.run();
    }
    
}
