import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/*
 * Generate symbol.java
 */

public class SymGen {
	private BufferedWriter writer = null;
	private String[] symbolName = null;
	private Production[] productions = null;
	private String[][] attributes = null;
	private String[][][] att_descriptions = null;
	
	public SymGen(String[] symbolName,
			Production[] productions, 
			String[][] attributes,
			String[][][] att_descriptions) {
		this.symbolName = symbolName;
		this.productions = productions;
		this.attributes = attributes;
		this.att_descriptions = att_descriptions;
	}

	public void Gen() throws IOException{
		writer = new BufferedWriter(new FileWriter("symbol.java.txt"));
		writer.write("import java.io.BufferedWriter;\n");
		writer.write("import java.io.IOException;\n");
		//class symbol
		writer.write("public class symbol {\n");
		
		for(int i = 0;i < symbolName.length;i++){
			Tab(1);writer.write("static final int S_"+symbolName[i]+" = "+i+";\n");			
		}
		Tab(1);writer.write("int sym;\n");
		Tab(1);writer.write("String value = null;\n");
		Tab(1);writer.write("static BufferedWriter writer = null;\n");
		Tab(1);writer.write("symbol(){sym = S_NULL;}\n");
		Tab(1);writer.write("public void visit() throws IOException{writer.write(\"<tree>\\n<declarations>\\n<attributeDecl name=\\\"name\\\" type=\\\"String\\\">\\n</declarations>\\n\");};\n");
		Tab(1);writer.write("public void dealInheritedAttribute() throws Exception {};\n");
		Tab(1);writer.write("public String getAttrs() {return \"[]\";};");
		Tab(1);writer.write("boolean updated = false;\n");
		writer.write("}\n");
		
		writer.write("enum AttributeType{NONE, WANTED, CALCULATED};\n");
		
		//child symbol
		for(int i = 1;i < symbolName.length; i++){
			newLine();
			writer.write("class symbol_"+symbolName[i]+" extends symbol{\n");
			// Attributes
			String[] att = attributes[i];
			for (int j = 0; j < att.length; j++) {
				Tab(1); writer.write("public Object " + att[j] + ";\n");
				Tab(1); writer.write("public AttributeType _" + att[j] + ";\n");
			}
			
			Tab(1);writer.write("symbol_"+symbolName[i]+"(){sym = S_"+symbolName[i]+";\n");
			// Attribute Init
			for (int j = 0; j < att.length; j++) {
				Tab(2);writer.write("_" + att[j] + " = AttributeType.NONE;\n");
			}
			Tab(1);writer.write("}\n");
			Tab(1);writer.write("symbol_"+symbolName[i]+"(String value){this.value = value;}\n");
			Tab(1);writer.write("public void visit() throws IOException{writer.write(\"<branch>\\n<attribute name=\\\"name\\\" value=\\\""+symbolName[i]+"\"+getAttrs()+\"\\\"/>\\n</branch>\\n\");}\n");
			// Attribute to String function
			Tab(1);writer.write("public String getAttrs() {\n");
			Tab(2);writer.write("StringBuilder res = new StringBuilder();\n");
			for (int j = 0; j < att.length; j++) {
				Tab(2);writer.write("if (" + att[j] + " != null) {\n");
				Tab(3);writer.write("if (res.length() != 0) res.append(\", \");\n");
				Tab(3);writer.write("res.append(\"" + att[j] + "=\" + " + att[j] + ");\n");
				Tab(2);writer.write("}\n");
			}
			Tab(2);writer.write("return \"[\" + res.toString() + \"]\";\n");
			Tab(1);writer.write("}\n");
			
			// Attribute empty get functions;
			for (int j = 0; j < att.length; j++) {
				Tab(1);writer.write("public Object get_" + att[j] 
				       + "() throws Exception { return " + att[j] + "; };\n");
			}
			writer.write("}\n");
			//Production
			for(int j = 0;j < productions.length; j++)
				if(productions[j].leftID == i){ 
					String[][] prod = att_descriptions[j];
					
					writer.write("class symbol_"+symbolName[i]+'_'+j+" extends symbol_"+symbolName[i]+"{\n");
					int[] rights = productions[j].rightIDs;
					for(int k = 0;k < rights.length; k++){
						Tab(1);
						writer.write("symbol_"+symbolName[rights[k]]+" e"+(k+1)+";\n");
					}
					Tab(1);writer.write("boolean inherited = false;\n");
					Tab(1);writer.write("symbol_"+symbolName[i]+'_'+j+"(");
					writer.write("symbol_"+symbolName[rights[0]]+" e1");
					for(int k = 1;k < rights.length; k++)
						writer.write(",symbol_"+symbolName[rights[k]]+" e"+(k+1));
					writer.write("){\n");
					for(int k = 0;k < rights.length; k++){
						Tab(2);
						writer.write("this.e"+(k+1)+" = e"+(k+1)+";\n");
					}					
					Tab(1);writer.write("}\n");
					Tab(1);writer.write("public void visit() throws IOException{\n");
					
					Tab(2);writer.write("writer.write(\"<branch>\\n<attribute name=\\\"name\\\" value=\\\""+symbolName[i]+"\"+getAttrs()+\"\\\"/>\\n\");\n");
					for(int k = 0;k < rights.length; k++){
						Tab(2);
						writer.write("e"+(k+1)+".visit();\n");
					}					
					Tab(2);writer.write("writer.write(\"</branch>\\n\");\n");
					Tab(1);writer.write("}\n");
					
					//Attribute visit 1: inherited attribute
					Tab(1);writer.write("public void dealInheritedAttribute()"
							+ " throws Exception {\n");
					Tab(2);writer.write("boolean can_calc;\n");
					for (int k = 0; k < prod.length; k++) {
						if (prod[k].length <= 0) continue;
						if (!prod[k][0].startsWith("e0")) {
							Tab(2);writer.write("can_calc = true;\n");
							for (int l = prod[k].length - 1; l >= 1; l--) {
								prod[k][l] = prod[k][l].replaceAll("e0.", "this.");
								Tab(2);writer.write("try{" + prod[k][l]
								       + "} catch(Exception e) {can_calc = false;};\n");
							}
							
							prod[k][0] = prod[k][0].replaceAll("e0.", "this.");
							Tab(2);writer.write("if(can_calc)\n");
							Tab(3);writer.write(prod[k][0] + "\n");
						}
					}
					Tab(1);writer.write("}\n");
					
					//Attribute visit 2: synthesized attribute
					for (int k = 0; k < prod.length; k++) {
						if (prod[k].length <= 0) continue;
						if (prod[k][0].startsWith("e0")) {
							
							String t = prod[k][0].split("=")[0].substring(3);
							prod[k][0] = prod[k][0].replaceAll("e0.", "this.");
							
							Tab(1);writer.write("public Object get_" + t + "() "
									+ "throws Exception {\n");
							Tab(2);writer.write("switch(_" + t + ") {\n");
							Tab(2);writer.write("case NONE:\n");
							Tab(3);writer.write("do{\n");
							Tab(4);writer.write("this.updated = false;\n");
							Tab(4);writer.write("_" + t + " = AttributeType.WANTED;\n");
							Tab(4);writer.write("boolean can_calc = true;\n");
							Tab(4);writer.write("if (!inherited) \n");
							Tab(5);writer.write("{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}\n");
							for (int l = 1; l < prod[k].length; l++) {
								prod[k][l] = prod[k][l].replaceAll("e0.", "this.");
								Tab(4);writer.write("try{" + prod[k][l] + "}catch(Exception e){ can_calc = false; }\n");
							}
							Tab(4);writer.write("if (can_calc) {\n");
							Tab(5);writer.write(prod[k][0] + "\n");
							Tab(5);writer.write("_" + t + " = AttributeType.CALCULATED;\n");
							Tab(4);writer.write("}\n");
							Tab(3);writer.write("}while(this.updated");
							for (int l = 0; l < rights.length; l++) writer.write(" || e" + (l + 1) + ".updated");
							writer.write(");\n");
							Tab(3);writer.write("this.updated = true;\n");
							Tab(3);writer.write("return " + t + ";\n");
							Tab(2);writer.write("case CALCULATED:\n");
							Tab(3);writer.write("this.updated = false;\n");
							Tab(3);writer.write("return " + t + ";\n");
							Tab(2);writer.write("default: // WANTED\n");
							Tab(3);writer.write("throw new Exception(\"Loop detected in "
									+ "attribute analysis.\");\n");
							Tab(2);writer.write("}\n");
							Tab(1);writer.write("}\n");
						}
					} 
					
					writer.write("}\n");					
				}		
		}

		writer.write("class token{\n");	
		Tab(1);writer.write("int tokenID;\n");	
		Tab(1);writer.write("String value;\n");	
			
		Tab(1);writer.write("token(int ID){\n");	
		Tab(2);writer.write("tokenID = ID;\n");	
		Tab(2);writer.write("value = null;\n");	
		Tab(1);writer.write("}\n");	
		Tab(1);writer.write("token(int ID, String v){\n");	
		Tab(2);writer.write("tokenID = ID;\n");	
		Tab(2);writer.write("value = v;\n");	
		Tab(1);writer.write("}\n");	
		writer.write("}\n");	
		writer.close();
	}
	
	private void Tab(int num) throws IOException{
		for(int i = 0; i < num;i++)
			writer.write('\t');
	}
	private void newLine() throws IOException{writer.write('\n');}
}
