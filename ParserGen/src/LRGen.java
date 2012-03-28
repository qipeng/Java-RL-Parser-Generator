
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class LRGen {
	private String[] symbolName;
	private boolean[] symbolTerminal;	//true:Terminal
	private Production[] productions;
	private boolean[][] First;
	private ArrayList<LRProduction> productionList =  new ArrayList<LRProduction>();
	private Action[][] actionTable;	
	private AttGen attGen = null;
	
	LRGen(String[] symbolNames, AttGen att){
		this.symbolName = symbolNames;
		symbolTerminal = new boolean[symbolNames.length];
		First  = new boolean[symbolNames.length][symbolNames.length];
		for(int i = 0; i < symbolNames.length; i++){
			symbolTerminal[i] = true;
			for(int j = 0; j < symbolNames.length; j++)
				First[i][j] = false;
		}
		
		// AttGen
		attGen = att;
	}
	
	private int getSymbolID(String s) throws Exception{
		for(int i = 0; i < symbolName.length; i++)
			if(symbolName[i].equals(s))
				return i;
		throw new Exception("Unknown Symbol:"+s);
	}

	public void Load(String fileName) throws Exception{
		ArrayList<Production> p = new ArrayList<Production>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String readline;
		String[] tmp;
		int l;
		
		boolean new_production = false;
		
		while((readline = reader.readLine()) != null){
			// Judge if the input string is the attribute description
			// of the last production.
			int j = 0;
			while ((j < readline.length()) && (readline.charAt(j) == ' ')) j++;
			if (j + 1 < readline.length()) {
				// The line starts with "//", so its the attribute
				// description of the last production.
				if ((readline.charAt(j) == '/') && (readline.charAt(j + 1) == '/')) {
					attGen.add_AD_to_production(readline.substring(j + 2), 
							new_production, p.get(p.size() - 1));
					
					new_production = false;
					
					continue;
				}
			}
			
			if (new_production) {
				attGen.add_AD_to_production("", 
						new_production, p.get(p.size() - 1));
			}
					
			tmp = reader.readLine().split(" ");
			l = getSymbolID(readline);
			int[] itmp = new int[tmp.length];
			for(int i = 0; i < tmp.length; i++)
				itmp[i] = getSymbolID(tmp[i].trim());
			symbolTerminal[l] = false;
			p.add(new Production(l,itmp));
			
			new_production = true;
		}
		
		if (new_production) {
			attGen.add_AD_to_production("", 
					new_production, p.get(p.size() - 1));
		}
		reader.close();
		
		productions = new Production[p.size()];
		p.toArray(productions);
	}
	
	public void ALL(LRProduction p){
		for(int i = 0; i < p.p.size(); i++){
			OneLRProduction tmp = p.p.get(i);
			Production ptmp = productions[tmp.productionID];
			if((tmp.pointer < ptmp.rightIDs.length)&&(!symbolTerminal[ptmp.rightIDs[tmp.pointer]])){
				int ltmp = ptmp.rightIDs[tmp.pointer];
				for(int j = 0; j < productions.length; j++)
					if(productions[j].leftID == ltmp){
						if(tmp.pointer == ptmp.rightIDs.length - 1)
							p.add(new OneLRProduction(j,tmp.nextSymbol));
						else{
							//First
							int kk = ptmp.rightIDs[tmp.pointer+1];
							for(int k = 0; k < symbolName.length; k++)
								if(First[kk][k])
								p.add(new OneLRProduction(j,k));
						}
					}
			}
		}		
	}
	
	public void CalFirst(){
		boolean flag;
		for(int i = 0; i < symbolName.length; i++)
			if(symbolTerminal[i])
				First[i][i] = true;
		do{
			flag = false;
			for(int i = 0; i < productions.length; i++)
				for(int j = 0; j < symbolName.length; j++)
					if((!First[productions[i].leftID][j])&&(First[productions[i].rightIDs[0]][j])){
						First[productions[i].leftID][j] = true;
						flag = true;
					}
		}while(flag);
	}
	
	public int getLRproductionID(LRProduction p){
		for(int i = 0; i < productionList.size(); i++)
			if(productionList.get(i).check(p))
				return i;
		productionList.add(p);
		return (productionList.size() - 1);
	}	
	public void LRTable(){
		CalFirst();
		//States
		LRProduction ptmp = new LRProduction(symbolName.length);
		ptmp.add(new OneLRProduction(0, 0));	//production 0(S'->x);symbol_NULL #
		ALL(ptmp);
		productionList.add(ptmp);
		for(int i = 0; i < productionList.size(); i++){
			ArrayList<OneLRProduction> cur = productionList.get(i).p;
			for(int j = 0; j< symbolName.length; j++){
				LRProduction ltmp = new LRProduction(symbolName.length);
				for(int k = 0; k < cur.size(); k++){
					OneLRProduction ocur = cur.get(k);
					if(ocur.pointer < productions[ocur.productionID].rightIDs.length){
						if(productions[ocur.productionID].rightIDs[ocur.pointer] == j)
							ltmp.add(new OneLRProduction(ocur.productionID, ocur.pointer+1, ocur.nextSymbol));
					}
				}
				if(ltmp.p.size() > 0){
					ALL(ltmp);
					productionList.get(i).nextLR[j] = getLRproductionID(ltmp);
				}			
			}
		}

		//Table
		actionTable = new Action[productionList.size()][symbolName.length];
		for(int i = 0; i < productionList.size(); i++){
			LRProduction cur = productionList.get(i);				
			for(int j = 0; j < symbolName.length; j++){
				if(cur.nextLR[j]>0){
				if(symbolTerminal[j]){
					if(actionTable[i][j] != null)
						System.err.println("Warning:Confilict!");
					else
						actionTable[i][j] = new Action(ActionType.Shift, cur.nextLR[j]);
				}
				else{
					if(actionTable[i][j] != null)
						System.err.println("Warning:Confilict!");
					else
						actionTable[i][j] = new Action(ActionType.GOTO, cur.nextLR[j]);
				}
				}
			}	
			for(int j = 0; j < cur.p.size(); j++){
				OneLRProduction tmp = cur.p.get(j);
				if((tmp.pointer == productions[tmp.productionID].rightIDs.length)){
					if((tmp.productionID == 0)&&(tmp.nextSymbol == 0))
						actionTable[i][0] = new Action(ActionType.Acc, 0);
					else
						actionTable[i][tmp.nextSymbol] = new Action(ActionType.Reduce, tmp.productionID);
				}
			}		
		}
		//Print Table
		System.out.print("\t");
		for(int j = 0; j < symbolName.length; j++){
			System.out.print(symbolName[j]+"\t");
		}
		System.out.println();
		for(int i = 0; i < productionList.size(); i++){
			System.out.print(i+":\t");
			for(int j = 0; j < symbolName.length; j++)
				if(actionTable[i][j] != null)
					System.out.print(actionTable[i][j].at.toString()+actionTable[i][j].v+"\t");
				else
					System.out.print("\t");
			System.out.println();
		}
		
	}
	
	
	public Production[] getProductions(){
		return productions;
	}
	
	public Action[][] getActionTable(){
		return actionTable;
	}
}

