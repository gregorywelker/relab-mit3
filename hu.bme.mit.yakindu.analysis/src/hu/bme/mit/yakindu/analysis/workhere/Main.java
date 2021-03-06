package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		
		/*
		---------------------------------------- TASK 2 ----------------------------------------
		List<State> trapStates = new ArrayList<State>();
		List<State> unnamedStates = new ArrayList<State>();s
		---------------------------------------- TASK 2 ----------------------------------------
		*/
		
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		
		
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;\r\n" + 
				"\r\n" + 
				"import java.io.IOException;\r\n" + 
				"import java.util.Scanner;\r\n" + 
				"\r\n" + 
				"import hu.bme.mit.yakindu.analysis.RuntimeService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.TimerService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"public class RunStatechart {\r\n" + 
				"	\r\n" + 
				"	public static void main(String[] args) throws IOException {\r\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\r\n" + 
				"		s.setTimer(new TimerService());\r\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"		\r\n" + 
				"		s.init();\r\n" + 
				"		s.enter();\r\n" + 
				"		s.runCycle();\r\n" + 
				"		\r\n" + 
				"		Scanner scanner = new Scanner(System.in);\r\n" + 
				"		\r\n" + 
				"		while(scanner.hasNext()) {\r\n" + 
				"			print(s);\r\n" + 
				"			switch(scanner.next()) {");
		
		
		while(iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof EventDefinition) {
				EventDefinition event = (EventDefinition) content;
				System.out.println(
				"			case " + '"' + event.getName() + '"' + ":\n" + 
				"				s.raise" + event.getName().substring(0,1).toUpperCase() + event.getName().substring(1) +"();"+ "\n"+
				"				break;"
				);
			}
		}
		
		System.out.println(
				"			case \"exit\":\r\n" + 
				"				scanner.close();\r\n" + 
				"				System.exit(0);\r\n" + 
				"				break;\r\n" + 
				"			}\r\n" + 
				"			s.runCycle();\n"+		
				"		}\n"+	
				"	}\n" + 
				"	public static void print(IExampleStatemachine s) {");
		
		iterator = s.eAllContents();
		
		while(iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition variable = (VariableDefinition) content;
				System.out.println( "		System.out.println(" + '"' + variable.getName() + " = " + '"' + " + s.getSCInterface()." + "get" + variable.getName().substring(0,1).toUpperCase() + variable.getName().substring(1) + "());");
			}
		}
	
		System.out.println(
				"	}\n"
				+ "}");
		
		
		
		/*
		---------------------------------------- TASK 2 ----------------------------------------
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
		
				if(state.getName().isEmpty()) {
					unnamedStates.add(state);
				}
				if(state.getOutgoingTransitions().size() <= 0) {
					trapStates.add(state);
				}else {
					for(Transition t : state.getOutgoingTransitions()) {
						System.out.println(state.getName() + "\t->\t" + t.getTarget().getName());
					}
				}
			}
		}
		
		if(trapStates.size() > 0) {
			System.out.println("Trap states:");
			for(int i = 0; i < trapStates.size(); ++i) {
				System.out.println("\t"+ i + ": Name: " + (trapStates.get(i).getName().isEmpty() ? "<UNNAMED STATE>" : trapStates.get(i).getName()));
			}
		}
		
		if(unnamedStates.size() > 0){
			System.out.println("Unnamed states exist, name suggestions:");
			for(int i = 0; i < unnamedStates.size(); ++i) {
				System.out.println("\t" + unnamedStates.get(i).getParentRegion().getName().replace(" ", "") + "State" + i);
			}
		}
		---------------------------------------- TASK 2 ----------------------------------------
		*/
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
