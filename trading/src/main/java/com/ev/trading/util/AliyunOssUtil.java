package com.ev.trading.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.ev.trading.constant.AliyunOssConfigConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class AliyunOssUtil {

    private static String File_URL;
    private static String bucketName = AliyunOssConfigConstant.BUCKE_NAME;
    private static String endPoint = AliyunOssConfigConstant.END_POINT;
    private static String accessKeyId = AliyunOssConfigConstant.AccessKey_ID;
    private static String accessKeySecret = AliyunOssConfigConstant.AccessKey_Secret;
    private static String fileHost = AliyunOssConfigConstant.FILE_HOST;

    public String upLoad(File file) {
        boolean isImage = true;
        try {
            Image image = ImageIO.read(file);
            isImage = image == null ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());

        if (file == null) {
            return null;
        }

        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            //??????????????????,???????????????????????????????????????
            String fileUrl = fileHost + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
            //???????????????
            if (isImage) {
                File_URL = "https://" + bucketName + "." + endPoint + "/" + fileUrl;
            } else {
                File_URL = "?????????????????????????????????????????????" + fileUrl;
            }
            //????????????
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            //?????????????????????
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return File_URL;
    }

    public String uploadImg(MultipartFile file) throws IOException {
        String imgFileName = "img/";//?????????????????????????????????
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        InputStream inputStream = file.getInputStream();
        String originName = file.getOriginalFilename(); //??????????????????
        //String filName = imgFileName + originName; //????????????????????????+?????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
        String filName = fileHost + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "")+"-"+originName);
        ossClient.putObject(bucketName,filName,inputStream);
        ossClient.shutdown();
        String urlName = URLEncoder.encode(filName, "UTF-8"); //?????????????????????????????????
        String imgUrl = "https://" + bucketName + "." + endPoint + "/"+ urlName;
        return imgUrl;
    }
}

