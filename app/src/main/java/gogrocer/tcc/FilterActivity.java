package gogrocer.tcc;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import Fragment.Product_fragment;
import util.CustomVolleyJsonRequest;

public class FilterActivity extends Fragment {

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
    ProgressDialog progressDialog ;

    RecyclerView.LayoutManager layoutManager1,layoutManager2,layoutManager3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_filter, container, false);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");

        chk_bk_class=(CheckBox)view.findViewById(R.id.chk_bk_class);
        chk_bk_subject=(CheckBox)view.findViewById(R.id.chk_bk_subject);
        chk_bk_language=(CheckBox)view.findViewById(R.id.chk_bk_language);

        btn_apply=(Button)view.findViewById(R.id.btn_apply);
        btn_clear=(Button)view.findViewById(R.id.btn_clear);

        txt_class=(TextView)view.findViewById(R.id.txt_class);
        txt_subject=(TextView)view.findViewById(R.id.txt_subject);
        txt_language=(TextView)view.findViewById(R.id.txt_language);

      //  img_back=(ImageView)view.findViewById(R.id.img_back);

        ProgressDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        ProgressDialog.setContentView(R.layout.progressbar);
        ProgressDialog.setCancelable(false);

//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    ((MainActivity) getActivity()).finish();
//                    return true;
//                }
//                return false;
//            }
//        });

        cat_id= getArguments().getString("category_id");

        list_class=new ArrayList<>();
        list_subject=new ArrayList<>();
        list_language=new ArrayList<>();
        rv_language=view.findViewById(R.id.rv_language);
        rv_subject=view.findViewById(R.id.rv_subject);
        rv_class=view.findViewById(R.id.rv_subject);

        layoutManager1=new LinearLayoutManager(getActivity());
        layoutManager2=new LinearLayoutManager(getActivity());
        layoutManager3=new LinearLayoutManager(getActivity());

        rv_language.setHasFixedSize(false);
        rv_subject.setHasFixedSize(false);
        rv_class.setHasFixedSize(false);

        rv_class.setLayoutManager(layoutManager1);
        rv_subject.setLayoutManager(layoutManager2);
        rv_language.setLayoutManager(layoutManager3);
        createClassList(cat_id);


        chk_bk_class.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    createBookClassList(cat_id);
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


        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object=new JSONObject();
                try {

                    object.put("cat_id",cat_id);
                    object.put("book_class",book_class);
                    object.put("subject",subject);
                    object.put("language",language);

                }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
                JSONArray array=new JSONArray();
                array.put(object);

                String data=array.toString();
                //String data=cat_id+"@"+book_class+","+subject+","+language;
                Bundle args = new Bundle();
                Fragment fm = new Product_fragment();
                args.putString("filter", data);
                fm.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction=  fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentPanel, fm).addToBackStack(null)
                        .commit();
             // fragmentTransaction.remove(getF);


            }
        });

        return view;
    }


    private void createClassList(String cat_id) {

        //Toast.makeText(FilterActivity.this,""+cat_id,Toast.LENGTH_LONG).show();
        progressDialog.show();
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
                    progressDialog.dismiss();
                    //Toast.makeText(FilterActivity.this,""+response.toString(),Toast.LENGTH_LONG).show();
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {

                        int a=0,b=0,c=0;
                        JSONObject object=response.getJSONObject("data");


                        if(object.has("book_class"))
                        {
                           chk_bk_class.setVisibility(View.VISIBLE);
                        }
                         if(object.has("subject"))
                        {
                            chk_bk_subject.setVisibility(View.VISIBLE);
                        }
                         if(object.has("language"))
                        {
                            chk_bk_language.setVisibility(View.VISIBLE);
                             }
                       //  else if(object.has()) {
                        //Toast.makeText(getActivity(),"a- "+a+"\n b- "+b+"\n c- "+c+"\n date: --"+object.toString(),Toast.LENGTH_LONG).show();

//                        JSONArray array=response.getJSONArray("data");
//                        for(int i=0; i<array.length();i++)
//                        {
//                            JSONObject object= (JSONObject) array.get(i);
//                            String bk=object.getString("book_class");
//                            list_class.add(bk);
//                        }
//                        filterAdapter=new FilterAdapter(list_class,getActivity(),1);
//                        rv_class.setVisibility(View.VISIBLE);
//                        rv_class.setAdapter(filterAdapter);
//

                    }
                    else if(status.equals("failed")) {

                       // Toast.makeText(getActivity(),"There are no filter in this category \n "+response.getString("data").toString(),Toast.LENGTH_LONG).show();

                    }
                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);


    }


    private void createSubjectList(String cat_id) {

        //Toast.makeText(FilterActivity.this,""+cat_id,Toast.LENGTH_LONG).show();
        progressDialog.show();
        String json_tag="json_subject";
        HashMap<String,String> map=new HashMap<>();
        map.put("category_id",cat_id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_SUBJECT_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("bk_class",response.toString());
                try
                {
                    list_subject.clear();
                    progressDialog.dismiss();
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
                        filterAdapter=new FilterAdapter(list_subject,getActivity(),2);
                        rv_subject.setVisibility(View.VISIBLE);
                        rv_subject.setAdapter(filterAdapter);

                    }
                    else {

                    }
                }
                catch (Exception ex)
                {
                    ProgressDialog.dismiss();
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressDialog.dismiss();
                Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);


    }


    private void createLanguageList(String cat_id) {

        //Toast.makeText(FilterActivity.this,""+cat_id,Toast.LENGTH_LONG).show();
        progressDialog.show();
        String json_tag="json_book_lang";
        HashMap<String,String> map=new HashMap<>();
        map.put("category_id",cat_id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_LANGUAGE_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("bk_class",response.toString());
                try
                {
                    list_language.clear();
                    progressDialog.dismiss();
                    //Toast.makeText(FilterActivity.this,""+response.toString(),Toast.LENGTH_LONG).show();
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONArray array=response.getJSONArray("data");
                        for(int i=0; i<array.length();i++)
                        {
                            JSONObject object= (JSONObject) array.get(i);
                            String bk=object.getString("language");
                            //String l=JsonArrToString(bk);
                            list_language.add(bk);
                        }
                        filterAdapter=new FilterAdapter(list_language,getActivity(),3);
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
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_LONG).show();
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


    private void createBookClassList(String cat_id) {

        //Toast.makeText(FilterActivity.this,""+cat_id,Toast.LENGTH_LONG).show();
        progressDialog.show();
        String json_tag="json_book_class";
        HashMap<String,String> map=new HashMap<>();
        map.put("category_id",cat_id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_BOOK_CLASS_LIST, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("bk_class",response.toString());
                try
                {
                    list_class.clear();
                    progressDialog.dismiss();
                    //Toast.makeText(FilterActivity.this,""+response.toString(),Toast.LENGTH_LONG).show();
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {

                        //JSONObject object=response.getJSONObject("data");



                        //  else if(object.has()) {
                        //Toast.makeText(getActivity(),"a- "+a+"\n b- "+b+"\n c- "+c+"\n date: --"+object.toString(),Toast.LENGTH_LONG).show();

                        JSONArray array=response.getJSONArray("data");
                        for(int i=0; i<array.length();i++)
                        {
                            JSONObject object= (JSONObject) array.get(i);
                            String bk=object.getString("book_class");
                            list_class.add(bk);
                        }
                        filterAdapter=new FilterAdapter(list_class,getActivity(),1);
                        rv_class.setVisibility(View.VISIBLE);
                        rv_class.setAdapter(filterAdapter);
//

                    }
                    else if(status.equals("failed")) {

                        // Toast.makeText(getActivity(),"There are no filter in this category \n "+response.getString("data").toString(),Toast.LENGTH_LONG).show();

                    }
                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);


    }

}
