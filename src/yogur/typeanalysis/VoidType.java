package yogur.typeanalysis;

public class VoidType implements MetaType {
	@Override
	public boolean equals(Object obj) {
		return (obj != null && (obj instanceof VoidType));
	}

	@Override
	public String toString() {
		return "void";
	}

	@Override
	public int getSize() {
		return 0;
	}
}
