package yogur.tree.statement;

import java.util.ArrayList;
import java.util.List;

public class Block implements Statement {
	private List<Statement> statementList;

	public Block(Statement s) {
		statementList = new ArrayList<>();
		statementList.add(s);
	}

	public Block(Block b, Statement s) {
		statementList = b.statementList;
		statementList.add(s);
	}
}
