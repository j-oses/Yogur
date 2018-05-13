package yogur.tree;

import yogur.codegen.IntegerReference;
import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
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
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		for (StatementOrDeclaration i: instructions) {
			i.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		for (StatementOrDeclaration s: instructions) {
			s.performTypeAnalysis(idTable);
		}
		return null;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset) {
		for (StatementOrDeclaration s: instructions) {
			s.performMemoryAssignment(currentOffset);
		}
	}
}
