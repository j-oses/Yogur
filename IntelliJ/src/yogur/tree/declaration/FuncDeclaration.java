package yogur.tree.declaration;

import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.tree.statement.Block;

import java.util.List;

public class FuncDeclaration implements FunctionOrVarDeclaration {
	private BaseIdentifier identifier;
	private List<Argument> arguments;
	private Argument returnArg;		// May be null
	private Block block;

	public FuncDeclaration(String identifier, List<Argument> arguments, Block block) {
		this(identifier, arguments, null, block);
	}

	public FuncDeclaration(String identifier, List<Argument> arguments, Argument returnArg, Block block) {
		this.identifier = new BaseIdentifier(identifier);
		this.arguments = arguments;
		this.returnArg = returnArg;
		this.block = block;
	}
}
