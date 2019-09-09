package cz.richardzan.netsuite2;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Expression {

    private List<Expression> expressions;
    private Operations operation;
    private Number number;
    private String variable;

    public Expression(Operations operation, List<Expression> expressions) {
        this.variable = null;
        this.number = null;
        this.operation = operation;
        this.expressions = expressions;
        
        switch(this.operation) {
            case Plus:
                if (expressions.size() == 0) 
                    throw new IllegalArgumentException("Operation plus expect at least one expression!");
                break;
            case Minus:
                if (expressions.size() != 2) 
                    throw new IllegalArgumentException("Operation minus expect two expressions!");
                break;
            case Multiplication:
                if (expressions.size() == 0) 
                    throw new IllegalArgumentException("Operation multiplication expect at least one expression!");
                break;
            case Division:
                if (expressions.size() != 2) 
                    throw new IllegalArgumentException("Operation division expect two expressions!");
                break;
            case Abs:
                if (expressions.size() != 1) 
                    throw new IllegalArgumentException("Operation abs expect one expression!");
                break;
        }
    }

    public Expression(String variable) {
        this.variable = variable;
        this.number = null;
        this.operation = Operations.Variable;
        this.expressions = null;
    }

    public Expression(Number number) {
        this.variable = null;
        this.number = number;
        this.operation = Operations.Value;
        this.expressions = null;
    }

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
                throw new IllegalArgumentException("Cannot use the same name for an variable in substitution, It will cause be not finite substitution!");
            }
        });
        
        return recursiveCalculation(map, expressions);
    }

    private Number recursiveCalculation(ExpressionMap map, List<Expression> expressions){
        
        Number result =  new BigDecimal( "0");

        switch(operation) {
            case Plus:
                for (Expression a : expressions){
                    result =  new BigDecimal(result.toString())
                            .add( new BigDecimal(a.recursiveCalculation(map, a.expressions).toString()));
                }
                break;
            case Minus:
                Expression b = expressions.get(0);
                Expression c = expressions.get(1);
                result =  new BigDecimal(b.recursiveCalculation(map, b.expressions).toString()).add(
                        new BigDecimal(c.recursiveCalculation(map, c.expressions).toString()).multiply(
                                new BigDecimal("-1")
                        )
                );
                break;
            case Multiplication:
                result =  new BigDecimal("1");
                for (Expression d : expressions){
                    result =  new BigDecimal(result.toString())
                            .multiply( new BigDecimal(d.recursiveCalculation(map, d.expressions).toString()));
                }
                break;
            case Division:
                Expression e = expressions.get(0);
                Expression f = expressions.get(1);
                result =  new BigDecimal(e.recursiveCalculation(map, e.expressions).toString()).divide(
                        new BigDecimal(f.recursiveCalculation(map, f.expressions).toString())
                );
                break;
            case Abs:
                Expression g = expressions.get(0);
                result =  new BigDecimal(g.recursiveCalculation(map, g.expressions).toString()).abs();
                break;
            case Variable:
                Expression h = map.getExpression(this.variable);
                result =  new BigDecimal(h.recursiveCalculation(map, h.expressions).toString());
                break;
            case Value:
                result = this.number;
                break;
        }
        return result;
    }

    /**
     * Returns a set of symbol names that the expression requires to be completely evaluable.
     */
    public Set<String> getReferencedSymbols(){
        
        if (this.operation.equals(Operations.Variable)) {
            return new HashSet<String>() {
                {
                    add(variable);
                }
            };
        } else {
            return getReferencedSymbolsRecursive(expressions);
        }
    }

    private Set<String> getReferencedSymbolsRecursive(List<Expression> expressions){
        Set<String> result = new HashSet<>();

        for (Expression exp : expressions){
            if (exp.operation.equals(Operations.Variable)){
                result.add(exp.variable);
            } else {
                if (exp.expressions != null) {
                    result.addAll(getReferencedSymbolsRecursive(exp.expressions));
                }
            }
        }

        return result;
    }
}

