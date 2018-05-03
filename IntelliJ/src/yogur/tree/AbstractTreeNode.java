package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

public abstract class AbstractTreeNode implements AbstractTreeNodeInterface {
	int line = -1;
	int column = -1;

	public abstract void performIdentifierAnalysis(IdIdentifier table) throws CompilationException;
	public abstract MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException;

	public void setLine(int line) {
		this.line = line;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public void setLineCol(int line, int col) {
		setLine(line);
		setColumn(col);
	}
}
