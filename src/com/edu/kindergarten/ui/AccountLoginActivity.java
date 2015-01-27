package com.edu.kindergarten.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.edu.kindergarten.R;
import com.edu.kindergarten.config.Constants;
import com.edu.kindergarten.entity.AccountEntity;
import com.edu.kindergarten.server.AccountHelper;
import com.edu.kindergarten.server.HttpServer;
import com.edu.kindergarten.utils.SharePrefUtils;
import com.edu.kindergarten.utils.UiUtils;

public class AccountLoginActivity extends BaseActivity {
	private static final String TAG = "AccountLoginActivity";
	
	private EditText etUserName;
	private EditText etPassword;
	private ToggleButton toggleShowPwd;
	private CheckBox checkRememberPwd;
	private TextView tvForgetPwd;
	private Button btnLogin;
	
	private InputMethodManager mInputMethodManager;
	
	private String userName;
	private String password;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_login);
		
		findViewById();
		initData();
		initView();
	}
	
	private void findViewById(){
		etUserName = (EditText)findViewById(R.id.login_username);
		etPassword = (EditText)findViewById(R.id.login_password);
		toggleShowPwd = (ToggleButton)findViewById(R.id.login_password_toggle);
		checkRememberPwd = (CheckBox)findViewById(R.id.check_remember_pwd);
		tvForgetPwd = (TextView)findViewById(R.id.forget_password);
		btnLogin = (Button)findViewById(R.id.btn_login);
	}
	
	private void initData(){
		if ((Boolean)SharePrefUtils.get(this, SharePrefUtils.PREF_ACCOUNT_REMEMBER_PWD, false)) {
			userName = (String)SharePrefUtils.get(this, SharePrefUtils.PREF_REMEMBER_USERNAME, "");
			
			if (!TextUtils.isEmpty(userName)) {
				password = AccountHelper.getPassword(this, userName);
			}
		}
		
		mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	private void initView(){
		setForgetPwdText();
		
		//set remember username and password
		if (!TextUtils.isEmpty(userName)) {
			etUserName.setText(userName);
			etPassword.setText(password);
			checkRememberPwd.toggle();
		}
		
		//set show password action
		toggleShowPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d(TAG,"zxj - psw toggle isChecked:" + isChecked);
				if(isChecked){
					etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
				}else{
					etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});
		
		//set remember password action
		/*checkRememberPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				setRememberPassword(isChecked);
			}
		});*/
		
		//set login action
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				login();
			}
		});
	}
	
	private void setForgetPwdText(){
		CharSequence text = getResources().getText(R.string.forget_password);
		SpannableString sp = new SpannableString(text);
		sp.setSpan(new ClickableSpan() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				forgetPassword();
			}
		}, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		tvForgetPwd.setText(sp);
		tvForgetPwd.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	private void setRememberPassword(){
		boolean isChecked = checkRememberPwd.isChecked();
		if (isChecked) {
			SharePrefUtils.put(this, SharePrefUtils.PREF_ACCOUNT_REMEMBER_PWD, true);
			SharePrefUtils.put(this, SharePrefUtils.PREF_REMEMBER_USERNAME, userName);
		}else {
			SharePrefUtils.put(this, SharePrefUtils.PREF_ACCOUNT_REMEMBER_PWD, false);
		}
	}
	
	private void forgetPassword(){
		Log.d(TAG, "onClick,forget password! ");
	}
	
	private void login(){
		userName = etUserName.getText().toString().trim();
		password = etPassword.getText().toString().trim();
		
		if (TextUtils.isEmpty(userName)) {
			UiUtils.showShortToast(this, "用户名不能为空");
			return;
		}
		
		AccountEntity entity = new AccountEntity();
		entity.userName = userName;
		entity.password = password;
		new LoginTask(this).execute(entity);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private class LoginTask extends AsyncTask<AccountEntity, Void, AccountEntity>{
		
		private Context mContext;
		private ProgressDialog mProgressDialog = null;
		
		LoginTask(Context context){
			mContext = context;
			
			mProgressDialog = new ProgressDialog(context);
			mProgressDialog.setMessage(getResources().getString(R.string.waiting));
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
		}

		@Override
		protected AccountEntity doInBackground(AccountEntity... infos) {
			// TODO Auto-generated method stub
			AccountEntity info = infos[0];
			return HttpServer.login(info);
		}

		@Override
		protected void onPostExecute(AccountEntity entity) {
			// TODO Auto-generated method stub
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			switch (entity.status) {
			case Constants.ACCOUNT_NOT_REGISTERED:
				UiUtils.showShortToast(mContext, "用户未注册");
				break;
			case Constants.ACCOUNT_PASSWORD_WRONG:
				UiUtils.showShortToast(mContext, "密码错误");
				break;
			case Constants.ACCOUNT_LOGIN_SUCCEED:
				mInputMethodManager.hideSoftInputFromWindow(etUserName.getWindowToken(), 0);
				mInputMethodManager.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
				AccountHelper.saveOrUpdateAccount(mContext, entity);
				setRememberPassword();
				UiUtils.showConfirmDialog(mContext, "登录成功", UiUtils.getGeneralClickListener());
				break;
			case Constants.NETWORK_ERROR:
				UiUtils.showShortToast(mContext, "网络错误");
				break;

			default:
				break;
			}
			super.onPostExecute(entity);
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgressDialog.show();
		}
	}

}
