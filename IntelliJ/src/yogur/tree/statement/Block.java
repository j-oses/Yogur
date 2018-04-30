package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;

import java.util.List;

public class Block implements Statement {
	private List<Statement> statementList;

	public Block(List<Statement> s) {
		statementList = s;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		performIdentifierAnalysis(table, true);
	}

	public void performIdentifierAnalysis(IdIdentifier table, boolean open) throws CompilationException {
		if (open) {
			table.openBlock();
		}
		statementList.forEach(s -> {
			try {
				s.performIdentifierAnalysis(table);
			} catch (CompilationException e) {
				e.printStackTrace();
			}
		});
		table.closeBlock();
	}
}
