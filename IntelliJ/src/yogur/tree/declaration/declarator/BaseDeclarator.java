package yogur.tree.declaration.declarator;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.declaration.Argument;
import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.tree.type.BaseType;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.typeidentification.MetaType;
import yogur.utils.Log;

import javax.swing.*;
import java.io.IOException;

public class BaseDeclarator extends Declarator {
	private String identifier;

	/**
	 * The declarator where the variable is declared.
	 */
	private Argument declaration;
	private int nestingDepth;

	public BaseDeclarator(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		Declaration dec = table.searchId(identifier, getLine(), getColumn());
		if (dec instanceof Argument) {
			declaration = (Argument)dec;
		} else {
			throw new CompilationException("Expected a var declaration instead of a "
					+ declaration.getDeclarationDescription(), getLine(), getColumn(),
					CompilationException.Scope.IdentificatorIdentification);
		}
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		return declaration.performTypeAnalysis(idTable);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		this.nestingDepth = nestingDepth.getValue();
		Log.debug("Assigned USE nesting depth " + this.nestingDepth + " for identifier " + identifier + " in line " + getLine());
	}

	@Override
	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		stream.appendInstruction("ldc", declaration.getOffset());
	}
}
