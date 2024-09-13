package org.example.digitalculturalportal.dao;

import org.example.digitalculturalportal.entity.Carousel;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Carousel)表数据库访问层
 *
 * @author adlx
 * @since 2024-09-05 15:15:11
 */
public interface CarouselDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Carousel queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param carousel 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Carousel> queryAllByLimit(Carousel carousel, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param carousel 查询条件
     * @return 总行数
     */
    long count(Carousel carousel);

    /**
     * 新增数据
     *
     * @param carousel 实例对象
     * @return 影响行数
     */
    int insert(Carousel carousel);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Carousel> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Carousel> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Carousel> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Carousel> entities);

    /**
     * 修改数据
     *
     * @param carousel 实例对象
     * @return 影响行数
     */
    int update(Carousel carousel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

