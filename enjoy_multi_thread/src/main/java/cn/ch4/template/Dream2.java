package cn.ch4.template;

/**
 * <p>
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class Dream2 extends DreamTemplate {

    @Override
    protected void dreamPremise() {
        System.out.println("我要吃喜之郎,我的梦想是成为太空人!");
    }

    @Override
    protected void dreamReady() {
        System.out.println("我正在努力的吃喜之郎中...");
    }

    @Override
    protected void dreamAchieve() {
        System.out.println("我梦想成真了,我变成了太空人");
    }

    @Override
    protected Boolean interval() {
        return false;
    }

}
