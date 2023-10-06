/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package principal;

import clases.Llamada;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class MainNGTest {
    
    public MainNGTest() {
    }

    @Test 
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Main.main(args);
    }

    @Test
    public void testHay_llamada() {
        System.out.println("hay_llamada");
        Llamada llamada = null;
        Main.hay_llamada(llamada);
    }

    @Test
    public void testTransferir_llamada() {
        System.out.println("transferir_llamada");
        Llamada llamada = null;
        boolean expResult = true;
        boolean result = Main.transferir_llamada(llamada);
        assertEquals(result, expResult);
    }

    @Test
    public void testMenuAdministrador() {
        System.out.println("menuAdministrador");
        Main.menuAdministrador();
        fail("The test case is a prototype.");
    }
    
}
