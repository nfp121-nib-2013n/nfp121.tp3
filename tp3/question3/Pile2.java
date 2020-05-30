package question3;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;

public class Pile2<T> implements PileI<T>{
    /** par délégation : utilisation de la class Stack */
    private Stack<T> stk;
    /** la capacité de la pile */
    private int capacite;

    /** Création d'une pile.
     * @param taille la "taille maximale" de la pile, doit être > 0
     */
    public Pile2(int taille){
        if (taille <= 0)
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = new Stack<T>();
        this.capacite = taille;
    }

    public Pile2(){
        this(0);
    }

    public void empiler(T o) throws PilePleineException{
        if (estPleine())
            throw new PilePleineException();
        this.stk.push(o);
    }

    public T depiler() throws PileVideException{
        if (estVide())
            throw new PileVideException();
        // pop(): supprime l'objet en t�te de stk et le renvoie  
        return this.stk.pop();
    }

    public T sommet() throws PileVideException{
        if (estVide())
            throw new PileVideException();
        // peep(): regarde l'objet en t�te de stk sans le retirer 
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
         * � chaque parcours de la boucle while,
         * 1- On enl�ve le dernier �l�ment de la pile
         * 2- On ajoute cet �l�ment � la pile temporaire
         * 3- On ajoute le .toString() de cet �l�ment � s
         * 
         * � la sortie de la boucle while, on rend les �l�ments
         * de la pile temporaire � la pile initiale
         */
        StringBuffer sb = new StringBuffer("[");
        Object elementStk = new Object();
        Pile2 temp = new Pile2 (this.capacite());

        while (!estVide()){
            try {
                elementStk = this.depiler();
                temp.empiler(elementStk);
            } catch (PileVideException videExc){}
            catch (PilePleineException pleineExc){}

            sb.append((elementStk == null)? "NULL":elementStk);
            if (!estVide())
                sb.append(", ");
        }

        rendreElements(this, temp);
        sb.append("]");
        return sb.toString();
    }

    public boolean equals(Object o){
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
         * PileI<T> pileT;
         * try{
         *     pileT = (PileI<T>)o;
         *  } catch (ClassCastException classCastExc){
         *      return false;
         *  }
         * if(this.toString().equals(pileT.toString())) 
         *      return true;
         * return false;
         */

        // Deuxi�me m�thode:

        // V�rifier que o n'est pas null 
        if (o == null)
            return false;

        /**
         * V�rifier que o est une instance d'une classe impl�mentant PileI.
         * On ne peut pas tester si o est une instance de PileI<T> ! Alors
         * ce cas sera pris en compte avec le try-catch ci-dessous
         */ 
        if (!(o instanceof PileI))
            return false;

        /**
         * Attention:
         * Ici, o est une instance de PileI mais le casting vers
         * PileI<T> peut g�n�rer une exception !
         */
        PileI<T> pileT;
        try{
            pileT = (PileI<T>)o;
        } catch (ClassCastException classCastExc){
            return false;
        }

        /**
         * V�rifier si o repr�sente la m�me instance sur laquelle
         * on teste l'�galit�
         */ 
        if (this == pileT)
            return true;

        // Deux piles de tailles diff�rentes ne sont pas �gales
        if (this.taille() != pileT.taille())
            return false;

        // Deux piles de capacit�s diff�rentes ne sont pas �gales
        if (this.capacite() != pileT.capacite())
            return false;

        /**
         * Afin d'�conomiser un long calcul, deux piles vides sont
         * toujours �gales
         * On peut tester si this.taille == 0 ou m�me si 
         * pile.taille == 0 car elles sont �gales
         */ 
        if (pileT.taille() == 0)
            return true;

        /**
         * L'ordre des �l�ments est important 
         * Ici un probl�me a lieu: la fonction sommet() est utilis�e
         * avec les deux piles � comparer. Cette m�thode retourne
         * le dernier �l�ment de la pile qui sera test� � chaque 
         * parcours de la boucle while, donc il faut l'enlever des 
         * deux piles � la fin de chaque parcours. Afin de r�soudre
         * ce probl�me on peut utiliser deux piles temporaires dans 
         * lesquelles on empile � chaque fois le sommet() de la pile
         * associ�e d�j� test�
         */ 

        boolean egales;
        Pile2<T> premierTemp = new Pile2<T> (this.taille());
        Pile2<T> secondTemp = new Pile2<T> (pileT.taille());

        while (!this.estVide()) { // ou while (!pile.estVide())
            try {
                egales = false;

                /**
                 * // Si this.sommet() ET pileT.sommet() sont toutes les 
                 * // deux nulles alors elles sont �gales
                 * // On pourrait �crire comme ci-dessous mais 
                 * // l'inconv�nient c'est que l'affectation de "egales"
                 * // � false est inutile car elle est d�j� affect�e
                 * // d�s l'entr�e � la boucle for !
                 * if(this.sommet() == null && pileT.sommet() != null) 
                 *      egales = false;
                 * else if(this.sommet() != null && pileT.sommet() == null) 
                 *      egales = false; 
                 * else if(this.sommet() == null && pileT.sommet() == null) 
                 *      egales = true;
                 */ 
                if (this.sommet() == null){ 
                    if (pileT.sommet() == null)
                        egales = true;
                }
                else if (pileT.sommet() == null){
                    if (this.sommet() == null)
                        egales = true;
                }
                else if (this.sommet().equals(pileT.sommet()))
                    egales = true;

                if (egales) {
                    T premierTempElement = this.depiler();
                    T secondTempElement = pileT.depiler();
                    premierTemp.empiler(premierTempElement);
                    secondTemp.empiler(secondTempElement);
                }
                else {
                    /**
                     * S'il existe des �l�ments dans les piles 
                     * temporaires, il faut les rendre aux piles 
                     * initiales correspondantes
                     */ 
                    rendreElements(this, premierTemp);
                    rendreElements(pileT, secondTemp);
                    return egales;
                }

            } catch (PileVideException videExc){}
            catch (PilePleineException pleineExc){}
        }

        /**
         * Rendre les �l�ments des piles temporaires aux piles 
         * initiales correspondantes
         */ 
        rendreElements(this, premierTemp);
        rendreElements(pileT, secondTemp);
        return true;
    }

    public void rendreElements(PileI<T> pileInit, PileI<T> temp){
        while (!temp.estVide()){
            try {
                T tempElement = temp.depiler();
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
} // Pile2