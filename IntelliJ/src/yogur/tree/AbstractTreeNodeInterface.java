package yogur.tree;

import yogur.codegen.PMachineOutputStream;
import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

import java.io.IOException;

public interface AbstractTreeNodeInterface {
	void performIdentifierAnalysis(IdIdentifier table) throws CompilationException;
	MetaType analyzeType(IdIdentifier idTable) throws CompilationException;
	MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException;
	void generateCode(PMachineOutputStream stream) throws IOException;

	void setLine(int line);

	void setColumn(int column);

	int getLine();

	int getColumn();

	void setLineCol(int line, int col);
}
