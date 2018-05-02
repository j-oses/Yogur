package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;

import java.util.List;

public class Block implements Statement {
	private List<Statement> statements;

	public Block(List<Statement> s) {
		statements = s;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		performIdentifierAnalysis(table, true);
	}

	public void performIdentifierAnalysis(IdIdentifier table, boolean open) throws CompilationException {
		if (open) {
			table.openBlock();
		}
		for (Statement s: statements) {
			s.performIdentifierAnalysis(table);
		}
		table.closeBlock();
	}
}
