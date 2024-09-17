package org.example.digitalculturalportal.controller;

import org.example.digitalculturalportal.entity.Carousel;
import org.example.digitalculturalportal.service.CarouselService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Carousel)表控制层
 *
 * @author adlx
 * @since 2024-09-05 15:15:11
 */
@RestController
@RequestMapping("carousel")
public class CarouselController {
    /**
     * 服务对象
     */
    @Resource
    private CarouselService carouselService;

    /**
     * 分页查询
     *
     * @param carousel 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Carousel>> queryByPage(Carousel carousel, PageRequest pageRequest) {
        return ResponseEntity.ok(this.carouselService.queryByPage(carousel, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Carousel> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.carouselService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param carousel 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Carousel> add(Carousel carousel) {
        return ResponseEntity.ok(this.carouselService.insert(carousel));
    }

    /**
     * 编辑数据
     *
     * @param carousel 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Carousel> edit(Carousel carousel) {
        return ResponseEntity.ok(this.carouselService.update(carousel));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.carouselService.deleteById(id));
    }

}

