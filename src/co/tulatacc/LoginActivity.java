package co.tulatacc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	public static String mEmail;
	public static String mPassword;
	public static String mCookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        
        Button loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SelectToTrackActivity.class);
				startActivity(intent);
			}
		});
        
        ((EditText) findViewById(R.id.edit_email)).setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.ACTION_UP == event.getAction()) {
					mEmail = ((EditText)v).getText().toString();
				}
				return false;
			}
		});
        
        ((EditText) findViewById(R.id.edit_pass)).setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.ACTION_UP == event.getAction()) {
					mPassword = ((EditText)v).getText().toString();
				}
				return false;
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_activity, menu);
        return true;
    }
}