package com.idleItem.tradeSystem.controller;


import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.FileService;
import com.idleItem.tradeSystem.utils.IdFactoryUtil;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
@CrossOrigin
@RestController
public class FileController {

    @Value("${userFilePath}")
    private String userFilePath;

    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    private FileService fileService;

    @PostMapping("/file")
    public ResultVo uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String uuid="file"+ IdFactoryUtil.getFileId();
        String fileName= uuid+ multipartFile.getOriginalFilename();
        try {
            if (fileService.uploadFile(multipartFile,fileName)) {
                return ResultVo.success(baseUrl+"/image?imageName="+fileName);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
        }
        return ResultVo.fail(ErrorMsg.FILE_UPLOAD_ERROR);
    }

    @GetMapping("/image")
    public void getImage(@RequestParam("imageName") String imageName,
                         HttpServletResponse response) throws IOException {
        File fileDir = new File(userFilePath);
        File image=new File(fileDir.getAbsolutePath() +"/"+imageName);
        if (image.exists()){
            FileInputStream fileInputStream=new FileInputStream(image);
            byte[] bytes=new byte[fileInputStream.available()];
            if (fileInputStream.read(bytes)>0){
                OutputStream outputStream=response.getOutputStream();
                outputStream.write(bytes);
                outputStream.close();
            }
            fileInputStream.close();
        }
    }

}
