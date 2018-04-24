package yogur.tree.expression;

public class Constant implements Expression {
	Object value;

	public Constant(Object value) {
		this.value = value;
	}
}
