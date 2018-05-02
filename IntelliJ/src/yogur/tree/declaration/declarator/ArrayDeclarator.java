package yogur.tree.declaration.declarator;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.identifier.ArrayIndex;
import yogur.typeidentification.MetaType;

public class ArrayDeclarator implements Declarator {
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
	public MetaType performTypeAnalysis(IdIdentifier idTable) {
		return null;
	}
}
