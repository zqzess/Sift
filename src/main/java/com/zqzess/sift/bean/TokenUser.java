package com.zqzess.sift.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/02 11:15
 * @Package com.zqzess.sift.bean
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(name = "用户名")
    private String username;
    @Schema(name = "拥有的菜单权限")
    private List<String> permissions;
    @Schema(name = "是否自动登录")
    private Boolean saveLogin;
}
