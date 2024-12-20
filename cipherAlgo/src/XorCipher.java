import java.util.Scanner;

public class XorCipher {
    public static String xorCipher(String input, String key) {
        StringBuilder output = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < input.length(); i++) {
            char inputChar = input.charAt(i);
            char keyChar = key.charAt(i % keyLength); // Reuse the key cyclically
            char resultChar = (char) (inputChar ^ keyChar); // XOR operation
            output.append(resultChar);
        }

        return output.toString();
    }

    // Method to convert a character to its 8-bit binary representation
    public static String charToBinary(char c) {
        return String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
    }

    // Method to display the binary XOR process
    public static void displayBinaryProcess(String input, String key, String output) {
        int keyLength = key.length();

        System.out.println("Character-by-Character XOR Process:");
        System.out.println("Plaintext Binary | Key Binary | Encrypted Binary | XOR Result");
        for (int i = 0; i < input.length(); i++) {
            char inputChar = input.charAt(i);
            char keyChar = key.charAt(i % keyLength);
            char resultChar = output.charAt(i);

            System.out.printf("%15s | %10s | %15s | %10s%n",
                    charToBinary(inputChar),
                    charToBinary(keyChar),
                    charToBinary(resultChar),
                    charToBinary((char) (inputChar ^ keyChar))
            );
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input plaintext
        System.out.print("Enter your message: ");
        String plaintext = scanner.nextLine();

        // Input key
        System.out.print("Enter encryption key: ");
        String key = scanner.nextLine();

        // Encrypt the plaintext
        String encryptedMessage = xorCipher(plaintext, key);

        // Display binary XOR process
        System.out.println("\n--- XOR Process (Binary Representation) ---");
        displayBinaryProcess(plaintext, key, encryptedMessage);

        // Decrypt the message
        String decryptedMessage = xorCipher(encryptedMessage, key);

        System.out.println("\n--- Results ---");
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);

        scanner.close();
    }
}
