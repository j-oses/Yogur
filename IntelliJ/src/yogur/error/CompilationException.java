package yogur.error;

import java_cup.runtime.Symbol;

import java.io.IOException;

import yogur.cup.sym;

public class CompilationException extends IOException {
	public enum Scope {
		LexicalAnalyzer, SyntacticalAnalyzer, TypeAnalyzer, IdentificatorIdentification, CodeGeneration
	}

	private Scope scope;

	private int line;
	private int column;

	public CompilationException(Symbol symbol, Scope scope) {
		this("Syntax error in or near symbol " + sym.terminalNames[symbol.sym],
				symbol.left, symbol.right, scope);
	}

	public CompilationException(String s, Scope scope) {
		this(s, -1, -1, scope);
	}

	public CompilationException(String s, int line, int column, Scope scope) {
		super(s);
		this.line = line;
		this.column = column;
		this.scope = scope;
	}

	public int getErrorLine() {
		return line;
	}

	public int getErrorColumn() {
		return column;
	}

	@Override
	public String getMessage() {
		String lineString = "";
		if (getErrorLine() >= 0) {
			lineString = getErrorLine() + ":" + getErrorColumn() + " | ";
		}
		return lineString + scope.name() + " error - " + super.getMessage();
	}
}
