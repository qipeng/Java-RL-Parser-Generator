import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Compiler{
	public static void main(String args[]){
		LoadSymbols s = new LoadSymbols();
		try {
			s.Load("symbols.txt");
		} catch (IOException e) {
			System.err.println("Cannot load symbol file!");
			e.printStackTrace();
		}
		
		//main.java
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter("Main.java.txt"));
			writer.write("import java.io.BufferedWriter;\n");
			writer.write("import java.io.FileWriter;\n");
			writer.write("public class Main{\n");
			writer.write("\tpublic static void main(String args[]) throws Exception{\n");
			writer.write("\t\tlexical lex = new lexical();\n");
			writer.write("\t\tlex.Load(\"test.txt\");\n");
			writer.write("\t\tlex.lex();\n");
			writer.write("\t\tparser par = new parser(lex.getTokens());\n");
			writer.write("\t\tpar.parse();\n");
			writer.write("\t\tsymbol s = par.getRoot();\n");
			writer.write("\t\ttry{\n");
			writer.write("\t\t\tsymbol.writer = new BufferedWriter(new FileWriter(\"result.xml\"));\n");
			writer.write("\t\t\tsymbol.writer.write(\"<tree>\\n<declarations>\\n<attributeDecl name=\\\"name\\\" type=\\\"String\\\"/>\\n</declarations>\\n\");\n");
			writer.write("\t\t\ts.visit();\n");
			writer.write("\t\t\tsymbol.writer.write(\"</tree>\");\n");
			writer.write("\t\t\tsymbol.writer.close();\n");
			writer.write("\t\t} catch(Exception e){\n");
			writer.write("\t\te.printStackTrace();\n");
			writer.write("\t\t}\n");
			writer.write("\t}\n");
			writer.write("}");
			writer.close();
		} catch (IOException e) {
			System.err.println("Cannot write main.java!");
			e.printStackTrace();
		}
		
		//lexical.java
		LexicalGen lex = new LexicalGen(s.getSymbolName());
		try {
			lex.Load("tokens.txt");
		} catch (IOException e) {
			System.err.println("Cannot load token description file!");
			e.printStackTrace();
		}
		try {
			lex.Gen();
		} catch (IOException e) {
			System.err.println("Cannot write lexical.java!");
			e.printStackTrace();
		}
		
		//Attribute Analysis
		AttGen att = new AttGen(s.getSymbolName().length);
		
		//LR table
		LRGen lr = new LRGen(s.getSymbolName(), att);
		try{
			lr.Load("productions.txt");
		} catch(Exception e){
			System.err.println("Error Loading cup file!");
			e.printStackTrace();
		}
		lr.LRTable();
		
		//symbol.java
		SymGen symgen = new SymGen(s.getSymbolName(), lr.getProductions(),
				att.getAttributes(), att.getAttributeDescriptions());
		try {
			symgen.Gen();
		} catch (IOException e) {
			System.err.println("Cannot write symbol.java!");
			e.printStackTrace();
		}
		
		//parser.java
		ParserGen parsergen = new ParserGen(s.getSymbolName(), lr.getProductions(), lr.getActionTable());
		try {
			parsergen.Gen();
		} catch (IOException e) {
			System.err.println("Cannot write parser.java!");
			e.printStackTrace();
		}
		
	}
}

class token{
	int tokenID;
	String value;
	
	token( int ID){
		tokenID = ID;
		value = null;
	}
}