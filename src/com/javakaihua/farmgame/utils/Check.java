package com.javakaihua.farmgame.utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.javakaihua.farmgame.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Check {
    //检查登录、注册、修改信息时输入是否正确

    //密码少于或等于5为的时候强度为低
    public static boolean checkPassworst(String pass){
       if(pass.length()>5)
           return true;
       return false;

    }

    //密码5位以上，既有数字和字母强度为中
    public static boolean checkPassmiddle(String pass){
        boolean isDight = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < pass.length(); i++) {
            if (Character.isDigit(pass.charAt(i))) {//用char包装类中的判断数字的方法判断每一个字符
                isDight = true;
            }
            if (Character.isLetter(pass.charAt(i))) {//用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9@#$%^&*!()]{5,}$";
        boolean isRight = isDight && isLetter && pass.matches(regex);
        return isRight;
    }

    //检查本地是否保存账号信息
    public static boolean checkLocalUserExist()
    {
        return Gdx.files.internal("user/user.json").exists();
    }



    //密码强度为高包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符
    public static boolean checkPassbest(String pass) {
        boolean isDight = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < pass.length(); i++) {
            if (Character.isDigit(pass.charAt(i))) {//用char包装类中的判断数字的方法判断每一个字符
                isDight = true;
            }
            if (Character.isLetter(pass.charAt(i))) {//用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^.*(?=.{10,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$";
        boolean isRight =pass.matches(regex);
        return isRight;
    }



   //检查用户帐号是否符合规范，5-12位
    public static boolean checkID(String num) {

        String regex = "^[a-zA-Z0-9]{5,12}";
        boolean isRight = num.matches(regex);
        return isRight;
    }


    //检查数据库中是否已有账号
    public static boolean checkIDExistFromSQL(String userID) throws SQLException {
        String id=null;
        if(SQLConnector.getConn()!=null)
        {
            ResultSet rs=SQLConnector.readData("select userID,Password from user");
            while (rs.next())
            {
                id=rs.getString("userID");
                if(id.equals(userID))
                    break;
            }
            if(id.equals(userID)){
                return true;
            }
            return false;
        }
        else {
            if(!Gdx.files.internal("user/"+userID+"/user.json").exists())
                return false;
            return true;
        }


    }

    //检查验证密码是不是正确的
    public static boolean checkVerifyPass(String pass,String veriPass)
    {
        return pass.equals(veriPass);

    }

    //检查账号密码是否正确
    public static int checkLogin(String userID,String pass) throws SQLException {
        int USERIDNOTEXIST=0;
        int PASSWORDERROR=1;
        int LOGINSUCEESS=2;
        String id=null;
        if(SQLConnector.getConn()!=null)
        {
            ResultSet rs=SQLConnector.readData("select userID,Password from user");
            while (rs.next())
            {
                id=rs.getString("userID");
                if(id.equals(userID))
                    break;
            }
            if(!id.equals(userID)){
                return USERIDNOTEXIST;
            }
            else {
                String password=rs.getString("Password");
                rs.close();
                if (password.equals(pass))
                    return LOGINSUCEESS;
                return PASSWORDERROR;
            }
        }
        else {
            Json json=new Json();
            FileHandle userFile;
            if(!Gdx.files.internal("user/"+userID+"/user.json").exists())
                return USERIDNOTEXIST;
            else {
                userFile=Gdx.files.internal("user/"+userID+"/user.json");
                User user=json.fromJson(User.class, userFile);
                if(user.getPass().equals(pass))
                    return LOGINSUCEESS;
            }
            return PASSWORDERROR;
        }


    }

    //检查用户名
    public static boolean checkusername(String username){
        return username.equals("");
    }

    //检查密码是否为空
    public static boolean checkPass(String pass){
        return pass.equals("");
    }
}

