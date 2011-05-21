package experiments.regex.parser.ast;

public class ZeroOrOneNode implements ASTNode {
	
	private ASTNode node;
	
	public ZeroOrOneNode(ASTNode n) {
		node = n;
	}
	
	public String toString() {
		return "[" + node + "]?";
	}
	
	public ASTNode getNode() {
		return node;
	}
	
}
