JAVAC = javac
JAVA = java
CLASSPATH = "java_cup_jars/java-cup-11b.jar:java_cup_jars/java-cup-11b-runtime.jar:."

init:
	$(JAVAC) JLex/*.java

parse: example.cup
	$(JAVA) -jar java_cup_jars/java-cup-11b-runtime.jar example.cup
	
lex: example.lex
	$(JAVA) JLex.Main example.lex
	mv example.lex.java Yylex.java

build: sym.java parser.java Yylex.java
	$(JAVAC) Program.java
	$(JAVAC) sym.java parser.java Yylex.java

all:
	$(JAVA) -jar java_cup_jars/java-cup-11b.jar example.cup
	$(JAVA) JLex.Main example.lex
	mv example.lex.java Yylex.java
	$(JAVAC) Program.java
	
	$(JAVAC) -cp $(CLASSPATH) sym.java parser.java Yylex.java

run:
	$(JAVA) -classpath $(CLASSPATH) parser

test:
	$(JAVA) -classpath $(CLASSPATH) parser < test.pl

clean:
	-rm *.class
	-rm Yylex.java
	-rm parser.java
	-rm sym.java

vclean:
	-rm *.class
	-rm Yylex.java
	-rm parser.java
	-rm sym.java
	-rm JLex/*.class
