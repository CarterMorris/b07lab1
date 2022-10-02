package Polynomial;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

public class Polynomial {
    double [] nonzero;
    int [] exp;

    public Polynomial() {
        nonzero = new double [1];
        nonzero[0] = 0;
        exp = new int [1];
        exp[0] = 0;
    }

    public Polynomial (double [] poly_nonzero, int [] poly_exp){
        nonzero = poly_nonzero;
        exp = poly_exp;
    }

    public Polynomial (File f) throws FileNotFoundException{
        Scanner poly_file = new Scanner(f);
        String poly = poly_file.nextLine();
        ArrayList<Character> new_poly = new ArrayList<Character>();

        if(poly.compareTo("0") == 0){
            double [] dzero = {0};
            nonzero = dzero;
            int [] ezero = {0};
            exp = ezero;
            return;
        }

        int count = 0;
        for (int i = 0; i<poly.length(); i++){
            if(poly.charAt(i) == '-' && i != 0){
                new_poly.add(i+count, '+');
                count++;
            }
            new_poly.add(poly.charAt(i));
        }
        String plus = new String();
        for (int i =0; i<new_poly.size(); i++){
            plus = plus + new_poly.get(i);
        }

        String [] parts = plus.split("\\+");

        for(int i = 0; i<parts.length; i++){
            for (int j = 0; j<parts[i].length(); j++){
                if(parts[i].charAt(j)=='x'){
                    break;
                }
                else if(j==parts[i].length()-1){
                    parts[i] = parts[i].substring(0,j+1) + "x0";
                }
            }
        }


        ArrayList<Integer> exponents = new ArrayList<Integer>();
        ArrayList<Double> coef = new ArrayList<Double>();
        for(int i = 0; i<parts.length; i++){
            String [] terms = parts[i].split("x");
            coef.add(Double.parseDouble(terms[0]));
            exponents.add(Integer.parseInt(terms[1]));
        }

        double [] finalcoef = new double[coef.size()];
        int [] finalexp = new int[exponents.size()];
        for (int i = 0; i < coef.size(); i++){
            finalcoef[i] = coef.get(i);
        }
        for (int i = 0; i < exponents.size(); i++){
            finalexp[i] = exponents.get(i);
        }

        nonzero = finalcoef;
        exp = finalexp;

    }


    public Polynomial add (Polynomial to_add){
        ArrayList<Double> newcoef = new ArrayList<Double>();
        ArrayList<Integer> newexp = new ArrayList<Integer>();


        for (int i = 0; i < to_add.exp.length; i++){
            for (int j = 0; j < this.exp.length; j++){
                if (this.exp[j] == to_add.exp [i]){
                    newcoef.add(this.nonzero[j] + to_add.nonzero[i]);
                    newexp.add(to_add.exp[i]);
                    break;
                }
                else if (j == this.exp.length - 1){
                    newcoef.add(to_add.nonzero[i]);
                    newexp.add(to_add.exp[i]);
                }
            }
        }
        for (int j = 0; j < this.exp.length; j++){
            for (int i = 0; i < to_add.exp.length; i++){
                if (this.exp[j] == to_add.exp [i]){
                    break;
                }
                else if (i == to_add.exp.length - 1){
                    newcoef.add(this.nonzero[j]);
                    newexp.add(this.exp[j]);
                }
            }
        }
        double [] finalcoef = new double[newcoef.size()];
        int [] finalexp = new int[newexp.size()];
        for (int i = 0; i < newcoef.size(); i++){
            finalcoef[i] = newcoef.get(i);
        }
        for (int i = 0; i < newexp.size(); i++){
            finalexp[i] = newexp.get(i);
        }
        return new Polynomial(finalcoef, finalexp);
    }


    public double evaluate (double variable){
        double total = 0;
        for (int i=0;i<this.exp.length;i++){
            total = total + (this.nonzero[i] * Math.pow(variable, this.exp[i]));
        }
        return total;
    }


    public boolean hasRoot (double root){
        return(evaluate(root)==0);
    }


    public Polynomial multiply (Polynomial to_mult){
        ArrayList<Double> newcoef = new ArrayList<Double>();
        ArrayList<Integer> newexp = new ArrayList<Integer>();

        for (int i=0; i<this.exp.length; i++){
            for (int j=0; j<to_mult.exp.length; j++){
                newcoef.add(this.nonzero[i] * to_mult.nonzero[j]);
                newexp.add(this.exp[i] + to_mult.exp[j]);
            }
        }

        for (int i=0; i< newexp.size() - 1; i++){
            for (int j=i+1; j<newexp.size(); j++){
                if((newexp.get(i) == newexp.get(j)) && newexp.get(j) != -1){
                    newcoef.set(i, newcoef.get(i)+newcoef.get(j));
                    newexp.set(j,-1);
                }
            }
        }
        for (int i=0; i< newexp.size(); i++){
            if((newexp.get(i) == -1)||(newexp.get(i) != 0 && newcoef.get(i) == 0)){
                newexp.remove(i);
                newcoef.remove(i);
            }
        }
        double [] finalcoef = new double[newcoef.size()];
        int [] finalexp = new int[newexp.size()];
        for (int i = 0; i < newcoef.size(); i++){
            finalcoef[i] = newcoef.get(i);
        }
        for (int i = 0; i < newexp.size(); i++){
            finalexp[i] = newexp.get(i);
        }
        return new Polynomial(finalcoef, finalexp);
    }


    public void saveToFile (String filename) throws IOException{
        File f = new File(filename);
        FileWriter output;
        if (f.createNewFile()){
            output = new FileWriter(f);
        }
        else{
            output = new FileWriter(filename, false);
        }

        int [] negatives = new int[this.nonzero.length];
        for (int i = 0; i<this.nonzero.length; i++){
            if(this.nonzero[i] < 0.0 && i>0){
                negatives[i-1] = 1;
            }
        }
        for (int i = 0; i<this.nonzero.length; i++){
            if(negatives[i] == 1 || i == this.nonzero.length-1){
                if(this.exp[i] == 0){
                    output.write(Double.toString(this.nonzero[i]));
                }
                else{
                    output.write(this.nonzero[i] + "x" + this.exp[i]);
                }
            }
            else{
                if(this.exp[i] == 0){
                    output.write(this.nonzero[i] + "+");
                }
                else{
                    output.write(this.nonzero[i] + "x" + this.exp[i] + "+");
                }
            }
        }
        output.close();
    }

}
