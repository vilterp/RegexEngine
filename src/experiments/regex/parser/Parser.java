package experiments.regex.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

import experiments.regex.parser.ast.*;

public class Parser {
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		while(true) {
			try {
				System.out.print("> ");
				System.out.println(new Parser(s.nextLine()).parse());
			} catch (SyntaxError e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ASTNode parse(String input) throws SyntaxError {
		return new Parser(input).parse();
	}
	
	private String input;
	private Queue<Token> tokens;
	private Stack<ASTNode> stack;
	
	public Parser(String in) {
		input = in;
		tokens = null;
		stack = new Stack<ASTNode>();
	}
	
	public ASTNode parse() throws SyntaxError {
		tokens = tokenize(input);
		if(parseConcat() && stack.size() == 1 && tokens.isEmpty()) {
			return stack.pop();
		} else
			throw new SyntaxError("Invalid syntax");
	}
	
	private Queue<Token> tokenize(String in) throws SyntaxError {
		Stack<Character> symmetry = new Stack<Character>();
		LinkedList<Token> tokens = new LinkedList<Token>();
		boolean afterBackslash = false;
		for(int i=0; i < in.length(); i++) {
			char next = in.charAt(i);
			switch(next) {
			case '\\':
				if(afterBackslash) {
					tokens.add(new Token(next,TokenType.CHAR,i));
					afterBackslash = false;
				} else
					afterBackslash = true;
				break;
			case '[':
				if(afterBackslash) {
					tokens.add(new Token(next,TokenType.CHAR,i));
					afterBackslash = false;
				} else {
					tokens.add(new Token(next,TokenType.SYMBOL,i));
					symmetry.push('[');
				}
				break;
			case ']':
				if(afterBackslash) {
					tokens.add(new Token(next,TokenType.CHAR,i));
					afterBackslash = false;
				} else {
					if(symmetry.peek() == '[') {
						symmetry.pop();
						tokens.add(new Token(next,TokenType.SYMBOL,i));
					} else {
						throw new SyntaxError("expected ], found " + symmetry.peek());
					}
				}
				break;
			case '|':
				if(afterBackslash) {
					tokens.add(new Token(next,TokenType.CHAR,i));
					afterBackslash = false;
				} else {
					tokens.add(new Token(next,TokenType.SYMBOL,i));
				}
				break;
			case '*':
				if(afterBackslash) {
					tokens.add(new Token(next,TokenType.CHAR,i));
					afterBackslash = false;
				} else {
					tokens.add(new Token(next,TokenType.SYMBOL,i));
				}
				break;
			case '?':
				if(afterBackslash) {
					tokens.add(new Token(next,TokenType.CHAR,i));
					afterBackslash = false;
				} else {
					tokens.add(new Token(next,TokenType.SYMBOL,i));
				}
				break;
			case 'n':
				if(afterBackslash) {
					tokens.add(new Token('\n',TokenType.CHAR,i));
					afterBackslash = false;
				} else
					tokens.add(new Token(next,TokenType.CHAR,i));
				break;
			case 't':
				if(afterBackslash) {
					tokens.add(new Token('\t',TokenType.CHAR,i));
					afterBackslash = false;
				} else
					tokens.add(new Token(next,TokenType.CHAR,i));
				break;
			default:
				if(afterBackslash)
					throw new SyntaxError("\\" + next + " is not a valid escape sequence " +
											"(only \\n and \\t)");
				else
					tokens.add(new Token(next,TokenType.CHAR,i));
			}
		}
		return tokens;
	}
	
	private boolean parseConcat() throws SyntaxError {
		ArrayList<ASTNode> segments = new ArrayList<ASTNode>();
		while(parseZeroOrMore())
			segments.add(stack.pop());
		stack.push(new ConcatNode(segments));
		return true;
	}
	
	private boolean parseZeroOrMore() throws SyntaxError {
		if(parseZeroOrOne()) {
			if(getSymbol('*')) {
				if(stack.peek() instanceof ZeroOrOneNode)
					throw new SyntaxError("can't nest a ? inside of a *");
				else {
					stack.push(new ZeroOrMoreNode(stack.pop()));
					return true;
				}
			} else
				return true;
		} else
			return false;
	}
	
	private boolean parseZeroOrOne() throws SyntaxError {
		if(parseSegment()) {
			if(getSymbol('?'))
				stack.push(new ZeroOrOneNode(stack.pop()));
			return true;
		} else
			return false;
	}

	private boolean parseSegment() throws SyntaxError {
		return parseOption() || parseChar();
	}
	
	private boolean parseOption() throws SyntaxError {
		if(getSymbol('[')) {
			ArrayList<ASTNode> options = new ArrayList<ASTNode>();
			if(getSymbol(']'))
				throw new SyntaxError("empty options");
			while(parseConcat()) {
				options.add(stack.pop());
				if(!getSymbol('|')) {
					if(getSymbol(']')) {
						stack.push(new OptionNode(options));
						return true;
					} else
						throw new SyntaxError("unclosed [");
				}
			}
		} else
			return false;
		return false;
	}
	
	private boolean parseChar() {
		Character c = getChar();
		if(c != null) {
			stack.push(new CharNode(c));
			return true;
		} else {
			return false;
		}
	}
	
	private Character getChar() {
		if(moreCode() && tokens.peek().getType() == TokenType.CHAR) {
			return tokens.poll().getValue();
		} else
			return null;
	}
	
	private boolean getSymbol(char val) {
		if(moreCode() && tokens.peek().getType() == TokenType.SYMBOL
				&& tokens.peek().getValue() == val) {
			tokens.poll();
			return true;
		} else
			return false;
	}
	
	private boolean moreCode() {
		return !tokens.isEmpty();
	}
	
}
