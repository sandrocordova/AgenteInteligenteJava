/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package agenteinteligente;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class AgenteInteligenteNGTest {
    
    public AgenteInteligenteNGTest() {
    }

    @Test
    public void testSetup() {
        System.out.println("setup");
        AgenteInteligente instance = new AgenteInteligente();
        instance.setup();
    }
    
}
