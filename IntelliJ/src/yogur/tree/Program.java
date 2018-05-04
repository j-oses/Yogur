package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

import java.util.ArrayList;
import java.util.List;

public class Program extends AbstractTreeNode {
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
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		for (StatementOrDeclaration s: instructions) {
			s.performTypeAnalysis(idTable);
		}
		return null;
	}
}
