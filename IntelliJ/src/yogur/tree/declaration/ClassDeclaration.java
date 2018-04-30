package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.Type;

import java.util.List;

public class ClassDeclaration implements Declaration {
	private Type type;
	private List<FunctionOrVarDeclaration> declarations;

	public ClassDeclaration(Type type, List<FunctionOrVarDeclaration> declarations) {
		this.type = type;
		this.declarations = declarations;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		type.performIdentifierAnalysis(table);
		declarations.forEach(declaration -> {
			try {
				declaration.performIdentifierAnalysis(table);
			} catch (CompilationException e) {
				e.printStackTrace();
			}
		});
	}
}
