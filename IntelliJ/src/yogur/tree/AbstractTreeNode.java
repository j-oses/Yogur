package yogur.tree;

import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeidentification.MetaType;

public abstract class AbstractTreeNode implements AbstractTreeNodeInterface {
	private int line = -1;
	private int column = -1;

	protected MetaType metaType;

	public abstract void performIdentifierAnalysis(IdentifierTable table) throws CompilationException;

	public abstract MetaType analyzeType(IdentifierTable idTable) throws CompilationException;

	@Override
	public MetaType performTypeAnalysis(IdentifierTable idTable) throws CompilationException {
		if (getMetaType() == null) {
			metaType = analyzeType(idTable);
		}

		return getMetaType();
	}

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

	public MetaType getMetaType() {
		return metaType;
	}

	public void setLineCol(int line, int col) {
		setLine(line);
		setColumn(col);
	}
}
