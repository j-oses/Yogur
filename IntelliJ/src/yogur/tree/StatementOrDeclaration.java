package yogur.tree;

import yogur.codegen.PMachineOutputStream;

import java.io.IOException;

public interface StatementOrDeclaration extends AbstractTreeNodeInterface {
	void generateCode(PMachineOutputStream stream) throws IOException;
}
