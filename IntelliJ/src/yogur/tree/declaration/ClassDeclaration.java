package yogur.tree.declaration;

import yogur.tree.Type;

import java.util.List;
import java.util.function.Function;

public class ClassDeclaration implements Declaration {
	private Type type;
	private List<FunctionOrVarDeclaration> declarations;

	public ClassDeclaration(Type type, List<FunctionOrVarDeclaration> declarations) {
		this.type = type;
		this.declarations = declarations;
	}
}
