public class Token {
    String type;
    String value;
    int index;

    public Token(String type, String value, int index){
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString(){
        return type + " " + value + " ";
    }
}
