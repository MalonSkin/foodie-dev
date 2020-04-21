package com.zhangzz.controller;

import com.zhangzz.enums.YesOrNo;
import com.zhangzz.pojo.Carousel;
import com.zhangzz.pojo.Category;
import com.zhangzz.pojo.vo.CategoryVO;
import com.zhangzz.service.CarouselService;
import com.zhangzz.service.CategoryService;
import com.zhangzz.utils.IMOOCJSONResult;
import com.zhangzz.utils.JsonUtils;
import com.zhangzz.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页控制器
 * @author zhangzz
 * @date 2020/2/22 12:29
 */
@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
        String carouselStr = redisOperator.get("carousel");
        List<Carousel> carousels;
        if (StringUtils.isBlank(carouselStr)) {
            carousels = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel", JsonUtils.objectToJson(carousels));
        } else {
            carousels = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }
        return IMOOCJSONResult.ok(carousels);
    }

    /**
     * 首页分类展示需求：
     * 1.第一次刷新主页查询大类，渲染展示到首页
     * 2.如果鼠标移动到大分类上，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）", httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        String catsStr = redisOperator.get("cats");
        List<Category> categories;
        if (StringUtils.isBlank(catsStr)) {
            categories = categoryService.queryAllRootLevelCat();
            redisOperator.set("cats", JsonUtils.objectToJson(categories));
        } else {
            categories = JsonUtils.jsonToList(catsStr, Category.class);
        }
        return IMOOCJSONResult.ok(categories);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类ID", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }
        String subCatsStr = redisOperator.get("subCats:" + rootCatId);
        List<CategoryVO> subCats;
        if (StringUtils.isBlank(subCatsStr)) {
            subCats = categoryService.getSubCatList(rootCatId);
            redisOperator.set("subCats:" + rootCatId, JsonUtils.objectToJson(subCats));
        } else {
            subCats = JsonUtils.jsonToList(subCatsStr, CategoryVO.class);
        }
        return IMOOCJSONResult.ok(subCats);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类ID", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }
        return IMOOCJSONResult.ok(categoryService.getSixNewItemsLazy(rootCatId));
    }

}
