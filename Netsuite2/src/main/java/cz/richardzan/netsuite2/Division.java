package cz.richardzan.netsuite2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Division extends Expression{
    private List<Expression> expressions;

    public Division(Expression exp1, Expression exp2){
        super();
        
        if (exp1 == null){
            throw new IllegalArgumentException("Expression exp1 cannot be null.");
        }
        
        if (exp2 == null){
            throw new IllegalArgumentException("Expression exp2 cannot be null.");
        }
        
        expressions = new ArrayList<>();
        expressions.add(exp1);
        expressions.add(exp2);
    }
    
    
    @Override
    protected List<Expression> getExpressions() {
        return this.expressions;
    }

    @Override
    protected Number recursiveCalculation(ExpressionMap map, List<Expression> expressions) {
        Expression exp1 = expressions.get(0);
        Expression exp2 = expressions.get(1);
        
        BigDecimal a = new BigDecimal(exp1.recursiveCalculation(map, exp1.getExpressions()).toString());
        BigDecimal b = new BigDecimal(exp2.recursiveCalculation(map, exp2.getExpressions()).toString());
        
        return a.divide(b);
    }
    
    
}
