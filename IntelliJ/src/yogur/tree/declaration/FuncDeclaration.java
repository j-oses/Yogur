package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.tree.statement.Block;
import yogur.typeidentification.FunctionType;
import yogur.typeidentification.MetaType;

import java.util.ArrayList;
import java.util.List;

public class FuncDeclaration extends AbstractTreeNode implements FunctionOrVarDeclaration, Declaration {
	private String identifier;
	private List<Argument> arguments;
	private Argument returnArg;		// May be null
	private Block block;

	public FuncDeclaration(String identifier, List<Argument> arguments, Block block) {
		this(identifier, arguments, null, block);
	}

	public FuncDeclaration(String identifier, List<Argument> arguments, Argument returnArg, Block block) {
		this.identifier = identifier;
		this.arguments = arguments;
		this.returnArg = returnArg;
		this.block = block;
	}

	@Override
	public String getName() {
		return identifier;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		performInsertIdentifierAnalysis(table);
		performBodyIdentifierAnalysis(table);
	}

	public void performInsertIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		table.insertId(identifier, this);
	}

	public void performBodyIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		table.openBlock();
		for (Argument a: arguments) {
			a.performIdentifierAnalysis(table);
		}
		if (returnArg != null) {
			returnArg.performIdentifierAnalysis(table);
		}
		block.performIdentifierAnalysis(table, false);	// Closes the block
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		List<MetaType> argTypes = new ArrayList<>();
		for (Argument a: arguments) {
			argTypes.add(a.performTypeAnalysis(idTable));
		}
		MetaType returnType = (returnArg != null) ? returnArg.performTypeAnalysis(idTable) : null;

		// We have to save the metatype before visiting the block to enable recursion.
		// If we don't do that, the program will enter on an infinite loop.
		metaType = new FunctionType(argTypes, returnType);
		block.performTypeAnalysis(idTable);
		return metaType;
	}
}
