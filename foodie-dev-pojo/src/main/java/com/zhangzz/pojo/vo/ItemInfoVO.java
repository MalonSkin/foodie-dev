package com.zhangzz.pojo.vo;

import com.zhangzz.pojo.Items;
import com.zhangzz.pojo.ItemsImg;
import com.zhangzz.pojo.ItemsParam;
import com.zhangzz.pojo.ItemsSpec;

import java.util.List;

/**
 * 商品详情VO
 * @author zhangzz
 * @date 2020/2/22 23:59
 */
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private ItemsParam itemParams;
    private List<ItemsSpec> itemSpecList;

    public ItemInfoVO() {
    }

    public ItemInfoVO(Items item, List<ItemsImg> itemImgList, ItemsParam itemParams, List<ItemsSpec> itemSpecList) {
        this.item = item;
        this.itemImgList = itemImgList;
        this.itemParams = itemParams;
        this.itemSpecList = itemSpecList;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }

    @Override
    public String toString() {
        return "ItemInfoVO{" +
                "item=" + item +
                ", itemImgList=" + itemImgList +
                ", itemParams=" + itemParams +
                ", itemSpecList=" + itemSpecList +
                '}';
    }
}
