package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class QuerySiteRequest extends RequestData {
    //站点ID
    @ApiModelProperty("站点ID")
    private String siteId;
    //站点名称
    @ApiModelProperty("站点名称")
    private String siteName;
    //站点ip
    @ApiModelProperty("站点ip")
    private String siteDomain;
    //站点端口
    @ApiModelProperty("站点端口port")
    private String sitePort;
}
