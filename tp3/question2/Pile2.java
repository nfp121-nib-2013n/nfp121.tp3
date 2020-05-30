package question2;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;

public class Pile2 implements PileI {
    /** par delegation : utilisation de la class Stack */
    private Stack<Object> stk;

    /** la capacite de la pile */
    private int capacite;

    /**
     * Creation d'une pile.
     * 
     * @param taille
     *            la taille de la pile, la taille doit etre > 0
     */
    public Pile2(int taille) {
        if (taille <= 0)
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = new Stack<Object>();
        this.capacite = taille;
    }

    // constructeur fourni
    public Pile2() {
        this(0);
    }

    public void empiler(Object o) throws PilePleineException {
        if (estPleine())
            throw new PilePleineException();
        this.stk.push(o);
    }

    public Object depiler() throws PileVideException {
        // L'empilation se fait en fin du stack, alors on dépile
        // de la fin du stack
        if (estVide())
            throw new PileVideException();
        // pop(): supprime l'objet en tête de stk et le renvoie  
        return this.stk.pop();
    }

    public Object sommet() throws PileVideException {
        // L'empilation se fait en fin du stack, alors le sommet
        // de la pile correspond au dernier élément du stack
        if (estVide())
            throw new PileVideException();
        // peep(): regarde l'objet en tête de stk sans le retirer 
        return this.stk.peek();
    }

    /**
     * Effectue un test de l'etat de la pile.
     * 
     * @return vrai si la pile est vide, faux autrement
     */
    public boolean estVide() {
        return this.taille() == 0; // ou return stk.isEmpty();
    }

    /**
     * Effectue un test de l'etat de la pile.
     * 
     * @return vrai si la pile est pleine, faux autrement
     */
    public boolean estPleine() {
        return this.taille() == this.capacite; // ou return this.taille() == this.capacite();
    }

    /**
     * Retourne une representation en String d'une pile, contenant la
     * representation en String de chaque element.
     * 
     * @return une representation en String d'une pile
     */
    public String toString() {
        if (estVide())
            return "[]";
        /**
         * Algorithme:
         * À chaque parcours de la boucle while,
         * 1- On enlève le dernier élément de la pile
         * 2- On ajoute cet élément à la pile temporaire
         * 3- On ajoute le .toString() de cet élément à s
         * 
         * À la sortie de la boucle while, on rend les éléments
         * de la pile temporaire à la pile initiale
         */
        String s = "[";
        Pile2 temp = new Pile2 (this.capacite());
        Object elementStk = new Object();
        
        while (!estVide()){
            try {
                elementStk = this.depiler();
                temp.empiler(elementStk);
            } catch (PileVideException videExc){}
              catch (PilePleineException pleineExc){}
              
            s += (elementStk == null)? "NULL":elementStk;
            if (!estVide())
                s += ", ";
        }
        
        rendreElements(this, temp);
        return s + "]";
    }


