import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lexical{
	private String source = null;
	private ArrayList<token> tokens = new ArrayList<token>();
	private ArrayList<regExpression> regs = new ArrayList<regExpression>();
	lexical(){
		regs.add(new regExpression(8,"EXP"));
		regs.add(new regExpression(9,"E1"));
		regs.add(new regExpression(10,"E2"));
		regs.add(new regExpression(1,"\\("));
		regs.add(new regExpression(2,"\\)"));
		regs.add(new regExpression(3,"0\\.[0-9][0-9]*|[1-9][0-9]*\\.[0-9][0-9]*"));
		regs.add(new regExpression(4,"\\+"));
		regs.add(new regExpression(5,"-"));
		regs.add(new regExpression(6,"\\*"));
		regs.add(new regExpression(7,"/"));
	}

	public void Load(String fileName)  throws IOException{
		FileInputStream file = new FileInputStream(fileName);
		byte[] buf = new byte[file.available()];
		file.read(buf, 0, file.available());
		source = new String(buf); 
		file.close();
	}

	void lex(){
		lexdfs(0,0,source.length());
	}

	void lexdfs(int level, int start, int end){
		if(start >= end) return;
		String tmp = source.substring(start, end);
		if(level == regs.size()){
			String tmp2 = tmp.trim();
			if(tmp2.length()>0) System.err.println("Unknown token:"+tmp2);
			return;
		}
		int last = start;
		Matcher m = regs.get(level).regExp.matcher(tmp);
			while(m.find()){
			lexdfs(level+1, last, start+m.start());
			last = start+m.end();
			tokens.add(new token(regs.get(level).sID,m.group()));
		}
		lexdfs(level+1, last, end);
		}

	public token[] getTokens(){
		token[] tmp = new token[tokens.size()];
		tokens.toArray(tmp);
		return tmp;
	}
}
class regExpression{
	int sID;
	Pattern regExp;
	regExpression(int id, String exp){
		sID = id;
		regExp = Pattern.compile(exp, Pattern.MULTILINE);
	}
}