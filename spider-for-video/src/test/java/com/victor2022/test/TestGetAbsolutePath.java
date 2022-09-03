package com.victor2022.test;

import org.junit.Test;

import java.io.File;

/**
 * @author victor2022
 * @creat 2022/9/3 12:16
 */
public class TestGetAbsolutePath {

    @Test
    public void testGetAbsolutePath(){
        File file = new File(".video");
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        System.out.println("done");
    }
}
