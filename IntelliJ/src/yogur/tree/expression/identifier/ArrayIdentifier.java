package yogur.tree.expression.identifier;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;
import yogur.tree.type.ArrayType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class ArrayIdentifier extends VarIdentifier {
	private Expression leftExpression;
	private ArrayIndex index;

	int length;
	int elementSize;

	public ArrayIdentifier(Expression expression, ArrayIndex index) {
		this.leftExpression = expression;
		this.index = index;
	}

	@Override
	public boolean isAssignable() {
		return leftExpression.isAssignable();
	}

	@Override
	public Declaration getDeclaration() {
		return null;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		leftExpression.performIdentifierAnalysis(table);
		index.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType leftType = leftExpression.performTypeAnalysis();
		if (leftType instanceof ArrayType) {
			if (index.returnsSingleElement()) {
				ArrayType leftT = (ArrayType)leftType;
				MetaType internalType = leftT.getInternalType();
				length = leftT.getLength();
				elementSize = internalType.getSize();
				return internalType;
			} else {
				return leftType;
			}
		}

		throw new CompilationException("Performing [] operator on a non-array type: " + leftType,
				getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		leftExpression.performMemoryAssignment(currentOffset, nestingDepth);
		index.performMemoryAssignment(currentOffset, nestingDepth);
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		leftExpression.generateCodeR(stream);
		generateCodeI(stream);
	}

	public void generateCodeI(PMachineOutputStream stream) throws IOException {
		// TODO: We will suppose all indices are singular
		index.getOffset().generateCodeR(stream);
		stream.appendInstruction("chk", 0, length - 1);
		stream.appendInstruction("ixa", elementSize);
	}

	@Override
	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		// TODO: We will suppose all indices are singular
		leftExpression.generateCodeL(stream);
		generateCodeI(stream);
	}
}
