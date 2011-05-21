package experiments.regex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class StateChart {
	
	private HashMap<Integer,HashMap<Character,HashSet<Integer>>> connections;
	private HashSet<Integer> finalStates;
	private static final HashSet<Integer> hashSetOfNull = new HashSet<Integer>();
	static {
		hashSetOfNull.add(null);
	}
	
	public StateChart() {
		connections = new HashMap<Integer,HashMap<Character,HashSet<Integer>>>();
		finalStates = new HashSet<Integer>();
	}
	
	public String toString() {
		return "StateChart" + connections;
	}
	
	public void addConnection(int from, int to, Character input) {
		HashMap<Character,HashSet<Integer>> connectionsFromState;
		if(!connections.containsKey(from)) {
			connectionsFromState = new HashMap<Character,HashSet<Integer>>();
			connections.put(from,connectionsFromState);
		} else {
			connectionsFromState = connections.get(from);
		}
		if(!connectionsFromState.containsKey(input)) {
			connectionsFromState.put(input,new HashSet<Integer>(2));
		}
		connectionsFromState.get(input).add(to);
	}
	
	public void addBlankConnection(int from, int to) {
		addConnection(from,to,null);
	}
	
	public void addFinalState(int state) {
		finalStates.add(state);
	}
	
	public HashSet<Integer> getBlankConnections(int state) {
		return getConnectionsForInput(state,null);
	}
	
	public HashSet<Integer> getConnectionsForInput(int state, Character input) {
		if(connections.containsKey(state)) {
			if(connections.get(state).containsKey(input))
				return connections.get(state).get(input);
			else
				return new HashSet<Integer>();
		} else
			return new HashSet<Integer>(); // no connections from that state
	}
	
	public boolean notOnlyBlankConnections(int state) {
		HashMap<Character,HashSet<Integer>> connectionsFrom = connections.get(state);
		if(connectionsFrom != null) {
			Set<Character> keys = connectionsFrom.keySet();
			return !keys.equals(hashSetOfNull);
		} else
			return true;
	}
	
	public boolean isFinalState(int state) {
		return finalStates.contains(state);
	}
	
}
