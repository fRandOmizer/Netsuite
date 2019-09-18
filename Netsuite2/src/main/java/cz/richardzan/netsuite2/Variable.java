package cz.richardzan.netsuite2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Variable extends Expression{

    private String variable;
    
    public Variable (String variable) {
        super();
        this.variable = variable;
    }
    
    @Override
    protected List<Expression> getExpressions() {
        return new ArrayList<>();
    }

    @Override
    protected Number recursiveCalculation(ExpressionMap map, List<Expression> expressions) {
        Expression h = map.getExpression(this.variable);
        return new BigDecimal(h.recursiveCalculation(map, h.getExpressions()).toString());
    }

    @Override
    public Set<String> getReferencedSymbols() {
        return Stream.of(this.variable).collect(Collectors.toSet());
    }
    
}
