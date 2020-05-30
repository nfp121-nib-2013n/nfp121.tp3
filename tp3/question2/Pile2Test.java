package question2;

/**
 * Classe-test Pile2Test.
 * 
 * @author Agatha Khairallah
 * @version 1.0
 * 
 *          Les classes-test sont documentées ici :
 *          http://junit.sourceforge.net/javadoc/junit/framework/TestCase.html
 *          et sont basées sur le document © 2002 Robert A. Ballance intitulé
 *          «JUnit: Unit Testing Framework».
 * 
 *          Les objets Test (et TestSuite) sont associés aux classes à tester
 *          par la simple relation yyyTest (e.g. qu'un Test de la classe
 *          Name.java se nommera NameTest.java); les deux se retrouvent dans le
 *          même paquetage. Les "engagements" (anglais : "fixture") forment un
 *          ensemble de conditions qui sont vraies pour chaque méthode Test à
 *          exécuter. Il peut y avoir plus d'une méthode Test dans une classe
 *          Test; leur ensemble forme un objet TestSuite. BlueJ découvrira
 *          automatiquement (par introspection) les méthodes Test de votre
 *          classe Test et générera la TestSuite conséquente. Chaque appel d'une
 *          méthode Test sera précédé d'un appel de setUp(), qui réalise les
 *          engagements, et suivi d'un appel à tearDown(), qui les détruit.
 */
public class Pile2Test extends junit.framework.TestCase {
    private PileI p1;
    private PileI p2;
    /**
     * Constructeur de la classe-test Pile2Test
     */
    public Pile2Test() {
    }

    /**
     * Met en place les engagements.
     * 
     * Méthode appelée avant chaque appel de méthode de test.
     */
    protected void setUp() // throws java.lang.Exception
    {
        p1 = new question2.Pile2();
        p2 = new question2.Pile2();
    }

    /**
     * Supprime les engagements
     * 
     * Méthode appelée après chaque appel de méthode de test.
     */
    protected void tearDown() // throws java.lang.Exception
    {
        // Libérez ici les ressources engagées par setUp()
    }

    /**
     * Il ne vous reste plus qu'à définir une ou plusieurs méthodes de test. Ces
     * méthodes doivent vérifier les résultats attendus à l'aide d'assertions
     * assertTrue(<boolean>). Par convention, leurs noms devraient débuter par
     * "test". Vous pouvez ébaucher le corps grâce au menu contextuel
     * "Enregistrer une méthode de test".
     */
    
    public void test_Pile_capacite() {
        assertEquals(PileI.CAPACITE_PAR_DEFAUT, p1.capacite());
    }

    public void test_Pile_estPleine() throws Exception {
        PileI p = new question2.Pile2(5);
        p.empiler(13);
        assertEquals(1, p.taille());
        p.empiler(18);
        assertEquals(2, p.taille());
        p.empiler(23);
        assertEquals(3, p.taille());
        p.empiler(35);
        assertEquals(4, p.taille());
        p.empiler(51);
        assertEquals(5, p.taille());

        assertEquals(true, p.estPleine());
        assertEquals(p.taille(), p.capacite());
        try {
            p.empiler(0);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof question1.PilePleineException);
        }
    }

    public void test_Pile_sommet() throws Exception {
        PileI p = new question2.Pile2(8);
        assertEquals(true, p.estVide());

        p.empiler(new Integer(83));
        assertEquals(" sommet ?? ", new Integer(83), p.sommet());
        assertEquals(1, p.taille());
        assertEquals(" depiler ?? ", new Integer(83), p.depiler());
        assertEquals(0, p.taille());
    }

    public void test_Pile_estVide() throws Exception {
        PileI p = new question2.Pile2(2);
        assertEquals(true, p.estVide());
        try {
            Object r = p.depiler();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof question1.PileVideException);
        }
    }

    public void test_Pile_toString() throws Exception {
        PileI pile1 = new question2.Pile2(4);
        assertEquals("toString incorrect ? ", "[]", pile1.toString());
        pile1.empiler(8);
        assertEquals("toString incorrect ? ", "[8]", pile1.toString());
        pile1.empiler(9);
        assertEquals("toString incorrect ? ", "[9, 8]", pile1.toString());
        pile1.empiler(10);
        assertEquals("toString incorrect ? ", "[10, 9, 8]", pile1.toString());
        pile1.empiler(11);
        assertEquals("toString incorrect ? ", "[11, 10, 9, 8]", pile1.toString());
    }

    public void test_Pile_TailleNegative() {
        PileI p = new question2.Pile2(-9);
        assertEquals(p.CAPACITE_PAR_DEFAUT, p.capacite());

    }

    public void test_Pile_equals() throws Exception {

        p1.empiler(22);
        p1.empiler(null);
        p1.empiler(24);

        p2.empiler(22);
        p2.empiler(null);
        p2.empiler(24);

        assertTrue("égalité de deux piles ? ", p1.equals(p2));
        assertTrue("égalité de deux piles ? ", p2.equals(p1));
        assertTrue("égalité de deux piles ? ", p1.equals(p1));

        p2.empiler(25);
        assertFalse("égalité de deux piles ? ", p1.equals(p2));

    }
    
    public void test_Pile_HashCode() throws Exception {
        /**
         * Si deux piles sont �gaux selon la m�thode equals(),
         * alors leur code de hachage doit �tre le m�me.
         */

        p1.empiler(820);
        p1.empiler(830);
        p1.empiler(840);

        p2.empiler(820);
        p2.empiler(830);
        p2.empiler(840);

        assertEquals ("Hachages de p1 et p2 sont �gales ?", true, 
            p1.hashCode() == p2.hashCode());
    }
}
