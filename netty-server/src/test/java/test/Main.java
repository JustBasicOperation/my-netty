package test;

import org.junit.Test;

/**
 * @description:
 * @Author zhaolinfeng3
 * @Date 2020/4/30 15:03
 */
public class Main {
    @Test
    public void test01(){
        String s1 = "坤磷";
        String s2 = "坤";
        System.out.println(s1.contains(s2));
        System.out.println(s1.contentEquals(s2));
    }
}
