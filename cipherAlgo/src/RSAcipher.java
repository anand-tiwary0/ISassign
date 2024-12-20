import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSAcipher {
    private int p, q, n, phi, e,d;

    //step1 : generate two distinct primes
    public boolean isPrime(int num){
        if(num <= 1) return false;
        for(int i = 2; i < Math.sqrt(num); i++){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }
    public int generateRandomPrime(int lower, int upper){
        Random random = new Random();
        int num;
        do{
            num = random.nextInt(upper - lower + 1) + 10;
        }while(!isPrime(num));
        return num;
    }
    public void generatePrime(int lower, int upper){
        p = generateRandomPrime(lower, upper);
        q = generateRandomPrime(lower, upper);
        while(p == q){
            q = generateRandomPrime(lower, upper);
        }
        System.out.println("Generated Prime P: " + p);
        System.out.println("Generated Prime Q: " + q);
    }

    //step 2: calculate n = p * q
    public void calculateN(){
        n = p * q;
    }
    //step 3: calculate phi(n)
    public void calculatePhi() {
        phi = (p - 1) * (q - 1);
    }
    // step4 : choose public exponents e such that 1 < e < phi(n) and gcd(e,phi(n)) == 1;
    public void choosePublicExponent(){
        Random random = new Random();
        for(e = 2; e < phi; e++){
            if(gcd(e, phi) == 1){
                break; // found e
            }
        }
    }
    public int gcd(int a, int b){
        if(b == 0) return a;
        return gcd(b, a % b);
    }
    // calculation of private key
    public void calculatePrivateExponent() {
        // Brute force (hit and trial) to find d
        for (d = 2; d < phi; d++) {
            if ((e * d) % phi == 1) {
                break;  // Found the correct d
            }
        }
    }

    // Step 6: Encrypt and Decrypt the message
    // Method to encrypt a message
    public int encrypt(int message) {
        return (int) (Math.pow(message, e) % n);
    }

    // Method to decrypt a message
    public int decrypt(int ciphertext) {
        return (int) (Math.pow(ciphertext, d) % n);
    }
    public void printall(){
        System.out.println("n: " + n + "; e = " + e + " ; d = " + d + "; phi = " + phi + "; p :" + p + "  ;q ::" + q );
    }
    public void generateKeys(int lower, int upper) {
        generatePrime(lower, upper);
        calculateN();
        calculatePhi();
        choosePublicExponent();
        calculatePrivateExponent();
        printall();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RSAcipher rsa = new RSAcipher();

        System.out.print("Enter the lower bound for prime generation: ");
        int lower = scanner.nextInt();
        System.out.print("Enter the upper bound for prime generation: ");
        int upper = scanner.nextInt();
        // Generate keys
        rsa.generateKeys(lower, upper);
        scanner.nextLine();

        // Reading message input
        System.out.println("Enter a message to encrypt:");
        String inpMessage = scanner.nextLine();
        System.out.println("Original Message: " + inpMessage);

        // we are using int so we will process message in block of 'int' bit only as msg must be less then product of primes;
        int[] msgArray = rsa.convertToArray(inpMessage);

        //now encrypting the message in blocks of 1 byte length;
        int[] encryptedMessage = new int[msgArray.length];
        for(int i = 0; i < msgArray.length; i++){
            encryptedMessage[i] = rsa.encrypt(msgArray[i]);
        }

        System.out.print("Encrypted Message (Ciphertext): ");
        for (int encryptedChar : encryptedMessage) {
            System.out.print(encryptedChar + " ");
        }
        System.out.println();

        // Decrypt the message
        int[] decryptedMessage = new int[encryptedMessage.length];
        for (int i = 0; i < encryptedMessage.length; i++) {
            decryptedMessage[i] = rsa.decrypt(encryptedMessage[i]);
        }
        // Convert the decrypted integer array back to string
        String decryptedMessageStr = rsa.convertIntArrayToMessage(decryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessageStr);
    }

    public String convertIntArrayToMessage(int[] decryptedMessage) {
        StringBuilder message = new StringBuilder();
        for (int i : decryptedMessage) {
            message.append((char) i);
        }
        return message.toString();
    }

    public int[] convertToArray(String inpMessage) {
        int[] messageArray = new int[inpMessage.length()];
        for(int i=0; i<inpMessage.length(); i++){
            messageArray[i] = inpMessage.charAt(i);
        }
        return messageArray;
    }
}
