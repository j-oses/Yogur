package yogur.tree.declaration.declarator;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.identifier.ArrayIndex;
import yogur.tree.type.ArrayType;
import yogur.tree.type.Type;
import yogur.typeidentification.MetaType;

public class ArrayDeclarator extends Declarator {
	private Declarator declarator;
	private ArrayIndex index;

	public ArrayDeclarator(Declarator declarator, ArrayIndex index) {
		this.declarator = declarator;
		this.index = index;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
		index.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		MetaType leftType = declarator.performTypeAnalysis(idTable);
		if (leftType instanceof ArrayType) {
			if (index.returnsSingleElement()) {
				return ((ArrayType) leftType).getInternalType();
			} else {
				return leftType;
			}
		}

		throw new CompilationException("Performing [] operator on a non-array type: " + leftType,
				getLine(), getColumn(), CompilationException.Scope.TypeAnalyzer);
	}
}
