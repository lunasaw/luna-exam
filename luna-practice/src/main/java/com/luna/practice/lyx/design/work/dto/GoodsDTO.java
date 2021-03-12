package com.luna.practice.lyx.design.work.dto;

import com.luna.practice.lyx.design.work.entity.BrandDO;
import com.luna.practice.lyx.design.work.entity.GoodsDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author luna@mac
 * @className GoodsDTO.java
 * @description TODO
 * @createTime 2021年03月12日 19:26:00
 */
public class GoodsDTO {

    public static List<GoodsDO> goodsDOList  = null;

    public static final String  XIANG_JIAO   = "香蕉";

    public static final String  PING_GUO     = "苹果";

    public static final String  LI_ZI        = "梨子";

    public static final String  JU_ZI        = "橘子";

    public static final String  NAN_ZHUANG   = "男装";

    public static final String  PI_XIE       = "皮鞋";

    public static final String  YUN_DONG_XIE = "运动鞋";

    public static final String  DIAN_SHI_JI  = "电视机";

    public static final String  SHOU_JI      = "手机";

    public static final String  XI_YI_JI     = "洗衣机";

    public static final String  YA_GAO       = "牙膏";

    public static final String  XI_SHOU_YE   = "洗手液";

    public static final String  FEI_ZHAO     = "肥皂";

    static {
        goodsDOList = new ArrayList<>();
        goodsDOList.add(new GoodsDO(1, XIANG_JIAO, 15, BrandDTO.brandDOMap.get(""),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.SHUI_GUO)));
        goodsDOList.add(new GoodsDO(2, PING_GUO, 20, BrandDTO.brandDOMap.get(""),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.SHUI_GUO)));
        goodsDOList.add(new GoodsDO(3, LI_ZI, 10, BrandDTO.brandDOMap.get(""),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.SHUI_GUO)));
        goodsDOList.add(new GoodsDO(4, JU_ZI, 20, BrandDTO.brandDOMap.get(""),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.SHUI_GUO)));
        goodsDOList.add(new GoodsDO(5, NAN_ZHUANG, 100, BrandDTO.brandDOMap.get(BrandDTO.HOME_XING_ER_KE),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.FU_ZHUANG)));
        goodsDOList.add(new GoodsDO(6, PI_XIE, 200, BrandDTO.brandDOMap.get(BrandDTO.JIU_MU_WANG),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.FU_ZHUANG)));
        goodsDOList.add(new GoodsDO(7, YUN_DONG_XIE, 2000, BrandDTO.brandDOMap.get(BrandDTO.YE_ZI),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.FU_ZHUANG)));
        goodsDOList.add(new GoodsDO(8, DIAN_SHI_JI, 1999, BrandDTO.brandDOMap.get(BrandDTO.KANG_JIA),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.JIA_DIAN)));
        goodsDOList.add(new GoodsDO(9, SHOU_JI, 3999, BrandDTO.brandDOMap.get(BrandDTO.XIAO_MI),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.JIA_DIAN)));
        goodsDOList.add(new GoodsDO(10, XI_YI_JI, 999, BrandDTO.brandDOMap.get(BrandDTO.HAI_ER),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.JIA_DIAN)));
        goodsDOList.add(new GoodsDO(11, YA_GAO, 2, BrandDTO.brandDOMap.get(BrandDTO.ZHONG_HUA),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.RI_HUA)));
        goodsDOList.add(new GoodsDO(11, XI_SHOU_YE, 12, BrandDTO.brandDOMap.get(BrandDTO.LAN_YUE_LIANG),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.RI_HUA)));
        goodsDOList.add(new GoodsDO(12, FEI_ZHAO, 3, BrandDTO.brandDOMap.get(BrandDTO.XIAO_BAI_TU),
            CateGoryDTO.categoryDOMap.get(CateGoryDTO.RI_HUA)));

    }

    public static GoodsDO findById(long id) {
        for (GoodsDO goodsDO : goodsDOList) {
            if (goodsDO.getId() == id) {
                return goodsDO;
            }
        }
        return null;
    }
}
