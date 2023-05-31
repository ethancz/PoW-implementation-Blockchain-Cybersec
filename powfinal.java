import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class powfinal {
    private static final String SHA_256 = "SHA-256";
    private static final String UTF_8 = "UTF-8";
    // Difficulty level for mining, change the number of zeroes to adjust difficulty
    private static final String START_HASH = "0000";

    public static void main(String[] args) {
        // Prompt user for input data
        System.out.println("Enter some data for a small hash generation");
        System.out.println("For BabyHash, all input data is converted to lower case");

        // Get start time for execution
        long t_begin = System.currentTimeMillis();

        // Get user input
        String inputString = getInputData();

        // Perform mining
        mine(inputString);

        // Get end time for execution
        long t_end = System.currentTimeMillis();

        // Calculate execution time in seconds
        double ex_time = (double) (t_end - t_begin) / 1000;

        // Display the execution time
        System.out.println("Mining time = " + ex_time + " sec");
    }

    // Function to get user input and convert it to lowercase
    private static String getInputData() {
        Scanner sc = new Scanner(System.in);
        String inputString = sc.nextLine();
        return inputString.toLowerCase();
    }

    // Function to perform mining until the hash starts with difficulty (START_HASH)
    private static void mine(String inputString) {
        String babyHash = "FFFF"; // Initialize hash value
        int nonce = 0;  // Initialize nonce

        // Keep trying different nonces until the hash starts with START_HASH
        while (!babyHash.equals(START_HASH)) {
            String inputStringWithNonce = inputString.concat(Integer.toString(nonce));
            String hash = computeSHA256AsHexString(inputStringWithNonce);
            babyHash = hash.substring(0, START_HASH.length());
            System.out.println("Nonce: " + nonce + " Hash: " + hash);
            nonce++;
        }
    }

    // Function to compute SHA-256 hash of the input string
    public static String computeSHA256AsHexString(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            byte[] hashBytes;
            digest.update(text.getBytes(UTF_8), 0, text.length());
            hashBytes = digest.digest();

            return convertToHex(hashBytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // handle problems
            System.out.println("Exception thrown " + e);
            return null;
        }
    }

    // Function which converts byte array to hex
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte datum : data) {
            int halfbyte = (datum >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9)
                        ? (char) ('0' + halfbyte)
                        : (char) ('a' + (halfbyte - 10)));
                halfbyte = datum & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}

