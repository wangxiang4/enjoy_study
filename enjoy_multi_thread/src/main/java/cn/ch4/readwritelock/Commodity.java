package cn.ch4.readwritelock;

/**
 * <p>
 * 吃饭
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class Commodity {

    private int length;

    private String name;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}