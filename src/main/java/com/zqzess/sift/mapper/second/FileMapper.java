package com.zqzess.sift.mapper.second;

import com.zqzess.sift.bean.FileItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/07/28 14:26
 * @Package com.zqzess.sift.mapper.second
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Mapper
public interface FileMapper {
    @Transactional(value = "secondDataSourceTransactionManager")
    void insertFile(FileItem file);
    List<FileItem> getFileListByPage();

    FileItem getFileByFileId(Integer fileId);
}
