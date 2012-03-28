import java.io.BufferedWriter;
import java.io.IOException;
public class symbol {
	static final int S_NULL = 0;
	static final int S_LBR = 1;
	static final int S_RBR = 2;
	static final int S_NUMBER = 3;
	static final int S_PLUS = 4;
	static final int S_MINUS = 5;
	static final int S_TIMES = 6;
	static final int S_DIV = 7;
	static final int S_EXP = 8;
	static final int S_E1 = 9;
	static final int S_E2 = 10;
	int sym;
	String value = null;
	static BufferedWriter writer = null;
	symbol(){sym = S_NULL;}
	public void visit() throws IOException{writer.write("<tree>\n<declarations>\n<attributeDecl name=\"name\" type=\"String\">\n</declarations>\n");};
	public void dealInheritedAttribute() throws Exception {};
	public String getAttrs() {return "[]";};	boolean updated = false;
}
enum AttributeType{NONE, WANTED, CALCULATED};

class symbol_LBR extends symbol{
	symbol_LBR(){sym = S_LBR;
	}
	symbol_LBR(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"LBR"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		return "[" + res.toString() + "]";
	}
}

class symbol_RBR extends symbol{
	symbol_RBR(){sym = S_RBR;
	}
	symbol_RBR(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"RBR"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		return "[" + res.toString() + "]";
	}
}

class symbol_NUMBER extends symbol{
	public Object v;
	public AttributeType _v;
	symbol_NUMBER(){sym = S_NUMBER;
		_v = AttributeType.NONE;
	}
	symbol_NUMBER(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"NUMBER"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		if (v != null) {
			if (res.length() != 0) res.append(", ");
			res.append("v=" + v);
		}
		return "[" + res.toString() + "]";
	}
	public Object get_v() throws Exception { return v; };
}

class symbol_PLUS extends symbol{
	symbol_PLUS(){sym = S_PLUS;
	}
	symbol_PLUS(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"PLUS"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		return "[" + res.toString() + "]";
	}
}

class symbol_MINUS extends symbol{
	symbol_MINUS(){sym = S_MINUS;
	}
	symbol_MINUS(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"MINUS"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		return "[" + res.toString() + "]";
	}
}

class symbol_TIMES extends symbol{
	symbol_TIMES(){sym = S_TIMES;
	}
	symbol_TIMES(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"TIMES"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		return "[" + res.toString() + "]";
	}
}

class symbol_DIV extends symbol{
	symbol_DIV(){sym = S_DIV;
	}
	symbol_DIV(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"DIV"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		return "[" + res.toString() + "]";
	}
}

