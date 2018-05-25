package yogur.tree.statement;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.expression.Expression;
import yogur.typeanalysis.VoidType;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeanalysis.MetaType;

import java.io.IOException;
import java.util.List;

public class Block extends Statement {
	private List<Statement> statements;

	public Block(List<Statement> s) {
		statements = s;
	}

	@Override
	public int getMaxDepthOnStack() {
		int result = 0;
		for (Statement s: statements) {
			result = Math.max(result, s.getMaxDepthOnStack());
		}
		return result;
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
	public MetaType analyzeType() throws CompilationException {
		for (Statement s: statements) {
			MetaType type = s.performTypeAnalysis();

			if ((s instanceof Expression) && !(type == null || type instanceof VoidType)) {
				throw new CompilationException("Found statement-level expression with non-void type",
						s.getLine(), s.getColumn(), CompilationException.Scope.TypeAnalyzer);
			}
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
