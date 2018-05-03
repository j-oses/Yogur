package yogur.ididentification;

import yogur.error.CompilationException;
import yogur.tree.Program;

public class IdentifierAnalyzer {
	private Program program;
	private IdIdentifier identifier;

	public IdentifierAnalyzer(Program program) {
		this.program = program;
		this.identifier = new IdIdentifier();
	}

	public Program decorateTree() throws CompilationException {
		this.identifier = new IdIdentifier();
		program.performIdentifierAnalysis(identifier);
		return program;
	}

	public IdIdentifier getIdentifierTable() {
		return identifier;
	}
}
