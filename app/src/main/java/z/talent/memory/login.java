package z.talent.memory;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/5/1.
 */

public class login extends AppCompatActivity {
Button login,register,findout,qqlogin;
EditText username,password,mail;
Tencent mTencent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TypefaceHelper.typeface(this);
        Bmob.initialize(login.this, "c3bc12088c3accaaa474fa38cad012c9");
        mTencent = Tencent.createInstance("1106091323", login.this.getApplicationContext());
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        mail=(EditText)findViewById(R.id.mail);
        login=(Button)findViewById(R.id.login);
        qqlogin=(Button)findViewById(R.id.loginbyqq);
        register=(Button)findViewById(R.id.register);
        findout=(Button)findViewById(R.id.findout);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
normallogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
register();
            }
        });
        findout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
findout();
            }
        });
        qqlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginbyqq();
            }
        });


        if(BmobUser.getCurrentUser(MyUser.class)!=null){
            startActivity(new Intent(login.this,firsthome.class));
            finish();
        }
        else{

        }
    }
    public void normallogin(){
        if(!username.getText().toString().equals("")&!password.getText().toString().equals("")){

            MyUser.loginByAccount(username.getText().toString(), password.getText().toString(), new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){
                    //Log.i("smile","用户登陆成功");
                    Toast.makeText(login.this,"登录成功",Toast.LENGTH_LONG).show();


                    startActivity(new Intent(login.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(login.this,"登录出错，用户不存在或网络出错",Toast.LENGTH_LONG).show();

                }
            }
        });}
        else{
            Toast.makeText(login.this, "用户名密码不能为空" , Toast.LENGTH_LONG).show();

        }
    }
    public void loginbyqq(){
        //mTencent.logout(login.this);
        mTencent.login(login.this, "all", new IUiListener() {
            public void onComplete(Object response) {
                Toast.makeText(login.this, "第三方登录ok：", Toast.LENGTH_LONG).show();

                //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
                //  mBaseMessageText.setText("onComplete:");
                doComplete((JSONObject)response);
            }
            protected void doComplete(JSONObject jsonObject) {
                // Toast.makeText(LoginActivity.this, "第三方登录ok：", Toast.LENGTH_LONG).show();

                // String openId = values.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                try {
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
                    BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(
                            "qq", token, expires, openId);
                    //BmobThirdUserAuth authInfo = new BmobThirdUserAuth(snsType,accessToken, expiresIn,userId);
                    //Toast.makeText(login.this, "1" , Toast.LENGTH_LONG).show();

                    BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {

                        @Override
                        public void done(final JSONObject userAuth, BmobException e) {
                            if(e==null){

                              MyUser mm=BmobUser.getCurrentUser(MyUser.class);
                                            if(mm.getVip()!=null&&!mm.getVip().equals("")){
                                            }else{ mm.setVip("普通用户");
                                                mm.setUsername( "QQ用户"+String.valueOf( System.currentTimeMillis()).substring(String.valueOf( System.currentTimeMillis()).length()-6,String.valueOf( System.currentTimeMillis()).length()));
                                                mm.update(mm.getObjectId(), new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        Toast.makeText(login.this, "qq登录" , Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(login.this,MainActivity.class));
                                                        finish();
                                                    }
                                                });
                                            }





                                //    Log.i("bmob","关联成功");
                            }else{            Toast.makeText(login.this, "第三方登录ok："+e.toString(), Toast.LENGTH_LONG).show();

                                //  Log.i("bmob","关联失败：code =" + e.getErrorCode() + ",msg = " + e.getMessage());
                            }

                        }
                    });
                    // loginWithAuth(authInfo);
                } catch (Exception e) {
                    Toast.makeText(login.this, "第三方登录222失败："+e.toString() , Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onError(UiError e) {
                Toast.makeText(login.this, "第三方登录222reeo："+e.toString() , Toast.LENGTH_LONG).show();

                //showResult("onError:", "code:" + e.errorCode + ", msg:"
                ///      + e.errorMessage + ", detail:" + e.errorDetail);
            }
            @Override
            public void onCancel() {
                //showResult("onCancel", "");
            }

        });
    }
    public void register(){
if(register.getText().toString().equals("注   册")){
    mail.setVisibility(View.VISIBLE);
    register.setText("确认注册");
}
else{
    if(!mail.getText().toString().equals("")&!username.getText().toString().equals("")&!password.getText().toString().equals("")){
        MyUser bu = new MyUser();
        bu.setVip("普通用户");
        bu.setUsername(username.getText().toString());
        bu.setPassword(password.getText().toString());
        bu.setEmail(mail.getText().toString());
//注意：不能用save方法进行注册
        bu.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if(e==null){
                    Toast.makeText(login.this,"注册成功，请登录",Toast.LENGTH_LONG).show();
                    mail.setVisibility(View.GONE);
                }else{
                    Toast.makeText(login.this,"注册失败",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    else{
        Toast.makeText(login.this,"三个都要填写,不能为空",Toast.LENGTH_LONG).show();
        //mEmailSignInButton.setText("登录");

    }
}
    }
    public void findout(){
        Dialog dialog=new Dialog(login.this);
        LayoutInflater layoutInflater=LayoutInflater.from(login.this);
        View view=layoutInflater.inflate(R.layout.dia,null);
        Button button=(Button)view.findViewById(R.id.bt);
        final EditText editText=(EditText)view.findViewById(R.id.et);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")){
                    BmobUser.resetPasswordByEmail(editText.getText().toString(), new UpdateListener() {


                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(login.this, "找回密码请求成功，请到" +editText.getText().toString() + "邮箱进行密码重置操作", Toast.LENGTH_LONG).show();

                                // toast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                            }else{
                                // toast("失败:" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Toast.makeText(login.this, "授权成功", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
            });
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
