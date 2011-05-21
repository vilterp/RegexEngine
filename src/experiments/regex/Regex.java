package experiments.regex;

import experiments.regex.parser.Parser;
import experiments.regex.parser.SyntaxError;
import experiments.regex.compiler.Compiler;

public class Regex {
	
	private String pattern;
	private StateMachine machine;
	
	public Regex(String patt) throws SyntaxError {
		pattern = patt;
		machine = new StateMachine(Compiler.compile(Parser.parse(pattern)));
	}
	
	public String toString() {
		return "/" + pattern + "/";
	}
	
	public boolean matches(String input) {
		for(int i=0; i < input.length(); i++) {
			machine.accept(input.charAt(i));
		}
		boolean result = machine.isOnFinalState();
		machine.reset();
		return result;
	}
	
	public void test(String input) {
		System.out.println(input + ": " + this.matches(input));
	}
	
}
