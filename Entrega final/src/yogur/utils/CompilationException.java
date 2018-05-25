package yogur.utils;

import java_cup.runtime.Symbol;

import java.io.IOException;

import yogur.cup.sym;

/**
 * A custom exception to manage compiler errors.
 */
public class CompilationException extends IOException {
	/**
	 * An enum representing the scope of a CompilationException.
	 */
	public enum Scope {
		LexicalAnalyzer, SyntacticalAnalyzer, TypeAnalyzer, IdentifierIdentification;

		@Override
		public String toString() {
			return super.toString().replaceAll("([A-Z][a-z]+)", " $1").trim();
		}
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
		return lineString + scope + " error - " + super.getMessage();
	}
}
