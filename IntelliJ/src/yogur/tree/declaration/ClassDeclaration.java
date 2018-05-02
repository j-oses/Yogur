package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import java.util.List;

public class ClassDeclaration implements Declaration {
	private BaseType type;
	private List<FunctionOrVarDeclaration> declarations;

	public ClassDeclaration(BaseType type, List<FunctionOrVarDeclaration> declarations) {
		this.type = type;
		this.declarations = declarations;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		table.openClass(type.getName());
		type.performIdentifierAnalysis(table);
		for (Declaration d: declarations) {
			d.performIdentifierAnalysis(table);
		}
		table.closeClass();
	}

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) {
		return type.performTypeAnalysis(idTable);
	}
}
