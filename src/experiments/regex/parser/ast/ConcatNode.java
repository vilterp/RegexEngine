package experiments.regex.parser.ast;

import java.util.ArrayList;

public class ConcatNode implements ASTNode {
	
	private ArrayList<ASTNode> segments;
	
	public ConcatNode(ArrayList<ASTNode> s) {
		segments = s;
	}
	
	public String toString() {
		return "Concat" + segments;
	}
	
	public ArrayList<ASTNode> getSegments() {
		return segments;
	}
	
}
