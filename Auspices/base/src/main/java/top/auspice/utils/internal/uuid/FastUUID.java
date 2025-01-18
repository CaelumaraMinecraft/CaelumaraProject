package top.auspice.utils.internal.uuid;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("unused")
public final class FastUUID {
    private static final boolean USE_JDK_UUID_TO_STRING;
    private static final int UUID_STRING_LENGTH = 36;
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final long[] NIBBLES = new long[128];
    public static final UUID ZERO = new UUID(0L, 0L);

    public static UUID randomUUID(Random random) {
        byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        randomBytes[6] = (byte)(randomBytes[6] & 15);
        randomBytes[6] = (byte)(randomBytes[6] | 64);
        randomBytes[8] = (byte)(randomBytes[8] & 63);
        randomBytes[8] = (byte)(randomBytes[8] | 128);
        return bytesToUUID(randomBytes);
    }

    public static UUID bytesToUUID(byte[] data) {
        long msb = 0L;
        long lsb = 0L;

        int i;
        for(i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 255);
        }

        for(i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 255);
        }

        return new UUID(msb, lsb);
    }

    public static boolean equals(UUID first, UUID other) {
        return first.getMostSignificantBits() == other.getMostSignificantBits() && first.getLeastSignificantBits() == other.getLeastSignificantBits();
    }

     public static UUID fromString(CharSequence uuid) {
        try {
            long mostSignificantBits = getHexValueForChar(uuid.charAt(0)) << 60 | getHexValueForChar(uuid.charAt(1)) << 56 | getHexValueForChar(uuid.charAt(2)) << 52 | getHexValueForChar(uuid.charAt(3)) << 48 | getHexValueForChar(uuid.charAt(4)) << 44 | getHexValueForChar(uuid.charAt(5)) << 40 | getHexValueForChar(uuid.charAt(6)) << 36 | getHexValueForChar(uuid.charAt(7)) << 32 | getHexValueForChar(uuid.charAt(9)) << 28 | getHexValueForChar(uuid.charAt(10)) << 24 | getHexValueForChar(uuid.charAt(11)) << 20 | getHexValueForChar(uuid.charAt(12)) << 16 | getHexValueForChar(uuid.charAt(14)) << 12 | getHexValueForChar(uuid.charAt(15)) << 8 | getHexValueForChar(uuid.charAt(16)) << 4 | getHexValueForChar(uuid.charAt(17));
            long leastSignificantBits = getHexValueForChar(uuid.charAt(19)) << 60 | getHexValueForChar(uuid.charAt(20)) << 56 | getHexValueForChar(uuid.charAt(21)) << 52 | getHexValueForChar(uuid.charAt(22)) << 48 | getHexValueForChar(uuid.charAt(24)) << 44 | getHexValueForChar(uuid.charAt(25)) << 40 | getHexValueForChar(uuid.charAt(26)) << 36 | getHexValueForChar(uuid.charAt(27)) << 32 | getHexValueForChar(uuid.charAt(28)) << 28 | getHexValueForChar(uuid.charAt(29)) << 24 | getHexValueForChar(uuid.charAt(30)) << 20 | getHexValueForChar(uuid.charAt(31)) << 16 | getHexValueForChar(uuid.charAt(32)) << 12 | getHexValueForChar(uuid.charAt(33)) << 8 | getHexValueForChar(uuid.charAt(34)) << 4 | getHexValueForChar(uuid.charAt(35));
            return new UUID(mostSignificantBits, leastSignificantBits);
        } catch (Throwable var5) {
            throw new MalformedUUIDException(uuid, var5);
        }
    }

    public static String toString(UUID uuid) {
        if (USE_JDK_UUID_TO_STRING) {
            return uuid.toString();
        } else {
            long mostSignificantBits = uuid.getMostSignificantBits();
            long leastSignificantBits = uuid.getLeastSignificantBits();
            char[] uuidChars = new char[]{HEX_DIGITS[(int)((mostSignificantBits & -1152921504606846976L) >>> 60)], HEX_DIGITS[(int)((mostSignificantBits & 1080863910568919040L) >>> 56)], HEX_DIGITS[(int)((mostSignificantBits & 67553994410557440L) >>> 52)], HEX_DIGITS[(int)((mostSignificantBits & 4222124650659840L) >>> 48)], HEX_DIGITS[(int)((mostSignificantBits & 263882790666240L) >>> 44)], HEX_DIGITS[(int)((mostSignificantBits & 16492674416640L) >>> 40)], HEX_DIGITS[(int)((mostSignificantBits & 1030792151040L) >>> 36)], HEX_DIGITS[(int)((mostSignificantBits & 64424509440L) >>> 32)], '-', HEX_DIGITS[(int)((mostSignificantBits & 4026531840L) >>> 28)], HEX_DIGITS[(int)((mostSignificantBits & 251658240L) >>> 24)], HEX_DIGITS[(int)((mostSignificantBits & 15728640L) >>> 20)], HEX_DIGITS[(int)((mostSignificantBits & 983040L) >>> 16)], '-', HEX_DIGITS[(int)((mostSignificantBits & 61440L) >>> 12)], HEX_DIGITS[(int)((mostSignificantBits & 3840L) >>> 8)], HEX_DIGITS[(int)((mostSignificantBits & 240L) >>> 4)], HEX_DIGITS[(int)(mostSignificantBits & 15L)], '-', HEX_DIGITS[(int)((leastSignificantBits & -1152921504606846976L) >>> 60)], HEX_DIGITS[(int)((leastSignificantBits & 1080863910568919040L) >>> 56)], HEX_DIGITS[(int)((leastSignificantBits & 67553994410557440L) >>> 52)], HEX_DIGITS[(int)((leastSignificantBits & 4222124650659840L) >>> 48)], '-', HEX_DIGITS[(int)((leastSignificantBits & 263882790666240L) >>> 44)], HEX_DIGITS[(int)((leastSignificantBits & 16492674416640L) >>> 40)], HEX_DIGITS[(int)((leastSignificantBits & 1030792151040L) >>> 36)], HEX_DIGITS[(int)((leastSignificantBits & 64424509440L) >>> 32)], HEX_DIGITS[(int)((leastSignificantBits & 4026531840L) >>> 28)], HEX_DIGITS[(int)((leastSignificantBits & 251658240L) >>> 24)], HEX_DIGITS[(int)((leastSignificantBits & 15728640L) >>> 20)], HEX_DIGITS[(int)((leastSignificantBits & 983040L) >>> 16)], HEX_DIGITS[(int)((leastSignificantBits & 61440L) >>> 12)], HEX_DIGITS[(int)((leastSignificantBits & 3840L) >>> 8)], HEX_DIGITS[(int)((leastSignificantBits & 240L) >>> 4)], HEX_DIGITS[(int)(leastSignificantBits & 15L)]};
            return new String(uuidChars);
        }
    }

    private static long getHexValueForChar(char ch) {
        try {
            long hex = NIBBLES[ch];
            if (hex == -1L) {
                throw new IllegalArgumentException("Illegal hexadecimal digit: " + ch);
            } else {
                return hex;
            }
        } catch (ArrayIndexOutOfBoundsException var3) {
            throw new IllegalArgumentException("Illegal hexadecimal digit: " + ch);
        }
    }

    static {
        String java = System.getProperty("java.specification.version");

        int version;
        try {
            version = Integer.parseInt(java);
        } catch (NumberFormatException var3) {
            version = 0;
        }

        USE_JDK_UUID_TO_STRING = version >= 11;
        Arrays.fill(NIBBLES, -1L);
        NIBBLES[48] = 0L;
        NIBBLES[49] = 1L;
        NIBBLES[50] = 2L;
        NIBBLES[51] = 3L;
        NIBBLES[52] = 4L;
        NIBBLES[53] = 5L;
        NIBBLES[54] = 6L;
        NIBBLES[55] = 7L;
        NIBBLES[56] = 8L;
        NIBBLES[57] = 9L;
        NIBBLES[97] = 10L;
        NIBBLES[98] = 11L;
        NIBBLES[99] = 12L;
        NIBBLES[100] = 13L;
        NIBBLES[101] = 14L;
        NIBBLES[102] = 15L;
        NIBBLES[65] = 10L;
        NIBBLES[66] = 11L;
        NIBBLES[67] = 12L;
        NIBBLES[68] = 13L;
        NIBBLES[69] = 14L;
        NIBBLES[70] = 15L;
    }
}
