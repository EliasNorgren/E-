import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

    private final ArrayList<Token>[] tokens;
    private final HashMap<String, Integer> digits;
    private int i;

    public Parser(ArrayList<Token>[] tokens) {
        this.tokens = tokens;
        this.digits = new HashMap<>();
        i = 0;
    }

    public void parse() throws SyntaxError {
        while(!tokens[i].get(0).value.equals("done")){
            if(tokens[i].get(0).value.equals("dig")){
                digits.put(tokens[i].get(1).value, Integer.parseInt(tokens[i].get(2).value));
            }else if(tokens[i].get(0).value.equals("add")){
                performCalculation("add");
            }else if(tokens[i].get(0).value.equals("sub")){
                performCalculation("sub");
            }else if(tokens[i].get(0).value.equals("mul")){
                performCalculation("mul");
            }else if(tokens[i].get(0).value.equals("div")){
                performCalculation("div");
            }else if(tokens[i].get(0).value.equals("pow")){
                performCalculation("pow");
            }else if(tokens[i].get(0).value.equals("mod")){
                performCalculation("mod");
            }else if(tokens[i].get(0).value.equals("out")){
                System.out.println(digits.get(tokens[i].get(1).value));
            }else if(tokens[i].get(0).value.equals("if")){
                interpretIf();
            }else if(tokens[i].get(0).value.equals("while")){
                if(!checkIfOrWhileVal()){
                    traverseToEndOfWhile();
                    continue;
                }
            }else if(tokens[i].get(0).value.equals("stopW")){
                reverseToWhile();
                continue;
            }

            i++;
        }
    }

    private void reverseToWhile() {
        int skip = 0;
        i--;
        while(true){
            if(tokens[i].get(0).value.equals("stopW")){
                skip++;
            }

            if(tokens[i].get(0).value.equals("while")){
                if(skip == 0){
                    break;
                }
                skip --;
            }
            i--;
        }
    }

    private void traverseToEndOfWhile() {
        int skip = 0;
        i++;
        while (true){
            if(tokens[i].get(0).value.equals("while")){
                skip ++;
            }

            if(tokens[i].get(0).value.equals("stopW")){
                if(skip == 0){
                    break;
                }
                skip --;
            }
            i ++;
        }
        i++;
    }

    private void interpretIf() {
        boolean val = checkIfOrWhileVal();
        if (!val){
            traverseToEndOfStop();
        }
    }

    private boolean checkIfOrWhileVal() {
        int a,b;
        if(tokens[i].get(1).type.equals("digit")){
            a = Integer.parseInt(tokens[i].get(1).value);
        }else {
            a = digits.get(tokens[i].get(1).value);
        }
        if(tokens[i].get(3).type.equals("digit")){
            b = Integer.parseInt(tokens[i].get(3).value);
        }else {
            b = digits.get(tokens[i].get(3).value);
        }
        String comparator = tokens[i].get(2).value;
        /* >, <, =, <=, >= */
        boolean val = false;
        switch (comparator){
            case "<" : val = a < b;break;
            case ">" : val = a > b;break;
            case "=" : val = a == b;break;
            case "<=" : val = a <= b;break;
            case ">=" : val = a <= b;break;
        }
        return val;
    }

    private void traverseToEndOfStop() {
        int skip = 0;
        i++;
        while (true){
            if(tokens[i].get(0).value.equals("if")){
                skip ++;
            }

            if(tokens[i].get(0).value.equals("stop")){
                if(skip == 0){
                    break;
                }
                skip --;
            }
            i ++;
        }
    }

    private void performCalculation(String opp) throws SyntaxError {
        if(tokens[i].get(1).type.equals("identifier") && tokens[i].get(2).type.equals("digit")){
            throw new SyntaxError("Can't compute variable " + tokens[i].get(1).type + " to a number.");
        }
        int a;
        if(tokens[i].get(1).type.equals("identifier")){
            a = digits.get(tokens[i].get(1).value);
        }else{
            a = Integer.parseInt(tokens[i].get(1).value);
        }
        int b = digits.get(tokens[i].get(2).value);
        switch (opp){
            case "add" : digits.put(tokens[i].get(2).value, b + a); break;
            case "sub" : digits.put(tokens[i].get(2).value, b - a); break;
            case "mul" : digits.put(tokens[i].get(2).value, b * a); break;
            case "div" : digits.put(tokens[i].get(2).value, b / a); break;
            case "pow" : digits.put(tokens[i].get(2).value, (int)Math.pow(a,b)); break;
            case "mod": digits.put(tokens[i].get(2).value, b % a);break;
        }
    }
}
