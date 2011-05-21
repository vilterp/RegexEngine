package experiments.regex.parser.ast;

public class CharNode implements ASTNode {
	
	private char c;
	
	public CharNode(char theChar) {
		c = theChar;
	}
	
	public String toString() {
		return String.valueOf(c);
	}
	
	public char getChar() {
		return c;
	}
	
}
