package com.zqzess.sift.vo.enums;


import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/25 09:12
 * @Package com.zqzess.sift.vo.enums
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public enum FileMediaTypeEnum {
    UNKNOWFILE(0, "未知文件", List.of("")),
    TXT(1, "文本", List.of("txt", "cpp", "conf", "java", "go", "py", "js", "css", "json", "ts", "yml", "yaml", "sql", "dockerfile")),
    PICTURE(2, "图片", List.of("png", "jpg", "jpeg")),
    FILE(3, "文件", List.of("doc", "docx", "pdf", "xls")),
    TXTVIEW(4, "markdown", List.of("md"));
    private Integer type;
    private String name;
    private List<String> list;

    FileMediaTypeEnum(Integer type, String name, List<String> list) {
        this.type = type;
        this.name = name;
        this.list = list;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public static Integer getTypeCodeByTypeName(String typeName) {
        for (FileMediaTypeEnum typeEnum:FileMediaTypeEnum.values()) {
            if (typeEnum.getList().contains(typeName)) {
                return typeEnum.getType();
            }
        }
        return UNKNOWFILE.getType();
    }
}
