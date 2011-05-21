package experiments.regex.parser.ast;

import java.util.ArrayList;

public class OptionNode implements ASTNode {
	
	private ArrayList<ASTNode> options;
	
	public OptionNode(ArrayList<ASTNode> opts) {
		options = opts;
	}
	
	public String toString() {
		return "Opt" + options;
	}
	
	public ArrayList<ASTNode> getOptions() {
		return options;
	}
	
}
