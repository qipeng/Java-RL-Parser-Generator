import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/*
 * Load Symbols from symbol file
 */
public class LoadSymbols{
	private ArrayList<String> symbolName = new ArrayList<String>();
	public void Load(String fileName) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String readline;
		symbolName.add("NULL");
		while((readline = reader.readLine())!= null)
			if(readline.length() > 0)
				symbolName.add(readline);
		reader.close();
	}
	
	public  String[] getSymbolName(){
		String[] tmp = new String[symbolName.size()];
		symbolName.toArray(tmp);
		return tmp;
	}
}