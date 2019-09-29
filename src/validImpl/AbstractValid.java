package validImpl;

import number.NumberCell;

/**
 * @author ： gaoang
 * @mudule: 应用模块名称
 * @Copyright: Copyright (C) 2019 BlueSea, Inc. All rights reserved.
 * @Company : 北京巅峰同道科技有限公司
 * @since ：2019/9/26 19:27
 */
public interface AbstractValid {
    /**
     * 校验格式
     * @param input
     * @throws Exception
     */
    void valid(NumberCell input) throws Exception;
}
