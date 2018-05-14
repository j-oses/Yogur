package yogur.tree.statement;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeidentification.MetaType;

import java.io.IOException;
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

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		for (Statement s: statements) {
			s.performMemoryAssignment(currentOffset, nestingDepth);
		}
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		for (Statement s: statements) {
			s.generateCode(stream);
		}
	}
}
