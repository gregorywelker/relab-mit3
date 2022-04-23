package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		
		s.init();
		s.enter();
		s.runCycle();
		
		Scanner scanner = new Scanner(System.in);
		
		while(scanner.hasNext()) {
			print(s);
			switch(scanner.next()) {
			case "startModified":
				s.raiseStartModified();
				break;
			case "whiteModified":
				s.raiseWhiteModified();
				break;
			case "blackModified":
				s.raiseBlackModified();
				break;
			case "exit":
				scanner.close();
				System.exit(0);
				break;
			}
			s.runCycle();
		}
	}
	public static void print(IExampleStatemachine s) {
		System.out.println("whiteTime = " + s.getSCInterface().getWhiteTime());
		System.out.println("blackTime = " + s.getSCInterface().getBlackTime());
		System.out.println("randomVar = " + s.getSCInterface().getRandomVar());
	}
}
