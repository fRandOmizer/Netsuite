package cz.richardzan.netsuite2;

import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionMapTest {
    
    /**
     * Testing if adding substitution for the same variable will cause exception
     */
    @Test
    void testInvalidExpresionMap(){
        Expression z = new Variable("z");

        ExpressionMap map = ExpressionMap.getInstance();
        map.addExpressionSubstitution("z", new Value(1));
        
        Assertions.assertThrows(IllegalArgumentException.class,() -> map.addExpressionSubstitution("z", new Value(2)));
    }
}
