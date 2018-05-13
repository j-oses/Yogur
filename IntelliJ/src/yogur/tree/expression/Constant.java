package yogur.tree.expression;

import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

public class Constant extends Expression {
	Object value;

	public Constant(Object value) {
		this.value = value;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		// Do nothing
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		if (value instanceof Integer) {
			return new BaseType(BaseType.PredefinedType.Int);
		} else if (value instanceof Boolean) {
			return new BaseType(BaseType.PredefinedType.Bool);
		} else {
			throw new CompilationException("Invalid constant value: " + value,  getLine(), getColumn(),
					CompilationException.Scope.TypeAnalyzer);
		}
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		if (value instanceof Integer) {
			stream.appendInstruction("ldc", String.valueOf((int)value));
		} else {
			stream.appendInstruction("ldc", (boolean)value ? "true" : "false");
		}
	}
}
