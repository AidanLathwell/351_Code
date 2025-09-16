import org.junit.Assert;
import org.junit.Test;

import static edu.gvsu.dlunit.DLUnit.*;

/**
 * JUnit test for a 16-bit unsigned adder.
 * Assumes:
 *  - 16-bit input pins: "A" and "B"
 *  - 16-bit output pin: "Sum"
 *  - Optional 1-bit overflow pin: "CarryOut"
 */
public class unsigned_tests {

    // Helper method to run a single test case
    private void testAddition(int a, int b) {
        long expected = (a & 0xFFFFL) + (b & 0xFFFFL); // Use long to prevent overflow
        int expectedSum = (int)(expected & 0xFFFF);    // Lower 16 bits
        int expectedCarry = (expected > 0xFFFF) ? 1 : 0;

        // Set input pins
        setPinUnsigned("A", a);
        setPinUnsigned("B", b);

        // Run simulation
        run();

        // Read output pins

        long actualSum = readPinUnsigned("Sum");

        // Check Sum
        Assert.assertEquals("Sum mismatch for A=" + a + " B=" + b,
                expectedSum, actualSum);

        // Check CarryOut if it exists
        long actualCarry = readPinUnsigned("CarryOut");
        Assert.assertEquals("CarryOut mismatch for A=" + a + " B=" + b,
                expectedCarry, actualCarry);
    }

    @Test
    public void testBasicCases() {
        testAddition(0, 0);               // 0 + 0 = 0
        testAddition(1, 0);               // 1 + 0 = 1
        testAddition(0, 1);               // 0 + 1 = 1
        testAddition(1, 1);               // 1 + 1 = 2
        testAddition(1234, 4321);         // Random values
    }

    @Test
    public void testEdgeCases() {
        testAddition(0xFFFF, 0x0001);     // Overflow: 65535 + 1 = 0 with carry
        testAddition(0xFFFF, 0xFFFF);     // Overflow: 65535 + 65535 = 0xFFFE with carry
        testAddition(0x8000, 0x8000);     // 32768 + 32768 = 65536 (carry)
        testAddition(0x7FFF, 0x0001);     // 32767 + 1 = 32768
        testAddition(0x1234, 0xEDCB);     // Random hex values
    }

    @Test
    public void testBoundaryCases() {
        testAddition(0x0000, 0xFFFF);
        testAddition(0xFFFF, 0x0000);
        testAddition(0x0001, 0xFFFE);
        testAddition(0xAAAA, 0x5555);     // Alternating bit pattern
    }
}
