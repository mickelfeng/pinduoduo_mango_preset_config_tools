import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.security.Key;
import java.util.stream.IntStream;

public class Main {

    public static String encrypt(String text, Key key, IvParameterSpec ivParameterSpec, String transformation) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            return new String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String text, Key key, IvParameterSpec ivParameterSpec, String transformation) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] original = cipher.doFinal(text.getBytes());
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(byte[] bArr, Key key, IvParameterSpec ivParameterSpec, String transformation) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(bArr);
            return encrypted;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] bArr, Key key, IvParameterSpec ivParameterSpec, String transformation) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] original = cipher.doFinal(bArr);
            return original;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] readByteFile(){
        String str = System.getProperty("user.dir")+"/mango_preset_config/mango_config.json";
        byte[] buffer = new byte[4096];
        FileInputStream inputStream = null;
        File file = new File(str);
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream2 = null;

        try {
            inputStream = new FileInputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream2 = new ByteArrayOutputStream();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (true){
            try {
                int rn = inputStream.read(buffer);
                if (rn != -1){
                    byteArrayOutputStream.write(buffer,0,rn);
                }else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                }
            } catch (IOException e) {
                byteArrayOutputStream2 = byteArrayOutputStream;
                try {
                    byteArrayOutputStream2.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveFile(byte[] bArr){
        File outputFile = new File(System.getProperty("user.dir")+"/mango_preset_config/mango_config_out.json");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputFile);
            if (bArr != null){
                outputStream.write(bArr);
                outputStream.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String K = "AES/CBC/PKCS5Padding";
        final Key M = new SecretKeySpec(Base64.decodeBase64("X2FtbV9jb25maWdfa2V5Xw=="), "AES");
        final IvParameterSpec N = new IvParameterSpec(new byte[]{33, 51, 68, 17, 66, 85, 119, 98, 1, 89, 49, 99, 22, 82, 73, 117});

        byte[] bArr = readByteFile();
        byte[] bDecrypt = decrypt(bArr, M, N, K);
        String s = new String(bDecrypt);
        saveFile(bDecrypt);
        System.out.println("decrypt: "+s);

    }
}