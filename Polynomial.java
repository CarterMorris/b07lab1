public class Polynomial {
    double [] coeff;

    public Polynomial() {
        coeff = new double [1];
        coeff[0] = 0;
    }

    public Polynomial (double [] poly){
        coeff = poly;
    }

    public Polynomial add (Polynomial to_add){
        if (to_add.coeff.length > coeff.length){
            for(int i=0; i<coeff.length; i++){
                to_add.coeff[i] = to_add.coeff[i] + coeff[i];
            }
            return to_add;
        }
        for(int i=0; i<to_add.coeff.length; i++){
            coeff[i] = to_add.coeff[i] + coeff[i];
        }
        return this;
    }

    public double evaluate (double variable){
        double total = 0;
        for (int i=0;i<coeff.length;i++){
            total = total + (coeff[i] * Math.pow(variable, i));
        }
        return total;
    }

    public boolean hasRoot (double root){
        return(evaluate(root)==0);
    }

}