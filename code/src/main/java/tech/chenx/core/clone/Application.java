package tech.chenx.core.clone;

import lombok.AllArgsConstructor;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/14 11:26
 * @description this is description about this file...
 */
@AllArgsConstructor
public class Application implements Cloneable{

    private int a;
    private Object o;
    public void plus() {
        a++;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Application application = new Application(0, new Object());
        Application application2 = (Application) application.clone();
        application.plus();
        System.out.println(application);

    }
}
