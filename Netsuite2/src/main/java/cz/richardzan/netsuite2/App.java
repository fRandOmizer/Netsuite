package cz.richardzan.netsuite2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {

    public static void main(String [] args) {

        // Init ExpressionMap
        Expression oneMinusThree = new Minus(new Value(1), new Value(3));

        Expression yMap = new AbsoluteValue(oneMinusThree);
        Expression xMap = new Plus(new Value(1), new Variable("y"));

        ExpressionMap map = ExpressionMap.getInstance();
        map.addExpressionSubstitution("x", xMap);
        map.addExpressionSubstitution("y", yMap);
        
        // Init tree
        Expression fivePlusX = new Plus(new Value(5), new Variable("x"));
        Expression xPlusYPlusOne = new Plus(new Variable("x"), new Variable("y"), new Value(1));

        Expression multiply = new Multiplication(fivePlusX, xPlusYPlusOne);
        Expression minus = new Minus(new Value(3), multiply);

        System.out.println(minus.calc(map).toString());

        minus.getReferencedSymbols().forEach((q) -> System.out.println(q));
    }
}