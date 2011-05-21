package experiments.regex.parser.ast;

public class ZeroOrMoreNode implements ASTNode {
	
	private ASTNode node;
	
	public ZeroOrMoreNode(ASTNode n) {
		node = n;
	}
	
	public String toString() {
		return "[" + node + "]*";
	}
	
	public ASTNode getNode() {
		return node;
	}
	
}
