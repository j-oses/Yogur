JAVAC = javac
JAVA = java
CLASSPATH = "java_cup_jars/java-cup-11b.jar:java_cup_jars/java-cup-11b-runtime.jar:JFLex/jflex-1.6.1.jar:."

init:
	$(JAVAC) JLex/*.java

parse: yogur.cup
	$(JAVA) -cp java_cup_jars/java-cup-11b.jar java_cup.Main -parser YogurParser yogur.cup
	mv YogurParser.java src/yogur/cup/YogurParser.java
	mv sym.java src/yogur/cup/sym.java

jflex: yogur.lex
	$(JAVA) -classpath $(CLASSPATH) jflex.Main yogur.lex
	cp YogurLex.java src/yogur/jflex/YogurLex.java

build: sym.java parser.java YogurLex.java
	$(JAVAC) Program.java
	$(JAVAC) -cp $(CLASSPATH) sym.java parser.java YogurLex.java

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
