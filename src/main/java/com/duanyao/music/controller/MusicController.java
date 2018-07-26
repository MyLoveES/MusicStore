package com.duanyao.music.controller;
import com.alibaba.fastjson.JSONObject;
import com.duanyao.music.model.HostHolder;
import com.duanyao.music.model.Music;
import com.duanyao.music.model.User;
import com.duanyao.music.service.MusicService;
import com.duanyao.music.util.GlobalConsts;
import com.duanyao.music.util.GlobalEnums;
import com.duanyao.music.util.MusicUtil;
import com.duanyao.music.util.ServerResponse;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping(path="/musics")
@RestController
public class MusicController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MusicService musicService;

    @RequestMapping(path="/{id}", method={RequestMethod.GET})
    public ServerResponse getMusic(@PathVariable(value = "id") int id){
        String msg;
        ServerResponse<Object> serverResponse  = new ServerResponse<Object>();
        try{
            Map<String, Object> serviceResult = musicService.getMusicByID(id);
            Music music = (Music)serviceResult.get("data");
            if(serviceResult.get("status") == GlobalEnums.MusicSUCCESS){
                msg = "get music success";
                return serverResponse.createBySuccess(msg, serviceResult);
            }
            else{
                msg = "get music FAILED";
                return serverResponse.createByError(msg, serviceResult);
            }
        }catch (Exception e){
            logger.error("getMusic异常" + e.getMessage());
            msg = "getMusic Server FAILED";
            return serverResponse.createByError(msg, "getMusic SERVER ERROR");
        }
    }

    @RequestMapping(path="/", method={RequestMethod.GET})
    public ServerResponse getList(){
        String msg;
        ServerResponse<Object> serverResponse = new ServerResponse<Object>();
        try {
            Map<String, Object> serviceResult = musicService.getMusics();
            msg = "get musics SUCCESS";
            return serverResponse.createBySuccess(msg, serviceResult.get("data"));
        }catch(Exception e){
            logger.error("getMusics异常" + e.getMessage());
            msg = "get musics FAILED";
            return serverResponse.createByErrorMsg(msg);
        }
    }

    @RequestMapping(path="/upload", method = RequestMethod.POST)
    public ServerResponse uploadMusic(@RequestParam(value = "file")MultipartFile[] files) throws IOException{
        String msg;
        String musicItem = "musicName";
        String status = "status";
        String data = "data";
        List<JSONObject> datas = new ArrayList<>();
        Map<String, Object> serviceResult = new HashMap<String, Object>();
        ServerResponse<Object> serverResponse = new ServerResponse<Object>();
        try{
            if (null != files && files.length > 0) {
                //遍历并保存文件
                for (MultipartFile file : files) {
                    JSONObject dataItem = new JSONObject();
                    String fileName = file.getOriginalFilename();
                    logger.info(fileName + "上传的文件名为：" + fileName);
                    dataItem.put(musicItem, fileName);
                    // 获取文件的后缀名
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    logger.info(fileName + "上传的后缀名为：" + suffixName);
                    if(!MusicUtil.isFileAllowed(suffixName)){
                        dataItem.put(status, GlobalEnums.MusicSuffixNameFormatERROR);
                        datas.add(dataItem);
                        continue;
                    }
                    // 解决中文问题，liunx下中文路径，图片显示问题
                    Date date = new Date();
                    String localPath = GlobalConsts.UploadPath + date.getTime() + UUID.randomUUID().toString().substring(0,5) + suffixName;
                    File dest = new File(GlobalConsts.LocalProjectPath + localPath);
                    // 检测是否存在目录
                    if (!dest.getParentFile().exists()) {
                        if(!dest.getParentFile().mkdirs()){
                            msg =fileName + "server mkdir ERROR";
                            dataItem.put(status, GlobalEnums.MusicServerERROR);
                            datas.add(dataItem);
                            return serverResponse.createByError(msg, datas);
                        }
                    }

                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    //本地上传图片方式
                    try {
                        file.transferTo(dest);
                    } catch (Exception e) {
                        logger.error(fileName + " local save 出错: "+e.getMessage());
                        dataItem.put(status, GlobalEnums.MusicLocalSaveERROR);
                        datas.add(dataItem);
                        msg =fileName + " local save 出错";
                        return serverResponse.createByError(msg, datas);
                    }

                    serviceResult = musicService.saveMusic(fileName, localPath);
                    if(serviceResult.get(status)!=GlobalEnums.MusicSUCCESS){
                        dataItem.put(status, serviceResult);
                        datas.add(dataItem);
                        msg = "db save 出错" + fileName;
                        return serverResponse.createByError(msg, datas);
                    }
                    dataItem.put(status, GlobalEnums.MusicSUCCESS);
                    dataItem.put(data, serviceResult);
                    datas.add(dataItem);
                    logger.info(">>>>>>>>>>>>>本地上传图片路径  {}", localPath);
                }
                msg = "upload success";
                return serverResponse.createBySuccess(msg, datas);
            } else {
                logger.error("上传文件不能为空");
                JSONObject dataItem = new JSONObject();
                dataItem.put(status, GlobalEnums.MusicUploadEmptyERROR);
                datas.add(dataItem);
                msg = "上传文件不能为空";

                return serverResponse.createByError(msg, datas);
            }
        }catch (Exception e){
            logger.error("upload server ERROR" + e.getMessage());
            msg = "upload server ERROR";
            return serverResponse.createByErrorMsg(msg);
        }
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ServerResponse getMusicByName(@RequestParam(value = "name", required = true)String name,
                                         @RequestParam(value = "page", required = false, defaultValue = "1")int page){
        String msg;
        String status = "status";
        String data = "data";
        Map<String, Object> serviceResult = new HashMap<String, Object>();
        ServerResponse<Object> serverResponse = new ServerResponse<Object>();
        try{
            serviceResult = musicService.getMusicByName(name, page-1);
            if(serviceResult.get(status)!=GlobalEnums.MusicSUCCESS){
                msg = "查询失败";
                return serverResponse.createByError(msg, serviceResult);
            }
            msg = "查询成功";
            return serverResponse.createBySuccess(msg, serviceResult);
        }catch (Exception e){
            logger.error("getMusicByName server ERROR");
            e.printStackTrace();
            msg = "getMusicByName server ERROR";
            return serverResponse.createByErrorMsg(msg);
        }
    }
}
