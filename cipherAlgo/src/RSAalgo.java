import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSAalgo {
    private BigInteger p, q, n, phi, e, d;

    //step1 : generate two distinct primes
    public boolean isPrime(BigInteger num){
        if(num.compareTo(BigInteger.ONE) <= 0) return false;
        for(BigInteger i = BigInteger.valueOf(2); i.compareTo(num.sqrt()) < 0; i = i.add(BigInteger.ONE)){
            if(num.mod(i).equals(BigInteger.ZERO)){
                return false;
            }
        }
        return true;
    }

    public BigInteger generateRandomPrime(BigInteger lower, BigInteger upper){
        Random random = new Random();
        BigInteger num;
        do{
            num = new BigInteger(upper.bitLength(), random);
        } while (num.compareTo(lower) < 0 || num.compareTo(upper) > 0 || !isPrime(num));
        return num;
    }

    public void generatePrime(BigInteger lower, BigInteger upper){
        p = generateRandomPrime(lower, upper);
        q = generateRandomPrime(lower, upper);
        while(p.equals(q)){
            q = generateRandomPrime(lower, upper);
        }
        System.out.println("Generated Prime P: " + p);
        System.out.println("Generated Prime Q: " + q);
    }

    //step 2: calculate n = p * q
    public void calculateN(){
        n = p.multiply(q);
    }

    //step 3: calculate phi(n)
    public void calculatePhi() {
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    // step4 : choose public exponents e such that 1 < e < phi(n) and gcd(e,phi(n)) == 1;
    public void choosePublicExponent(){
        Random random = new Random();
        e = BigInteger.valueOf(2);
        while(e.compareTo(phi) < 0){
            if(gcd(e, phi).equals(BigInteger.ONE)){
                break; // found e
            }
            e = e.add(BigInteger.ONE);
        }
    }

    public BigInteger gcd(BigInteger a, BigInteger b){
        while (!b.equals(BigInteger.ZERO)){
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }

    // calculation of private key
    public void calculatePrivateKey() {
        d = e.modInverse(phi);
    }

    // Step 6: Encrypt and Decrypt the message
    // Method to encrypt a message
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    // Method to decrypt a message
    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n);
    }

    public void printall(){
        System.out.println("n: " + n + "; e = " + e + " ; d = " + d + "; phi = " + phi + "; p :" + p + "  ;q ::" + q );
    }

    public void generateKeys(BigInteger lower, BigInteger upper) {
        generatePrime(lower, upper);
        calculateN();
        calculatePhi();
        choosePublicExponent();
        calculatePrivateKey();
        printall();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        RSAalgo rsa = new RSAalgo();

        System.out.print("Enter the lower bound for prime generation: ");
        BigInteger lower = new BigInteger(input.nextLine());
        System.out.print("Enter the upper bound for prime generation: ");
        BigInteger upper = new BigInteger(input.nextLine());
        input.nextLine();
        // Generate keys
        rsa.generateKeys(lower, upper);


        // Reading message input
        System.out.println("Enter a message to encrypt:");
        String inpMessage = input.nextLine();
        System.out.println("Original Message: " + inpMessage);

        // Convert message to BigInteger array
        BigInteger[] msgArray = rsa.convertToArray(inpMessage);

        // Encrypt the message in blocks
        BigInteger[] encryptedMessage = new BigInteger[msgArray.length];
        for (int i = 0; i < msgArray.length; i++) {
            encryptedMessage[i] = rsa.encrypt(msgArray[i]);
        }

        System.out.print("Encrypted Message (Ciphertext): ");
        for (BigInteger encryptedChar : encryptedMessage) {
            System.out.print(encryptedChar + " ");
        }
        System.out.println();

        // Decrypt the message
        BigInteger[] decryptedMessage = new BigInteger[encryptedMessage.length];
        for (int i = 0; i < encryptedMessage.length; i++) {
            decryptedMessage[i] = rsa.decrypt(encryptedMessage[i]);
        }

        // Convert the decrypted BigInteger array back to string
        String decryptedStr = rsa.arrayToMessage(decryptedMessage);
        System.out.println("Decrypted Message: " + decryptedStr);
    }


public String arrayToMessage(BigInteger[] decryptedMessage) {
    StringBuilder message = new StringBuilder();
    for (BigInteger i : decryptedMessage) {
        // Check if BigInteger can fit into a character range
        int intValue = i.intValue();

        // Ensure the int value is within valid char range (0 to 65535)
        if (intValue < 0 || intValue > 65535) {
            System.out.println("Decrypted value out of valid char range: " + intValue);
            continue; // Skip or handle invalid values
        }

        message.append((char) intValue);  // Convert the int to char and append it
    }
    return message.toString();
}


 // for block wise RSA encryption one element will be taken as one block
    public BigInteger[] convertToArray(String inpMessage) {
        BigInteger[] messageArray = new BigInteger[inpMessage.length()];
        for (int i = 0; i < inpMessage.length(); i++) {
            messageArray[i] = BigInteger.valueOf(inpMessage.charAt(i));
        }
        return messageArray;
    }
}

