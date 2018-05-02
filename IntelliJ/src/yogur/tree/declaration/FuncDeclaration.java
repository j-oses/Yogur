package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.tree.statement.Block;

import java.util.List;

public class FuncDeclaration implements FunctionOrVarDeclaration {
	private BaseDeclarator declarator;
	private List<Argument> arguments;
	private Argument returnArg;		// May be null
	private Block block;

	public FuncDeclaration(String identifier, List<Argument> arguments, Block block) {
		this(identifier, arguments, null, block);
	}

	public FuncDeclaration(String declarator, List<Argument> arguments, Argument returnArg, Block block) {
		this.declarator = new BaseDeclarator(declarator);
		this.arguments = arguments;
		this.returnArg = returnArg;
		this.block = block;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		table.insertId(declarator.getIdentifier(), this);
		table.openBlock();
		for (Argument a: arguments) {
			a.performIdentifierAnalysis(table);
		}
		returnArg.performIdentifierAnalysis(table);
		block.performIdentifierAnalysis(table, false);
	}
}
