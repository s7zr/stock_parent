package org.mmj.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 21:33
 */
@Data
@ApiModel(description = "日志分页多条件查询对象封装")
public class LogPageReqVo {
    @ApiModelProperty(value = "当前页")
    private Integer pageNum=1;
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize=10;
    @ApiModelProperty("操作者账户")
    private String username;
    @ApiModelProperty("操作行为")
    private String operation;
    @ApiModelProperty("起始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
}
