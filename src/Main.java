import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, SyntaxError {
        Scanner sc = new Scanner(new File("D:\\Java\\Language\\src\\test.E"));
        Lexer lex = new Lexer();
        ArrayList<Token>[] tokens = lex.parse(sc);
        Parser p = new Parser(tokens);
        p.parse();
    }
}
