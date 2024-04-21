package com.qing.admin.system.util;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class QiNiuUtil {

    // 设置好账号的ACCESS_KEY和SECRET_KEY
    public static String ACCESS_KEY = "MfOamh-w_d7137ZrZVAigESPWxejv3PROC1tkN3S";
    public static String SECRET_KEY = "qf9a4Pih6KJxRx8vMM_u6aLwcqwiuOO_trhs6C9E";

    // 要上传的空间（创建空间的名称）
    public static String bucketname = "systemk";

    // 密钥配置
    public static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    // 构造一个带指定Zone对象的配置类,不同的七云牛存储区域调用不同的zone
    public static Configuration cfg = new Configuration(Zone.zone1());
    // ...其他参数参考类注释
    public static UploadManager uploadManager = new UploadManager(cfg);

    // 使用的是测试域名
    private static String QINIU_IMAGE_DOMAIN = "sc83rwiqp.hb-bkt.clouddn.com/";

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public static String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            // 判断是否是合法的文件后缀
            if (!FileUtil.isFileAllowed(fileExt)) {
                return null;
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            // 调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                System.out.println(1);
                return null;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
        }
    }
}
