package cz.richardzan.netsuite2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionTest {
   
    @Test
    void testExpresionEvaulation(){
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

        Assertions.assertEquals("-45", minus.calc(map).toString());
    }

    @Test
    void testInvalidExpresionInitPlus(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> new Plus());
    }
    
    @Test
    void testInvalidExpresionInitMultiply(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> new Multiplication());
    }
    
    /**
     * Testing if recursive substitution will cause exception.
     */
    @Test
    void testInvalidExpresionSubstitution1(){
        Expression a = new Variable("a");
        
        ExpressionMap map = ExpressionMap.getInstance();
        map.addExpressionSubstitution("a", a);

        Assertions.assertThrows(IllegalArgumentException.class,() -> a.calc(map));
    }
    
    /**
     * Testing if insufficient coverage of variables  will cause exception.
     */
    @Test
    void testInvalidExpresionSubstitution2(){
        Expression b = new Variable("b");
        Expression c = new Variable("c");
        
        ExpressionMap map = ExpressionMap.getInstance();
        map.addExpressionSubstitution("b", new Value(1));

        Assertions.assertThrows(IllegalArgumentException.class, () -> c.calc(map));
    }
}
