package parsetree;

import java.util.ArrayList;
import java.util.Stack;

public class Postfix {
    
    private final String infixExpr;
    private final String postfixExpr;
    private final ArrayList<String> infixTkns;
    private final ArrayList<String> postfixTkns;
    
    Postfix(String infixExpr) {
        this.infixExpr = infixExpr;
        this.infixTkns = tokenizeExpr(infixExpr);
        this.postfixTkns = convertToPostfix(infixExpr);
        this.postfixExpr = convertPostTkns();
    }
    
    
    // converts an arraylist of postfix tkns into an expression
    private String convertPostTkns() {
        return convertPostTkns(postfixTkns);
    }
    
    // converts postfix tokens into a string
    private static String convertPostTkns(ArrayList<String> postfixTkns) {
        if(postfixTkns.isEmpty())
            return "";
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < postfixTkns.size()- 1; i++)
            sb.append(postfixTkns.get(i).concat(" "));
        sb.append(postfixTkns.get(postfixTkns.size()-1));
        
        return sb.toString();
    }
    
    // returns the postfix expression corresponding to an infix expression
    public static String convert(String infixExpr) {
        return convertPostTkns(convertToPostfix(infixExpr));
    }
    
    // returns the postfix expression corresponding to an infix expression as
    // an ArrayList of tokens
    public static ArrayList<String> convertTkns(String infixExpr) {
        return convertToPostfix(infixExpr);
    }
    
    // getters
    public String getPostExpr() {
        return this.postfixExpr;
    }
    
    public String getInExpr() {
        return this.infixExpr;
    }
    
    public ArrayList<String> getInTkns() {
        return this.infixTkns;
    }
    
    public ArrayList<String> getPostTkns() {
        return this.postfixTkns;
    }
    
    // converts an infix expression to postfix expr as an ArrayList of tokens
    private static ArrayList<String> convertToPostfix(String expr) {
        // if expr is empty or null, return an empty ArrayList
        if(expr == null || expr.equals(""))
            return new ArrayList<>();
        
        ArrayList<String> infix = tokenizeExpr(expr);
        ArrayList<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        
        
        // for each token in the infix expression
        for(String token : infix) {
            
            // if the token is not an operator
            // put it in the postfix expression
            if(!isOperator(token)) {
                postfix.add(token);
            }
            
            // if the token is an opening parentheses, 
            // just push it onto the stack
            else if(token.equals("(")) {
                stack.push(token);
            }
            
            // if the token is a closing parentheses,
            // empty the stack into postfix until an opening parentheses
            // is encountered. Then pops the opening parenteses from the stack
            else if(token.equals(")")) {
                while(!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                }
                stack.pop();
            }
            
            // if the token is an operator
            // check if precedence of the operator at the top of the stack
            // is greater than the precedance of the token
            // if it is, pop the stack into postfix until it isn't
            // then push our token to the top of the stack
            else {
                while(!stack.isEmpty() && (prec(stack.peek()) > prec(token)) && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());                    
                }
                
                stack.push(token);
                 
            }
        }
        
        
        // now empty the stack into postfix
        while(!stack.isEmpty()) {
            postfix.add(stack.pop());
        }
        
        return postfix;
    }
    
    
    // returns -1 if token passed to it is not an operator
    private static int prec(String token) {
        switch(token) {
            case "(": case ")":
                return 7;
            case "==": case "!=":
                return 6;
            case "^":
                return 5;
            case "*": case "/":
                return 4;
            case "+": case "-":
                return 3;
            case "&&":
                return 2;
            case "||":
                return 1;
            case "=":
                return 0;
            default: return -1;
        }
    }
    
    // returns whether a given token is an operator
    public static boolean isOperator(String str) {
        return
                str.equals("+")
                || str.equals("-")
                || str.equals("*")
                || str.equals("/")
                || str.equals("^")
                || str.equals("(")
                || str.equals(")")
                || str.equals("=")
                || str.equals("==")
                || str.equals("||")
                || str.equals("&&");
    }
    
    // checks if postfix expression is valid 
    public boolean isValidExpr() {
        int operators = 0, operands = 0;
        
        if(infixExpr == null || infixExpr.equals(""))
            return false;
        
        // if we only have one value, and its an operand, return true
        if(postfixTkns.size() == 1) {
            try {
                Double.parseDouble(postfixTkns.get(0));
                return true;
            }
            catch(NumberFormatException e) {
                return false;
            }
        }
        
        // check that the first two values are operands
        try {
            Double.parseDouble(postfixTkns.get(0));
            Double.parseDouble(postfixTkns.get(1));
        }
        catch(NumberFormatException e) {
            return false;
        }
        
        // if the last expression isn't an operator, this cannot be valid
        if(!isOperator(postfixTkns.get(postfixTkns.size()-1)))
            return false;
        
        // check that there is one less operand than operators
        for(String token : postfixTkns) {
            if(isOperator(token)) 
                operators++;
            else 
                operands++;
        }
        
        if (operators != operands - 1)
            return false;
        
        
        return true;
    }
    
    // tokenize an expression into an ArrayList
    private static ArrayList<String> tokenizeExpr(String expr) {
        if(expr == null) 
            return null;
        
        ArrayList<String> tkns = MathParser.parse(expr);
        
        return tkns;
    }
    
    @Override
    public String toString() {
        return this.postfixExpr;
    }
}