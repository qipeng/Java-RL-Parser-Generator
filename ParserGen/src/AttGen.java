import java.util.ArrayList;

public class AttGen {
	private ArrayList<ArrayList<String>> attributes = null;
	private ArrayList<ArrayList<ArrayList<String>>> att_descriptions = null;
	
	public AttGen(int SymbolCount) {
		attributes = new ArrayList<ArrayList<String>>();
		
		for (int i = 0; i < SymbolCount; i++) {
			attributes.add(new ArrayList<String>());
		}
		
		att_descriptions = new ArrayList<ArrayList<ArrayList<String>>>();
	}
	
	private int findStringInArrayList(ArrayList<String> arr, String s) {
		int res = -1;
		
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).equals(s)) {
				res = i;
				break;
			}
		}
		
		return res;
	}
	
	// Analyze attribute names of symbols in the given attribute description.
	private ArrayList<String> analyzeAtt(Production p, String attDes) throws Exception {
		if (attDes.equals("")) return new ArrayList<String>();
		int read_i = 0;
		int last_read = 0;
		String token = "";
		int token_id = -1;

		ArrayList<String> res = new ArrayList<String>();
		boolean beforeEqual = true;
		String lastToken = "";
		
		attDes = attDes.trim();
		res.add(attDes);
		
		for (; read_i < attDes.length(); read_i++) {
			char cur_char = attDes.charAt(read_i);
			
			switch (cur_char) {
			case '.' : 
				token = attDes.substring(last_read, read_i).trim();
				last_read = read_i + 1;
				// The string before dot has the form "e*", assume that it's a symbol rep.
				if (token.charAt(0) == 'e') {
					try {
						token_id = Integer.parseInt(token.substring(1, token.length()));
						
						lastToken = token;
						
						if (token_id == 0) {
							token_id = p.leftID;
						} else {
							token_id = p.rightIDs[token_id - 1];
						}
					} catch (NumberFormatException e) {
						// String begins with 'e' but ends with non-number string.
						// This token will not be recognized as an variable in the AD.
						token_id = -1;
					}
				}

				break;
			case '+':
			case '-':
			case '*':
			case '/':
			case ';':
			case '(':
			case ')':
			case '&':
			case '!':
			case '|':
			case '=':
				token = attDes.substring(last_read, read_i).trim();
				last_read = read_i + 1;
				
				// This token is expected to be the name of some attribute of last token
				if (token_id >= 0) {
					if ((token == null) || (token.length() <= 0)) {
						throw new Exception("Error in attribute description: \" "
								+ attDes + "\"");
					}
					if (!token.equals("value")) { 

						if (!beforeEqual) 
							res.add(lastToken + "." + token + "=" + lastToken + "."
								+ "get_" + token + "();");
						
						lastToken = "";
						
						int index = findStringInArrayList(attributes.get(token_id), token);
						
						if (index < 0) {
							attributes.get(token_id).add(token);
						}
					}
				} 
				
				token_id = -1;
				
				
				if (cur_char == '=') {
					beforeEqual = false;
				}
				break;
			}
		}
		
		return res;
	}
	
	public void add_AD_to_production(String attDes, boolean new_production, Production p) throws Exception {
		if (new_production) att_descriptions.add(new ArrayList<ArrayList<String>>());
		
		ArrayList<String> analyzedStrs = analyzeAtt(p, attDes);
		
		ArrayList<ArrayList<String>> prod = att_descriptions.get(att_descriptions.size() - 1);
		
		prod.add(analyzedStrs);
	}
	
	public String[][] getAttributes() {
		String[][] res = new String[attributes.size()][];
		
		for (int i = 0; i < attributes.size(); i++) {
			Object[] t = attributes.get(i).toArray();
			if (t == null) res[i] = new String[0];
			else {
				res[i] = new String[t.length];
				
				for (int j = 0; j < t.length; j++)
					res[i][j] = (String)t[j];
			}
		}
		
		return res;
	}
	
	public String[][][] getAttributeDescriptions() {
		String[][][] res = new String[att_descriptions.size()][][];
		
		for (int k = 0; k < att_descriptions.size(); k++) {
			ArrayList<ArrayList<String>> att_description = att_descriptions.get(k);
			if (att_description == null) res[k] = new String[0][];
			else res[k] = new String[att_description.size()][];
			for (int i = 0; i < att_description.size(); i++) {
				Object[] t = att_description.get(i).toArray();
				if (t == null) res[k][i] = new String[0];
				else {
					res[k][i] = new String[t.length];
					
					for (int j = 0; j < t.length; j++)
						res[k][i][j] = (String)t[j];
				}
			}

		}
				
		return res;
	}
}
