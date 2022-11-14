import java.util.ArrayList;
import java.util.Scanner;

public class Lexer {
    public ArrayList<Token>[] parse(Scanner sc){

        StringBuilder sb = new StringBuilder();

        while(sc.hasNext()){
            String s = sc.nextLine();
            sb.append(s.replaceAll("\t", ""));

        }
        String[] splitted = sb.toString().split("\\.");
        ArrayList<Token>[] tokens = new ArrayList[sb.length()];
        int i = 0;
        for(String s : splitted){
            tokens[i] = new ArrayList<>();
            String[] spaceSplitted = s.split(" ");

            switch (spaceSplitted[0]){
                case "dig": tokens[i] = this.parseDig(spaceSplitted, i); break;
                case "done": tokens[i] = this.parseSingleKeyWord("done", i); break;
                case "add": tokens[i] = this.parseOperationBin(i, "add", spaceSplitted); break;
                case "sub": tokens[i] = this.parseOperationBin(i, "sub", spaceSplitted); break;
                case "mul": tokens[i] = this.parseOperationBin(i, "mul", spaceSplitted); break;
                case "div": tokens[i] = this.parseOperationBin(i, "div", spaceSplitted); break;
                case "pow": tokens[i] = this.parseOperationBin(i, "pow", spaceSplitted); break;
                case "out": tokens[i] = this.parseOut(spaceSplitted, i); break;
                case "if": tokens[i] = this.parseIfOrWhile(spaceSplitted, i, "if"); break;
                case "stop": tokens[i] = this.parseSingleKeyWord("stop", i);break;
                case "while": tokens[i] = this.parseIfOrWhile(spaceSplitted, i, "while");break;
                case "stopW": tokens[i] = this.parseSingleKeyWord("stopW", i);
            }

            i++;
        }
        return tokens;
    }

    private ArrayList<Token> parseIfOrWhile(String[] spaceSplitted, int i, String keyword) {
        ArrayList<Token> tokens = new ArrayList<>();

        tokens.add(new Token("keyword", keyword, i));

        if(isDigit(spaceSplitted[1])){
            tokens.add(new Token("digit", spaceSplitted[1], i));
        }else {
            tokens.add(new Token("identifier", spaceSplitted[1], i));
        }

        tokens.add(new Token("operator", spaceSplitted[2], i));

        if(isDigit(spaceSplitted[3])){
            tokens.add(new Token("digit", spaceSplitted[3], i));
        }else{
            tokens.add(new Token("identifier", spaceSplitted[3], i));
        }

        return tokens;
    }


    private ArrayList<Token> parseOut(String[] spaceSplitted, int i) {
        ArrayList<Token> tokens = new ArrayList<>();

        tokens.add(new Token("keyword", "out", i));
        tokens.add(new Token("identifier", spaceSplitted[1], i));

        return tokens;
    }

    private ArrayList<Token> parseOperationBin(int i, String operation, String[] spaceSplitted) {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token("keyword", operation, i));
        if(isDigit(spaceSplitted[1])){
            tokens.add(new Token("digit", spaceSplitted[1], i));
        }else{
            tokens.add(new Token("identifier", spaceSplitted[1], i));
        }

        if(isDigit(spaceSplitted[2])){
            tokens.add(new Token("digit", spaceSplitted[2], i));
        }else{
            tokens.add(new Token("identifier", spaceSplitted[2], i));
        }
        return tokens;
    }

    private ArrayList<Token> parseSingleKeyWord(String done, int i) {
        ArrayList<Token> end = new ArrayList<>();
        end.add(new Token("keyword", done, i));
        return end;
    }

    private ArrayList<Token> parseDig(String[] spaceSplitted, int index) {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token("keyword", "dig", index));
        tokens.add(new Token("identifier", spaceSplitted[1], index));
        tokens.add(new Token("digit", spaceSplitted[2], index));
        return tokens;
    }

    private boolean isDigit(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
