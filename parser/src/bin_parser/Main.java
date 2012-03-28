package bin_parser;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class Main{
	public static void main(String args[]) throws Exception{
		lexical lex = new lexical();
		lex.Load("test.txt");
		lex.lex();
		parser par = new parser(lex.getTokens());
		par.parse();
		symbol s = par.getRoot();
		((symbol_E)s).get_v();
		try{
			symbol.writer = new BufferedWriter(new FileWriter("result.xml"));
			symbol.writer.write("<tree>\n<declarations>\n<attributeDecl name=\"name\" type=\"String\"/>\n</declarations>\n");
			s.visit();
			symbol.writer.write("</tree>");
			symbol.writer.close();
		} catch(Exception e){
		e.printStackTrace();
		}
	}
}