package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeidentification.MetaType;

import java.util.List;

public class Block extends Statement {
	private List<Statement> statements;

	public Block(List<Statement> s) {
		statements = s;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		performIdentifierAnalysis(table, true);
	}

	public void performIdentifierAnalysis(IdentifierTable table, boolean open) throws CompilationException {
		if (open) {
			table.openBlock();
		}
		for (Statement s: statements) {
			s.performIdentifierAnalysis(table);
		}
		table.closeBlock();
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		for (Statement s: statements) {
			s.performTypeAnalysis(idTable);
		}
		return null;
	}
}
