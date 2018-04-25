package yogur.tree.statement;

import java.util.ArrayList;
import java.util.List;

public class Block implements Statement {
	private List<Statement> statementList;

	public Block(List<Statement> s) {
		statementList = s;
	}
}
