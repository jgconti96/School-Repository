package majoritytester;

public class MajorityTester {

    public static void main(String[] args) {
        //create majoruty object
        Majority m = new Majority(100, 100, 0.6, 46235);
        
        //run simulation
        m.run();
    }
    
}
