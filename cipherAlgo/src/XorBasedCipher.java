import java.util.Scanner;
//correct

public class XorBasedCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter plainText:: ");
        String plainText = input.nextLine();
        System.out.println("Enter key (ideally of same length as plaintext max security - one time pad) :: ");
        String key = input.nextLine();

        //encryption part
        String encryptedMessage = xorCipher(plainText, key);
        System.out.println("Encypted message :: " + encryptedMessage);

        //Decryption part
        String decryptedMsg = xorCipher(encryptedMessage, key);
        System.out.println("decrypted message :: " + decryptedMsg);

        printHex(plainText, key, encryptedMessage);
    }
    private static void printHex(String plaintext, String key, String encryptedMsg) {
        System.out.println("\nInput Text (Hex):");
        for (int i = 0; i < plaintext.length(); i++) {
            System.out.printf("%02X ", (int) plaintext.charAt(i));
        }
        System.out.println();

        // Display the key in hexadecimal (repeated if necessary)
        System.out.println("Key (Hex):");
        for (int i = 0; i < plaintext.length(); i++) {
            System.out.printf("%02X ", (int) key.charAt(i % key.length()));
        }
        System.out.println();

        // Display the encrypted message in hexadecimal
        System.out.println("Encrypted Message (Hex):");
        for (int i = 0; i < encryptedMsg.length(); i++) {
            System.out.printf("%02X ", (int) encryptedMsg.charAt(i));
        }
        System.out.println();
    }

    private static String xorCipher(String msg, String key){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i< msg.length(); i++){
            char inputChar = msg.charAt(i);
            char keyChar = key.charAt(i % key.length()); //for key repetition if keyLength < plainTextLength

            char resultChar = (char)(inputChar ^ keyChar);
            result.append(resultChar);
        }
        return result.toString();
    }

}
