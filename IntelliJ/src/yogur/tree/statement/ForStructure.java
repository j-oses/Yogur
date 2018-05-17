package yogur.tree.statement;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.expression.BinaryOperation;
import yogur.tree.expression.Constant;
import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.type.BaseType;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.tree.expression.Expression;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class ForStructure extends Statement {
	private Argument argument;
	private Expression start;
	private Expression end;
	private Block block;

	public ForStructure(BaseIdentifier declarator, Expression s, Expression e, Block b) {
		this.argument = new Argument(declarator, new BaseType(BaseType.PredefinedType.Int));
		this.start = s;
		this.end = e;
		this.block = b;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		table.openBlock();
		argument.performIdentifierAnalysis(table);
		start.performIdentifierAnalysis(table);
		end.performIdentifierAnalysis(table);
		block.performIdentifierAnalysis(table, false);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType argType = argument.performTypeAnalysis();
		MetaType startType = start.performTypeAnalysis();
		MetaType endType = end.performTypeAnalysis();

		if (!argType.equals(startType)) {
			throw new CompilationException("Invalid iteration limits on for loop start, with type: " + startType,
					start.getLine(), start.getColumn(), TypeAnalyzer);
		}

		if (!argType.equals(endType)) {
			throw new CompilationException("Invalid iteration limits on for loop end, with type: " + endType,
					end.getLine(), end.getColumn(), TypeAnalyzer);
		}

		block.performTypeAnalysis();
		return null;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		argument.performMemoryAssignment(currentOffset, nestingDepth);
		start.performMemoryAssignment(currentOffset, nestingDepth);
		end.performMemoryAssignment(currentOffset, nestingDepth);
		block.performMemoryAssignment(currentOffset, nestingDepth);
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		String labelStart = stream.generateLabelWithUnusedId("for");
		String labelEnd = stream.generateLabel("endFor");

		BaseIdentifier varId = new BaseIdentifier(argument);
		Assignment startAssignment = new Assignment(argument.getDeclarator(), start);
		Assignment incrementAssignment = new Assignment(argument.getDeclarator(),
				new BinaryOperation(varId, new Constant(1), BinaryOperation.Operator.SUM));
		Expression condition = new BinaryOperation(varId, end, BinaryOperation.Operator.LEQ);

		startAssignment.generateCode(stream);
		stream.appendLabel(labelStart);
		condition.generateCodeR(stream);
		stream.appendInstruction("fjp", labelEnd);
		block.generateCode(stream);
		incrementAssignment.generateCode(stream);
		stream.appendInstruction("ujp", labelStart);
		stream.appendLabel(labelEnd);
	}
}
