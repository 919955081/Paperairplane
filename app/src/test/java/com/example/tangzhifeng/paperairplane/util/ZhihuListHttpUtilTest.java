package com.example.tangzhifeng.paperairplane.util;

import org.junit.Before;
import org.junit.Test;

/**
 * 作者: tangzhifeng on 2017/2/15.
 * 邮箱: tzfjobmail@gmail.com
 */
public class ZhihuListHttpUtilTest {
    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void getCurrentDate() throws Exception {
        System.out.println(ZhihuListHttpUtil.getCurrentDate());
        System.out.println(ZhihuListHttpUtil.getSpecifiedDayBefore("20170201"));
    }

}