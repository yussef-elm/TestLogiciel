import com.enseirb.models.AirCraft;
import com.enseirb.tcas.TCAS;
import org.junit.Test;
import java.util.Locale;

import static junit.framework.Assert.*;

public class TCASTest {



    @Test
    public void testIsInND() {
        TCAS tcas = new TCAS(new AirCraft("F333", 50, 40, 2, 10000, -Math.PI / 4));
        AirCraft other = new AirCraft("F222", 240, 110, 3, 10000, Math.PI / 2);
        boolean result = tcas.isInND(other);
        assertTrue(result);

        AirCraft other1 = new AirCraft("T390", 240, 110, 1, 9900, Math.PI / 3);
        boolean result1 = tcas.isInND(other1);
        assertTrue(result1);

        AirCraft other2 = new AirCraft("R789", -180, -30, 4, 9500, -Math.PI / 5);
        boolean result2 = tcas.isInND(other2);
        assertFalse(result2);

        AirCraft other3 = new AirCraft("F098", 600, 400, 2.2, 10050, Math.PI / 10);
        boolean result3 = tcas.isInND(other3);
        assertFalse(result3);

    }

    @Test
    public void testIsInTA() {
        TCAS tcas = new TCAS(new AirCraft("F333", 100, 100, 2, 10000, -Math.PI / 4));

        AirCraft other = new AirCraft("F222", 210, 150, 3, 10000, Math.PI / 8);
        boolean result = tcas.isInTA(other);
        assertTrue(result);

        AirCraft other1 = new AirCraft("F111", 210, 150, 5, 9750, Math.PI / 6);
        boolean result1 = tcas.isInTA(other1);
        assertTrue(result1);

        AirCraft other2 = new AirCraft("F242", 210, 150, 1.4, 9100, Math.PI / 2);
        boolean result2 = tcas.isInTA(other2);
        assertFalse(result2);

        AirCraft other3 = new AirCraft("F777", 300, 180, 2.3, 10200, Math.PI / 10);
        boolean result3 = tcas.isInTA(other3);
        assertFalse(result3);

    }

    @Test
    public void testIsInRA() {
        TCAS tcas = new TCAS(new AirCraft("F333", 340, 210, 2, 10000, -Math.PI / 4));
        AirCraft other = new AirCraft("F222", 390, 250, 3, 10000, Math.PI / 8);
        boolean result = tcas.isInRA(other);
        assertTrue(result);

        AirCraft other1 = new AirCraft("F111", 390, 250, 1.9, 9800, Math.PI / 2);
        boolean result1 = tcas.isInRA(other1);
        assertTrue(result1);

        AirCraft other2 = new AirCraft("R242", 390, 250, 2.9, 9300, Math.PI / 3);
        boolean result2 = tcas.isInRA(other2);
        assertFalse(result2);

        AirCraft other3 = new AirCraft("C777", 420, 120, 2, 10150, Math.PI / 7);
        boolean result3 = tcas.isInRA(other3);
        assertFalse(result3);
    }

    @Test
    public void testIsInCLEAR() {

        TCAS tcas = new TCAS(new AirCraft("F333", 50, 40, 2, 10000, -Math.PI / 4));
        AirCraft other = new AirCraft("G456", 70, 10, 3, 11000, Math.PI / 2);
        boolean result = tcas.isInCLEAR(other);
        assertTrue(result);

        AirCraft other1 = new AirCraft("T003", 600, 400, 2.2, 10000, Math.PI / 10);
        boolean result1 = tcas.isInCLEAR(other1);
        assertTrue(result1);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        TCAS tcas = new TCAS(new AirCraft("F333", 340, 210, 2, 10000, -Math.PI / 4));
        tcas.isInND(null);
        tcas.isInRA(null);
        tcas.isInTA(null);
    }



    @Test
    public void testTrafficDetector() {
        TCAS tcas = new TCAS(new AirCraft("F333", 340, 210, 2, 10000, -Math.PI / 4));

        // Test si l'avion est dans ND ou CLEAR il n'affiche aucun message
        AirCraft other =  new AirCraft("T007", 500, 300, 2, 10000, -Math.PI / 4);
        String result = tcas.trafficDetector(other);
        assertEquals("", result);

        // Test si l'avion est dans TA il affiche bien le message "Traffic Traffic"
        AirCraft other1 =  new AirCraft("R008", 230, 190, 1, 9900, -Math.PI / 4);
        String result1 = tcas.trafficDetector(other1);
        assertEquals("ALERT : Traffic, Traffic!".toUpperCase(Locale.ROOT)+"\n",result1 );

        //Test avec un avion dans RA est qu'elle se trouve au dessous le message "Climb,Clim now!" s'affiche
        AirCraft other2 =new AirCraft("E333", 300, 190, 1.9, 9800, Math.PI / 3);
        String result2 =tcas.trafficDetector(other2);
        assertEquals("Required Action : Climb, climb now!".toUpperCase(Locale.ROOT)+"\n", result2);

        //Test avec un avion dans RA est qu'elle se trouve au dessus le message "Descend, descend now!" s'affiche
        AirCraft other3 =new AirCraft("T657", 300, 190, 2.2, 10250, -Math.PI / 6);
        String result3 = tcas.trafficDetector(other3);
        assertEquals("Required Action : Descend, descend now!".toUpperCase(Locale.ROOT)+"\n", result3);

        //Test si l'avion n'est pas dans aucune zone aucun message ne doit s'afficher
        AirCraft other4 =  new AirCraft("T207", 340, 210, 4, 11000, -Math.PI / 4);
        String result4 = tcas.trafficDetector(other4);
        assertEquals("", result4);

    }

}

