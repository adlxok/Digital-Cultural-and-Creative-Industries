package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.entity.Carousel;
import org.example.digitalculturalportal.dao.CarouselDao;
import org.example.digitalculturalportal.service.CarouselService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Carousel)表服务实现类
 *
 * @author adlx
 * @since 2024-09-05 15:15:18
 */
@Service("carouselService")
public class CarouselServiceImpl implements CarouselService {
    @Resource
    private CarouselDao carouselDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Carousel queryById(Integer id) {
        return this.carouselDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param carousel 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Carousel> queryByPage(Carousel carousel, PageRequest pageRequest) {
        long total = this.carouselDao.count(carousel);
        return new PageImpl<>(this.carouselDao.queryAllByLimit(carousel, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param carousel 实例对象
     * @return 实例对象
     */
    @Override
    public Carousel insert(Carousel carousel) {
        this.carouselDao.insert(carousel);
        return carousel;
    }

    /**
     * 修改数据
     *
     * @param carousel 实例对象
     * @return 实例对象
     */
    @Override
    public Carousel update(Carousel carousel) {
        this.carouselDao.update(carousel);
        return this.queryById(carousel.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.carouselDao.deleteById(id) > 0;
    }
}
