package cz.richardzan.netsuite2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Multiplication extends Expression{
    private List<Expression> expressions;

    public Multiplication(Expression... exps){
        super();
        
        expressions = new ArrayList<>();
        
        if (exps.length == 0) {
            throw new IllegalArgumentException("At least one Expression is required.");
        }
        
        for (Expression exp: exps){
            if (exp == null){
                throw new IllegalArgumentException("Expression exp1 cannot be null.");
            }
            expressions.add(exp);
        }
    }
    
    @Override
    protected List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    protected Number recursiveCalculation(ExpressionMap map, List<Expression> expressions) {
        BigDecimal result =  new BigDecimal( "1");
        
        for (Expression a : expressions){
            BigDecimal value = new BigDecimal(a.recursiveCalculation(map, a.getExpressions()).toString());
            
            result =  result.multiply(value);
        }
        
        return result;
    }
}
