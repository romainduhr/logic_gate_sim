package test;
import circuits.*;

public class TestSerial{
    public static void main(String[] args){
        String fileName = args[0];
        Circuit circ = new Circuit();
        circ.load(fileName);
        circ.traceEtats();
    }
}