class LRProduction{
	public ArrayList<OneLRProduction> p;
	public int[] nextLR;		//symbolName[i] --> nextLRProduction
	
	public LRProduction(int size){
		p = new ArrayList<OneLRProduction>();
		nextLR = new int[size];
		for(int i = 0; i < size; i++)
			nextLR[i] = -1;
	}
	
	public void add(OneLRProduction o){
		int l = p.size();
		for(int i = 0; i < l; i++)
			if(p.get(i).check(o))
				return;
		p.add(o);
	}
	
	public boolean check(LRProduction l){
		if(l.p.size() != p.size())
			return false;
		int len = p.size();
		for(int i = 0; i < len; i++){
			boolean flag = false;
			OneLRProduction tmp = p.get(i);
			for(int j = 0; j < len; j++)
				if(tmp.check(l.p.get(j))){
					flag = true;
					break;
				}
			if(!flag)
				return false;
		}		
		return true;
	}
}

class OneLRProduction{
	public int productionID, pointer, nextSymbol;
	OneLRProduction(int productionID, int nextSymbol){
		this.pointer = 0;
		this.productionID = productionID;
		this.nextSymbol = nextSymbol;
	}
	OneLRProduction(int productionID, int pointer, int nextSymbol){
		this.pointer = pointer;
		this.productionID = productionID;
		this.nextSymbol = nextSymbol;
	}
	public boolean check(OneLRProduction o){
		return (this.pointer == o.pointer)&&(this.productionID == o.productionID)&&(this.nextSymbol == o.nextSymbol);
	}
}

class Production{
	public int leftID;
	public int[] rightIDs;
	
	// Attribute description
	
	//
	
	Production(int lID,int[] rIDs){
		leftID = lID;
		rightIDs = rIDs;
	}
}

class Action{
	public ActionType at;
	public int v;
	Action(ActionType at, int v){
		this.at = at;
		this.v = v;
	}
}

enum ActionType{Shift,Reduce,Acc,GOTO}
