package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.AbstractTreeNode;
import yogur.tree.type.BaseType;
import yogur.tree.type.Type;
import yogur.typeidentification.MetaType;

import java.util.List;

public class ClassDeclaration extends AbstractTreeNode implements Declaration {
	private BaseType type;
	private List<FunctionOrVarDeclaration> declarations;

	private int size = 0;

	public ClassDeclaration(BaseType type, List<FunctionOrVarDeclaration> declarations) {
		this.type = type;
		this.declarations = declarations;
	}

	public int getSize() {
		return size;
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
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType typeType = type.performTypeAnalysis(idTable);

		for (FunctionOrVarDeclaration d : declarations) {
			MetaType dType = d.performTypeAnalysis(idTable);

			if (dType instanceof Type && ((Type)dType).getMetaType().equals(typeType)) {
				throw new CompilationException("Cannot create recursive type " + type.getName(),
						d.getLine(), d.getColumn(), CompilationException.Scope.TypeAnalyzer);
			}

			size += dType.sizeOf();
		}

		return typeType;
	}
}
