package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;

public class ArrayType implements Type {
	private Type internalType;
	private int size;

	public ArrayType(Type t, int size) {
		this.internalType = t;
		this.size = size;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		internalType.performIdentifierAnalysis(table);
	}
}
