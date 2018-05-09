package yogur.tree.statement;

import yogur.codegen.PMachineOutputStream;
import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.declarator.Declarator;
import yogur.tree.expression.Expression;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class Assignment extends Statement {
	private Declarator declarator;
	private Expression expression;

	public Assignment(Declarator declarator, Expression e) {
		this.declarator = declarator;
		this.expression = e;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType decType = declarator.performTypeAnalysis(idTable);
		MetaType expType = expression.performTypeAnalysis(idTable);

		if (!expType.equals(decType)) {
			throw new CompilationException("Could not assign result of type " + expType
					+ " to variable of type " + decType, getLine(), getColumn(),
					TypeAnalyzer);
		}

		return null;
	}

	@Override
	public int performMemoryAnalysis(int currentOffset, int currentDepth) {
		expression.performMemoryAnalysis(currentOffset, currentDepth);
		return currentOffset;
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		declarator.generateCodeL(stream);
		expression.generateCodeR(stream);
		stream.appendInstruction("sto");
	}
}
