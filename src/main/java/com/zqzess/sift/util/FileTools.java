package com.zqzess.sift.util;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/24 21:12
 * @Package com.zqzess.sift.util
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Slf4j
public class FileTools {
    /**
     * 计算SHA256哈希值
     * @param filePath 文件路径
     * @return 字节数组
     * @throws IOException IO异常
     * @throws NoSuchAlgorithmException NoSearch算法异常
     */
    private static byte[] calculateSHA256(String filePath) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        //获取网络URL文件
        InputStream fis2 = new URL(filePath).openStream();
        //创建临时文件
        File file = File.createTempFile(IdWorker.getIdStr(),null);
        FileUtil.writeFromStream(fis2,file);
        return getBytes(digest, file);
    }

    private static byte[] calculateSHA256(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //创建临时文件
        String realFileName = file.getOriginalFilename();
        String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1);
        File tempFile = File.createTempFile(IdWorker.getIdStr(), "." + suffix);
        file.transferTo(tempFile);
        return getBytes(digest, tempFile);
    }

    private static byte[] calculateSHA256(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //创建临时文件
        return getBytes(digest, file);
    }

    private static byte[] getBytes(MessageDigest digest, File fileTmp) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(fileTmp);
                FileChannel channel = fis.getChannel();
                DigestInputStream dis = new DigestInputStream(fis, digest)) {
            ByteBuffer buffer = ByteBuffer.allocate(8192); // 8 KB buffer
            while (channel.read(buffer) != -1) {
                buffer.flip();
                digest.update(buffer);
                buffer.clear();
            }
//            fileTmp.deleteOnExit();
            return digest.digest();
        }
    }

    /**
     * 将字节数组转换为String类型哈希值
     * @param bytes 字节数组
     * @return 哈希值
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    /**
     * 获取网络文件 hash
     * @param filePath
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileHash(String filePath) throws IOException, NoSuchAlgorithmException {
        String sha256Hex = "";
        byte[] sha256 = calculateSHA256(filePath);
        sha256Hex = bytesToHex(sha256);
        return sha256Hex;
    }

    /**
     * 获取文件 hash
     * @param file
     * @return
     */
    public static String getFileHash(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String sha256Hex = "";
        byte[] sha256 = calculateSHA256(file);
        sha256Hex = bytesToHex(sha256);
        return sha256Hex;
    }

    /**
     * 获取文件 hash
     * @param file
     * @return
     */
    public static String getFileHash(File file) throws IOException, NoSuchAlgorithmException {
        String sha256Hex = "";
        byte[] sha256 = calculateSHA256(file);
        sha256Hex = bytesToHex(sha256);
        return sha256Hex;
    }

    /**
     * 获取上传文件的md5
     * @param file
     * @return
     * @throws IOException
     */
    public static String getMd5(MultipartFile file) {
        String md5Hex = "";
        try {
            byte[] uploadBytes = file.getBytes();
            //file->byte[],生成md5
//            md5Hex = DigestUtils.md5DigestAsHex(uploadBytes);
            //file->InputStream,生成md5
            md5Hex = DigestUtils.md5DigestAsHex(file.getInputStream());
            //对字符串生成md5
            return md5Hex ;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return md5Hex;
    }

    public static String getMd5(File file) {
        String md5Hex = "";
        try {
            md5Hex = DigestUtils.md5DigestAsHex(new FileInputStream(file));
            //对字符串生成md5
            return md5Hex ;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return md5Hex;
    }

    /**
     * 判断是否存在相同文件，即内容一样且名称一样
     * @param file
     * @return
     */
    public static boolean isFileExists(File file, MultipartFile newFile){
        String oldHash = "";
        String newHash = "";
        newHash = FileTools.getMd5(newFile);
        if (file.exists()) {
            try {
                oldHash = FileTools.getMd5(file);
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
            //  同名文件存在，判断 hash 是否相同
            if (oldHash.equals(newHash)) {
                return true;
            }
            return false;
        }
        return false;
    }
}
