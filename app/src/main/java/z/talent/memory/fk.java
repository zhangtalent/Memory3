package z.talent.memory;

import android.content.*;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import leocry.emailutils.*;

public class fk extends AppCompatActivity
{
	EditText et1,et2;
	Button bt;SharedPreferences sp;
	SharedPreferences.Editor ed;
	EmailUtils em=new EmailUtils();
LinearLayout fll;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fk);
		//setc.setColor(fk.this,getResources().getColor(android.R.color.holo_blue_light));
		sp=getSharedPreferences("rs",MODE_PRIVATE);
		ed=sp.edit();
		et1=(EditText)findViewById(R.id.et1);
		et2=(EditText)findViewById(R.id.et2);
		bt=(Button)findViewById(R.id.bt);
		fll=(LinearLayout)findViewById(R.id.fll);

		bt.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{String strr=et1.getText().toString();
					String str2=et2.getText().toString();
					/*startService(new Intent(fk.this,ser.class));*/
					if(str2.equals("")){
						Toast.makeText(fk.this,"请输入反馈内容",Toast.LENGTH_SHORT).show();
					}

					else{
						em.setSenderName("2350832702@qq.com");
						em.setContent("邮箱地址："+strr+"反馈内容："+str2);
						em.setSenderPassword("yss870");
						em.setReceiver("1322293658@qq.com");
						em.setSubject("意见反馈App-忆记");
						em.send();
						Toast.makeText(fk.this,"你的反馈信息已经发送成功",Toast.LENGTH_SHORT).show();

					}

					// TODO: Implement this method
				}


			});

	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){

			fk.this.finish();
		}
	return super.onKeyDown(keyCode, event);
		}


}
