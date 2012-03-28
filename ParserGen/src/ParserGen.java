import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/*
 * Generate parser.java
 */

public class ParserGen {
	private BufferedWriter writer = null;
	private Action[][] actionTable = null;
	private String[] symbolName = null;
	private Production[] productions = null;
	
	ParserGen(String[] symbolName,
			Production[] productions,
			Action[][] actionTable){
		this.symbolName = symbolName;
		this.productions = productions;
		this.actionTable = actionTable;
	}
	
	public void Gen() throws IOException{
		writer = new BufferedWriter(new FileWriter("parser.java.txt"));
		writer.write("import java.util.Stack;\n");
		newLine();
		writer.write("public class parser {\n");
		Tab(1);writer.write("private token[] tokens = null;\n");
		Tab(1);writer.write("private int count;\n");
		Tab(1);writer.write("private Stack<symbol> sym_stack = new Stack<symbol>();\n");
		Tab(1);writer.write("private Stack<Integer> stack = new Stack<Integer>();\n");

		newLine();
		Tab(1);writer.write("parser(token[] tokens){\n");
		Tab(2);writer.write("count = 0;\n");
		Tab(2);writer.write("stack.push(0);\n");
		Tab(2);writer.write("this.tokens = tokens;\n");
		Tab(1);writer.write("}\n");
		
		//next token
		newLine();
		Tab(1);writer.write("int nextToken(){\n");
		Tab(2);writer.write("if(count >= tokens.length)\n");
		Tab(3);writer.write("return 0;\n");
		Tab(2);writer.write("else\n");
		Tab(3);writer.write("return tokens[count].tokenID;\n");
		Tab(1);writer.write("}\n");
		
		//parse()
		newLine();
		Tab(1);writer.write("void parse() throws Exception{\n");
		Tab(2);writer.write("while(true){\n");
		Tab(3);writer.write("int nextoken = nextToken();\n"); 
		Tab(3);writer.write("switch(stack.get(stack.size() - 1)){\n");
		//cases
		for(int i = 0; i < actionTable.length; i++){
			boolean flag = false;
			for(int j = 0; j < actionTable[i].length; j++)
				if((actionTable[i][j] != null)&&(actionTable[i][j].at != ActionType.GOTO)){
					flag = true;
					break;
				}
			if(flag){
				boolean hasAcc = false, hasOther = false;
				Tab(3);writer.write("case "+i+":\n");
				Tab(4);writer.write("switch(nextoken){\n");
				for(int j = 0; j < actionTable[i].length; j++)
					if((actionTable[i][j] != null)&&(actionTable[i][j].at != ActionType.GOTO)){
						Tab(4);writer.write("case (int)symbol.S_"+symbolName[j]+":{\n");
						switch(actionTable[i][j].at){
						case Reduce:{
							hasOther = true;
							Production p = productions[actionTable[i][j].v];
							for(int k=p.rightIDs.length - 1; k>= 0;k--){
								Tab(5);
								writer.write("symbol_"+symbolName[p.rightIDs[k]]+
										" e"+(k+1)+"=(symbol_"+symbolName[p.rightIDs[k]]+
										")sym_stack.pop();\n");
							}
							Tab(5);writer.write("sym_stack.push(new symbol_"+symbolName[p.leftID]+'_'+actionTable[i][j].v);
							writer.write("(e1");
							for(int k=1;k<p.rightIDs.length;k++)
								writer.write(",e"+(k+1));
							writer.write("));\n");

							for(int k=0;k<p.rightIDs.length;k++){
								Tab(5);writer.write("stack.pop();\n");
							}
							Tab(5);writer.write("GOTO();\n");
							Tab(5);writer.write("break;}\n");
							
							break;
						}
						case Shift:{
							hasOther = true;
							Tab(5);writer.write("sym_stack.push(new symbol_"+symbolName[j]+"(tokens[count].value));\n");
							Tab(5);writer.write("stack.push("+actionTable[i][j].v+");\n");
							Tab(5);writer.write("count++;\n");
							Tab(5);writer.write("break;}\n");
							break;
						}
						case Acc:{
							hasAcc = true;
							Tab(5);writer.write("return;}\n");
							break;
						}
						}
					}
				Tab(4);writer.write("default: throw new Exception(\"Unknown ACTION!\");}\n");
				if((!hasAcc)||(hasOther)){
					Tab(4);writer.write("break;\n");
				}
			}
		}
		Tab(3);writer.write("}\n");		//for switch
		Tab(2);writer.write("}\n");		//for while(true)
		Tab(1);writer.write("}\n");		//for parse()
		
		
		//GOTO
		newLine();
		Tab(1);writer.write("void GOTO() throws Exception{\n");
		Tab(2);writer.write("int sym_top = sym_stack.get(sym_stack.size()-1).sym;\n");
		Tab(2);writer.write("switch(stack.get(stack.size()-1)){\n");
		//cases
		for(int i = 0; i < actionTable.length; i++){
			boolean flag = false;
			for(int j = 0; j < actionTable[i].length; j++)
				if((actionTable[i][j] != null)&&(actionTable[i][j].at == ActionType.GOTO)){
					flag = true;
					break;
				}
			if(flag){
				Tab(2);writer.write("case "+i+":\n");
				Tab(3);writer.write("switch(sym_top){\n");
				for(int j = 0; j < actionTable[i].length; j++)
					if((actionTable[i][j] != null)&&(actionTable[i][j].at == ActionType.GOTO)){
						Tab(3);writer.write("case symbol.S_"+symbolName[j]+":\n");
						Tab(4);writer.write("stack.push("+actionTable[i][j].v+");\n");
						Tab(4);writer.write("break;\n");
					}				
				Tab(3);writer.write("default: throw new Exception(\"Unknown GOTO!\");}\n");
				Tab(3);writer.write("break;\n");
			}
		}
		Tab(2);writer.write("default: throw new Exception(\"Unknown GOTO!\");}\n");
		Tab(1);writer.write("}\n");		//for GOTO()
		newLine();
		Tab(1);writer.write("public symbol getRoot(){return sym_stack.get(0);}\n");
		writer.write('}');
		writer.close();
	}
	
	private void Tab(int num) throws IOException{
		for(int i = 0; i < num;i++)
			writer.write('\t');
	}
	private void newLine() throws IOException{writer.write('\n');}
}
