JAVAC = javac
JAVA = java
CLASSPATH = "java_cup_jars/java-cup-11b.jar:java_cup_jars/java-cup-11b-runtime.jar:."

init:
	$(JAVAC) JLex/*.java

parse: yogur.cup YogurLex.java
	$(JAVA) -cp java_cup_jars/java-cup-11b.jar java_cup.Main yogur.cup
	mv parser.java IntelliJ/src/yogur/cup/parser.java
	mv sym.java IntelliJ/src/yogur/cup/sym.java
	
lex: yogur.lex
	$(JAVA) JLex.Main yogur.lex
	cp yogur.lex.java IntelliJ/src/yogur/jlex/YogurLex.java
	mv yogur.lex.java YogurLex.java

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
