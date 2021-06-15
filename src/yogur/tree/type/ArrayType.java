package yogur.tree.type;

import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;

public class ArrayType extends Type {
	private Type internalType;
	private int length;

	public ArrayType(Type t, int length) {
		this.internalType = t;
		this.length = length;
	}

	public Type getInternalType() {
		return internalType;
	}

	public int getLength() {
		return length;
	}

	@Override
	public int getSize() {
		return length * internalType.getSize();
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		internalType.performIdentifierAnalysis(table);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ArrayType)) {
			return false;
		}
		ArrayType other = (ArrayType)obj;
		return other.internalType.equals(internalType) && other.length == length;
	}

	@Override
	public String toString() {
		return internalType.toString() + "[" + length + "]";
	}
}
