package com.zqzess.sift.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqzess.sift.bean.FileItem;
import com.zqzess.sift.infrastructure.Result;
import com.zqzess.sift.mapper.second.FileMapper;
import com.zqzess.sift.vo.enums.FileMediaTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.zqzess.sift.util.FileTools;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/24 18:51
 * @Package com.zqzess.sift.service
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Service
@Slf4j
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    @Resource
    private AuthenUserInfoService userInfoService;

    @Value(value = "${file.path}")
    String filePath;

    public Result<?> insertFile(MultipartFile file) {
        File filee = new File(filePath);
        if (!filee.exists()) {
            filee.mkdirs();
        }
        //文件名称
        String realFileName = file.getOriginalFilename();
        //文件后缀
        assert realFileName != null;
        Result result = new Result<>();
        String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1);
        suffix = suffix.toLowerCase();
        /***************文件处理*********************/
        String newFileName = UUID.randomUUID() + realFileName;
//        String newFilePath=filePath+newFileName;
        File dest = new File(filePath, realFileName);
        if (FileTools.isFileExists(dest, file)) {
            return Result.fail("文件已存在");
        }
        /***************文件处理*********************/
        try {
//            file.transferTo(new File(newFilePath));
            String filePath = "/file/" + realFileName;
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sha256Hex = FileTools.getMd5(file);
            FileItem file1 = new FileItem(realFileName, String.valueOf(file.getSize()), filePath, userInfoService.getLoginUserName(), sdf.format(System.currentTimeMillis()), userInfoService.getLoginUserName(), sdf.format(System.currentTimeMillis()), userInfoService.getLoginUserRank(),sha256Hex, 0, "/", FileMediaTypeEnum.getTypeCodeByTypeName(suffix));
            fileMapper.insertFile(file1);
            file.transferTo(dest);
//        } catch (IllegalStateException | IOException e) {
        } catch (Exception e) {
            // 处理异常
            log.error(e.getMessage());
            return Result.fail(" 上传失败");
        }
        return Result.success(" 上传成功");
    }


    public Result<?> getFileListByPageOld(int offset, int limit) {
        return Result.success();
    }

    public Result<?> getFileListByPage(int page, int pageSize) {
        Page<Map> pageManager = PageHelper.startPage(page, pageSize);
        List<FileItem> list = fileMapper.getFileListByPage();
        PageInfo<FileItem> pageInfo = new PageInfo<FileItem>(list);
        Map<String, Object> map = new HashMap<>();
        map.put("list", pageInfo.getList());
        map.put("total", pageInfo.getTotal());
        return Result.success("", null, map);
    }

    /**
     * 用 fileId 获取文件
     * @param fileId
     * @return
     */
    public FileItem getFileByFileId(Integer fileId) {
        FileItem fileItem = fileMapper.getFileByFileId(fileId);
        return fileItem;
    }
}
