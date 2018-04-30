package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.BaseType;
import yogur.tree.Type;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.tree.expression.Expression;

public class ForStructure implements Statement {
	private Argument argument;
	private Expression start;
	private Expression end;
	private Block block;

	public ForStructure(BaseDeclarator declarator, Expression s, Expression e, Block b) {
		this.argument = new Argument(declarator, new BaseType("Int"));
		this.start = s;
		this.end = e;
		this.block = b;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		table.openBlock();
		argument.performIdentifierAnalysis(table);
		start.performIdentifierAnalysis(table);
		end.performIdentifierAnalysis(table);
		block.performIdentifierAnalysis(table, false);
	}
}
