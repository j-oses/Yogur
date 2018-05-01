package yogur.typeidentification;

import yogur.error.CompilationException;
import yogur.tree.Program;

public class TypeAnalyzer {
    Program program;
    TypeIdentifier Type;

    public TypeAnalyzer(Program program) {
        this.program = program;
        this.Type = new TypeIdentifier();
    }

    public Program decorateTree() {
        this.Type = new TypeIdentifier();

        try {
            program.performTypeAnalysis(Type);
        } catch (CompilationException e) {
            System.out.println(e.getMessage());
        }

        return program;
    }
}
