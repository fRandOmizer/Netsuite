package cz.richardzan.netsuite2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {

    public static void main(String [] args) {

        //Shared stuff
        Expression one = new Expression(1);
        Expression three = new Expression(3);
        Expression five = new Expression(5);

        // Init ExpressionMap
        List<Expression> oneThree = new ArrayList<>() {
            {
                add(one);
                add(three);
            }
        };

        Expression oneMinusThree = new Expression(Operations.Minus, oneThree);

        List<Expression> minusTwo = new ArrayList<>() {
            {
                add(oneMinusThree);
            }
        };

        Expression yMap = new Expression(Operations.Abs, minusTwo);

        List<Expression> oneY = new ArrayList<>() {
            {
                add(one);
                add(yMap);
            }
        };

        Expression xMap = new Expression(Operations.Plus, oneY);

        HashMap<String, Expression> hashMap = new HashMap<>(){
            {
                put("x", xMap);
                put("y", yMap);
            }
        };

        ExpressionMap map = new ExpressionMap(hashMap);


        // Init tree
        Expression x = new Expression("x");
        Expression y = new Expression("y");

        List<Expression> fiveX = new ArrayList<>() {
            {
                add(five);
                add(x);
            }
        };

        Expression fivePlusX = new Expression(Operations.Plus, fiveX);

        List<Expression> xyOne = new ArrayList<>() {
            {
                add(x);
                add(y);
                add(one);
            }
        };

        Expression xPlusYPlusOne = new Expression(Operations.Plus, xyOne);

        List<Expression> fivePlusX_XPlusYPlusOne = new ArrayList<>() {
            {
                add(fivePlusX);
                add(xPlusYPlusOne);
            }
        };

        Expression multiply = new Expression(Operations.Multiplication, fivePlusX_XPlusYPlusOne);

        List<Expression> threeMultiply = new ArrayList<>() {
            {
                add(three);
                add(multiply);
            }
        };

        Expression minus = new Expression(Operations.Minus, threeMultiply);

        System.out.println(minus.calc(map).toString());

        minus.getReferencedSymbols().forEach((q) -> System.out.println(q));
    }
}