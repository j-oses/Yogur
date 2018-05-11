package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.statement.VarDeclaration;
import yogur.tree.type.BaseType;
import yogur.tree.type.ClassType;
import yogur.typeidentification.MetaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDeclaration extends AbstractTreeNode implements Declaration {
	private String name;
	private List<FunctionOrVarDeclaration> declarations;

	private Map<String, Declaration> declarationMap;

	public ClassDeclaration(String name, List<FunctionOrVarDeclaration> declarations) {
		this.name = name;
		this.declarations = declarations;
	}

	public String getName() {
		return name;
	}

	public Declaration getDeclaration(String id) throws CompilationException {
		if (declarationMap.containsKey(id)) {
			return declarationMap.get(id);
		} else {
			throw new CompilationException("Class " + name + " does not have a field nor a function called " + id,
					getLine(), getColumn(), CompilationException.Scope.IdentificatorIdentification);
		}
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declarationMap = new HashMap<>();

		table.insertId(name, this);
		table.openBlock();

		// Two steps. First we "register" all variables and functions
		// and then we perform the analysis on the rest if needed.
		for (FunctionOrVarDeclaration d: declarations) {
			d.performInsertIdentifierAnalysis(table);

			// We save the declarations in a map to make querying easier
			declarationMap.put(d.getName(), d);
		}

		for (FunctionOrVarDeclaration d: declarations) {
			d.performBodyIdentifierAnalysis(table);
		}

		table.closeBlock();
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType type = new ClassType(this);

		for (FunctionOrVarDeclaration d : declarations) {
			d.performTypeAnalysis(idTable);
		}

		return type;
	}
}
