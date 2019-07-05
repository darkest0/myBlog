package blog.demo.Input;

import lombok.Data;

@Data
public class CommentInput {
    /**
     * id
     */
    Long blogId;
    /**
     * 验证码
     */
    String VerifyCode;
    /**
     * 昵称
     */
    String nick;
    /**
     * emial
     */
    String email;
    /**
     * 网址 （可不填）
     */
    String webUrl;
    /**
     * 评论
     */
    String commentBody;
}
