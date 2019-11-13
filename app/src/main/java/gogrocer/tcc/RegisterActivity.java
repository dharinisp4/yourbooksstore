package gogrocer.tcc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Config.BaseURL;
import util.ConnectivityReceiver;
import util.CustomVolleyJsonRequest;

public class RegisterActivity extends AppCompatActivity {

    private static String TAG = RegisterActivity.class.getSimpleName();

    private EditText et_phone, et_name, et_password, et_email;
    private RelativeLayout btn_register;
    private TextView  tv_login ,tv_phone, tv_name, tv_password, tv_email;
    @Override
    protected void attachBaseContext(Context newBase) {



        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title

        setContentView(R.layout.activity_register);

        et_phone = (EditText) findViewById(R.id.et_reg_phone);
        et_name = (EditText) findViewById(R.id.et_reg_name);
        et_password = (EditText) findViewById(R.id.et_reg_password);
        et_email = (EditText) findViewById(R.id.et_reg_email);
        tv_login =(TextView)findViewById( R.id.btnSignin);
//        tv_password = (TextView) findViewById(R.id.tv_reg_password);
//        tv_phone = (TextView) findViewById(R.id.tv_reg_phone);
//        tv_name = (TextView) findViewById(R.id.tv_reg_name);
//        tv_email = (TextView) findViewById(R.id.tv_reg_email);
        btn_register = (RelativeLayout) findViewById(R.id.btnRegister);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 attemptRegister();
            }
        });
        tv_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RegisterActivity.this,LoginActivity.class );
                startActivity( intent );
            }
        } );
    }

    private void attemptRegister() {

//        tv_phone.setText(getResources().getString(R.string.et_login_phone_hint));
//        tv_email.setText(getResources().getString(R.string.tv_login_email));
//        tv_name.setText(getResources().getString(R.string.tv_reg_name_hint));
//        tv_password.setText(getResources().getString(R.string.tv_login_password));

//        tv_name.setTextColor(getResources().getColor(R.color.dark_gray));
//        tv_phone.setTextColor(getResources().getColor(R.color.dark_gray));
//        tv_password.setTextColor(getResources().getColor(R.color.dark_gray));
//        tv_email.setTextColor(getResources().getColor(R.color.dark_gray));

        String getphone = et_phone.getText().toString();
        String getname = et_name.getText().toString();
        String getpassword = et_password.getText().toString();
        String getemail = et_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(getphone)) {
            tv_phone.setTextColor(getResources().getColor(R.color.black));
            et_phone.requestFocus();
            et_phone.setError( "invalid" );
            focusView = et_phone;
            cancel = true;
        }
        else if (getphone.charAt( 0 )<6) {

            et_phone.setError("Invalid Mobile number");
          //  et_phone.setText(getResources().getString(R.string.phone_too_short));
            et_phone.setTextColor(getResources().getColor(R.color.black));
            focusView = et_phone;
            cancel = true;
        }


        if (TextUtils.isEmpty(getname)) {
            tv_name.setTextColor(getResources().getColor(R.color.black));
            et_name.requestFocus();
            et_name.setError( "mandatory field" );
            focusView = et_name;
            cancel = true;
        }

        if (TextUtils.isEmpty(getpassword)) {
            tv_password.setTextColor(getResources().getColor(R.color.black));
            et_password.setError( "mandatory field" );
            et_password.requestFocus();
            focusView = et_password;
            cancel = true;
        } else if (!isPasswordValid(getpassword)) {
          //  tv_password.setText(getResources().getString(R.string.password_too_short));
            tv_password.setTextColor(getResources().getColor(R.color.black));
            et_password.setError( "too short" );
            et_password.requestFocus();
            focusView = et_password;
            cancel = true;
        }

        if (TextUtils.isEmpty(getemail)) {
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(getemail)) {
           // et_email.setText(getResources().getString(R.string.invalide_email_address));
            et_email.setTextColor(getResources().getColor(R.color.black));
            et_email.setError( "invalid" );
            et_email.requestFocus();
            focusView = et_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null)
                focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (ConnectivityReceiver.isConnected()) {
                makeRegisterRequest(getname, getphone, getemail, getpassword);
            }
        }


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid(String phoneno) {
        //TODO: Replace this with your own logic
        return phoneno.length() > 9;
    }

    /**
     * Method to make json object request where json response starts wtih
     */
    private void makeRegisterRequest(String name, String mobile,
                                     final String email, String password) {

        // Tag used to cancel the request
        String tag_json_obj = "json_register_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_name", name);
        params.put("user_mobile", mobile);
        params.put("user_email", email);
        params.put("password", password);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.REGISTER_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {
                        String msg = response.getString("message");
                        Toast.makeText(RegisterActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        BackgroundMail.newBuilder(RegisterActivity.this)
                                .withUsername("anshuwap1@gmail.com")
                                .withPassword("Mynewpass@123")
                                .withMailto(email)
                                .withSubject("Your Book Store")
                                .withBody("Congratulations ! your account has been created for yourbookstore")
                                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(RegisterActivity.this, "check your mail !", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }).withOnFailCallback(new BackgroundMail.OnFailCallback() {
                            @Override
                            public void onFail() {
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                                Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }).send();
                        /*  BackgroundMail.newBuilder(RegisterActivity.this)
                                .withUsername("anshuwap1@gmail.com")
                                .withPassword("Mynewpass@123")
                                .withMailTo("ayushsrivastva23@gmail.com")
                                .withType(BackgroundMail.TYPE_PLAIN)
                                .withSubject("this is the subject")
                                .withBody("this is the body")
                                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                    @Override
                                    public void onSuccess() {
                                        //do some magic
                                    }
                                })
                                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                    @Override
                                    public void onFail() {
                                        //do some magic
                                    }
                                })
                                .send();*/
                        btn_register.setEnabled(false);

                    } else {
                        String error = response.getString("error");
                        btn_register.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

}
