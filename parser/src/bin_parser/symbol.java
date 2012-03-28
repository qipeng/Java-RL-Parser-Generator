package bin_parser;
import java.io.BufferedWriter;
import java.io.IOException;
public class symbol {
	static final int S_NULL = 0;
	static final int S_ONE = 1;
	static final int S_ZERO = 2;
	static final int S_DOT = 3;
	static final int S_EXP = 4;
	static final int S_E = 5;
	static final int S_BINARY = 6;
	static final int S_B = 7;
	int sym;
	String value = null;
	static BufferedWriter writer = null;
	symbol(){sym = S_NULL;}
	public void visit() throws IOException{writer.write("<tree>\n<declarations>\n<attributeDecl name=\"name\" type=\"String\">\n</declarations>\n");};
	public void dealInheritedAttribute() throws Exception {};
	public String getAttrs() {return "[]";};	boolean updated = false;
}
enum AttributeType{NONE, WANTED, CALCULATED};

class symbol_ONE extends symbol{
	symbol_ONE(){sym = S_ONE;
	}
	symbol_ONE(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"ONE"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		return "[" + res.toString() + "]";
	}
}

class symbol_ZERO extends symbol{
	symbol_ZERO(){sym = S_ZERO;
	}
	symbol_ZERO(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"ZERO"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		return "[" + res.toString() + "]";
	}
}

class symbol_DOT extends symbol{
	symbol_DOT(){sym = S_DOT;
	}
	symbol_DOT(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"DOT"+getAttrs()+"\"/>\n</branch>\n");}
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
	symbol_E e1;
	boolean inherited = false;
	symbol_EXP_0(symbol_E e1){
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

class symbol_E extends symbol{
	public Object v;
	public AttributeType _v;
	symbol_E(){sym = S_E;
		_v = AttributeType.NONE;
	}
	symbol_E(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"E"+getAttrs()+"\"/>\n</branch>\n");}
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
class symbol_E_1 extends symbol_E{
	symbol_BINARY e1;
	symbol_DOT e2;
	symbol_BINARY e3;
	boolean inherited = false;
	symbol_E_1(symbol_BINARY e1,symbol_DOT e2,symbol_BINARY e3){
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"E"+getAttrs()+"\"/>\n");
		e1.visit();
		e2.visit();
		e3.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
		can_calc = true;
		if(can_calc)
			e1.b = 1.0;
		can_calc = true;
		try{e3.l=e3.get_l();} catch(Exception e) {can_calc = false;};
		if(can_calc)
			e3.b = Math.pow(2, -(Integer)e3.l);
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

class symbol_BINARY extends symbol{
	public Object v;
	public AttributeType _v;
	public Object b;
	public AttributeType _b;
	public Object l;
	public AttributeType _l;
	symbol_BINARY(){sym = S_BINARY;
		_v = AttributeType.NONE;
		_b = AttributeType.NONE;
		_l = AttributeType.NONE;
	}
	symbol_BINARY(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"BINARY"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		if (v != null) {
			if (res.length() != 0) res.append(", ");
			res.append("v=" + v);
		}
		if (b != null) {
			if (res.length() != 0) res.append(", ");
			res.append("b=" + b);
		}
		if (l != null) {
			if (res.length() != 0) res.append(", ");
			res.append("l=" + l);
		}
		return "[" + res.toString() + "]";
	}
	public Object get_v() throws Exception { return v; };
	public Object get_b() throws Exception { return b; };
	public Object get_l() throws Exception { return l; };
}
class symbol_BINARY_2 extends symbol_BINARY{
	symbol_BINARY e1;
	symbol_B e2;
	boolean inherited = false;
	symbol_BINARY_2(symbol_BINARY e1,symbol_B e2){
		this.e1 = e1;
		this.e2 = e2;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"BINARY"+getAttrs()+"\"/>\n");
		e1.visit();
		e2.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
		can_calc = true;
		try{this.b=this.get_b();} catch(Exception e) {can_calc = false;};
		if(can_calc)
			e1.b = 2 * (Double)this.b;
		can_calc = true;
		try{this.b=this.get_b();} catch(Exception e) {can_calc = false;};
		if(can_calc)
			e2.b = (Double)this.b;
	}
	public Object get_l () throws Exception {
		switch(_l ) {
		case NONE:
			do{
				this.updated = false;
				_l  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				try{e1.l=e1.get_l();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.l = (Integer)e1.l + 1;
					_l  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated || e2.updated);
			this.updated = true;
			return l ;
		case CALCULATED:
			this.updated = false;
			return l ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
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
				try{e2.v=e2.get_v();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = (Double)e1.v + (Double)e2.v;
					_v  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated || e2.updated);
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
class symbol_BINARY_3 extends symbol_BINARY{
	symbol_B e1;
	boolean inherited = false;
	symbol_BINARY_3(symbol_B e1){
		this.e1 = e1;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"BINARY"+getAttrs()+"\"/>\n");
		e1.visit();
		writer.write("</branch>\n");
	}
	public void dealInheritedAttribute() throws Exception {
		boolean can_calc;
		can_calc = true;
		try{this.b=this.get_b();} catch(Exception e) {can_calc = false;};
		if(can_calc)
			e1.b = (Double)this.b;
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
	public Object get_l () throws Exception {
		switch(_l ) {
		case NONE:
			do{
				this.updated = false;
				_l  = AttributeType.WANTED;
				boolean can_calc = true;
				if (!inherited) 
					{ try{dealInheritedAttribute(); inherited = true;} catch(Exception e){}}
				if (can_calc) {
					this.l = 1;
					_l  = AttributeType.CALCULATED;
				}
			}while(this.updated || e1.updated);
			this.updated = true;
			return l ;
		case CALCULATED:
			this.updated = false;
			return l ;
		default: // WANTED
			throw new Exception("Loop detected in attribute analysis.");
		}
	}
}

class symbol_B extends symbol{
	public Object v;
	public AttributeType _v;
	public Object b;
	public AttributeType _b;
	symbol_B(){sym = S_B;
		_v = AttributeType.NONE;
		_b = AttributeType.NONE;
	}
	symbol_B(String value){this.value = value;}
	public void visit() throws IOException{writer.write("<branch>\n<attribute name=\"name\" value=\"B"+getAttrs()+"\"/>\n</branch>\n");}
	public String getAttrs() {
		StringBuilder res = new StringBuilder();
		if (v != null) {
			if (res.length() != 0) res.append(", ");
			res.append("v=" + v);
		}
		if (b != null) {
			if (res.length() != 0) res.append(", ");
			res.append("b=" + b);
		}
		return "[" + res.toString() + "]";
	}
	public Object get_v() throws Exception { return v; };
	public Object get_b() throws Exception { return b; };
}
class symbol_B_4 extends symbol_B{
	symbol_ONE e1;
	boolean inherited = false;
	symbol_B_4(symbol_ONE e1){
		this.e1 = e1;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"B"+getAttrs()+"\"/>\n");
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
				try{this.b=this.get_b();}catch(Exception e){ can_calc = false; }
				if (can_calc) {
					this.v = this.b;
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
class symbol_B_5 extends symbol_B{
	symbol_ZERO e1;
	boolean inherited = false;
	symbol_B_5(symbol_ZERO e1){
		this.e1 = e1;
	}
	public void visit() throws IOException{
		writer.write("<branch>\n<attribute name=\"name\" value=\"B"+getAttrs()+"\"/>\n");
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
				if (can_calc) {
					this.v = 0.0;
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
