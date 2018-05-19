package yogur.tree.type;

import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;

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

	public String defaultValue() {
		if (PredefinedType.Int.equals(type)) {
			return "0";
		} else {
			return "false";
		}
	}

	@Override
	public int getSize() {
		return 1;
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
