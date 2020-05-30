package question2;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Vector;

/**
 * Décrivez votre classe PileVector ici.
 * 
 * @author Agatha Khairallah
 * @version 1.0
 */
public class Pile3 implements PileI {

    private Vector<Object> v;

    public Pile3() {
        this(0);
    }

    public Pile3(int taille) {
        if (taille <= 0)
            taille = CAPACITE_PAR_DEFAUT;
        /**
         * Cr�ation d'un vecteur dont la capacit� initiale est 
         * �gale � "taille"
         */ 
        this.v = new Vector<Object>(taille);
    }

    public void empiler(Object o) throws PilePleineException {
        if (estPleine())
            throw new PilePleineException();
        this.v.addElement(o);
    }

    public Object depiler() throws PileVideException {
        // L'empilation se fait en fin du vector, alors on d�pile
        // de la fin du vector
        if (estVide())
            throw new PileVideException();
        return this.v.remove(this.taille() -1);
    }

    public Object sommet() throws PileVideException {
        // L'empilation se fait en fin du vector, alors le sommet
        // de la pile correspond au dernier �l�ment du vector
        if (estVide())
            throw new PileVideException();
        return this.v.lastElement();
    }

    public int taille() {
        return this.v.size();
    }

    public int capacite() {
        return this.v.capacity();
    }

    public boolean estVide() {
        return this.taille() == 0; // ou return this.v.isEmpty();
    }

    public boolean estPleine() {
        return this.taille() == this.capacite();
    }

    public String toString() {
        if (estVide())
            return "[]";
        StringBuffer sb = new StringBuffer("[");
        int vectorTaille = this.taille();
        for (int i = vectorTaille - 1; i >= 0; i--) {
            sb.append(this.v.get(i));
            if (i > 0)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
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
         * On peut tester si this.taille() == 0 ou m�me si 
         * pile.taille() == 0 car elles sont �gales
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

        Pile3 temp = new Pile3 (secondPile.taille()); // ou this.taille(), elles sont �gales
        boolean egales;

        for (int i = this.taille() - 1; i >= 0; i -= 1){
            try {
                egales = false;

                /**
                 * // Si v.get(i) ET secondPile.sommet() sont toutes les 
                 * // deux nulles alors elles sont �gales
                 * // On pourrait �crire comme ci-dessous mais 
                 * // l'inconv�nient c'est que l'affectation de "egales"
                 * // � false est inutile car elle est d�j� affect�e
                 * // d�s l'entr�e � la boucle for !
                 * if(v.get(i) == null && secondPile.sommet() != null) 
                 *      egales = false;
                 * else if(v.get(i) != null && secondPile.sommet() == null) 
                 *      egales = false; 
                 * else if(v.get(i) == null && secondPile.sommet() == null) 
                 *      egales = true;
                 */ 
                if (v.get(i) == null){ 
                    if (secondPile.sommet() == null)
                        egales = true;
                }
                else if (secondPile.sommet() == null){
                    if (v.get(i) == null)
                        egales = true;
                }
                else if (v.get(i).equals(secondPile.sommet()))
                    egales = true;

                /**
                 * Si "egale = true" alors on enl�ve l'�l�ment de
                 * la pile initiale et on le met dans la pile temporaire
                 */ 
                if (egales) {
                    Object tempElement = secondPile.depiler();
                    temp.empiler(tempElement);
                }
                else {
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

        /**
         * Rendre les �l�ments de la pile temporaire � la pile 
         * initiale
         */ 
        rendreElements(secondPile, temp);
        return true;
    }

    public void rendreElements(PileI pile, PileI temp){
        /**
         * Attention: 
         * La taille de temp doit �tre d�clar�e
         * en tant que variable. Elle ne peut pas �tre mis
         * dans la condition de la boucle for car celle-ci 
         * diminue � chaque parcours de la boucle for (ou 
         * en d'autre terme: � chaque appel � la fonction 
         * depiler())
         */ 
        int tempTaille = temp.taille();
        for (int i = tempTaille; i > 0; i -=1){
            try {
                Object tempElement = temp.depiler();
                pile.empiler(tempElement);
            } catch (PileVideException videExc){}
            catch (PilePleineException pleineExc){}
        }
    }

    // fonction fournie
    public int hashCode() {
        return toString().hashCode();
    }

}
