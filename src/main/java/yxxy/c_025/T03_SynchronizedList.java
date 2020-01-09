package yxxy.c_025;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class T03_SynchronizedList {

    public static void main(String[] args) {
        List<String> strList =new ArrayList<>();
        List<String> list = Collections.synchronizedList(strList);

    }
}
