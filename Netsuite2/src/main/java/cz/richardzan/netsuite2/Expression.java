package cz.richardzan.netsuite2;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Expression {

    /**
     * Calculate the result of operations within the tree
     * @param map variables expression substitution
     * @return Result number of tree
     */
    public Number calc(ExpressionMap map){
        getReferencedSymbols().forEach((x) -> {
            if (map.getExpression(x) == null) {
                throw new IllegalArgumentException("Not specified substitution for " + x + "!");
            }
            
            if (map.getExpression(x).getReferencedSymbols().contains(x)){
                throw new IllegalArgumentException("Cannot use the same name for an variable in substitution. It will cause infinite substitution!");
            }
        });
        
        return recursiveCalculation(map, getExpressions());
    }
    
    /**
     * Returns a set of symbol names that the expression requires to be completely evaluable.
     * @return referenced symbols
     */
    public Set<String> getReferencedSymbols() {
        
        Set<String> result = new HashSet<>();

        getExpressions().forEach((exp) -> {
            result.addAll(exp.getReferencedSymbols());
        });

        return result;
    }

    protected abstract List<Expression> getExpressions();
    
    protected abstract Number recursiveCalculation(ExpressionMap map, List<Expression> expressions);
}

