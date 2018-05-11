package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeidentification.MetaType;

public interface AbstractTreeNodeInterface {
	void performIdentifierAnalysis(IdentifierTable table) throws CompilationException;
	MetaType analyzeType(IdentifierTable idTable) throws CompilationException;
	MetaType performTypeAnalysis(IdentifierTable idTable) throws CompilationException;

	void setLine(int line);

	void setColumn(int column);

	int getLine();

	int getColumn();

	void setLineCol(int line, int col);
}
