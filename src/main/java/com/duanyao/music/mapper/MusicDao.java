package com.duanyao.music.mapper;

import com.duanyao.music.model.Music;
import com.duanyao.music.util.GlobalConsts;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MusicDao {
    /**
     * Get all musics
     * @return List<Music>
     */
    @Select("select * from music")
    List<Music> getMusicList();

    /**
     * Get music detail info by id
     * @param id music id
     * @return Music
     */
    @Select("select * from music where id=#{id}")
    Music getMusicByID(@Param("id")int id);

    /**
     * Get music detail info by name
     * @param name music name
     * @return Music
     */
    @Select("select * from music where name like concat(concat('%',#{name}),'%') limit #{page}," + GlobalConsts.PageCounts)
    List<Music> getMusicByName(@Param("name")String name, @Param("page")int page);

    /**
     *Save music instance
     * @param music musicInstance
     */
    @Insert("insert into music(name, author, upload_time, status, file) values(#{name}, #{author}, #{upload_time}, #{status}, #{file})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(Music music);

    /**
     * Del Music Instance
     * @param id musicID
     */
    @Update({"update music set status=" + GlobalConsts.NotExitsStatus, " where id=#{id} "})
    void del(@Param("id")int id);

    /**
     * Update musicInstance info
     * @param music musicInstance
     */
    @Update("update music set name=#{name}, author=#{author}, upload_time=#{upload_time}, status=#{status}, file=#{file}")
    void update(Music music);


}
