package question3;

import question1.PolygoneRegulier;

public class UneUtilisation {

    public static void main(String[] args) throws Exception {
        // p1 ne contient que des polygones rÈguliers ---> T = PolygoneRegulier
        PileI<PolygoneRegulier> p1 = new Pile2 (10);

        // p2 ne contient que des piles de polygones rÈguliers ---> T = PileI<PolygoneRegulier>
        PileI<PileI<PolygoneRegulier>> p2 = new Pile2 (10);
        
        // p1 est ici une pile de polygones r√©guliers PolygoneRegulier.java
        p1.empiler(new PolygoneRegulier(4, 100));
        p1.empiler(new PolygoneRegulier(5, 100));

        System.out.println(" la pile p1 = " + p1);

        p2.empiler(p1);
        System.out.println(" la pile p2 = " + p2);

        try {
            // p1.empiler(new PolygoneRegulier(5,100)); // d√©sormais une erreur de
            // compilation
            // ....
            // String s = (String)p1.depiler(); // d√©sormais une erreur de
            // compilation
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}