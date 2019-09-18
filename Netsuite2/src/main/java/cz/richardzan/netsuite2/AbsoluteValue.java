package cz.richardzan.netsuite2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class AbsoluteValue extends Expression{
    
    private List<Expression> expressions;

    public AbsoluteValue (Expression exp){
        super();
        
        if (exp == null){
            throw new IllegalArgumentException("Expression cannot be null.");
        }
        
        expressions = new ArrayList<>();
        expressions.add(exp);
    }
    
    @Override
    protected List<Expression> getExpressions() {
        return this.expressions;
    }

    @Override
    protected Number recursiveCalculation(ExpressionMap map, List<Expression> expressions) {
        Expression exp = expressions.get(0);
        
        return new BigDecimal(exp.recursiveCalculation(map, exp.getExpressions()).toString()).abs();          
    }
    
}
