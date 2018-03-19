package com.hellobaby.library.uploadpic;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

public class PutObjectSamples {

    private OSS oss;
    private String testBucket;
    private String testObject;
    private String uploadFilePath;

    public PutObjectSamples(OSS client, String testBucket, String testObject, String uploadFilePath) {
        this.oss = client;
        this.testBucket = testBucket;
        this.testObject = testObject;
        this.uploadFilePath = uploadFilePath;
    }


    // 从本地文件上传，采用阻塞的同步接口
    public void putObjectFromLocalFile() throws ClientException, ServiceException {
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(testBucket, testObject, uploadFilePath);
            PutObjectResult putResult = oss.putObject(put);

            Log.d("PutObject", "UploadSuccess");

            Log.d("ETag", putResult.getETag());
            Log.d("RequestId", putResult.getRequestId());
    }
}
