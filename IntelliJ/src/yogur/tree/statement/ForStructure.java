package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.type.BaseType;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.tree.expression.Expression;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope;
import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class ForStructure extends Statement {
	private Argument argument;
	private Expression start;
	private Expression end;
	private Block block;

	public ForStructure(BaseDeclarator declarator, Expression s, Expression e, Block b) {
		this.argument = new Argument(declarator, new BaseType(BaseType.PredefinedType.Int));
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

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType argType = argument.performTypeAnalysis(idTable);
		MetaType startType = start.performTypeAnalysis(idTable);
		MetaType endType = end.performTypeAnalysis(idTable);

		if (!argType.equals(startType)) {
			throw new CompilationException("Invalid iteration limits on for loop start, with type: " + startType,
					start.getLine(), start.getColumn(), TypeAnalyzer);
		}

		if (!argType.equals(endType)) {
			throw new CompilationException("Invalid iteration limits on for loop end, with type: " + endType,
					end.getLine(), end.getColumn(), TypeAnalyzer);
		}

		block.performIdentifierAnalysis(idTable);
		return null;
	}
}
