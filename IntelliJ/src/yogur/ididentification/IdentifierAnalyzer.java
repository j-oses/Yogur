package yogur.ididentification;

import yogur.utils.CompilationException;
import yogur.tree.Program;

public class IdentifierAnalyzer {
	private Program program;
	private IdentifierTable identifier;

	public IdentifierAnalyzer(Program program) {
		this.program = program;
		this.identifier = new IdentifierTable();
	}

	public Program decorateTree() throws CompilationException {
		this.identifier = new IdentifierTable();
		program.performIdentifierAnalysis(identifier);
		return program;
	}

	public IdentifierTable getIdentifierTable() {
		return identifier;
	}
}
