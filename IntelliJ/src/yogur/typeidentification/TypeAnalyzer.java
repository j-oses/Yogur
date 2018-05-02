package yogur.typeidentification;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.Program;

public class TypeAnalyzer {
    private Program program;
    private IdIdentifier idTable;

    public TypeAnalyzer(Program program, IdIdentifier idTable) {
        this.program = program;
        this.idTable = idTable;
    }

    public Program decorateTree() {
        try {
            program.performTypeAnalysis(idTable);
        } catch (CompilationException e) {
            System.out.println(e.getMessage());
        }

        return program;
    }
}
