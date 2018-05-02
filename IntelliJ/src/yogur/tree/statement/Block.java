package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

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

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		for (Statement s: statements) {
			s.performTypeAnalysis(idTable);
		}
		return null;
	}
}
