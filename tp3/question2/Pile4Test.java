package question2;

/**
 * Classe-test Pile4Test.
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
public class Pile4Test extends junit.framework.TestCase {
    private PileI p1;
    private PileI p2;
    /**
     * Constructeur de la classe-test Pile2Test
     */
    public Pile4Test() {
    }

    /**
     * Met en place les engagements.
     * 
     * Méthode appelée avant chaque appel de méthode de test.
     */
    protected void setUp() // throws java.lang.Exception
    {
        p1 = new question2.Pile4();
        p2 = new question2.Pile4();
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
        PileI p = new question2.Pile4(1);
        p.empiler(59);
        assertEquals(1, p.taille());

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
        PileI p = new question2.Pile4(10);
        assertEquals(true, p.estVide());

        p.empiler(new Integer(36));
        assertEquals(" sommet ?? ", new Integer(36), p.sommet());
        assertEquals(1, p.taille());
        assertEquals(" depiler ?? ", new Integer(36), p.depiler());
        assertEquals(0, p.taille());
    }

    public void test_Pile_estVide() throws Exception {
        PileI p = new question2.Pile4(2);
        assertEquals(true, p.estVide());
        try {
            Object r = p.depiler();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof question1.PileVideException);
        }
    }

    public void test_Pile_toString() throws Exception {
        PileI pile1 = new question2.Pile4(2);
        assertEquals("toString incorrect ? ", "[]", pile1.toString());
        pile1.empiler(208);
        assertEquals("toString incorrect ? ", "[208]", pile1.toString());
        pile1.empiler(599);
        assertEquals("toString incorrect ? ", "[599, 208]", pile1.toString());
    }

    public void test_Pile_TailleNegative() {
        PileI p = new question2.Pile4(-5);
        assertEquals(p.CAPACITE_PAR_DEFAUT, p.capacite());

    }

    public void test_Pile_equals() throws Exception {

        p1.empiler(100);
        p1.empiler(102);
        p1.empiler(104);

        p2.empiler(100);
        p2.empiler(102);
        p2.empiler(104);

        assertTrue("égalité de deux piles ? ", p1.equals(p2));
        assertTrue("égalité de deux piles ? ", p2.equals(p1));
        assertTrue("égalité de deux piles ? ", p1.equals(p1));

        p1.empiler(106);
        p2.empiler(106);
        assertTrue("égalité de deux piles ? ", p1.equals(p2));

        p1.empiler(null);
        p2.empiler(108);
        assertFalse("égalité de deux piles ? ", p1.equals(p2));
    }

    public void test_Pile_HashCode() throws Exception {
        /**
         * Si deux piles sont �gaux selon la m�thode equals(),
         * alors leur code de hachage doit �tre le m�me.
         */

        p1.empiler(61);
        p1.empiler(62);
        p1.empiler(63);

        p2.empiler(61);
        p2.empiler(62);
        p2.empiler(63);

        assertEquals ("Hachages de p1 et p2 sont �gales ?", true, 
            p1.hashCode() == p2.hashCode());
    }

}
