package com.ponking.pblog.web.api;

import com.ponking.pblog.common.result.R;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.service.IUploadService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ponking
 * @ClassName UploadFileController
 * @date 2020/4/4--17:08
 **/
@Controller
@Slf4j
@Api(tags = {"文件功能模块"})
@RequestMapping("/api")
public class ApiUploadFileController {


    @Autowired
    private IUploadService uploadService;


    @PostMapping("/image/upload")
    @ResponseBody
    public R imageUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new GlobalException("文件为空");
        }
        String imageUrl = uploadService.upload(file);
        if (imageUrl == null) {
            throw new GlobalException("上传失败");
        }
        Map<String, String> result = new HashMap<>();
        result.put("imageUrl", imageUrl);
        return R.success(result);
    }

//
//    @Value("${file.commons.uploadWindows}")
//    private String filePathWindow;
//
//    @Value("${file.commons.uploadLinux}")
//    private String filePathLinux;
//
//    @GetMapping("/upload")
//    public String upload(){
//        return "upload";
//    }

//    @PostMapping("/image/upload")
//    @ResponseBody
//    public R fileUpload(@RequestParam(value = "file")MultipartFile file,
//                        HttpServletRequest request){
//        if(file.isEmpty()){
//            throw new GlobalException("文件为空");
//        }
//        // 文件名称
//        String fileName = file.getOriginalFilename();
//        // 文件后缀
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        fileName = UUID.randomUUID().toString().replace("-","")+suffixName;
//        String os = System.getProperty("os.name");
//        String filePath; // 上传后的路径
//        if(os.toLowerCase().startsWith("win")){
//            filePath = filePathWindow;
//        }else{
//            filePath = filePathLinux;
//        }
//        File dest = new File(filePath+fileName);
//        if(!dest.getParentFile().exists()){
//            dest.getParentFile().mkdirs();
//        }
//        try {
//            file.transferTo(dest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return R.success();
//    }
}
