import java.util.Scanner;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter plainText:: ");
        String plainText = input.nextLine();
        System.out.println("Enter key(0-25) :: ");
        int key = input.nextInt();
        //encryption part
        String encryptedMsg = encrypt(plainText,key);
        System.out.println("Encrypted message is :: "+ encryptedMsg);

        //decryption part
        String decryptedMsg = decrypt(encryptedMsg,key);
        System.out.println("Decrypted message is :: " + decryptedMsg);

        input.close();
    }

    private static String decrypt(String encryptedMsg, int key) {
        StringBuilder plainText = new StringBuilder();
        for(int i=0; i< encryptedMsg.length(); i++){
          char ch = encryptedMsg.charAt(i);

          if(Character.isLetter(ch)){
              char base = Character.isUpperCase(ch) ? 'A' : 'a';
              char decryptedChar = (char)((ch - base - key + 26) % 26 + base);
              plainText.append(decryptedChar);
          }
          else{
              //for non-alphabetical char
              plainText.append(ch);
          }
        }
        return plainText.toString();
    }

    private static String encrypt(String plainText, int key) {
        StringBuilder cipherText = new StringBuilder();

        for(int i =0; i<plainText.length(); i++){
            char ch = plainText.charAt(i);

            if(Character.isLetter(ch)){
                // in order to consider a or A -> 0 type correspondence
                char base = Character.isUpperCase(ch) ? 'A' : 'a';

                //applying encryption formula for caesar cipher
                char encryptedChar = (char) ((ch - base + key) % 26 + base);
                cipherText.append(encryptedChar);
            }
            else{
                //for non-alphabets we are using same char in there cipher also
                cipherText.append(ch);
            }
        }
        return cipherText.toString();
    }
}
