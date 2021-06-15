package yogur.tree;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.expression.Expression;
import yogur.typeanalysis.VoidType;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeanalysis.MetaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program extends AbstractTreeNode {
	private List<StatementOrDeclaration> instructions;
	int staticDataLength;

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
		for (StatementOrDeclaration s: instructions) {
			s.performMemoryAssignment(currentOffset, nestingDepth);
		}

		staticDataLength = currentOffset.getValue();
	}

	public void generateCode(PMachineOutputStream stream) throws IOException {
		stream.appendInstruction("ssp", staticDataLength);
		for (StatementOrDeclaration s: instructions) {
			s.generateCode(stream);
		}
	}
}
