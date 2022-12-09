package cn.cleanarch.dmp.module.product.controller.app.category;

import cn.cleanarch.dmp.framework.common.pojo.CommonResult;
import cn.cleanarch.dmp.module.product.controller.app.category.vo.AppCategoryRespVO;
import cn.cleanarch.dmp.module.product.convert.category.ProductCategoryConvert;
import cn.cleanarch.dmp.module.product.dal.dataobject.category.ProductCategoryDO;
import cn.cleanarch.dmp.module.product.service.category.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

import static cn.cleanarch.dmp.framework.common.pojo.CommonResult.success;

@Api(tags = "用户 APP - 商品分类")
@RestController
@RequestMapping("/product/category")
@Validated
public class AppCategoryController {

    @Resource
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("获得商品分类列表")
    public CommonResult<List<AppCategoryRespVO>> getProductCategoryList() {
        List<ProductCategoryDO> list = categoryService.getEnableCategoryList();
        list.sort(Comparator.comparing(ProductCategoryDO::getSort));
        return success(ProductCategoryConvert.INSTANCE.convertList03(list));
    }

}
