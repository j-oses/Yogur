package yogur.tree;

import java.util.ArrayList;
import java.util.List;

public class Program {
	private List<StatementOrDeclaration> instructions;

	public Program(StatementOrDeclaration s) {
		instructions = new ArrayList<>();
		instructions.add(s);
	}

	public Program(Program p, StatementOrDeclaration s) {
		instructions = p.instructions;
		instructions.add(s);
	}
}
