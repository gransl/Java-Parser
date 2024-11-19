public class TestClass {
    private String str;
    private int num;
    private byte testByte;
    private char testChar;
    private Suit suit;
    public static final double CONSTANT = 3.0;


    public TestClass() {
        this("", 0, (byte) 0, ' ', Suit.SPADES); // need to downcast the integer
    }

    public TestClass(String str, int num, byte testByte, char testChar, Suit suit) {
        this.str = str;
        this.num = num;
        this.testByte = testByte;
        this.testChar = testChar;
        this.suit = suit;
    }

    protected enum Suit {
        SPADES, HEARTS, CLUBS, DIAMONDS;
    }

    public void setStr(String newStr) {
        str = newStr;
    }

}
