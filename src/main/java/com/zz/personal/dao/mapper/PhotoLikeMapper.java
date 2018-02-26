package com.zz.personal.dao.mapper;

import com.zz.personal.dao.entity.PhotoLike;
import com.zz.personal.dao.entity.PhotoLikeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PhotoLikeMapper {
    int countByExample(PhotoLikeExample example);

    int deleteByExample(PhotoLikeExample example);

    int deleteByPrimaryKey(Integer likeId);

    int insert(PhotoLike record);

    int insertSelective(PhotoLike record);

    List<PhotoLike> selectByExample(PhotoLikeExample example);

    PhotoLike selectByPrimaryKey(Integer likeId);

    int updateByExampleSelective(@Param("record") PhotoLike record, @Param("example") PhotoLikeExample example);

    int updateByExample(@Param("record") PhotoLike record, @Param("example") PhotoLikeExample example);

    int updateByPrimaryKeySelective(PhotoLike record);

    int updateByPrimaryKey(PhotoLike record);
}