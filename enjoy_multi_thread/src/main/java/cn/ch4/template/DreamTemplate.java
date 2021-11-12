package cn.ch4.template;

/**
 * <p>
 * 梦想抽象模板
 *
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public abstract class DreamTemplate {

    protected abstract void dreamPremise();
    protected abstract void dreamReady();
    protected abstract void dreamAchieve();


    public void run(){
        dreamPremise();
        if(interval()){
            dreamReady();
        }
        dreamAchieve();
    }

    /** 去除中间执行部分 **/
    protected  Boolean interval(){
        return true;
    };

}
