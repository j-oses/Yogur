package yogur.tree.statement;

import yogur.codegen.PMachineOutputStream;
import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

import java.io.IOException;
import java.util.List;

public class Block extends Statement {
	private List<Statement> statements;

	private int decSize;	// Size of declarations on its level

	public Block(List<Statement> s) {
		statements = s;
	}

	public int getMaxSize() {
		int max = -1;
		for (Statement s: statements) {
			if (s instanceof Block) {
				max = Math.max(max, ((Block) s).getMaxSize());
			}
		}
		return max + decSize;
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
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		for (Statement s: statements) {
			s.performTypeAnalysis(idTable);
		}
		return null;
	}

	@Override
	public int performMemoryAnalysis(int currentOffset, int currentDepth) {
		int offset = currentOffset;
		for (Statement s: statements) {
			offset = s.performMemoryAnalysis(offset, currentDepth);
		}
		decSize = offset - currentOffset;
		return currentOffset;
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		for (Statement s: statements) {
			s.generateCode(stream);
		}
	}
}
