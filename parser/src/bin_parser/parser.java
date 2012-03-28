package bin_parser;
import java.util.Stack;

public class parser {
	private token[] tokens = null;
	private int count;
	private Stack<symbol> sym_stack = new Stack<symbol>();
	private Stack<Integer> stack = new Stack<Integer>();

	parser(token[] tokens){
		count = 0;
		stack.push(0);
		this.tokens = tokens;
	}

	int nextToken(){
		if(count >= tokens.length)
			return 0;
		else
			return tokens[count].tokenID;
	}

	void parse() throws Exception{
		while(true){
			int nextoken = nextToken();
			switch(stack.get(stack.size() - 1)){
			case 0:
				switch(nextoken){
				case (int)symbol.S_ONE:{
					sym_stack.push(new symbol_ONE(tokens[count].value));
					stack.push(1);
					count++;
					break;}
				case (int)symbol.S_ZERO:{
					sym_stack.push(new symbol_ZERO(tokens[count].value));
					stack.push(2);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 1:
				switch(nextoken){
				case (int)symbol.S_ONE:{
					symbol_ONE e1=(symbol_ONE)sym_stack.pop();
					sym_stack.push(new symbol_B_4(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ZERO:{
					symbol_ONE e1=(symbol_ONE)sym_stack.pop();
					sym_stack.push(new symbol_B_4(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DOT:{
					symbol_ONE e1=(symbol_ONE)sym_stack.pop();
					sym_stack.push(new symbol_B_4(e1));
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 2:
				switch(nextoken){
				case (int)symbol.S_ONE:{
					symbol_ZERO e1=(symbol_ZERO)sym_stack.pop();
					sym_stack.push(new symbol_B_5(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ZERO:{
					symbol_ZERO e1=(symbol_ZERO)sym_stack.pop();
					sym_stack.push(new symbol_B_5(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DOT:{
					symbol_ZERO e1=(symbol_ZERO)sym_stack.pop();
					sym_stack.push(new symbol_B_5(e1));
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 3:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					return;}
				default: throw new Exception("Unknown ACTION!");}
			case 4:
				switch(nextoken){
				case (int)symbol.S_ONE:{
					sym_stack.push(new symbol_ONE(tokens[count].value));
					stack.push(1);
					count++;
					break;}
				case (int)symbol.S_ZERO:{
					sym_stack.push(new symbol_ZERO(tokens[count].value));
					stack.push(2);
					count++;
					break;}
				case (int)symbol.S_DOT:{
					sym_stack.push(new symbol_DOT(tokens[count].value));
					stack.push(6);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 5:
				switch(nextoken){
				case (int)symbol.S_ONE:{
					symbol_B e1=(symbol_B)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_3(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ZERO:{
					symbol_B e1=(symbol_B)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_3(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DOT:{
					symbol_B e1=(symbol_B)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_3(e1));
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 6:
				switch(nextoken){
				case (int)symbol.S_ONE:{
					sym_stack.push(new symbol_ONE(tokens[count].value));
					stack.push(8);
					count++;
					break;}
				case (int)symbol.S_ZERO:{
					sym_stack.push(new symbol_ZERO(tokens[count].value));
					stack.push(9);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 7:
				switch(nextoken){
				case (int)symbol.S_ONE:{
					symbol_B e2=(symbol_B)sym_stack.pop();
					symbol_BINARY e1=(symbol_BINARY)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_2(e1,e2));
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ZERO:{
					symbol_B e2=(symbol_B)sym_stack.pop();
					symbol_BINARY e1=(symbol_BINARY)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_2(e1,e2));
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DOT:{
					symbol_B e2=(symbol_B)sym_stack.pop();
					symbol_BINARY e1=(symbol_BINARY)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_2(e1,e2));
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 8:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_ONE e1=(symbol_ONE)sym_stack.pop();
					sym_stack.push(new symbol_B_4(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ONE:{
					symbol_ONE e1=(symbol_ONE)sym_stack.pop();
					sym_stack.push(new symbol_B_4(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ZERO:{
					symbol_ONE e1=(symbol_ONE)sym_stack.pop();
					sym_stack.push(new symbol_B_4(e1));
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 9:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_ZERO e1=(symbol_ZERO)sym_stack.pop();
					sym_stack.push(new symbol_B_5(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ONE:{
					symbol_ZERO e1=(symbol_ZERO)sym_stack.pop();
					sym_stack.push(new symbol_B_5(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ZERO:{
					symbol_ZERO e1=(symbol_ZERO)sym_stack.pop();
					sym_stack.push(new symbol_B_5(e1));
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 10:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_BINARY e3=(symbol_BINARY)sym_stack.pop();
					symbol_DOT e2=(symbol_DOT)sym_stack.pop();
					symbol_BINARY e1=(symbol_BINARY)sym_stack.pop();
					sym_stack.push(new symbol_E_1(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ONE:{
					sym_stack.push(new symbol_ONE(tokens[count].value));
					stack.push(8);
					count++;
					break;}
				case (int)symbol.S_ZERO:{
					sym_stack.push(new symbol_ZERO(tokens[count].value));
					stack.push(9);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 11:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_B e1=(symbol_B)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_3(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ONE:{
					symbol_B e1=(symbol_B)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_3(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ZERO:{
					symbol_B e1=(symbol_B)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_3(e1));
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 12:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_B e2=(symbol_B)sym_stack.pop();
					symbol_BINARY e1=(symbol_BINARY)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_2(e1,e2));
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ONE:{
					symbol_B e2=(symbol_B)sym_stack.pop();
					symbol_BINARY e1=(symbol_BINARY)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_2(e1,e2));
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_ZERO:{
					symbol_B e2=(symbol_B)sym_stack.pop();
					symbol_BINARY e1=(symbol_BINARY)sym_stack.pop();
					sym_stack.push(new symbol_BINARY_2(e1,e2));
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			}
		}
	}

	void GOTO() throws Exception{
		int sym_top = sym_stack.get(sym_stack.size()-1).sym;
		switch(stack.get(stack.size()-1)){
		case 0:
			switch(sym_top){
			case symbol.S_E:
				stack.push(3);
				break;
			case symbol.S_BINARY:
				stack.push(4);
				break;
			case symbol.S_B:
				stack.push(5);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 4:
			switch(sym_top){
			case symbol.S_B:
				stack.push(7);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 6:
			switch(sym_top){
			case symbol.S_BINARY:
				stack.push(10);
				break;
			case symbol.S_B:
				stack.push(11);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 10:
			switch(sym_top){
			case symbol.S_B:
				stack.push(12);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		default: throw new Exception("Unknown GOTO!");}
	}

	public symbol getRoot(){return sym_stack.get(0);}
}