package com.logSys.util;

import com.logSys.domain.LogReaderDomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ytj on 2017/12/19.
 */
public class FileUtil {


    public static void main(String[] args) throws Exception
    {
        long beginTime = System.currentTimeMillis();
        LogReaderDomain str = getString(100,110,"C:\\event\\20171213_0.txt");
        System.out.println(str);
        System.out.println(System.currentTimeMillis()-beginTime);
    }

    /**
     * 根据行数读取文件
     * @param from 开始行数
     * @param to 结束行数
     * @param pathname 文件
     * @return
     * @throws Exception
     */
    public static LogReaderDomain getString(int from, int to, String pathname) throws Exception{
        LogReaderDomain result = new LogReaderDomain();
        BufferedReader br = new BufferedReader(new FileReader(new File(pathname)));
        List content = new ArrayList();
        String temp = null;
        int count = 0;
        while((temp = br.readLine() ) != null){
            count ++;
            if(count >= from && count< to){
                content.add(temp + "\n");
            }
        }
        result.setContent(content);
        result.setFinished(count == to);
        return result;
    }
    private File[] fileChildren(String path, Date date){
        File directory = new File(path);
        if(!directory.exists() && !directory.isDirectory()){
            return null;
        }
        File[] files = directory.listFiles();
        if(files != null){
            for(File file : files){
                if(file.getName().equals("")){

                }
            }
        }
        return null;
    }
}
