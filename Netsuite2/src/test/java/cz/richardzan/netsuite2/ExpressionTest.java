package cz.richardzan.netsuite2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionTest {
   
    @Test
    void testExpresionEvaulation(){
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

        Assertions.assertEquals("-45", minus.calc(map).toString());
    }
    
    @Test
    void testInvalidExpresionInitMinus(){
        Expression one = new Expression(1);
        Expression three = new Expression(3);
        Expression five = new Expression(5);

        List<Expression> oneThreeFive = new ArrayList<>() {
            {
                add(one);
                add(three);
                add(five);
            }
        };
        Assertions.assertThrows(IllegalArgumentException.class,() -> new Expression(Operations.Minus, oneThreeFive));
    }
    
    @Test
    void testInvalidExpresionInitDivide(){
        Expression one = new Expression(1);
        Expression three = new Expression(3);
        Expression five = new Expression(5);

        List<Expression> oneThreeFive = new ArrayList<>() {
            {
                add(one);
                add(three);
                add(five);
            }
        };
        Assertions.assertThrows(IllegalArgumentException.class,() -> new Expression(Operations.Division, oneThreeFive));
    }
    
    @Test
    void testInvalidExpresionInitPlus(){
        List<Expression> none = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class,() -> new Expression(Operations.Plus, none));
    }
    
    @Test
    void testInvalidExpresionInitMultiply(){
        List<Expression> none = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class,() -> new Expression(Operations.Multiplication, none));
    }
    
    @Test
    void testInvalidExpresionInitAbs(){
        List<Expression> none = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class,() -> new Expression(Operations.Abs, none));
    }
    
    @Test
    void testInvalidExpresionSubstitution1(){
        Expression x = new Expression("x");
       
        HashMap<String, Expression> hashMap = new HashMap<>(){
            {
                put("x", x);
            }
        };
        
        ExpressionMap map = new ExpressionMap(hashMap);

        Assertions.assertThrows(IllegalArgumentException.class,() -> x.calc(map));
    }
    
    @Test
    void testInvalidExpresionSubstitution2(){
        Expression x = new Expression("x");
       
        HashMap<String, Expression> hashMap = new HashMap<>(){
            {
                put("x", x);
            }
        };
        
        Expression y = new Expression("y");
        
        ExpressionMap map = new ExpressionMap(hashMap);

        Assertions.assertThrows(IllegalArgumentException.class, () -> y.calc(map));
    }
}
