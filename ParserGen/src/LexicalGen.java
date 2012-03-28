import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LexicalGen {
	private BufferedWriter writer = null;
	private ArrayList<regExpression> regs = new ArrayList<regExpression>();
	private String[] symbolName = null;
	LexicalGen(String[] symbolName){
		this.symbolName = symbolName;
	}
	public void Load(String fileName) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String readline, readline2;
		while((readline = reader.readLine())!= null)
			if(readline.length() > 0){
				readline2 = reader.readLine();
				int i;
				for(i = 0; i < symbolName.length; i++)
					if(readline.equals(symbolName[i]))
						break;
				if(i < symbolName.length)
					regs.add(new regExpression(i, readline2));
				else
					System.err.println("Unknown symbol:"+readline);
			}
		reader.close();
	}
	
	public void Gen() throws IOException{
		writer = new BufferedWriter(new FileWriter("lexical.java.txt"));
		writer.write("import java.io.FileInputStream;\n");
		writer.write("import java.io.IOException;\n");
		writer.write("import java.util.ArrayList;\n");
		writer.write("import java.util.regex.Matcher;\n");
		writer.write("import java.util.regex.Pattern;\n");
		newLine();
		
		writer.write("public class lexical{\n");
		Tab(1);writer.write("private String source = null;\n");
		Tab(1);writer.write("private ArrayList<token> tokens = new ArrayList<token>();\n");
		Tab(1);writer.write("private ArrayList<regExpression> regs = new ArrayList<regExpression>();\n");
		Tab(1);writer.write("lexical(){\n");
		for(int i = 0; i < regs.size(); i++){
			Tab(2);
			writer.write("regs.add(new regExpression("+regs.get(i).sID+",\""+regs.get(i).regExp.replace("\\", "\\\\")+"\"));\n");
		}
		Tab(1);writer.write("}\n");
		newLine();
		
		//Load()
		Tab(1);writer.write("public void Load(String fileName)  throws IOException{\n");
		Tab(2);writer.write("FileInputStream file = new FileInputStream(fileName);\n");
		Tab(2);writer.write("byte[] buf = new byte[file.available()];\n");
		Tab(2);writer.write("file.read(buf, 0, file.available());\n");
		Tab(2);writer.write("source = new String(buf); \n");
		Tab(2);writer.write("file.close();\n");
		Tab(1);writer.write("}\n");
		newLine();
		
		//Lex
		Tab(1);writer.write("void lex(){\n");
		Tab(2);writer.write("lexdfs(0,0,source.length());\n");
		Tab(1);writer.write("}\n");
		newLine();

		//LexDFS
		Tab(1);writer.write("void lexdfs(int level, int start, int end){\n");
		Tab(2);writer.write("if(start >= end) return;\n");
		Tab(2);writer.write("String tmp = source.substring(start, end);\n");
		Tab(2);writer.write("if(level == regs.size()){\n");
		Tab(3);writer.write("String tmp2 = tmp.trim();\n");
		Tab(3);writer.write("if(tmp2.length()>0) System.err.println(\"Unknown token:\"+tmp2);\n");
		Tab(3);writer.write("return;\n");
		Tab(2);writer.write("}\n");
		Tab(2);writer.write("int last = start;\n");
		Tab(2);writer.write("Matcher m = regs.get(level).regExp.matcher(tmp);\n");
		Tab(3);writer.write("while(m.find()){\n");
		Tab(3);writer.write("lexdfs(level+1, last, start+m.start());\n");
		Tab(3);writer.write("last = start+m.end();\n");
		Tab(3);writer.write("tokens.add(new token(regs.get(level).sID,m.group()));\n");
	    Tab(2);writer.write("}\n");
	    Tab(2);writer.write("lexdfs(level+1, last, end);\n");
	    Tab(2);writer.write("}\n");
		newLine();
		
		
		//getTokens()
		Tab(1);writer.write("public token[] getTokens(){\n");
		Tab(2);writer.write("token[] tmp = new token[tokens.size()];\n");
		Tab(2);writer.write("tokens.toArray(tmp);\n");
		Tab(2);writer.write("return tmp;\n");
		Tab(1);writer.write("}\n");

		writer.write("}");
		newLine();
		
		writer.write("class regExpression{\n");
		Tab(1);writer.write("int sID;\n");
		Tab(1);writer.write("Pattern regExp;\n");
		Tab(1);writer.write("regExpression(int id, String exp){\n");
		Tab(2);writer.write("sID = id;\n");
		Tab(2);writer.write("regExp = Pattern.compile(exp, Pattern.MULTILINE);\n");
		Tab(1);writer.write("}\n");
		writer.write("}");
		writer.close();
	}
	
	private void Tab(int num) throws IOException{
		for(int i = 0; i < num;i++)
			writer.write('\t');
	}
	private void newLine() throws IOException{writer.write('\n');}
}

class regExpression{
	int sID;
	String regExp;
	regExpression(int id, String exp){
		sID = id;
		regExp = exp;
	}
}
