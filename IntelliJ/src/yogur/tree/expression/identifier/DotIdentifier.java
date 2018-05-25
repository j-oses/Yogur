package yogur.tree.expression.identifier;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.declaration.Argument;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;
import yogur.tree.type.ClassType;
import yogur.typeanalysis.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class DotIdentifier extends Identifier {
	private Expression expression;
	private String identifier;

	private Declaration declaration;

	public DotIdentifier(Expression left, String right) {
		this.expression = left;
		this.identifier = right;
	}

	@Override
	public boolean isAssignable() {
		return expression.isAssignable() && declaration instanceof Argument;
	}

	@Override
	public Declaration getDeclaration() {
		return declaration;
	}

	@Override
	public int getDepthOnStack() {
		return expression.getDepthOnStack();
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType left = expression.performTypeAnalysis();
		if (left instanceof ClassType) {
			ClassType classT = (ClassType) left;
			declaration = classT.getDeclaration().getDeclaration(identifier);
			return declaration.performTypeAnalysis();
		}

		throw new CompilationException("Trying to member access (." + identifier + ") on a non-class type " + left,
				getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		expression.performMemoryAssignment(currentOffset, nestingDepth);
	}

	@Override
	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		expression.generateCodeL(stream);
		if (declaration instanceof Argument) {
			stream.appendInstruction("inc", ((Argument)declaration).getOffset());
		}

		// For a function, we have to do nothing more. We only want to identify the class that is on the left of
		// this dot operator, and this is already done with codeL(exp).
	}
}
