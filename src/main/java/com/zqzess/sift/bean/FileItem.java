package com.zqzess.sift.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/24 18:45
 * @Package com.zqzess.sift.bean
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileItem {
    Integer fileId;
    String fileName;
    String fileSize;
    String filePath;
    String fileUploadUserName;
    String fileUploadTime;
    String fileUpdateUserName;
    String fileUpdateTime;
    int fileRank;
    String fileHash;
    int fileType; // 默认 0 为文件，1 为文件夹
    String fileDir;
    Integer fileMediaType;

    public FileItem(String fileName, String fileSize, String filePath, String fileUploadUserName, String fileUploadTime, String fileUpdateUserName, String fileUpdateTime, int fileRank, String fileHash, int fileType, String fileDir, Integer file_media_type) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.fileUploadUserName = fileUploadUserName;
        this.fileUploadTime = fileUploadTime;
        this.fileUpdateUserName = fileUpdateUserName;
        this.fileUpdateTime = fileUpdateTime;
        this.fileRank = fileRank;
        this.fileHash = fileHash;
        this.fileType = fileType;
        this.fileDir = fileDir;
        this.fileMediaType = file_media_type;
    }
}
