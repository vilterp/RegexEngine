package experiments.regex.compiler;

import java.util.ArrayList;

import experiments.regex.StateChart;
import experiments.regex.parser.ast.*;

public class Compiler {
	
	public static StateChart compile(ASTNode parsedPattern) {
		return new Compiler(parsedPattern).compile();
	}
	
	private ASTNode ast;
	private StateChart chart;
	
	public Compiler(ASTNode parsedPattern) {
		ast = parsedPattern;
		chart = new StateChart();
	}
	
	public StateChart compile() {
		chart.addFinalState(compile(ast,0));
		return chart;
	}
	
	private int compile(ASTNode tree, int startId) {
		if(tree instanceof ConcatNode)
			return compileConcat((ConcatNode)tree,startId);
		else if(tree instanceof OptionNode)
			return compileOpt((OptionNode)tree,startId);
		else if(tree instanceof CharNode)
			return compileChar((CharNode)tree,startId);
		else if(tree instanceof ZeroOrOneNode)
			return compileZoO((ZeroOrOneNode)tree,startId);
		else {
			throw new UnsupportedOperationException("can't compile " + 
									tree.getClass().getName() + "'s yet");
		}
	}

	private int compileZoO(ZeroOrOneNode tree, int startId) {
		int endId = compile(tree.getNode(),startId);
		chart.addBlankConnection(startId,endId);
		return endId;
	}

	private int compileChar(CharNode tree, int startId) {
		chart.addConnection(startId,startId+1,tree.getChar());
		return startId+1;
	}
	
	private int compileOpt(OptionNode tree, int startId) {
		ArrayList<Integer> startIds = new ArrayList<Integer>();
		ArrayList<Integer> endIds = new ArrayList<Integer>();
		int endId = startId;
		for(ASTNode option: tree.getOptions()) {
			int nextStartId = endId+1;
			startIds.add(nextStartId);
			endId = compile(option,nextStartId);
			endIds.add(endId);
		}
		int overallEnd = endIds.get(endIds.size()-1) + 1;
		for(int i=0; i < startIds.size(); i++) {
			chart.addBlankConnection(startId,startIds.get(i));
			chart.addBlankConnection(endIds.get(i),overallEnd);
		}
		return overallEnd;
	}
	
	private int compileConcat(ConcatNode tree, int startId) {
		int endId = startId;
		for(ASTNode segment: tree.getSegments())
			endId = compile(segment,endId);
		return endId;
	}
	
}
