# The Yogur Compiler

This document details the syntax of the **Yogur** programming language and the structure of its compiler.

## Quick set-up

    make init
    make all
    make run < inputfile
    make test < test.pl

## Language features

### File structure

Yogur files can have code outside of any scope. It is, in fact, almost mandatory: execution will start on the first line of code of the file. Thus, there is no main method; executing a file that has only classes and/or functions will be a no op.

### Identifiers and scope

- Statement delimiters are line breaks.
- Variable declaration:

  	var name: Type

- Array declaration (length is an integer):

  	var name: Type[length]

- C-style blocks will be used, opened with `{` and closed with `}`. Statements such as `if`, `while`, `for`... must open a block.
- Functions do not use the `return` statement to return a value. Instead, they will return a variable declared on their header (`argR` in the following example):

  	def name(arg1 : Type, arg2 : Type) -> argR : Type {
  		...
  	}

- Only basic types (integers and booleans) may be returned by a function. However, they can accept objects of any type. Basic types and arrays will be passed by value, and classes will be passed by reference.

- Procedures can be declared with the same syntax as functions, but without a return value:

  	def name(arg1 : Type, arg2 : Type) {
  		...
  	}

- Classes may be defined as follows:

  	class Class {
  		var a : Type
  		...
  		def fun(...) -> argR : Type {
  			...
  		}
  	}

- The body of class functions may access the declaring object with the reserved identifier `this`.
- Class variables and functions may be accessed with the usual operator `.`. Class functions may use class variables without prefixing them by `this.`.

### Basic types and operators

- The predefined types will be **Int** for (32 bit) integers and **Bool** for booleans.
- Integers may be specified in decimal notation (default) or hexadecimal when prefixed by `0x`.
- Infix operators will be `+`, `*`, `-`, `/`, `and` or `&&`, `not` or `!`, `or` or `||`, `==`, `>=`, `>`, `<=`, `<`. They follow C's associativity and priority rules.
- Parentheses (`(` and `)`) may be used as usual to override the default operator precedence.
- Values may be assigned with `=`.
- **false** and **true** will be reserved keywords, for boolean literals.

### Statements

- Array access will be the usual `a[i]` to access the `i`-th element of array `a`. Arrays are 0-indexed.
- Conditional structures will look as follows:

  	if condition { 
  		// Statements
  	} else if condition { 
  		// Statements
  	} else {
  		// Statements
  	}

- While loops will look like this:

  	while condition {
  		// Statements
  	}

- A for loop from `a` to `b`:

  	for i in a to b {
  		// Statements
  	}

- Function calls:

  	fun(arg1, arg2, ...)

- Procedure calls:

  	proc(arg1, arg2, ...)

### Comments

Comments will be C-style, with `//` for single-line comments and `/* ... */` for block comments.

## Language examples

Folder `tests` contains an assortment of yogur files testing different language features. Folder structure:
- Examples: files starting by a two digit number.
- `gencode`: the p-machine code generated on a successful compilation for each example (`*.yogur.txt`) and the resulting p-machine output after running it (`*.yogur.txt.out`).
- `errors`: contains a file for every possible error the compiler may throw.
    - `screenshots`: the screenshots of the errors thrown when runing the above files.

## Compiler structure

This section will describe the code structure of the Yogur compiler.

### Compilation process

The main class for compilation is `yogur.Compiler`. Its main method expects two arguments: the path of the input file and the path of the output file (which will be replaced by a default path if none is provided).
- Input: a yogur file with the syntax described above.
- Output: a text file with the p-code machine statements that run the program.

### Package responsibilities

The main packages have the following responsibilities:

- `yogur.utils`: general utility classes used all throughout the compiler. It is worth mentioning `CompilationException`, that encapsulates all exceptions thrown during the compilation process.
- `yogur.jflex`: the lexical analysis package. Contains the JFlex-generated `YogurLex` class.
- `yogur.cup`: the syntax analysis package. Contains cup classes, like `sym` and `YogurParser`.
- `yogur.tree`: contains the abstract syntax tree (AST). It has subpackages for declarations (`declaration`), expressions (`expression`), identifiers (`expression.identifier`), statements (`statement`) and types (`type`).
- `yogur.ididentification`: its classes are used for identifier identification.
- `yogur.typeanalysis`: the classes for our type analizer.
- `yogur.codegen`: the code generation classes.

### Abstract Syntax Tree (AST)

Every AST node inherits from `AbstractTreeNode`. This class has the shared node attributes (such as declaration line and column). It also implements `AbstractTreeNodeInterface`, which defines the methods that every node must implement. The different compilation phases will call these methods to do their job: a node is able to identify its own identifiers, type themselves and generate their own code.


The tree root is a special node of `Program` class. The rest of the nodes will implement different abstract classes or interfaces depending on their role: `Declaration`, `Expression`, `Identifier`, `Statement`...

### Compilation phases

The compiler tries to differenciate explicitely between all compilation phases, apart from the lexical and syntax analizer, which are automated by Jlex and cup respectively.

#### Identifier identification

Each node performs this phase through the method:

	void performIdentifierAnalysis(IdentifierTable table) throws CompilationException;

An `IdentifierTable` manages the current identifier table. It is a stack of hash maps, where each map associates a node (a reference to the AST) and its identifier. Each map on the stack represents a nesting level.

The identification phase also saves necessary information about declaration in the nodes that need it. The idea is that this information should be available even after discarding the identifier table.

#### Type analysis

Performed by two different methods in the nodes

	MetaType analyzeType() throws CompilationException;
	MetaType performTypeAnalysis() throws CompilationException;

The first one is just a convenience method to store in the node the resulting type. The actual analysis happens on the second method.

The types returned here are "metatypes" (implementing `MetaType`). Metatypes include the normal types, but also non-declarable types such as `void` or function types.

During type analysis, the dot operator is also solved, associating accesses to their corresponding attribute declaration. This information will be retrieved during code generation.

#### Code generation

Divided in two subphases. The first one assigns memory, using the following method:

	void performMemoryAssignment(IntegerReference currentOffset,
								 IntegerReference nestingDepth);

Here, an `IntegerReference` is just an integer that is passed by reference. The memory assignment phase stores, in relevant nodes, information such as nesting depth or memory offsets.

The second subphase is the actual code generation, through implementations of:

	public void generateCode(PMachineOutputStream stream) throws IOException

This phase uses `PMachineOutputStream`, a subclass of `FileWriter` that stores instructions until the file is saved. This allows us to use references throughout the compiler, that won't be translated into absolute memory addresses until the output stream writes the file.