package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.TypeIdentifier;

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
		for (StatementOrDeclaration i: instructions) {
			i.performIdentifierAnalysis(table);
		}
	}

	@Override
	public void performTypeAnalysis(TypeIdentifier table) throws CompilationException {
		for (StatementOrDeclaration i : instructions) {
			i.performTypeAnalysis(table);
		}
	}
}
