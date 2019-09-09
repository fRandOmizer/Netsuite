package cz.richardzan.netsuite2;

import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionMapTest {
    
    @Test
    void testInvalidExpresionMap(){
        Expression x = new Expression("x");
       
        HashMap<String, Expression> hashMap = new HashMap<>(){
            {
                put("y", x);
            }
        };
        
        Assertions.assertThrows(IllegalArgumentException.class,() -> new ExpressionMap(hashMap));
    }
}
