package com.bestbigkk.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bestbigkk.common.ListResponse;
import com.bestbigkk.common.Pagination;
import com.bestbigkk.common.exception.BusinessException;
import com.bestbigkk.common.utils.QueryWrapperUtils;
import com.bestbigkk.persistence.entity.MaterialCategoryPO;
import com.bestbigkk.persistence.entity.MaterialPO;
import com.bestbigkk.service.IMaterialCategoryService;
import com.bestbigkk.service.IMaterialService;
import com.bestbigkk.web.response.annotation.RW;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author: 开
 * @date: 2020-04-19 18:57:11
 * @describe: 用户控制器
 */
@RW
@Api(tags = "医疗资源分类接口")
@RequestMapping(value = "/dev/material/category", produces = {"application/json;charset=UTF-8"})
public class MaterialCategoryController {

    @Autowired
    private IMaterialCategoryService materialCategoryService;
    @Autowired
    private IMaterialService materialService;
    @Autowired
    private QueryWrapperUtils queryWrapperUtils;

    @PostMapping
    @ApiOperation(value = "新增一个医疗资源分类")
    public MaterialCategoryPO add(MaterialCategoryPO materialCategory) {
        boolean save = materialCategoryService.save(materialCategory);
        if (save) {
            return materialCategory;
        }
        throw new BusinessException("新增失败");
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "按照ID删除一个医疗资源分类")
    public Boolean delete(@PathVariable("id") String id) {

        final MaterialCategoryPO beDelete = materialCategoryService.getById(id);
        if (Objects.isNull(beDelete)) {
            throw new BusinessException("被删除医疗资源分类不存在");
        }

        final int count = materialService.count(new QueryWrapper<MaterialPO>().lambda().eq(MaterialPO::getMaterialCategoryCode, beDelete.getCategoryCode()));
        if (count > 0) {
            throw new BusinessException("暂时无法删除该分类，因为有具体的医疗资源隶属于该分类下，数量："+count);
        }

        final boolean remove = materialCategoryService.removeById(id);
        if (remove) {
            return true;
        }

        throw new BusinessException("删除失败，请检查医疗资源分类是否存在");
    }

    @PutMapping
    @ApiOperation(value = "按照ID更新用户信息，以ID确定被更新医疗资源分类")
    public MaterialCategoryPO update(MaterialCategoryPO materialCategory) {

        if (Objects.isNull(materialCategory.getId())) {
            throw new BusinessException("必须指定更新医疗资源的ID");
        }

        final MaterialCategoryPO beUpdate = materialCategoryService.getById(materialCategory.getId());
        if (Objects.isNull(beUpdate)) {
            throw new BusinessException("被更新医疗资源分类不存在");
        }

        final boolean update = materialCategoryService.updateById(materialCategory);
        if (update) {
            return query(materialCategory.getId());
        }
        throw new BusinessException("更新失败");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "按照ID查询一个医疗资源分类")
    public MaterialCategoryPO query(@PathVariable("id") Long id) {
        final MaterialCategoryPO user = materialCategoryService.getById(id);
        if (Objects.nonNull(user)) {
            return user;
        }
        throw new BusinessException("医疗资源分类不存在");
    }

    @GetMapping("/list")
    @ApiOperation(value = "按照条件查询医疗资源分类列表")
    public ListResponse<MaterialCategoryPO> querys(MaterialCategoryPO user, Pagination<MaterialCategoryPO> page) {
        final QueryWrapper<MaterialCategoryPO> query = queryWrapperUtils.buildNotNullEqualsWrapper(user);
        final IPage<MaterialCategoryPO> record = materialCategoryService.page(page.toPage("create_time"), query);
        return new ListResponse<>(record.getRecords(), new Pagination<MaterialCategoryPO>().toPagination(record));
    }

}
