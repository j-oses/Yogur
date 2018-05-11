package yogur.tree.declaration.declarator;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.tree.statement.VarDeclaration;
import yogur.tree.type.ClassType;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class DotDeclarator extends Declarator {
	private Declarator declarator;
	private String identifier;

	private VarDeclaration declaration;

	public DotDeclarator(Declarator declarator, String identifier) {
		this.declarator = declarator;
		this.identifier = identifier;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType left = declarator.performTypeAnalysis(idTable);
		if (left instanceof ClassType) {
			ClassType classT = (ClassType) left;
			Declaration tempDec = classT.getDeclaration().getDeclaration(identifier);

			if (tempDec instanceof VarDeclaration) {
				declaration = (VarDeclaration)tempDec;
			} else {
				throw new CompilationException("Trying to access a function in the left hand side of an assignment",
						getLine(), getColumn(), CompilationException.Scope.TypeAnalyzer);
			}

			return declaration.performTypeAnalysis(idTable);
		}

		throw new CompilationException("Trying to member access (." + identifier + ") on a compound type " + left,
				getLine(), getColumn(), TypeAnalyzer);
	}
}
