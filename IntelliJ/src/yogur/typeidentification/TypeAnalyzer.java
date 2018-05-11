package yogur.typeidentification;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.Program;

public class TypeAnalyzer {
    private Program program;
    private IdentifierTable idTable;

    public TypeAnalyzer(Program program, IdentifierTable idTable) {
        this.program = program;
        this.idTable = idTable;
    }

    public Program decorateTree() throws CompilationException {
		program.performTypeAnalysis(idTable);

        return program;
    }
}
