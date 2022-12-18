import java.lang.Math;
import java.util.Map;
import java.util.HashMap;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class Pdu {
    private String pduString;
    private byte pduBytes[];
    //total de bytes pretendido em cada bloco
    private static int block = 64;
    private static String key = "CC2020";

    /**
     * Método construtor
     * @param s   String a ser convertida
     */
    public Pdu(String s){
        this.pduString = s;
        this.pduBytes  = s.getBytes();
    }

    /**
     * Método construtor
     * @param b   Conjunto de bytes a ser convertidos
     */
    public Pdu(byte[] b, int syze){
        this.pduBytes  = new byte[syze];
        for(int i = 0; i < syze; i++)
            this.pduBytes[i]  = b[i];
        this.pduString = new String(this.pduBytes, 0, this.pduBytes.length);
    }

    public String getPduString(){
        return this.pduString;
    }

    public byte[] getPduBytes(){
        byte aux[] = new byte[this.pduBytes.length];
        for(int i = 0; i < this.pduBytes.length; i++)
            aux[i] = this.pduBytes[i];

        return aux;
    }

    /**
     * Função que divide a mensagem guardada num Pdu em diversos blocos,
     * com o tamanho maximo predefinido. Regista tambem qual a posicao do bloco na mensagem,
     * por forma a poder ordenar os blocos posteriormente
     * @param qAntes   Valor a partir do qual devem ser as posicoes dos blocos
     */
    public Map<Integer, byte[]> getByteBlocks(int qAntes){
    	//estrutura que ira conter todos os blocos de informacao associados a sua posicao
        Map<Integer, byte[]> ret = new HashMap<>();
        byte aux[];
        int min = 0;
        String posS;
        byte posB[];

        for(int i = qAntes; min < this.pduBytes.length; i++){

            posS = new String(i + ",");
            posB = new byte[this.block];
            posB = posS.getBytes();

            /*Definicao do tamanho do bloco
              (por forma a nao superar o tamanho maximo,
               ao mesmo tempo que nao se aloca memoria desnecessaria)*/
            int tam = Math.min(this.block, this.pduBytes.length-min+posB.length);
            aux = new byte[tam];

            /*Criacao da informacao que o bloco contera,
              inserindo primeiramente a informacao relativa a posicao,
              seguida do segmento de mensagem que deve conter*/
            System.arraycopy(posB, 0, aux, 0, posB.length);
            System.arraycopy(this.pduBytes, min, aux, posB.length, tam-posB.length);

            //adicao do bloco e da sua posicao a estrutura contendo todos os blocos
            ret.put(i, aux);

            min = min + this.block - posB.length;
        }

        return ret;
    }

    /**
     * Função processa a informacao contida numa mensagem, quando esta
     * é proveniente de um bloco. A partir desta, retira a posicao do bloco
     * e a informacao que contem
     */
    public int posBlock() throws Exception{
    	String aux = this.pduString;

    	/*Separacao da posicao do bloco da restante informacao,
    	ja que estas estao separadas por uma virgula*/
    	String[] divisor = aux.split(",", 2);
    	//obtencao da posicao do bloco
    	int pos = Integer.parseInt(divisor[0]);
    	//obtencao da informacao do bloco
    	this.pduString = divisor[1];
    	this.pduBytes = this.pduString.getBytes();

    	return pos;
    }

    /**
     * Função que encripta a informacao guardada num array de bytes
     * @param data   informacao a encriptar
     */
    public byte[] encriptacao(byte[] data) throws Exception{

        //preparar o nonce
        SecureRandom nonce = new SecureRandom();

        //o nonce deverá possuir 12 bytes
        byte[] iv = new byte[12];
        nonce.nextBytes(iv);

        //criacao da chave de encriptacao
        SecretKey secretKey = gerarSecretKey(this.key, iv);


        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        //encriptar a informacao
        byte [] encryptedData = cipher.doFinal(data);

        //concatenar tudo e envio da informacao
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + encryptedData.length);
        byteBuffer.putInt(iv.length);
        byteBuffer.put(iv);
        byteBuffer.put(encryptedData);
        return byteBuffer.array();
    }

    /**
     * Função que desencripta a informacao guardada num array de bytes
     * @param encryptedData   informacao a desencriptar
     */
    public byte[] desencriptacao(byte[] encryptedData) throws Exception{

        //obtencao da informacao guardada
        ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);

        int nonceSize = byteBuffer.getInt();

        //confirmar a encriptacao do ficheiro
        if(nonceSize < 12 || nonceSize >= 16) {
            throw new Exception("Encriptacao incorreta.");
        }
        byte[] iv = new byte[nonceSize];
        byteBuffer.get(iv);

        //criacao da chave de encriptacao
        SecretKey secretKey = gerarSecretKey(this.key, iv);

        //obtencao da informacao
        byte[] cipherBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherBytes);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        //desencriptar a informacao
        return cipher.doFinal(cipherBytes);

    }

    /**
     * Funcao que gera uma chave a partir dos parametros passados
     * @param password
     * @param iv
     */
    public SecretKey gerarSecretKey(String password, byte[] iv) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), iv, 65536, 128); // AES-128
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] key = secretKeyFactory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }
}