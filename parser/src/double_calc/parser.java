package double_calc;

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
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(1);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(2);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 1:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(5);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(6);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 2:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DIV:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 3:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					return;}
				case (int)symbol.S_PLUS:{
					sym_stack.push(new symbol_PLUS(tokens[count].value));
					stack.push(9);
					count++;
					break;}
				case (int)symbol.S_MINUS:{
					sym_stack.push(new symbol_MINUS(tokens[count].value));
					stack.push(10);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 4:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E1_1(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E1_1(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E1_1(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					sym_stack.push(new symbol_TIMES(tokens[count].value));
					stack.push(11);
					count++;
					break;}
				case (int)symbol.S_DIV:{
					sym_stack.push(new symbol_DIV(tokens[count].value));
					stack.push(12);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 5:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(5);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(6);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 6:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DIV:{
					symbol_NUMBER e1=(symbol_NUMBER)sym_stack.pop();
					sym_stack.push(new symbol_E2_7(e1));
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 7:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					sym_stack.push(new symbol_RBR(tokens[count].value));
					stack.push(14);
					count++;
					break;}
				case (int)symbol.S_PLUS:{
					sym_stack.push(new symbol_PLUS(tokens[count].value));
					stack.push(15);
					count++;
					break;}
				case (int)symbol.S_MINUS:{
					sym_stack.push(new symbol_MINUS(tokens[count].value));
					stack.push(16);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 8:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E1_1(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E1_1(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E1_1(e1));
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					sym_stack.push(new symbol_TIMES(tokens[count].value));
					stack.push(17);
					count++;
					break;}
				case (int)symbol.S_DIV:{
					sym_stack.push(new symbol_DIV(tokens[count].value));
					stack.push(18);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 9:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(1);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(2);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 10:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(1);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(2);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 11:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(1);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(2);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 12:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(1);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(2);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 13:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					sym_stack.push(new symbol_RBR(tokens[count].value));
					stack.push(23);
					count++;
					break;}
				case (int)symbol.S_PLUS:{
					sym_stack.push(new symbol_PLUS(tokens[count].value));
					stack.push(15);
					count++;
					break;}
				case (int)symbol.S_MINUS:{
					sym_stack.push(new symbol_MINUS(tokens[count].value));
					stack.push(16);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 14:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DIV:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 15:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(5);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(6);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 16:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(5);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(6);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 17:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(5);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(6);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 18:
				switch(nextoken){
				case (int)symbol.S_LBR:{
					sym_stack.push(new symbol_LBR(tokens[count].value));
					stack.push(5);
					count++;
					break;}
				case (int)symbol.S_NUMBER:{
					sym_stack.push(new symbol_NUMBER(tokens[count].value));
					stack.push(6);
					count++;
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 19:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_PLUS e2=(symbol_PLUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_3(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_PLUS e2=(symbol_PLUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_3(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_PLUS e2=(symbol_PLUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_3(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 20:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_MINUS e2=(symbol_MINUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_4(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_MINUS e2=(symbol_MINUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_4(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_MINUS e2=(symbol_MINUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_4(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 21:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DIV:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 22:
				switch(nextoken){
				case (int)symbol.S_NULL:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DIV:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 23:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DIV:{
					symbol_RBR e3=(symbol_RBR)sym_stack.pop();
					symbol_E1 e2=(symbol_E1)sym_stack.pop();
					symbol_LBR e1=(symbol_LBR)sym_stack.pop();
					sym_stack.push(new symbol_E2_2(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 24:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_PLUS e2=(symbol_PLUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_3(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_PLUS e2=(symbol_PLUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_3(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_PLUS e2=(symbol_PLUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_3(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 25:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_MINUS e2=(symbol_MINUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_4(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_MINUS e2=(symbol_MINUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_4(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E1 e3=(symbol_E1)sym_stack.pop();
					symbol_MINUS e2=(symbol_MINUS)sym_stack.pop();
					symbol_E1 e1=(symbol_E1)sym_stack.pop();
					sym_stack.push(new symbol_E1_4(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 26:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DIV:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_TIMES e2=(symbol_TIMES)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_5(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				default: throw new Exception("Unknown ACTION!");}
				break;
			case 27:
				switch(nextoken){
				case (int)symbol.S_RBR:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_PLUS:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_MINUS:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_TIMES:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
					stack.pop();
					stack.pop();
					GOTO();
					break;}
				case (int)symbol.S_DIV:{
					symbol_E2 e3=(symbol_E2)sym_stack.pop();
					symbol_DIV e2=(symbol_DIV)sym_stack.pop();
					symbol_E2 e1=(symbol_E2)sym_stack.pop();
					sym_stack.push(new symbol_E2_6(e1,e2,e3));
					stack.pop();
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
			case symbol.S_E1:
				stack.push(3);
				break;
			case symbol.S_E2:
				stack.push(4);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 1:
			switch(sym_top){
			case symbol.S_E1:
				stack.push(7);
				break;
			case symbol.S_E2:
				stack.push(8);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 5:
			switch(sym_top){
			case symbol.S_E1:
				stack.push(13);
				break;
			case symbol.S_E2:
				stack.push(8);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 9:
			switch(sym_top){
			case symbol.S_E1:
				stack.push(19);
				break;
			case symbol.S_E2:
				stack.push(4);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 10:
			switch(sym_top){
			case symbol.S_E1:
				stack.push(20);
				break;
			case symbol.S_E2:
				stack.push(4);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 11:
			switch(sym_top){
			case symbol.S_E2:
				stack.push(21);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 12:
			switch(sym_top){
			case symbol.S_E2:
				stack.push(22);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 15:
			switch(sym_top){
			case symbol.S_E1:
				stack.push(24);
				break;
			case symbol.S_E2:
				stack.push(8);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 16:
			switch(sym_top){
			case symbol.S_E1:
				stack.push(25);
				break;
			case symbol.S_E2:
				stack.push(8);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 17:
			switch(sym_top){
			case symbol.S_E2:
				stack.push(26);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		case 18:
			switch(sym_top){
			case symbol.S_E2:
				stack.push(27);
				break;
			default: throw new Exception("Unknown GOTO!");}
			break;
		default: throw new Exception("Unknown GOTO!");}
	}

	public symbol getRoot(){return sym_stack.get(0);}
}