package com.pani.auroraojuserservice.manager;

import com.google.gson.Gson;

import com.pani.ojcommon.common.ErrorCode;
import com.pani.ojcommon.exception.BusinessException;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Pani
 * @date Created in 2023/11/30 15:07
 * @description
 */
@Component
public class CosManager {
    @Resource
    private UploadManager uploadManager;

    @Resource
    private String upToken;

    private String QINIU_IMAGE_DOMAIN = "http://pics.soogyu.xyz/";

    private String UPLOAD_DIR = "pani/oj/avatar/";

    /**
     * 上传对象,，返回url
     * @param filename 默认不指定key的情况下，以文件内容的hash值作为文件名
     */
    public String putObject(InputStream inputStream,String filename) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String upFileName = dtf.format(LocalDate.now())+"-"+filename;
        Response response = null;
        try {
            response = uploadManager.put(inputStream, UPLOAD_DIR +upFileName,upToken,null,null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            //卧槽我傻了，key不就是url吗
            return QINIU_IMAGE_DOMAIN + putRet.key;
        } catch (QiniuException ex) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR,ex.getMessage());
        }

    }
}
