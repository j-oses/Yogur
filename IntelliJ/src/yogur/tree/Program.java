package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;

import java.util.ArrayList;
import java.util.List;

public class Program implements AbstractTreeNode {
	private List<StatementOrDeclaration> instructions;

	public Program() {
		instructions = new ArrayList<>();
	}

	public Program(StatementOrDeclaration s) {
		instructions = new ArrayList<>();
		instructions.add(s);
	}

	public Program(Program p, StatementOrDeclaration s) {
		instructions = p.instructions;
		instructions.add(s);
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		instructions.forEach(sta -> {
			try {
				sta.performIdentifierAnalysis(table);
			} catch (CompilationException e) {
				e.printStackTrace();
			}
		});
	}
}
