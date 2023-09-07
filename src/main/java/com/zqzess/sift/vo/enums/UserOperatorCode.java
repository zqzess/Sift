package com.zqzess.sift.vo.enums;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/01 16:23
 * @Package com.zqzess.sift.vo
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public enum UserOperatorCode {

    ACTION_VIEW(0, "查看", ""), ACTION_DOWN(1, "下载", ""),
    ACTION_UP(2, "上传", ""), ACTION_DEL(3, "删除", ""),
    ACTION_EDIT(4, "编辑", ""), ACTION_MOVE(5, "移动", ""),
    ACTION_UP_RANK(6, "提权", ""), ACTION_DOWN_RANK(7, "降权", "");
    private final int type;
    private final String describe;

    private final String describeEN;

    UserOperatorCode(int type, String describe, String describeEN) {
        this.type = type;
        this.describe = describe;
        this.describeEN = describeEN;
    }

    public int getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }

    public String getDescribeEN() {
        return describeEN;
    }
}
