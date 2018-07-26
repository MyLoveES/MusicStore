package com.duanyao.music.service;

import com.duanyao.music.mapper.MusicDao;
import com.duanyao.music.model.Music;
import com.duanyao.music.util.GlobalConsts;
import com.duanyao.music.util.GlobalEnums;
import com.duanyao.music.util.MusicUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MusicService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    MusicDao musicDao;

    public Map<String, Object> getMusicByID(int id){
        Map<String, Object> result = new HashMap<String, Object>();
        String status = "status";
        String data = "data";
        if(!MusicUtil.match(GlobalConsts.NumberReg, Integer.toString(id))){
            result.put(status, GlobalEnums.MusicIDFormatERROR);
        }else{
            Music music = musicDao.getMusicByID(id);
            if(music == null){
                result.put(status, GlobalEnums.MusicIDWrongERROR);
            }else{
                result.put(status, GlobalEnums.MusicSUCCESS);
                result.put(data, music);
            }
        }
        return result;
    }

    public Map<String, Object> getMusicByName(String name, int page){
        Map<String, Object> result = new HashMap<String, Object>();
        String status = "status";
        String data = "data";
        if(name==null){
            result.put(status, GlobalEnums.MusicNameEmptyERROR);
        }else{
            List<Music> musics = musicDao.getMusicByName(name, page*GlobalConsts.PageCounts);
            result.put(status, GlobalEnums.MusicSUCCESS);
            result.put(data, musics);
        }
        return result;
    }

    public Map<String, Object> getMusics(){
        Map<String, Object> result = new HashMap<String, Object>();
        String status = "status";
        String data = "data";
        List<Music> musics = musicDao.getMusicList();
        result.put(status, GlobalEnums.MusicSUCCESS);
        result.put(data, musics);
        return result;
    }

    public Map<String, Object> saveMusic(String name, String path){
        Map<String, Object> result = new HashMap<String, Object>();
        String status = "status";
        String data = "data";
        if(StringUtils.isBlank(name)){
            result.put(status, GlobalEnums.MusicNameEmptyERROR);
            return result;
        }
        Date date = new Date();
        Music music = new Music();
        music.setName(name);
        music.setFile(path);
        music.setStatus(GlobalConsts.ExitsStatus);
        music.setUpload_time(date);
        musicDao.save(music);
        result.put(status, GlobalEnums.MusicSUCCESS);
        result.put(data, music);
        return result;
    }
}
