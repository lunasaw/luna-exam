package com.luna.practice.lyx.design.work.dto;

import com.luna.practice.lyx.design.work.entity.BrandDO;
import com.luna.practice.lyx.design.work.entity.CategoryDO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luna@mac
 * @className BrandDTO.java
 * @description TODO
 * @createTime 2021年03月12日 19:11:00
 */
public class BrandDTO {

    public static Map<String, BrandDO> brandDOMap      = null;

    public static final String         HOME_XING_ER_KE = "鸿星尔克";

    public static final String         JIU_MU_WANG     = "九牧王";

    public static final String         YE_ZI           = "椰子";

    public static final String         KANG_JIA        = "康佳";

    public static final String         XIAO_MI         = "小米";

    public static final String         HAI_ER          = "海尔";

    public static final String         ZHONG_HUA       = "中华";

    public static final String         LAN_YUE_LIANG   = "蓝月亮";

    public static final String         XIAO_BAI_TU     = "小白兔";

    static {
        brandDOMap = new HashMap<>();

        // 服装
        brandDOMap.put(HOME_XING_ER_KE, new BrandDO(1, HOME_XING_ER_KE));
        brandDOMap.put(JIU_MU_WANG, new BrandDO(2, JIU_MU_WANG));
        brandDOMap.put(YE_ZI, new BrandDO(3, YE_ZI));

        // 家电
        brandDOMap.put(KANG_JIA, new BrandDO(4, KANG_JIA));
        brandDOMap.put(XIAO_MI, new BrandDO(5, XIAO_MI));
        brandDOMap.put(HAI_ER, new BrandDO(6, HAI_ER));

        // 日化
        brandDOMap.put(ZHONG_HUA, new BrandDO(7, ZHONG_HUA));
        brandDOMap.put(LAN_YUE_LIANG, new BrandDO(8, LAN_YUE_LIANG));
        brandDOMap.put(XIAO_BAI_TU, new BrandDO(9, XIAO_BAI_TU));

        brandDOMap.put("", new BrandDO(0, ""));
    }

    public static BrandDO findById(long id) {
        for (Map.Entry<String, BrandDO> entry : brandDOMap.entrySet()) {
            if (entry.getValue().getId() == id) {
                return entry.getValue();
            }
        }
        return null;
    }
}
