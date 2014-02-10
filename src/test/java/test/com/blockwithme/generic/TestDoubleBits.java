package test.com.blockwithme.generic;

public class TestDoubleBits {
    public static void main(final String[] args) {
        int failsAt = -1;
        long value = 1;
        for (int bit = 1; bit < 64; bit++) {
            value = value | (1L << bit);
            final double d = value;
            final long l2 = (long) d;
            if (value != l2) {
                failsAt = bit;
                break;
            }
        }
        System.out.println("failsAt: " + failsAt);
        value = value & ~(1L << failsAt);
        System.out.println("Max value decimal: " + value);
        System.out.println("Max value hex:     " + Long.toHexString(value));
        System.out.println("Max value binary:  " + Long.toBinaryString(value));
    }
}
