/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lomente2.pkg1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author faielomente
 */
public class Polynomial {
    String operation;
    Term term;
    ArrayList expression;

    public Polynomial(String operation, Term term, ArrayList expression) {
        this.operation = operation;
        this.term = term;
        this.expression = expression;
    }

    public Polynomial() {
    }
    
    public Polynomial Polynimial (String exp){
        Polynomial p = new Polynomial();
        char[] tmp = exp.toCharArray();
        
//        for(char c : tmp){
            String regex = "([\\/\\+\\-\\*\\=\\>\\<\\{\\}\\(\\)\\[\\]])";
            Pattern pattern = Pattern.compile(regex);
            Matcher match = pattern.matcher(exp);
            
//        }
        return p;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void setExpression(ArrayList expression) {
        this.expression = expression;
    }
    
}
