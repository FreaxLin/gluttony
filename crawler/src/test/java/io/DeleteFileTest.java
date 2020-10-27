package io;

import org.junit.Test;

import java.io.File;

public class DeleteFileTest {

    @Test
    public void delete(){
        String path = "/Users/sai/OpenSources/gluttony/data";
        File storage = new File(path);
        File[] files = storage.listFiles();
        for (File file : files){
            file.delete();
        }
    }
}
