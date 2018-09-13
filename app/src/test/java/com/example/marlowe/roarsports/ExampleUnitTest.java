package com.example.marlowe.roarsports;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void beforeAll(){
        System.out.println("STARTING");
    }

    @Test
    public void upperCase(){
        String string = "abcde";
        String result = string.toUpperCase();
        assertNotNull(result);
        assertEquals("ABCDE", result);
    }
}

