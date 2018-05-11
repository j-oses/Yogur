package yogur.tree.type;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeidentification.MetaType;

public class BaseType extends Type {
	public enum PredefinedType {
		Int, Bool;

		public static boolean hasValue(String str) {
			for (PredefinedType type: values()) {
				if (type.name().equals(str)) {
					return true;
				}
			}
			return false;
		}
	}

	private PredefinedType type;

	public BaseType(String name) {
		this.type = PredefinedType.valueOf(name);
	}

	public BaseType(PredefinedType preType) {
		this.type = preType;
	}

	public String getName() {
		return type.name();
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		// Do nothing
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BaseType)) {
			return false;
		}
		BaseType other = (BaseType)obj;
		return other.type.equals(type);
	}

	@Override
	public String toString() {
		return type.name();
	}
}
