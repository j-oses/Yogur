package yogur.codegen;

/**
 * A small helper class to be able to pass integers by reference.
 */
public class IntegerReference {
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public IntegerReference(int value) {
		this.value = value;
	}

	public void increment() {
		add(1);
	}

	public void add(int sum) {
		value += sum;
	}
}
