    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lomente2.pkg1;

/**
 *
 * @author faielomente
 */
public class Term {
    String sign;
    private int coeficient;
    private String variable;
    private int exponent;
    
    public Term(String sign, int coef, String var, int exp){
        this.sign = sign;
        this.coeficient = coef;
        this.variable = var;
        this.exponent = exp;
    }
    
    public Term(String sign, int coef){
        this.sign = sign;
        this.coeficient = coef;
        this.variable = "n";
        this.exponent = 0;
    }
    
    public Term(int coef){
        this.sign = "+";
        this.coeficient = coef;
        this.variable = "n";
        this.exponent = 0;
    }
    
    public Term(String var){
        this.coeficient = 1;
        this.variable = "n";
        this.exponent = 1;
    }
    
    public void setCoeficient(int coeficient) {
        this.coeficient = coeficient;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

}
