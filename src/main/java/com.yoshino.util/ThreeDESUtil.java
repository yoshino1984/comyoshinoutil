package com.yoshino.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ThreeDESUtil {

    //    private static Logger logger = LogManager.getLogger();
    private static final String ALGORITHM = "DESede"; // 定义 加密算法,可用
    // DES,DESede,Blowfish
    private static byte[] keyByte = {0x11, 0x28, 0x4F, 0x58, (byte) 0x88,
            0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
            0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,
            (byte) 0xE2}; // 24字节的密钥

//    public static String encryptMode(String src) {
//        String encrypt = null;
//        try {
//            byte[] ret = encryptMode(src.getBytes("UTF-8"));
//            encrypt = new String(Base64.encodeBase64(ret));
//            encrypt = encrypt.replaceAll("=", "Q");
//        } catch (Exception e) {
//            logger.error("ThreeDES Error");
//        }
//        return encrypt;
//    }
//
//    /**
//     * 加密
//     *
//     * @param src
//     * @return
//     */
//    private static byte[] encryptMode(byte[] src) {
//        try {
//            // 生成密钥
//            SecretKey desKey = new SecretKeySpec(keyByte, ALGORITHM);
//            // 加密
//            Cipher c1 = Cipher.getInstance(ALGORITHM);
//            c1.init(Cipher.ENCRYPT_MODE, desKey);
//            return c1.doFinal(src);
//        } catch (Exception e) {
//            logger.error("ThreeDES Error");
//        }
//        return null;
//    }
//
//    private static String getDESSecretKey(Byte siteId) {
//        int length = 24;
//        StringBuilder secret = new StringBuilder();
//        if (siteId == 0) {
//            secret.append(OtherConst.PC_JC_SECRET_KEY);
//        } else if (siteId == AppEnum.MARKAVIP.getSiteId()) {
//            secret.append(OtherConst.PC_MV_SECRET_KEY);
//        }
//        int secretLength = secret.length();
//        if (secretLength >= length) {
//            return secret.toString();
//        }
//        for (int i = 1; i <= length - secretLength; i++) {
//            secret.append("0");
//        }
//        return secret.toString();
//    }
//
//    /**
//     * 3DES加密+BASE64
//     *
//     * @param src
//     * @return
//     */
//    public static String encode3DES(String src, Byte siteId) {
//        try {
//            // 生成密钥
//            byte[] keyByte = getDESSecretKey(siteId).getBytes("UTF-8");
//            SecretKey desKey = new SecretKeySpec(keyByte, ALGORITHM);
//            // 加密
//            Cipher c1 = Cipher.getInstance(ALGORITHM);
//            c1.init(Cipher.ENCRYPT_MODE, desKey);
//            byte[] ret = c1.doFinal(src.getBytes("UTF-8"));
//            return Base64.encodeBase64String(ret);
//        } catch (Exception e) {
//            logger.error("DES加密发生异常", e);
//        }
//        return null;
//    }
//
//    /**
//     * 获取加密的商品编号
//     *
//     * @param goodsId
//     * @return
//     */
//
//    public static String getEncryptGoodsID(String goodsId, Byte siteId) {
//        if (goodsId == null) {
//            return null;
//        }
//        goodsId = encode3DES(goodsId, siteId);
//        Map<String, String> replaceItems = new HashMap<>();
//        //定向替换
//        replaceItems.put("=", "oo-73");
//        replaceItems.put("+", "9io-24");
//        replaceItems.put("/", "t3o-35");
//
//        for (Map.Entry<String, String> entry : replaceItems.entrySet()) {
//            goodsId = goodsId.replace(entry.getKey(), entry.getValue());
//        }
//
//        //大写转小写，且前面加入-
//        StringBuilder result = new StringBuilder();
//
//        for (int i = 0; i < goodsId.length(); i++) {
//            Character c = goodsId.charAt(i);
//            if (Character.isUpperCase(c)) {
//                result.append("-").append(Character.toLowerCase(c));
//            } else {
//                result.append(c);
//            }
//        }
//        return result.toString();
//    }

    /**
     * 获取私钥（调查问卷userId加密）
     *
     * @return
     */
    private static String getDESSecretKeySurvey() {
        int length = 24;
        StringBuilder secret = new StringBuilder();
        secret.append(current_key);
        int secretLength = secret.length();
        if (secretLength >= length) {
            return secret.toString();
        }
        for (int i = 1; i <= length - secretLength; i++) {
            secret.append("0");
        }
        return secret.toString();
    }

    /**
     * 3DES加密+BASE64（调查问卷userId加密）
     *
     * @param src
     * @return
     */
    private static String encode3DESSurvey(String src) {
        try {
            // 生成密钥
            byte[] keyByte = getDESSecretKeySurvey().getBytes("UTF-8");
            SecretKey desKey = new SecretKeySpec(keyByte, ALGORITHM);
            // 加密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] ret = c1.doFinal(src.getBytes("UTF-8"));
            return Base64.encodeBase64String(ret);
        } catch (Exception e) {
            Logger.getGlobal().warning("DES加密发生异常-survey:\n" + e);
        }
        return null;
    }

    /**
     * 解密（调查问卷userId加密）
     *
     * @param src
     * @return
     */
    private static String decode3DESSurvey(String src) {
        try {
            // 生成密钥
            byte[] keyByte = getDESSecretKeySurvey().getBytes("UTF-8");
            SecretKey desKey = new SecretKeySpec(keyByte, ALGORITHM);
            byte[] srcBytes = Base64.decodeBase64(src);
            // 加密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, desKey);
            byte[] ret = c1.doFinal(srcBytes);
            return new String(ret);
        } catch (Exception e) {
            Logger.getGlobal().warning("DES解密发生异常-survey:\n" + e);
        }
        return null;
    }

    /**
     * 获取加密的userId
     *
     * @param userId
     * @return
     */
    public static String getEncryptUserID(Integer userId) {
        if (userId == null || userId <= 0) {
            return "";
        }
        String res = encode3DESSurvey(userId.toString());
        Map<String, String> replaceItems = new HashMap<>();
        //定向替换
        replaceItems.put("=", "oo_73");
        replaceItems.put("+", "9io_24");
        replaceItems.put("/", "t3o_35");

        for (Map.Entry<String, String> entry : replaceItems.entrySet()) {
            res = res.replace(entry.getKey(), entry.getValue());
        }
        return res;
    }

    /**
     * 获取解密的userId
     *
     * @param code
     * @return
     */
    public static String getDecryptUserID(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        Map<String, String> replaceItems = new HashMap<>();
        //定向替换
        replaceItems.put("oo_73", "=");
        replaceItems.put("9io_24", "+");
        replaceItems.put("t3o_35", "/");

        for (Map.Entry<String, String> entry : replaceItems.entrySet()) {
            code = code.replace(entry.getKey(), entry.getValue());
        }

        return decode3DESSurvey(code);
    }

    /**
     * @param filePath   文件路径
     * @param rowNum     解密的行数
     * @param columnNum1 需要解密的列
     * @param columnNum2 解密完写入的列
     */
    public static void getDecryptUserIdFile(String filePath, Integer rowNum, Integer columnNum1, Integer columnNum2) {
        try {
            Workbook wb = ExcelUtil.readExcel(filePath);
            Sheet sheet = wb.getSheetAt(0); //获取第一个页签
            Map<String, String> map = new HashMap<>();
            Long startTime = System.currentTimeMillis();
            for (int j = rowNum; j < sheet.getPhysicalNumberOfRows(); j++) {
                Row row = sheet.getRow(j);
                Object cellinfo = ExcelUtil.getCellFormatValue(row.getCell(columnNum1));
                if (cellinfo == null) {
                    continue;
                }
                String cellStr = (String) cellinfo;
                if (cellStr.isEmpty()) {
                    continue;
                }
                String decodeStr = ThreeDESUtil.getDecryptUserID(cellStr);
                //创建的位置
                Cell newcell = row.createCell(columnNum2);
                newcell.setCellValue(decodeStr);
//                System.out.println(j + ": " + cellinfo + ":" + decodeStr);
            }
            try (OutputStream stream = new FileOutputStream(new File(filePath))) {
                wb.write(stream);
            }
            System.out.println(System.currentTimeMillis() - startTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String key1 = "userid_survey_key";
    private static String key2 = "Percentage plan";
    private static String current_key = key1;

    public static void main(String[] args) {
//        getDecryptUserID("");

//        System.out.println(new String(Base64.decodeBase64("+")));
//
        String encode = getEncryptUserID(91);

        System.out.println(encode);
//        String decodeStr = getDecryptUserID(encode);
//        System.out.println(decodeStr);
        String i = Arrays.toString(keyByte);
        System.out.println(i);
    }


}
