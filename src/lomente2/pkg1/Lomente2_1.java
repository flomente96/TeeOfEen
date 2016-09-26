/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lomente2.pkg1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author faielomente
 */
public class Lomente2_1 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        String file_path = "file.in_b.txt";
        String line;
        ArrayList tokens;
        String lb = "";
        String up = "";
        String increment = "";
        
        Stack<String> braces = new Stack<>();
        
        int unitCount = 0;
        int constantCount = 0;
        
        BufferedReader br = readFile(file_path);
        
        while ((line = br.readLine()) != null){
//            System.out.println(line);
            if(line.contains("{"))
                braces.push("{");
            else if(line.contains("}"))
                braces.pop();
            
            if(!braces.empty()){
                if (line.contains("for(") || line.contains("for (")){

                    tokens = tokenizer(line);
                    

                    lb = extract(getLowerBound(tokens));

                    up = getUpperBound(tokens);
                    increment = tokens.get(2).toString();

                    System.out.println("Lower Bound: " + lb);
                    System.out.println("Upper Bound: " + up);
                    System.out.println("Iterator: " + increment);
                    
                    if(line.contains(",")){
                        for (Object str : tokens){
//                            System.out.println(str.toString());
                            String[] tmp = str.toString().split(",");
                            constantCount += tmp.length;
                        }
                    }
                    else{
                        constantCount += 2;
                    }

                }
                else if(line.contains("{"))
                    braces.push("{");
                else if(line.contains("}"))
                    braces.pop();
                
                unitCount += countUnitTime(line);
                
            }
            else if(braces.empty() && !line.isEmpty()){
                String equation = "";
                
                unitCount -= constantCount-1;
                System.out.println("Unit time: " + unitCount);
                
                if(unitCount > 1){
                    equation = createEquation(lb, up, constantCount, unitCount);
                    System.out.println("Equation: " + equation);
                }
                else
                    System.out.println("\n");
                
                
                
                unitCount = 0;
                constantCount = 0;
                
            }
            
        }
        
    }
    
    public static String createEquation(String lower, String upper, int cons, int units){
        return String.valueOf(units).concat("(").concat(upper).concat(")").concat("+").concat(String.valueOf(cons)).concat("\n");
        
    }
    
    public static BufferedReader readFile(String file) throws FileNotFoundException{
        
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        
        return br;
        
    }
    
    public static ArrayList tokenizer(String line){
        
        StringTokenizer tokenizer;
        ArrayList<String> tokens = new ArrayList<String>();
        

        tokenizer = new StringTokenizer(line, ";");

        while (tokenizer.hasMoreTokens()){

            String token = tokenizer.nextToken();

//            System.out.println(token);
            if (token.contains("for") == true){

                String temp = token.substring(token.indexOf('('));
                tokens.add(temp.substring(temp.indexOf(' ')));

            }
            else if (token.contains("){") || token.contains(") {") || (token.charAt(token.length()-1) == ')')){

                tokens.add(token.substring(0, token.indexOf(")")));

            }
            else{

                tokens.add(token);

            }

        }
        
        return tokens;
    }
    
    public static String slice(String token, ArrayList tokens){
        
        String[] temp = token.split(",");

        for(String str : temp){

            String var = str.substring(0, str.indexOf("="));
            int j;

            for(j = 0; j < tokens.size(); j++){

//              System.out.println(var);

                if(tokens.get(j).toString().contains(var) == false)
                    break;

            }

            if(j == tokens.size())
                token = str;

        }

        return token;
    }
    
    public static String getLowerBound(ArrayList tokens){
        
        String lb;
        
        //if the iterator is decreasing the lowerbound is the conditional portion
        if(tokens.get(2).toString().contains("/=") || tokens.get(2).toString().contains("-=") || tokens.get(2).toString().contains("--"))
            lb = tokens.get(1).toString();
        else
            lb = tokens.get(0).toString();
            
        if(lb.contains(",")){
            
            lb = slice(lb, tokens);

        }
            
        return lb;
    }
    
    public static String getUpperBound(ArrayList tokens){
        
        String up;
        
        if(tokens.get(2).toString().contains("*=") || tokens.get(2).toString().contains("+=") || tokens.get(2).toString().contains("++"))
            up = tokens.get(1).toString();
        else
            up = tokens.get(0).toString();
        
        if(up.contains(","))
            up = slice(up, tokens);
        
        up = extract(up);
        
        
        //Checks if the token is logarithmic or linear
        if (tokens.get(2).toString().contains("*=") || tokens.get(2).toString().contains("/="))
            up = "Log".concat("(").concat(extract(tokens.get(2).toString())).concat(")").concat(up);
        else if(tokens.get(2).toString().contains("+=") || tokens.get(2).toString().contains("-="))
            up = up.concat("/").concat(extract(tokens.get(2).toString()));
        
        return up;
        
    }
    
    public static String extract(String token){
        //Extract a specific component out of a token
        
        if(token.contains(">") && token.contains(">=") == false){
            
            token = token.substring(token.indexOf(">")+1, token.length());
            int temp = Integer.valueOf(token) + 1;
            return String.valueOf(temp);
            
        }
        else if(token.contains("<") && token.contains("<=") ==false)
            return token.substring(token.indexOf("<")+1, token.length());
//            return token.substring(token.indexOf("<")+1, token.length()).concat("-1");
        
        return token.substring(token.indexOf("=")+1, token.length());
        
    }
    
    public static int countUnitTime(String str){
        String regex = "(([\\/\\+\\-\\*\\=\\>\\<]\\s*([a-z]|[0-9])+)|([\\-\\+]{2}))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        
        int count = 0;
        
//        System.out.println("String: " + str);
        while(m.find()){
            count++;
//            System.out.println(str.toCharArray()[m.start()]);
//            System.out.println("start(): "+m.start());
//            System.out.println("end(): "+m.end());
        }
        
//        System.out.println("Count: " + count);
        
        return count;
    }
    
}