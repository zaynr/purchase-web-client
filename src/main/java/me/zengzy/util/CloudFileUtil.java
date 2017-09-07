package me.zengzy.util;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import me.zengzy.dict.ApiKey;

public class CloudFileUtil {
    private String key;

    public CloudFileUtil(String fileKey){
        this.key = fileKey;
    }

    public void deleteFile(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(ApiKey.Qiniu.zone);

        Auth auth = Auth.create(ApiKey.Qiniu.AccessKey, ApiKey.Qiniu.SecretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(ApiKey.Qiniu.BucketName, key);
        } catch (QiniuException ex) {
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
