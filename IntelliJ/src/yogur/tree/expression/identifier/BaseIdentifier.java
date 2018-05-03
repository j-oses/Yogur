package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.Declaration;
import yogur.tree.type.ArrayType;
import yogur.typeidentification.MetaType;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class BaseIdentifier extends VarIdentifier {
	private String name;

	private Declaration declaration;

	public BaseIdentifier(String name) {
		this.name = name;
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declaration = table.searchId(name, getLine(), getColumn());
	}

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		return declaration.performTypeAnalysis(idTable);
	}
}
