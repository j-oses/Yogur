package yogur.tree.expression;

public class BinaryOperation implements Expression {
	public enum Operator {
		SUM, SUBS, PROD, DIV, MOD,
		LT, LEQ, GT, GEQ, EQ, NEQ,
		AND, OR
	}

	private Expression left;
	private Expression right;
	private Operator operator;

	public BinaryOperation(Expression l, Expression r, Operator o) {
		this.left = l;
		this.right = r;
		this.operator = o;
	}
}
