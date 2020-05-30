package question2;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;

public class Pile4 implements PileI, Cloneable {
    /** la liste des Maillons/Elements */
    private Maillon stk;
    /** la capacitÃ© de la pile */
    private int capacite;
    /** le nombre */
    private int nombre;

    /**
     * Classe interne "statique" contenant chaque Ã©lÃ©ment de la chaine c'est une
     * proposition, vous pouvez l'ignorer !
     */
    private static class Maillon implements Cloneable {
        private Object element;
        private Maillon suivant;

        public Maillon(Object element, Maillon suivant) {
            this.element = element;
            this.suivant = suivant;
        }

        public Maillon suivant() {
            return this.suivant;
        }

        public Object element() {
            return this.element;
        }

        public Object clone() throws CloneNotSupportedException {
            Maillon m = (Maillon) super.clone();
            m.element = element;
            return m;
        }
    }

    /**
     * CrÃ©ation d'une pile.
     * 
     * @param taille
     *            la taille de la pile, la taille doit Ãªtre > 0
     */
    public Pile4(int taille) {
        if (taille <= 0)
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = null;
        this.capacite = taille;
        this.nombre = 0;
    }

    public Pile4() {
        this(PileI.CAPACITE_PAR_DEFAUT);
    }

    public void empiler(Object o) throws PilePleineException {
        if (estPleine())
            throw new PilePleineException();
        // Empiler en début de la pile
        Maillon m = new Maillon(o, stk);
        stk = m;
        nombre ++;
    }

    public Object depiler() throws PileVideException {
        // L'empilation se fait en début de la liste, alors on dépile
        // du début de la liste
        if (estVide())
            throw new PileVideException();
        Object element = stk.element();
        stk = stk.suivant();
        nombre --;
        return element;
    }

    public Object sommet() throws PileVideException {
        // L'empilation se fait en début de la liste, alors le sommet
        // de la pile correspond au premier élément de la liste
        if (estVide())
            throw new PileVideException();
        return this.stk.element();
    }

    /**
     * Effectue un test de l'Ã©tat de la pile.
     * 
     * @return vrai si la pile est vide, faux autrement
     */
    public boolean estVide() {
        return this.stk == null; 
    }

    /**
     * Effectue un test de l'Ã©tat de la pile.
     * 
     * @return vrai si la pile est pleine, faux autrement
     */
    public boolean estPleine() {
        return this.capacite == this.nombre; // ou return this.capacite() == this.taille();
    }

    /**
     * Retourne une reprÃ©sentation en String d'une pile, contenant la
     * reprÃ©sentation en String de chaque Ã©lÃ©ment.
     * 
     * @return une reprÃ©sentation en String d'une pile
     */
    public String toString() {
        // Première méthode:

        if (estVide())
            return "[]";
        String s = "[";
        Maillon temp = stk;
        while (temp != null){
            if (temp.element() == null)
                s += "NULL";
            else
                s += temp.element().toString();

            temp = temp.suivant();
            if (temp !=null)
                s += ", ";
        }
        return s + "]";

        /**
         * // Deuxième méthode:
         * if (estVide())
         *      return "[]";
         * if (stk == null)
         *      return "[]"
         * return "[" + stk.toString() + "]"
         */
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
         * On peut tester si this.taille == 0 ou même si 
         * pile.taille == 0 car elles sont égales
         */ 
        if (secondPile.taille() == 0)
            return true;

        /**
         * L'ordre des éléments est important 
         * Ici un problème a lieu: la fonction sommet() retourne
         * le dernier élément de la secondPile qui sera testé à chaque 
         * parcours de la boucle for, donc il faut l'enlever à la
         * fin de chaque parcours. Afin de résoudre ce 
         * problème on peut utiliser une pile temporaire dans 
         * laquelle on empile à chaque fois le sommet() déjà testé
         */ 

        Pile4 temp = new Pile4 (secondPile.taille()); // ou this.taille(), elles sont égales
        boolean egales;
        /**
         * stk avance à chaque parcours de la boucle for, alors il faut conserver
         * sa valeur dans une variable afin d'en revenir à la fin
         */ 
        Maillon tempMaillon = this.stk;

        while (this.stk != null){
            try {
                egales = false;

                /**
                 * // Si v.get(i) ET secondPile.sommet() sont toutes les 
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

                /**
                 * Si "egale = true" alors on enlève l'élément de
                 * la pile initiale et on le met dans la pile temporaire
                 */ 
                if (egales) {
                    this.stk = this.stk.suivant();
                    Object tempElement = secondPile.depiler();
                    temp.empiler(tempElement);
                }
                else {
                    stk = tempMaillon;
                    /**
                     * S'il existe des éléments dans la pile 
                     * temporaire, il faut les rendre à la pile 
                     * initiale
                     */ 
                    rendreElements(secondPile, temp);
                    return egales;
                }

            } catch (PileVideException videExc){}
            catch (PilePleineException pleineExc){}
        }

        stk = tempMaillon;
        /**
         * Rendre les éléments de la pile temporaire à la pile 
         * initiale
         */ 
        rendreElements(secondPile, temp);
        return true;

    }

    public void rendreElements(PileI pile, PileI temp){
        while (!temp.estVide()){
            try {
                Object tempElement = temp.depiler();
                pile.empiler(tempElement);
            } catch (PileVideException videExc){}
            catch (PilePleineException pleineExc){}
        }
    }

    public int capacite() {
        return this.capacite;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public int taille() {
        return nombre;
    }
}