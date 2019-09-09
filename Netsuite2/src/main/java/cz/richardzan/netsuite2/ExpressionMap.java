package cz.richardzan.netsuite2;

import java.util.HashMap;

public class ExpressionMap {
    
    private HashMap<String, Expression> map = new HashMap<String, Expression>();

    public ExpressionMap(HashMap<String, Expression> map) {
        this.map = map;
        
        map.forEach((x, y) -> {
            y.getReferencedSymbols().forEach((a) -> {
                if (map.get(a) == null){
                    throw new IllegalArgumentException("Expression map does not cover all variables");
                }
            });
        });
    }

    public Expression getExpression(String symbolName){
       return map.get(symbolName);
    }
}
