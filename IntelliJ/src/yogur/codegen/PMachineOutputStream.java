package yogur.codegen;

import java.io.*;

public class PMachineOutputStream extends FileWriter {
	private int lineCount = 0;

	public PMachineOutputStream(String fileName) throws IOException {
		super(fileName);
	}

	public PMachineOutputStream(String fileName, boolean append) throws IOException {
		super(fileName, append);
	}

	public PMachineOutputStream(File file) throws IOException {
		super(file);
	}

	public PMachineOutputStream(File file, boolean append) throws IOException {
		super(file, append);
	}

	public PMachineOutputStream(FileDescriptor fd) {
		super(fd);
	}


	public void appendInstruction(String name, Object... arguments) throws IOException {
		this.appendComment(String.valueOf(lineCount), true);
		this.append(name);
		for (Object o: arguments) {
			this.append(" " + o);
		}
		this.append(";\n");
		lineCount++;
	}

	public void appendComment(String string, boolean forceMultiline) throws IOException {
		if (forceMultiline || string.contains("\n")) {
			this.append("{" + string + "}");
		} else {
			this.append("\\\\" + string + "\n");
		}
	}
}