    public boolean equals(Object o) {
        /**
         * Il existe deux méthodes:
         * 1- Une simple comparaison des .toString() des deux piles
         * 2- En comparant membre par membre
         */
        /**
         * Première méthode:
         * if (o == null)
         *      return false;
         * if (!(o instanceof PileI))
         *      return false;
         * PileI secondPile = (PileI)o;
         * if (this == secondPile)
         *      return true;
         * if (this.taille() != secondPile.taille())
         *      return false;
         * if (this.capacite() != secondPile.capacite())
         *      return false;
         * if (secondPile.taille() == 0)
         *      return true;
         * if(this.toString().equals(secondPile.toString())) 
         *      return true;
         * return false;
         */
        
        // Deuxième méthode:
        
        // Vérifier que o n'est pas null 
        if (o == null)
            return false;

        // Vérifier que o est une instance d'une classe implémentant PileI
        if (!(o instanceof PileI))
            return false;
        PileI secondPile = (PileI)o;

        /**
         * Vérifier si o représente la même instance sur laquelle
         * on teste l'égalité
         */ 
        if (this == secondPile)
            return true;

        // Deux piles de tailles différentes ne sont pas égales
        if (this.taille() != secondPile.taille())
            return false;

        // Deux piles de capacités différentes ne sont pas égales
        if (this.capacite() != secondPile.capacite())
            return false;

        /**
         * Afin d'économiser un long calcul, deux piles vides sont
         * toujours égales
         * On peut tester si secondPile.taille() == 0 ou même si 
         * secondPile.taille() == 0 car elles sont égales
         */ 
        if (secondPile.taille() == 0)
            return true;

        /**
         * L'ordre des éléments est important 
         * Ici un problème a lieu: la fonction sommet() est utilisée
         * avec les deux piles à comparer. Cette méthode retourne
         * le dernier élément de la pile qui sera testé à chaque 
         * parcours de la boucle while, donc il faut l'enlever des 
         * deux piles à la fin de chaque parcours. Afin de résoudre
         * ce problème on peut utiliser deux piles temporaires dans 
         * lesquelles on empile à chaque fois le sommet() de la pile
         * associée déjà testé
         */ 

        Pile2 premierTemp = new Pile2 (this.taille());
        Pile2 secondTemp = new Pile2 (secondPile.taille());
        boolean egales;
        
        while (!this.estVide()) { // ou while (!pile.estVide())
            try {
                egales = false;

                /**
                 * // Si this.sommet() ET secondPile.sommet() sont toutes les 
                 * // deux nulles alors elles sont égales
                 * // On pourrait écrire comme ci-dessous mais 
                 * // l'inconvénient c'est que l'affectation de "egales"
                 * // à false est inutile car elle est déjà affectée
                 * // dès l'entrée à la boucle for !
                 * if(this.sommet() == null && secondPile.sommet() != null) 
                 *      egales = false;
                 * else if(this.sommet() != null && secondPile.sommet() == null) 
                 *      egales = false; 
                 * else if(this.sommet() == null && secondPile.sommet() == null) 
                 *      egales = true;
                 */ 
                if (this.sommet() == null){ 
                    if (secondPile.sommet() == null)
                        egales = true;
                }
                else if (secondPile.sommet() == null){
                    if (this.sommet() == null)
                        egales = true;
                }
                else if (this.sommet().equals(secondPile.sommet()))
                    egales = true;

                if (egales) {
                    Object premierTempElement = this.depiler();
                    Object secondTempElement = secondPile.depiler();
                    premierTemp.empiler(premierTempElement);
                    secondTemp.empiler(secondTempElement);
                }
                else {
                    /**
                     * S'il existe des éléments dans les piles 
                     * temporaires, il faut les rendre aux piles 
                     * initiales correspondantes
                     */ 
                    rendreElements(this, premierTemp);
                    rendreElements(secondPile, secondTemp);
                    return egales;
                }

            } catch (PileVideException videExc){}
            catch (PilePleineException pleineExc){}
        }

        /**
         * Rendre les éléments des piles temporaires aux piles 
         * initiales correspondantes
         */ 
        rendreElements(this, premierTemp);
        rendreElements(secondPile, secondTemp);
        return true;
    }
    
    public void rendreElements(PileI pileInit, PileI temp){
        while (!temp.estVide()){
            try {
                Object tempElement = temp.depiler();
                pileInit.empiler(tempElement);
            } catch (PileVideException videExc){}
            catch (PilePleineException pleineExc){}
        }
    }

    // fonction fournie
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Retourne le nombre d'element d'une pile.
     * 
     * @return le nombre d'element
     */
    public int taille() {
        return this.stk.size();
    }

    /**
     * Retourne la capacite de cette pile.
     * 
     * @return le nombre d'element
     */
    public int capacite() {
        return this.capacite;
    }

} // Pile2.java
