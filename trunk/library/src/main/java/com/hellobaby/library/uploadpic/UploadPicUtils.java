package com.hellobaby.library.uploadpic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.alibaba.sdk.android.oss.model.DeleteObjectResult;
import com.hellobaby.library.utils.LogZS;

import java.io.File;
import java.util.UUID;

import me.shaohui.advancedluban.Luban;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/11.
 */

public class UploadPicUtils {
    private static OSS oss;

    // 运行sample前需要配置以下字段为有效的值
    private static final String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
    private static final String accessKeyId = "LTAIbn6iGVGzxXrX";
    private static final String accessKeySecret = "aQv4rKTbbGaj4NRNbFq9lm5CN1Q3SO";
    private static final String uploadFilePath = Environment.getExternalStorageDirectory().getPath() + "/HellobabySave/15757870946.jpeg";
    private static final String testBucket = "hellobaobei";
    private static final String BaseuploadObject = "static/images/indexCommonImg/";
    private static final String BaseuploadDymic = "static/images/dynamicImgs/";
//    private static final String downloadObject = "http://hellobaobei.oss-cn-shanghai.aliyuncs.com/static/images/indexCommonImg/15757870946.jpeg";

    /**
     * 文件名带双引号
     *
     * @param context
     * @param path
     * @return
     */
    public static String upLoadPic(Context context, final String path) throws ClientException, ServiceException {
        final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        final String filename = uuid + path.substring(path.lastIndexOf("."), path.length());
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 10000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
        final String[] compressImgPath = {path};
        File file = new File(path);
        if (file.exists() && file.isFile() && file.length() > 1048580) {
            //大于  1M
            final long startTime = System.currentTimeMillis();
//            Luban.get(context)
//                    .load(new File(path))
//                    .putGear(Luban.THIRD_GEAR)
//                    .asObservable()
//                    .doOnError(new Action1<Throwable>() {
//                        @Override
//                        public void call(Throwable throwable) {
//                            throwable.printStackTrace();
//                        }
//                    })
//                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
//                        @Override
//                        public Observable<? extends File> call(Throwable throwable) {
//                            return Observable.empty();
//                        }
//                    })
//                    .subscribe(new Action1<File>() {
//                        @Override
//                        public void call(File file) {
//                            // 压缩成功后调用，返回压缩后的图片文件
//                            Log.i("ZLog","File  size"+file.length()/1024+" k");
//                            compressImgPath[0] = file.getAbsolutePath();
//                        }
//                    });

            Luban.compress(context, file)
                    .putGear(Luban.CUSTOM_GEAR)
                    .setMaxSize(500)                // 限制最终图片大小（单位：Kb）
                    .setMaxHeight(1920)             // 限制图片高度
                    .setMaxWidth(1080)              // 限制图片宽度
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .asObservable()
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline())
                    .subscribe(new Subscriber<File>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(File file) {

                            long endTime = System.currentTimeMillis();
                            Log.i("ZLog","压缩耗时"+(endTime-startTime));
                            Log.i("ZLog","File  size"+file.length()/1024+" k");
                            compressImgPath[0] = file.getAbsolutePath();
                        }
                    });

        }
        new PutObjectSamples(oss, testBucket, BaseuploadObject + filename, compressImgPath[0]).putObjectFromLocalFile();
        return "\"" + filename + "\"";
    }

    /**
     * Oss删除单张图片文件
     * @param fileName
     */
    public static void deleteSingleOssPicture(Context context,String fileName){
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 10000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
        LogZS.i("删除的图片地址："+fileName);
        String deletePath = fileName.replace("http://hellobaobei.oss-cn-shanghai.aliyuncs.com/","");
        LogZS.i("删除oss的图片地址："+deletePath);
        // 创建删除请求
        DeleteObjectRequest delete = new DeleteObjectRequest(testBucket, deletePath);
        // 异步删除
        OSSAsyncTask deleteTask = oss.asyncDeleteObject(delete, new OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult>() {
            @Override
            public void onSuccess(DeleteObjectRequest request, DeleteObjectResult result) {
                Log.d("asyncCopyAndDelObject", "success!");
            }

            @Override
            public void onFailure(DeleteObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }

        });
    }


    public static String upLoadTimeCardImg(Context context, final String path) throws ClientException, ServiceException {
        return BaseUpLoadPic(context, path, "static/images/timeCardImg/");
    }

    public static String upLoadFirstFrameImg(Context context, final String path) throws ClientException, ServiceException {
        return BaseUpLoadPic(context, path, "static/images/indexCommonImg/firstFrameImg/");
    }

    public static String upLoadSmallVideo(Context context, final String path) throws ClientException, ServiceException {
        return BaseUpLoadPic(context, path, "static/images/indexCommonImg/smallVideo/");
    }

    public static String upLoadDynamicFirstFrameImg(Context context, final String path) throws ClientException, ServiceException {
        return BaseUpLoadPic(context, path, "static/images/dynamicFirstFrame/");
    }

    public static String upLoadDynamicSmallVideo(Context context, final String path) throws ClientException, ServiceException {
        return BaseUpLoadPic(context, path, "static/images/dynamicSmallVideo/");
    }
    /**
     * 教师端动态文件名带双引号
     *
     * @param context
     * @param path
     * @return
     */
    public static String upLoadPicDymic(Context context, final String path) throws ClientException, ServiceException {
        final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        final String filename = uuid + path.substring(path.lastIndexOf("."), path.length());
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 10000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
        new PutObjectSamples(oss, testBucket, BaseuploadDymic + filename, path).putObjectFromLocalFile();
        return "\"" + filename + "\"";
    }

    /**
     * 纯文件名
     *
     * @param context
     * @param path
     * @param catalog 目录名称
     * @return
     */
    private static String BaseUpLoadPic(Context context, final String path, String catalog) throws ClientException, ServiceException {
        final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        final String filename = uuid + path.substring(path.lastIndexOf("."), path.length());
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 10000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
        new PutObjectSamples(oss, testBucket, catalog + filename, path).putObjectFromLocalFile();
        return filename;
    }
}
