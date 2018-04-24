package yogur.error;

import java.text.ParseException;

public class CompilationException extends ParseException {
	public enum Scope {
		LexicalAnalyzer, SyntacticalAnalyzer, TypeAnalyzer, IdentificatorIdentification, CodeGeneration
	}

	private Scope scope;

	public CompilationException(String s, int line, Scope scope) {
		super(s, line);
		this.scope = scope;
	}

	public int getErrorLine() {
		return super.getErrorOffset();
	}

	@Override
	public String getMessage() {
		return getErrorLine() + ":" + scope.name() + " error - " + super.getMessage();
	}
}
