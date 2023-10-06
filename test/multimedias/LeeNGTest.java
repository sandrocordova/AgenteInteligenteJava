/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package multimedias;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class LeeNGTest {
    
    public LeeNGTest() {
    }

    @Test
    public void testLeer() {
        System.out.println("leer");
        String texto = "Este es un ejemplo";
        Lee instance = new Lee();
        instance.leer(texto);
    }
    
}
