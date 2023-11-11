package test;
import circuits.*;
import java.util.*;
import java.io.*;

public class TestCircuits {
	
	static void test(Circuit circ){
	
		// List<String> ids = new ArrayList<String>();
		// ids = circ.nomenclature();
		// System.out.println();
		// System.out.println("Nomenclature :");
		// for(String id : ids){
		// 	System.out.println(id);
		// }
		
		System.out.println();
		System.out.println("Description :");
		circ.description();
		
		List<Interrupteur> ins = new ArrayList<Interrupteur>();
		ins = circ.getIns();
		System.out.println();
		System.out.println("GetIns :");
		for(Interrupteur in : ins){
			System.out.println(in.getId());
		}
		
		// (ins.get(0)).on();
		// (ins.get(1)).off();
		// (ins.get(2)).on();
		
		List<Vanne> outs = new ArrayList<Vanne>();
		outs = circ.getOuts();
		System.out.println();
		System.out.println("GetOuts :");
		for(Vanne out : outs){
			System.out.println(out.getId());
		}
		
// 		System.out.println();
// 		System.out.println("Probe :");
// 		circ.probe();
// 		circ.traceEtats();

		String fileName;
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nVeuillez indiquer le nom du fichier pour la sauvegarde");
		fileName = scanner.next();
		try {
			circ.save(fileName);
		}catch (IOException e){
			System.err.println("Erreur d'accès au fichier");
		}
		
// 		System.out.println();
// 		System.out.println("Reset Probe :");
// 		circ.resetSondes();
// 		circ.traceEtats();
// 		
// 		System.out.println();
// 		System.out.println("Unprobe :");
// 		circ.unProbe();
// 		circ.traceEtats();
	}
	
	
	public static void main(String[] args){
		//Construction

		Composant composants[] = new Composant[7];
		
		composants[0] = new Interrupteur();			//interrupteur i1
		composants[1] = new Interrupteur();			//interrupteur i2
		composants[2] = new Interrupteur();			//interrupteur de sécu
		composants[3] = new Or();
		composants[4] = new Not();
		composants[5] = new And();
		composants[6] = new Vanne();
		
		//Connexions
		// ((Interrupteur)composants[0]).on();
		// ((Interrupteur)composants[1]).off();
		// ((Interrupteur)composants[2]).on();
		
		((Porte2Entrees)composants[3]).setIn1(composants[0]);
		// ((Porte2Entrees)composants[3]).setIn1(new LazySonde((Porte2Entrees)composants[3], "in1"));
		((Porte2Entrees)composants[3]).setIn2(composants[1]);
		
		((Not)composants[4]).setIn(composants[2]);
		
		((Porte2Entrees)composants[5]).setIn1(composants[3]);
		((Porte2Entrees)composants[5]).setIn2(composants[4]);
		// ((Porte2Entrees)composants[5]).setIn2(new LazySonde((Porte2Entrees)composants[5], "in2"));
		
		((Vanne)composants[6]).setIn(composants[5]);
		// ((Vanne)composants[6]).setIn(new Sonde((Vanne)composants[6], "in"));
		
		Circuit circuit = new Circuit("Circuit 1", composants);
		
		//Affichage
		
		test(circuit);
		
		System.out.println("Au revoir!");
	}
}
