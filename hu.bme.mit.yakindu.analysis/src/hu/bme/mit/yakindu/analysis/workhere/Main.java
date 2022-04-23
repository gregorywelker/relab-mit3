package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
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
		
		List<State> trapStates = new ArrayList<State>();
		List<State> unnamedStates = new ArrayList<State>();
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
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
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
