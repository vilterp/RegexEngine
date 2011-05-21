package experiments.regex;

import experiments.regex.parser.SyntaxError;

public class Test {
	
	public static void main(String[] args) {
		Regex r = null;
		try {
			r = new Regex("I [don't ]?like [[Hormel |Spamco ]?spam|bacon|eggs][ for [breakfast|lunch|dinner]]?.");
		} catch (SyntaxError e) {
			e.printStackTrace();
		}
		r.test("I like Hormel spam.");
		r.test("I don't like Spamco spam.");
		r.test("I like bacon for dinner.");
		r.test("I like eggs.");
		r.test("I don't like eggs for lunch.");
	}
	
}