class symbol_EXP extends symbol{
	public Object v;
	public AttributeType _v;
	symbol_EXP(){sym = S_EXP;
		_v = AttributeType.NONE;
	}
	symbol_EXP(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"EXP"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		if (v != null) {
			if (res.length() != 0) res.append(", ");
			res.append("v=" + v);
		}
		return "[" + res.toString() + "]";
	}
	public Object get_v() throws Exception { return v; };
}
class symbol_EXP_0 extends symbol_EXP{
	symbol_E1 e1;
	boolean inherited = false;
	symbol_EXP_0(symbol_E1 e1){
		this.e1 = e1;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"EXP"+getAttrs()+"\"/>\n");
		e1.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
	}
	public Object get_v () throws Exception {
		switch(_v ) {
		case NONE:
			do{
				this.updated = false;
				_v  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e1.v=e1.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e1.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated);
			this.updated = true;
			return v ;
		case CALCULATED:
			this.updated = false;
			return v ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}

class symbol_E1 extends symbol{
	public Object v;
	public AttributeType _v;
	symbol_E1(){sym = S_E1;
		_v = AttributeType.NONE;
	}
	symbol_E1(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"E1"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		if (v != null) {
			if (res.length() != 0) res.append(", ");
			res.append("v=" + v);
		}
		return "[" + res.toString() + "]";
	}
	public Object get_v() throws Exception { return v; };
}
class symbol_E1_1 extends symbol_E1{
	symbol_E2 e1;
	boolean inherited = false;
	symbol_E1_1(symbol_E2 e1){
		this.e1 = e1;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"E1"+getAttrs()+"\"/>\n");
		e1.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
	}
	public Object get_v () throws Exception {
		switch(_v ) {
		case NONE:
			do{
				this.updated = false;
				_v  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e1.v=e1.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e1.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated);
			this.updated = true;
			return v ;
		case CALCULATED:
			this.updated = false;
			return v ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}
class symbol_E1_3 extends symbol_E1{
	symbol_E1 e1;
	symbol_PLUS e2;
	symbol_E1 e3;
	boolean inherited = false;
	symbol_E1_3(symbol_E1 e1,symbol_PLUS e2,symbol_E1 e3){
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"E1"+getAttrs()+"\"/>\n");
		e1.visit();
		e2.visit();
		e3.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
	}
	public Object get_v () throws Exception {
		switch(_v ) {
		case NONE:
			do{
				this.updated = false;
				_v  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e1.v=e1.get_v();}catch(Exception e){ can_calc = false; }
				try{e3.v=e3.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e1.v + (Double)e3.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated || e2.updated || e3.updated);
			this.updated = true;
			return v ;
		case CALCULATED:
			this.updated = false;
			return v ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}
class symbol_E1_4 extends symbol_E1{
	symbol_E1 e1;
	symbol_MINUS e2;
	symbol_E1 e3;
	boolean inherited = false;
	symbol_E1_4(symbol_E1 e1,symbol_MINUS e2,symbol_E1 e3){
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"E1"+getAttrs()+"\"/>\n");
		e1.visit();
		e2.visit();
		e3.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
	}
	public Object get_v () throws Exception {
		switch(_v ) {
		case NONE:
			do{
				this.updated = false;
				_v  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e1.v=e1.get_v();}catch(Exception e){ can_calc = false; }
				try{e3.v=e3.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e1.v - (Double)e3.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated || e2.updated || e3.updated);
			this.updated = true;
			return v ;
		case CALCULATED:
			this.updated = false;
			return v ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}

class symbol_E2 extends symbol{
	public Object v;
	public AttributeType _v;
	symbol_E2(){sym = S_E2;
		_v = AttributeType.NONE;
	}
	symbol_E2(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"E2"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		if (v != null) {
			if (res.length() != 0) res.append(", ");
			res.append("v=" + v);
		}
		return "[" + res.toString() + "]";
	}
	public Object get_v() throws Exception { return v; };
}
class symbol_E2_2 extends symbol_E2{
	symbol_LBR e1;
	symbol_E1 e2;
	symbol_RBR e3;
	boolean inherited = false;
	symbol_E2_2(symbol_LBR e1,symbol_E1 e2,symbol_RBR e3){
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"E2"+getAttrs()+"\"/>\n");
		e1.visit();
		e2.visit();
		e3.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
	}
	public Object get_v () throws Exception {
		switch(_v ) {
		case NONE:
			do{
				this.updated = false;
				_v  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e2.v=e2.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e2.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated || e2.updated || e3.updated);
			this.updated = true;
			return v ;
		case CALCULATED:
			this.updated = false;
			return v ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}
class symbol_E2_5 extends symbol_E2{
	symbol_E2 e1;
	symbol_TIMES e2;
	symbol_E2 e3;
	boolean inherited = false;
	symbol_E2_5(symbol_E2 e1,symbol_TIMES e2,symbol_E2 e3){
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"E2"+getAttrs()+"\"/>\n");
		e1.visit();
		e2.visit();
		e3.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
	}
	public Object get_v () throws Exception {
		switch(_v ) {
		case NONE:
			do{
				this.updated = false;
				_v  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e1.v=e1.get_v();}catch(Exception e){ can_calc = false; }
				try{e3.v=e3.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e1.v * (Double)e3.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated || e2.updated || e3.updated);
			this.updated = true;
			return v ;
		case CALCULATED:
			this.updated = false;
			return v ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}
class symbol_E2_6 extends symbol_E2{
	symbol_E2 e1;
	symbol_DIV e2;
	symbol_E2 e3;
	boolean inherited = false;
	symbol_E2_6(symbol_E2 e1,symbol_DIV e2,symbol_E2 e3){
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"E2"+getAttrs()+"\"/>\n");
		e1.visit();
		e2.visit();
		e3.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
	}
	public Object get_v () throws Exception {
		switch(_v ) {
		case NONE:
			do{
				this.updated = false;
				_v  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e1.v=e1.get_v();}catch(Exception e){ can_calc = false; }
				try{e3.v=e3.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e1.v / (Double)e3.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated || e2.updated || e3.updated);
			this.updated = true;
			return v ;
		case CALCULATED:
			this.updated = false;
			return v ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}
class symbol_E2_7 extends symbol_E2{
	symbol_NUMBER e1;
	boolean inherited = false;
	symbol_E2_7(symbol_NUMBER e1){
		this.e1 = e1;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"E2"+getAttrs()+"\"/>\n");
		e1.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
		can_calc = true;
		if(can_calc)
			e1.v = Double.parseDouble(e1.value);
	}
	public Object get_v () throws Exception {
		switch(_v ) {
		case NONE:
			do{
				this.updated = false;
				_v  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e1.v=e1.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e1.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated);
			this.updated = true;
			return v ;
		case CALCULATED:
			this.updated = false;
			return v ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}
class token{
	int tokenID;
	String value;
	token(int ID){
		tokenID = ID;
		value = null;
	}
	token(int ID, String v){
		tokenID = ID;
		value = v;
	}
}
