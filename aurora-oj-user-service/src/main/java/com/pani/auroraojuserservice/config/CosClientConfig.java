package com.pani.auroraojuserservice.config;


import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Pani 七牛云对象存储客户端
 * @date Created in 2023/11/30 15:12
 * @description
 */
@org.springframework.context.annotation.Configuration
@ConfigurationProperties(prefix = "cos.qiniu")
@Data
public class CosClientConfig {
    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 区域
     */
    private String region;

    /**
     * 桶名
     */
    private String bucket;

    @Bean
    public UploadManager uploadManager() {

        Region region01234 = null;
        switch (region) {
            //华东1
            case "region0":
                region01234 = Region.region0();
                break;
            case "huadong":
                region01234 = Region.region0();
                break;
            //华东2
            case "huadong2":
                region01234 = Region.regionCnEast2();
                break;
            //华北
            case "huabei":
                region01234 = Region.region1();
                break;
            case "region1":
                region01234 = Region.region1();
                break;
            //华南
            case "region2":
                region01234 = Region.region2();
                break;
            case "huanan":
                region01234 = Region.region2();
                break;
            //            case "beimei":
            //                region01234 = Region.regionNa0();
            //                break;
            //            case "regionNa0":
            //                region01234 = Region.regionNa0();
            //                break;
            default:
                region01234 = Region.region0();
        }
        Configuration cfg =
                new Configuration(region01234);
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
//        设置请求协议为http
//        cfg.useHttpsDomains = false;
        UploadManager uploadManager = new UploadManager(cfg);

        return uploadManager;
    }

    @Bean
    public String upToken(){
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }
}
