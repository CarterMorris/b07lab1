package Polynomial;
import java.io.File;

public class Driver {
 public static void main(String [] args) {
  double [] pn = {-4,3,1,-9};
  int [] pe = {0,2,6,4};
  double [] p2n = {0.2,1,9};
  int [] p2e = {15,4,9};
  Polynomial p = new Polynomial(pn, pe);
  Polynomial p2 = new Polynomial(p2n, p2e);

  for (int i = 0; i < p.add(p2).exp.length; i++){
   System.out.println(p.add(p2).exp[i]);
  }
  System.out.println("Now coefficients");
  for (int i = 0; i < p.add(p2).nonzero.length; i++){
   System.out.println(p.add(p2).nonzero[i]);
  }

        for (int i = 0; i < p.multiply(p2).exp.length; i++){
            System.out.println(p.multiply(p2).exp[i]);
        }
        System.out.println("Now coefficients");
        for (int i = 0; i < p.multiply(p2).nonzero.length; i++){
            System.out.println(p.multiply(p2).nonzero[i]);
        }
        System.out.println(p.evaluate(1.0));
        System.out.println(p.hasRoot(1.0));


        File fpoly = new File("poly.txt");
        try{
            Polynomial filep = new Polynomial(fpoly);

            filep.saveToFile("newfiletest.txt");

            for(int i = 0; i<filep.nonzero.length; i++){
                System.out.print(filep.nonzero[i] + ",");
            }
            System.out.println();
            for(int i = 0; i<filep.exp.length; i++){
                System.out.print(filep.exp[i] + ",");
            }
        } catch (Exception FileNotFoundException){
            System.out.println("caught");
        }
 }
}



