package yogur.tree.declaration.declarator;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope;
import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class DotDeclarator extends Declarator {
	private Declarator declarator;
	private String identifier;

	private Declaration declaration;

	public DotDeclarator(Declarator declarator, String identifier) {
		this.declarator = declarator;
		this.identifier = identifier;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType left = declarator.performTypeAnalysis(idTable);
		if (left instanceof BaseType) {
			String name = ((BaseType) left).getName();
			declaration = idTable.searchIdOnClass(identifier, name, getLine(), getColumn());
			return declaration.performTypeAnalysis(idTable);
		}

		throw new CompilationException("Trying to member access (." + identifier + ") on a compound type " + left,
				getLine(), getColumn(), TypeAnalyzer);
	}
}
