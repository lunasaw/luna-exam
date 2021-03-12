package com.luna.practice.lyx.design.work.dto;

import com.luna.practice.lyx.design.work.entity.CategoryDO;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author luna@mac
 * @className CateGoryDTO.java
 * @description TODO
 * @createTime 2021年03月12日 19:28:00
 */
public class CateGoryDTO {

    public static Map<String, CategoryDO> categoryDOMap = null;

    public static final String            SHUI_GUO      = "水果";

    public static final String            FU_ZHUANG     = "服装";

    public static final String            JIA_DIAN      = "家电";

    public static final String            RI_HUA        = "日化";

    static {
        categoryDOMap = new HashMap<>();
        categoryDOMap.put(SHUI_GUO, new CategoryDO(1, SHUI_GUO));
        categoryDOMap.put(FU_ZHUANG, new CategoryDO(2, FU_ZHUANG));
        categoryDOMap.put(JIA_DIAN, new CategoryDO(3, JIA_DIAN));
        categoryDOMap.put(RI_HUA, new CategoryDO(4, RI_HUA));
    }

    public static CategoryDO findById(long id) {
        for (Map.Entry<String, CategoryDO> entry : categoryDOMap.entrySet()) {
            if (entry.getValue().getId() == id) {
                return entry.getValue();
            }
        }
        return null;
    }
}
