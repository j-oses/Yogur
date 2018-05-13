package yogur.tree.declaration.declarator;

import yogur.codegen.IntegerReference;
import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.expression.identifier.ArrayIndex;
import yogur.tree.type.ArrayType;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class ArrayDeclarator extends Declarator {
	private Declarator declarator;
	private ArrayIndex index;

	public ArrayDeclarator(Declarator declarator, ArrayIndex index) {
		this.declarator = declarator;
		this.index = index;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
		index.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType leftType = declarator.performTypeAnalysis(idTable);
		if (leftType instanceof ArrayType) {
			if (index.returnsSingleElement()) {
				return ((ArrayType) leftType).getInternalType();
			} else {
				return leftType;
			}
		}

		throw new CompilationException("Performing [] operator on a non-array type: " + leftType,
				getLine(), getColumn(), TypeAnalyzer);
	}
}
