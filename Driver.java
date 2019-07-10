package parsetree;


public class Driver {
    public static void main(String[] args) {
        // create a new expression tree with this expression to be parsed
        ExpressionTree tree = new ExpressionTree("32 * 3 + 2 / 5 ^    7");
        // print the value of the expression
        System.out.println(tree.eval());
        // print the expression in postfix notation
        System.out.println(tree);
    }
}
