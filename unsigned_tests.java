import org.junit.Assert;
import org.junit.Test;

import static edu.gvsu.dlunit.DLUnit.*;

/**
 * Tests for a 16-bit unsigned adder.
 *
 * Ensure your circuit has 16-bit inputs named "InputA" and "InputB",
 * and a 16-bit output named "Output", and optionally a 1-bit "CarryOut".
 */
public class Adder16BitTest {

    // Helper to test a single case
    private void testAddition(int a, int b) {
        long expected = (a & 0xFFFFL) + (b & 0xFFFFL); // prevent overflow
        int expectedSum = (int)(expected & 0xFFFF);
        int expectedCarry = (expected > 0xFFFF) ? 1 : 0;

        setPinUnsigned("InputA", a);
        setPinUnsigned("InputB", b);
        run();

        int actualSum = readPinUnsigned("Output");

        // Check sum
        Assert.assertEquals("Sum mismatch for A=" + a + " B=" + b,
                expectedSum, actualSum);

        
        int actualCarry = readPinUnsigned("CarryOut");
        Assert.assertEquals("CarryOut mismatch for A=" + a + " B=" + b,
            expectedCarry, actualCarry);
    }

    @Test
    public void testBasicCases() {
        testAddition(0, 0);               // 0 + 0 = 0
        testAddition(1, 0);               // 1 + 0 = 1
        testAddition(0, 1);               // 0 + 1 = 1
        testAddition(1, 1);               // 1 + 1 = 2
        testAddition(1234, 4321);         // random values
    }

    @Test
    public void testEdgeCases() {
        testAddition(0xFFFF, 0x0001);     // overflow, expect 0x0000 with carry
        testAddition(0xFFFF, 0xFFFF);     // overflow, expect 0xFFFE with carry
        testAddition(0x8000, 0x8000);     // 32768 + 32768 = 65536 (overflow)
        testAddition(0x7FFF, 0x0001);     // 32767 + 1 = 32768
        testAddition(0x1234, 0xEDCB);     // check non-trivial values
    }

    @Test
    public void testBoundaryCases() {
        testAddition(0x0000, 0xFFFF);
        testAddition(0xFFFF, 0x0000);
        testAddition(0x0001, 0xFFFE);
        testAddition(0xAAAA, 0x5555);     // alternating bits
    }
}
