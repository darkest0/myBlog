package blog.demo.Input;

import lombok.Data;

@Data
public class LoginInput {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String verifyCode;
}
