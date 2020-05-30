package question2;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;

public class Pile4 implements PileI, Cloneable {
    /** la liste des Maillons/Elements */
    private Maillon stk;
    /** la capacité de la pile */
    private int capacite;
    /** le nombre */
    private int nombre;

    /**
     * Classe interne "statique" contenant chaque élément de la chaine c'est une
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
     * Création d'une pile.
     * 
     * @param taille
     *            la taille de la pile, la taille doit être > 0
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
        // Empiler en d�but de la pile
        Maillon m = new Maillon(o, stk);
        stk = m;
        nombre ++;
    }

    public Object depiler() throws PileVideException {
        // L'empilation se fait en d�but de la liste, alors on d�pile
        // du d�but de la liste
        if (estVide())
            throw new PileVideException();
        Object element = stk.element();
        stk = stk.suivant();
        nombre --;
        return element;
    }

    public Object sommet() throws PileVideException {
        // L'empilation se fait en d�but de la liste, alors le sommet
        // de la pile correspond au premier �l�ment de la liste
        if (estVide())
            throw new PileVideException();
        return this.stk.element();
    }

    /**
     * Effectue un test de l'état de la pile.
     * 
     * @return vrai si la pile est vide, faux autrement
     */
    public boolean estVide() {
        return this.stk == null; 
    }

    /**
     * Effectue un test de l'état de la pile.
     * 
     * @return vrai si la pile est pleine, faux autrement
     */
    public boolean estPleine() {
        return this.capacite == this.nombre; // ou return this.capacite() == this.taille();
    }

    /**
     * Retourne une représentation en String d'une pile, contenant la
     * représentation en String de chaque élément.
     * 
     * @return une représentation en String d'une pile
     */
    public String toString() {
        // Premi�re m�thode:

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
         * // Deuxi�me m�thode:
         * if (estVide())
         *      return "[]";
         * if (stk == null)
         *      return "[]"
         * return "[" + stk.toString() + "]"
         */
    }

    public boolean equals(Object o) {
        /**
         * Il existe deux m�thodes:
         * 1- Une simple comparaison des .toString() des deux piles
         * 2- En comparant membre par membre
         */
        /**
         * Premi�re m�thode:
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

        // Deuxi�me m�thode:

        // V�rifier que o n'est pas null 
        if (o == null)
            return false;

        // V�rifier que o est une instance d'une classe impl�mentant PileI
        if (!(o instanceof PileI))
            return false;
        PileI secondPile = (PileI)o;

        /**
         * V�rifier si o repr�sente la m�me instance sur laquelle
         * on teste l'�galit�
         */ 
        if (this == secondPile)
            return true;

        // Deux piles de tailles diff�rentes ne sont pas �gales
        if (this.taille() != secondPile.taille())
            return false;

        // Deux piles de capacit�s diff�rentes ne sont pas �gales
        if (this.capacite() != secondPile.capacite())
            return false;

        /**
         * Afin d'�conomiser un long calcul, deux piles vides sont
         * toujours �gales
         * On peut tester si this.taille == 0 ou m�me si 
         * pile.taille == 0 car elles sont �gales
         */ 
        if (secondPile.taille() == 0)
            return true;

        /**
         * L'ordre des �l�ments est important 
         * Ici un probl�me a lieu: la fonction sommet() retourne
         * le dernier �l�ment de la secondPile qui sera test� � chaque 
         * parcours de la boucle for, donc il faut l'enlever � la
         * fin de chaque parcours. Afin de r�soudre ce 
         * probl�me on peut utiliser une pile temporaire dans 
         * laquelle on empile � chaque fois le sommet() d�j� test�
         */ 

        Pile4 temp = new Pile4 (secondPile.taille()); // ou this.taille(), elles sont �gales
        boolean egales;
        /**
         * stk avance � chaque parcours de la boucle for, alors il faut conserver
         * sa valeur dans une variable afin d'en revenir � la fin
         */ 
        Maillon tempMaillon = this.stk;

        while (this.stk != null){
            try {
                egales = false;

                /**
                 * // Si v.get(i) ET secondPile.sommet() sont toutes les 
                 * // deux nulles alors elles sont �gales
                 * // On pourrait �crire comme ci-dessous mais 
                 * // l'inconv�nient c'est que l'affectation de "egales"
                 * // � false est inutile car elle est d�j� affect�e
                 * // d�s l'entr�e � la boucle for !
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
                 * Si "egale = true" alors on enl�ve l'�l�ment de
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
                     * S'il existe des �l�ments dans la pile 
                     * temporaire, il faut les rendre � la pile 
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
         * Rendre les �l�ments de la pile temporaire � la pile 
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