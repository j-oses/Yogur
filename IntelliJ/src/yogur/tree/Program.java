package yogur.tree;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeidentification.MetaType;

import java.io.IOException;
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
	public MetaType analyzeType() throws CompilationException {
		for (StatementOrDeclaration s: instructions) {
			s.performTypeAnalysis();
		}
		return null;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		for (StatementOrDeclaration s: instructions) {
			s.performMemoryAssignment(currentOffset, nestingDepth);
		}
	}

	public void generateCode(PMachineOutputStream stream) throws IOException {
		for (StatementOrDeclaration s: instructions) {
			s.generateCode(stream);
		}
	}
}
