package z.talent.memory;

import cn.bmob.v3.BmobUser;

/**
 * Created by 张天才 on 2017/4/3.
 */

 public class MyUser extends BmobUser {

    private Boolean sex;
    private String time;
    private String vip;
    private String icon;
    private Integer age;
    private String nick;
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon =icon;
    }
    public String getVip() {
        return this.vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }
    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public boolean getSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}