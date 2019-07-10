package parsetree;


public class Driver {
    public static void main(String[] args) {
        ExpressionTree tree = new ExpressionTree("32 * 3 + 2 / 5 ^    7");
        System.out.println(tree.eval());
        System.out.println(tree);
    }
}
