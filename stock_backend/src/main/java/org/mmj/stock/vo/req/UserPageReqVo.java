package org.mmj.stock.vo.req;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author mmj
 * @Description
 * @create 2024-06-06 21:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPageReqVo {
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 开始创建日期
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    /**
     * 结束创建日期
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;
}
