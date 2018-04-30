package yogur.ididentification;

import yogur.error.CompilationException;
import yogur.tree.Program;

public class IdentifierAnalyzer {
	Program program;
	IdIdentifier identifier;

	public IdentifierAnalyzer(Program program) {
		this.program = program;
		this.identifier = new IdIdentifier();
	}

	public Program decorateTree() {
		this.identifier = new IdIdentifier();

		try {
			program.performIdentifierAnalysis(identifier);
		} catch (CompilationException e) {
			System.out.println(e.getMessage());
		}

		return program;
	}
}
