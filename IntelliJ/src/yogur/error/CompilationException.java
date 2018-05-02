package yogur.error;

import java_cup.runtime.Symbol;

import java.text.ParseException;

import yogur.cup.CustomSymbol;
import yogur.cup.sym;

public class CompilationException extends ParseException {
	public enum Scope {
		LexicalAnalyzer, SyntacticalAnalyzer, TypeAnalyzer, IdentificatorIdentification, CodeGeneration
	}

	private Scope scope;

	public CompilationException(Symbol symbol, Scope scope) {
		super("Syntax error in or near symbol " + sym.terminalNames[symbol.sym],
				(symbol instanceof CustomSymbol) ? ((CustomSymbol) symbol).getLine() : -1);
		this.scope = scope;
	}

	public CompilationException(String s, Scope scope) {
		this(s, -1, scope);
	}

	public CompilationException(String s, int line, Scope scope) {
		super(s, line);
		this.scope = scope;
	}

	public int getErrorLine() {
		return super.getErrorOffset();
	}

	@Override
	public String getMessage() {
		String lineString = "";
		if (getErrorLine() >= 0) {
			lineString = getErrorLine() + ": ";
		}
		return lineString + scope.name() + " error - " + super.getMessage();
	}
}
