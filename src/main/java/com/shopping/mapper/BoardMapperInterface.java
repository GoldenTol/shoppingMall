package com.shopping.mapper;

import com.shopping.entity.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapperInterface {
    @Select("select * from boards")
    List<Board> SelectAll();

    @Insert("insert into boards(writer, subject, description) values(#{board.writer}, #{board.subject}, #{board.description})")
    int Insert(@Param("board") final Board bean);

    @Select("select * from boards where no = #{no}")
    Board SelectOne(@Param("no")Integer no);

    @Update("update boards set writer=#{board.writer}, subject=#{board.subject}, description=#{board.description} where no=#{board.no}")
    int Update(@Param("board") final Board bean);

    @Delete("delete from boards where no = #{no}")
    int Delete(@Param("no")Integer no);
}
