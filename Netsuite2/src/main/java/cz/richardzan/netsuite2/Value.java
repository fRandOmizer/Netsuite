package cz.richardzan.netsuite2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Value extends Expression {

    private Number value;
    
    public Value (Number value) {
        super();
        this.value = value;
    }
    
    @Override
    protected List<Expression> getExpressions() {
        return new ArrayList<>();
    }

    @Override
    protected Number recursiveCalculation(ExpressionMap map, List<Expression> expressions) {
        return value;
    }

    @Override
    public Set<String> getReferencedSymbols() {
        return new HashSet<>();
    }
    
}
