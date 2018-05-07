package yogur.tree;

import yogur.codegen.PMachineOutputStream;
import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.statement.Statement;
import yogur.typeidentification.MetaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program extends AbstractTreeNode {
	private List<StatementOrDeclaration> statements;

	public Program() {
		statements = new ArrayList<>();
	}

	public Program(StatementOrDeclaration s) {
		statements = new ArrayList<>();
		statements.add(s);
	}

	public Program(Program p, StatementOrDeclaration s) {
		statements = p.statements;
		statements.add(s);
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		for (StatementOrDeclaration i: statements) {
			i.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		for (StatementOrDeclaration s: statements) {
			s.performTypeAnalysis(idTable);
		}
		return null;
	}

	public void generateCode(PMachineOutputStream stream) throws IOException {
		for (StatementOrDeclaration s: statements) {
			s.generateCode(stream);
		}
	}
}
