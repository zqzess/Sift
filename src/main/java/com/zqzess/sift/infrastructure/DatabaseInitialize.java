package com.zqzess.sift.infrastructure;

import com.zqzess.sift.bean.User;
import com.zqzess.sift.service.UserService;
import com.zqzess.sift.util.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/07/28 17:00
 * @Package com.zqzess.sift.infrastructure
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Slf4j
@Component
public class DatabaseInitialize {

    @Value("${spring.datasource.first.url:}")
    private String userUrl;

    @Value("${spring.datasource.second.url:}")
    private String fileUrl;

    String sqlPath = "";

    @Autowired
    UserService userService;

    @PostConstruct
    private void initDatabase() {
        createSQLDir();
        log.info("开始检查数据库是否需要初始化...");
        // 检测当前连接数据库是否存在
        if (currentTableExists(userUrl, "select * from User")) {
            log.info("数据库User表存在，不需要初始化！");
        }
        else {
            log.warn("数据库User表不存在！准备执行初始化步骤...");
            createTable(userUrl, "User");
            if (createDefaultUser()) {
                log.warn("初始用户为 root，密码为 root");
            }
            else {
                log.warn("初始用户创建失败！！！");
            }
        }

        if (currentTableExists(userUrl, "select * from UserAction")) {
            log.info("数据库UserAction表存在，不需要初始化！");
        }
        else {
            log.warn("数据库UserAction表不存在！准备执行初始化步骤...");
            createTable(userUrl, "UserAction");
        }

        if (currentTableExists(fileUrl, "select * from File")) {
            log.info("数据库File表存在，不需要初始化！");
        }
        else {
            log.warn("数据库File表不存在！准备执行初始化步骤...");
            createTable(fileUrl, "File");
        }
    }

    /**
     * 检查表是否存在
     * @param url
     * @param sql
     * @return
     */
    private boolean currentTableExists(String url, String sql) {
        // 尝试以配置文件中的URL建立连接
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            connection.prepareStatement(sql).executeQuery() ;
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error(String.valueOf(e.getErrorCode()));
            // 若连接抛出异常且错误代码为1049，则说明连接URL中指定数据库不存在
            if (e.getErrorCode() == 0 || e.getErrorCode() == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 执行sql文件，创建表
     * @param url
     * @param name
     * @return
     */
    private boolean createTable(String url, String name) {
        if (createSQLFile(name) && StringUtil.isNotEmpty(sqlPath)) {
            log.warn("执行sql脚本");
            try {
                Resource resource = new FileSystemResource(sqlPath);
                EncodedResource resourceDelegate = new EncodedResource(resource,"utf-8");
                Connection conn = DriverManager.getConnection(url);
                ScriptUtils.executeSqlScript(conn, resourceDelegate);
                conn.close();
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return true;
    }

    /**
     * 创建数据库存储目录
     * @return
     */
    private boolean createSQLDir() {
        try {
            String fileString = new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent();
            File fileDir = new File(fileString.replace("file:", "") + "/db");
            log.warn(fileDir.toString());
            if (!fileDir.exists()) {
                log.info("创建数据库目录");
                fileDir.mkdirs();
                return true;
            }
        }
        catch (IOException | RuntimeException e) {
            log.warn(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 将sql脚本复制到目录中
     * @param name
     * @return
     */
    private boolean createSQLFile(String name) {
        ClassPathResource classPathResource = new ClassPathResource("sql/" + name + ".sql");
        try {
            if (classPathResource.exists()) {
                log.warn("创建" + name + ".sql文件");
                String fileString = new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent();
                sqlPath = fileString.replace("file:", "") + "/db/" + name + ".sql";
                OutputStream os = new FileOutputStream(sqlPath);
                InputStream in = classPathResource.getInputStream();
                FileCopyUtils.copy(in, os);
                os.close();
                in.close();
                return true;
            }
        }
        catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 创建默认用户
     * @return
     */
    private boolean createDefaultUser() {
        User user = new User(1, "root", new BCryptPasswordEncoder().encode("root"), 10);
        userService.saveUser(user);
        return userService.findUserByName("root") != null;
    }

    public boolean dataRecovery(String filePath) {
        log.warn(filePath);
        try {
            File file = new File(filePath); // 转换成File对象
            if (!file.exists()) {
                log.warn("该文件不存在: " + filePath);
                return false;
            }
            if (file.isFile()){
                // 该路径表示一个文件
                String fileName = file.getName();
                String fileNameLast = fileName.substring(fileName.indexOf("."));
                if (".sql".equals(fileNameLast)){
                    // 如果是sql文件执行
//                    initTable(file);
                } else{
                    log.warn("文件格式错误: " + filePath);
                    return false;
                }
            }
            if (file.isDirectory()){
                // 该路径表示一个文件夹
                File f = getFile(file); // 公共方法，下面提供
                if (null == f){
                    log.warn("文件格式错误: " + filePath);
                    return false;
                }
                // 如果是sql文件执行
//                initTable(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public File getFile(File file){
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                String fileName = f.getName();
                String fileNameLast = fileName.substring(fileName.indexOf("."));
                if (".sql".equals(fileNameLast)){
                    return f;
                }
            }
        }
        return null;
    }

    public void initTable(String url,File file) throws Exception {
        Connection conn = DriverManager.getConnection(url);
        FileReader reader = new FileReader(file);
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        // 设置编码，防止中文乱码
        Resources.setCharset(StandardCharsets.UTF_8);
        // 必须为true，不然容易报错
        scriptRunner.setSendFullScript(true);
        // 执行
        scriptRunner.runScript(reader);
        scriptRunner.closeConnection();
        reader.close();
    }
}
