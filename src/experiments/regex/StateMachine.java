package experiments.regex;
import java.util.HashSet;

public class StateMachine {
	
	private StateChart stateChart;
	private HashSet<Integer> currentStates;
	
	public StateMachine(StateChart chart) {
		stateChart = chart;
		currentStates = new HashSet<Integer>();
		addCurrentState(0);
	}
	
	public void accept(char input) {
		HashSet<Integer> newStates = new HashSet<Integer>();
		for(int state: currentStates)
			newStates.addAll(stateChart.getConnectionsForInput(state, input));
		currentStates.clear();
		for(int state: newStates)
			addCurrentState(state);
	}
	
	private void addCurrentState(int state) {
		for(int newState: stateChart.getBlankConnections(state))
			addCurrentState(newState);
		if(stateChart.notOnlyBlankConnections(state))
			currentStates.add(state);
	}
	
	public boolean isOnFinalState() {
		for(int state: currentStates)
			if(stateChart.isFinalState(state))
				return true;
		return false;
	}
	
	public void reset() {
		currentStates.clear();
		addCurrentState(0);
	}
	
}
