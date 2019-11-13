package gogrocer.tcc;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.FilterAdapter;
import Config.BaseURL;
import util.CustomVolleyJsonRequest;
import util.RecyclerTouchListener;

public class FilterActivity extends AppCompatActivity {

    Dialog ProgressDialog;
    CheckBox chk_bk_class,chk_bk_subject,chk_bk_language;
    RecyclerView rv_class,rv_subject,rv_language;
    String cat_id="";
    List<String> list_class;
    List<String> list_language;
    List<String> list_subject;
    FilterAdapter filterAdapter;
    Button btn_apply,btn_clear;
   public static TextView txt_class,txt_subject,txt_language;
    ImageView img_back;
    public static String book_class="",subject="",language="";
    int flag=0;

    RecyclerView.LayoutManager layoutManager1,layoutManager2,layoutManager3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        chk_bk_class=(CheckBox)findViewById(R.id.chk_bk_class);
        chk_bk_subject=(CheckBox)findViewById(R.id.chk_bk_subject);
        chk_bk_language=(CheckBox)findViewById(R.id.chk_bk_language);

        btn_apply=(Button)findViewById(R.id.btn_apply);
        btn_clear=(Button)findViewById(R.id.btn_clear);

        txt_class=(TextView)findViewById(R.id.txt_class);
        txt_subject=(TextView)findViewById(R.id.txt_subject);
        txt_language=(TextView)findViewById(R.id.txt_language);

        img_back=(ImageView)findViewById(R.id.img_back);

        ProgressDialog = new Dialog(FilterActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        ProgressDialog.setContentView(R.layout.progressbar);
        ProgressDialog.setCancelable(false);

        cat_id=getIntent().getStringExtra("category_id");

        list_class=new ArrayList<>();
        list_subject=new ArrayList<>();
        list_language=new ArrayList<>();
        rv_language=findViewById(R.id.rv_language);
        rv_subject=findViewById(R.id.rv_subject);
        rv_class=findViewById(R.id.rv_subject);

        layoutManager1=new LinearLayoutManager(this);
        layoutManager2=new LinearLayoutManager(this);
        layoutManager3=new LinearLayoutManager(this);

        rv_language.setHasFixedSize(false);
        rv_subject.setHasFixedSize(false);
        rv_class.setHasFixedSize(false);

        rv_class.setLayoutManager(layoutManager1);
        rv_subject.setLayoutManager(layoutManager2);
        rv_language.setLayoutManager(layoutManager3);


        chk_bk_class.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    createClassList(cat_id);
                    if(chk_bk_subject.isChecked())
                    {
                        chk_bk_subject.setChecked(false);
                    }
                    if(chk_bk_language.isChecked())
                    {
                        chk_bk_language.setChecked(false);
                    }
                }
                else
                {
                    rv_class.setVisibility(View.GONE);
                }

            }
        });

        chk_bk_language.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                   createLanguageList(cat_id);
                    if(chk_bk_subject.isChecked())
                    {
                        chk_bk_subject.setChecked(false);
                    }
                    if(chk_bk_class.isChecked())
                    {
                        chk_bk_class.setChecked(false);
                    }
                }
                else
                {
                   rv_language.setVisibility(View.GONE);
                }

            }
        });

        chk_bk_subject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    createSubjectList(cat_id);
                    if(chk_bk_language.isChecked())
                    {
                        chk_bk_language.setChecked(false);
                    }
                    if(chk_bk_class.isChecked())
                    {
                        chk_bk_class.setChecked(false);
                    }
                }
                else
                {
                    rv_subject.setVisibility(View.GONE);
                }

            }
        });

    }

    private void createClassList(String cat_id) {

        //Toast.makeText(FilterActivity.this,""+cat_id,Toast.LENGTH_LONG).show();
        ProgressDialog.show();
        String json_tag="json_book_class";
        HashMap<String,String> map=new HashMap<>();
        map.put("category_id",cat_id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_BOOK_CLASS_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("bk_class",response.toString());
                try
                {
                    list_class.clear();
                    ProgressDialog.dismiss();
                    //Toast.makeText(FilterActivity.this,""+response.toString(),Toast.LENGTH_LONG).show();
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONArray array=response.getJSONArray("data");
                        for(int i=0; i<array.length();i++)
                        {
                            JSONObject object= (JSONObject) array.get(i);
                            String bk=object.getString("book_class");
                            list_class.add(bk);
                        }
                        filterAdapter=new FilterAdapter(list_class,FilterActivity.this,1);
                        rv_class.setVisibility(View.VISIBLE);
                        rv_class.setAdapter(filterAdapter);


                    }
                    else {

                    }
                }
                catch (Exception ex)
                {
                    ProgressDialog.dismiss();
                    Toast.makeText(FilterActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressDialog.dismiss();
                Toast.makeText(FilterActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);


    }


    private void createSubjectList(String cat_id) {

        //Toast.makeText(FilterActivity.this,""+cat_id,Toast.LENGTH_LONG).show();
        ProgressDialog.show();
        String json_tag="json_book_class";
        HashMap<String,String> map=new HashMap<>();
        map.put("category_id",cat_id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_SUBJECT_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("bk_class",response.toString());
                try
                {
                    list_subject.clear();
                    ProgressDialog.dismiss();
                    //Toast.makeText(FilterActivity.this,""+response.toString(),Toast.LENGTH_LONG).show();
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONArray array=response.getJSONArray("data");
                        for(int i=0; i<array.length();i++)
                        {
                            JSONObject object= (JSONObject) array.get(i);
                            String bk=object.getString("subject");
                            list_subject.add(bk);
                        }
                        filterAdapter=new FilterAdapter(list_subject,FilterActivity.this,2);
                        rv_subject.setVisibility(View.VISIBLE);
                        rv_subject.setAdapter(filterAdapter);

                    }
                    else {

                    }
                }
                catch (Exception ex)
                {
                    ProgressDialog.dismiss();
                    Toast.makeText(FilterActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressDialog.dismiss();
                Toast.makeText(FilterActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);


    }


    private void createLanguageList(String cat_id) {

        //Toast.makeText(FilterActivity.this,""+cat_id,Toast.LENGTH_LONG).show();
        ProgressDialog.show();
        String json_tag="json_book_class";
        HashMap<String,String> map=new HashMap<>();
        map.put("category_id",cat_id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_LANGUAGE_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("bk_class",response.toString());
                try
                {
                    list_language.clear();
                    ProgressDialog.dismiss();
                    //Toast.makeText(FilterActivity.this,""+response.toString(),Toast.LENGTH_LONG).show();
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONArray array=response.getJSONArray("data");
                        for(int i=0; i<array.length();i++)
                        {
                            JSONObject object= (JSONObject) array.get(i);
                            String bk=object.getString("language");
                            String l=JsonArrToString(bk);
                            list_language.add(l);
                        }
                        filterAdapter=new FilterAdapter(list_language,FilterActivity.this,3);
                        rv_language.setVisibility(View.VISIBLE);
                        rv_language.setAdapter(filterAdapter);
                        rv_subject.setVisibility(View.GONE);
                        rv_class.setVisibility(View.GONE);
                    }
                    else {

                    }
                }
                catch (Exception ex)
                {
                    ProgressDialog.dismiss();
                    Toast.makeText(FilterActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressDialog.dismiss();
                Toast.makeText(FilterActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);


    }

    public String JsonArrToString(String str)
    {
        String s="";
        try
        {
            JSONArray array=new JSONArray(str);
            for(int i=0; i<array.length();i++)
            {
                s=array.getString(i).toString();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return s;
    }
}
