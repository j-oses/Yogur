package yogur.tree.declaration;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.statement.VarDeclaration;
import yogur.tree.type.ClassType;
import yogur.typeidentification.MetaType;
import yogur.utils.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDeclaration extends AbstractTreeNode implements Declaration {
	private String name;
	private List<FunctionOrVarDeclaration> declarations;

	private Map<String, Declaration> declarationMap;
	private int size = 0;

	public ClassDeclaration(String name, List<FunctionOrVarDeclaration> declarations) {
		this.name = name;
		this.declarations = declarations;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	@Override
	public String getDeclarationDescription() {
		return "Class declaration";
	}

	public Declaration getDeclaration(String id) throws CompilationException {
		if (declarationMap.containsKey(id)) {
			return declarationMap.get(id);
		} else {
			throw new CompilationException("Class " + name + " does not have a field nor a function called " + id,
					getLine(), getColumn(), CompilationException.Scope.IdentifierIdentification);
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
			d.setDeclaredOnClass(this);

			// We save the declarations in a map to make querying easier
			if (d instanceof VarDeclaration) {
				declarationMap.put(d.getName(), ((VarDeclaration) d).getArgument());
			} else {
				declarationMap.put(d.getName(), d);
			}
		}

		for (FunctionOrVarDeclaration d: declarations) {
			d.performBodyIdentifierAnalysis(table);
		}

		table.closeBlock();
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType type = new ClassType(this);

		for (FunctionOrVarDeclaration d : declarations) {
			MetaType decType = d.performTypeAnalysis();
			if (d instanceof VarDeclaration) {
				size += decType.getSize();
			}
		}

		return type;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		IntegerReference internalOffset = new IntegerReference(0);
		Log.debug("Entering class memory assignment for class " + name);

		for (FunctionOrVarDeclaration d: declarations) {
			d.performMemoryAssignment(internalOffset, nestingDepth);
		}

		Log.debug("Exiting class memory assignment for class " + name);
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		for (FunctionOrVarDeclaration d: declarations) {
			d.generateCode(stream);
		}
	}
}
