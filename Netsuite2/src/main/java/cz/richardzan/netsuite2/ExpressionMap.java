package cz.richardzan.netsuite2;

import java.util.HashMap;
import java.util.Map;

public class ExpressionMap {
    
    private static ExpressionMap expressionMap;
    
    private HashMap<String, Expression> map = new HashMap<String, Expression>();

    private ExpressionMap() {
        this.map = new HashMap<>();
    }
    
    // static method to create instance of Singleton class 
    public static ExpressionMap getInstance() 
    { 
        if (expressionMap == null) 
            expressionMap = new ExpressionMap(); 
  
        return expressionMap; 
    } 

    public Expression getExpression(String symbolName){
       return map.get(symbolName);
    }
    
    public void addExpressionSubstitution(String key, Expression value){
        
        if (map.get(key) != null) {
            throw new IllegalArgumentException("Expression " + key + " has been already substituted by other definition.");
        }
        map.put(key, value);
    }
}
