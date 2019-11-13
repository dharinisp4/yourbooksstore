package Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.Produccts_images_adapter;

import Adapter.RelatedProductAdapter;
import Config.BaseURL;

import Config.ExpandableSecondTextView;
import Model.Product_model;
import Model.RelatedProductModel;
import Model.SellerModel;
import Module.Module;
import gogrocer.tcc.AppController;
import gogrocer.tcc.LoginActivity;
import gogrocer.tcc.MainActivity;
import gogrocer.tcc.R;

import util.ConnectivityReceiver;
import util.CustomVolleyJsonRequest;
import util.DatabaseCartHandler;
import util.DatabaseHandlerWishList;
import util.Session_management;


public class Details_Fragment extends Fragment {
     Context context;
    Button btn_add ,btn_checkout;
//    ArrayList<ProductVariantModel> variantList;
//    ProductVariantAdapter productVariantAdapter;
private List<Product_model> modelList ;
    private static String TAG = Details_Fragment.class.getSimpleName();
    private RecyclerView rv_cat;
  Module module;
    int index;
    double tot_amt=0;
    ProgressDialog loadingBar;
    double tot=0;
   // RelativeLayout rel_variant;
    SharedPreferences preferences ;
    private List<Product_model> product_modelList = new ArrayList<>();
    private RelatedProductAdapter adapter_product;

    Activity activity;
    //DatabaseHandler dbcart ;
    DatabaseCartHandler db_cart;
  DatabaseHandlerWishList db_wish ;
    //TextView txtColor,txtSize;
    TextView txtPer,sel_info,featuresTitle,tv_rewards;

    ImageView wish_before ,wish_after ;
    int status=0;

    private TextView dialog_unit_type,dialog_txtId,dialog_txtVar;
    String color ,size ;
    Dialog dialog;
    String language ;

    public static ProgressBar progressBar,pgb,pbg1;
    RelativeLayout relativeLayout_spinner,relativeLayout_size,relativeLayout_color ,rel_seller;
    Produccts_images_adapter imagesAdapter;
    String seller_id,cat_id,product_id,product_images,details_product_name,details_product_desc,details_product_inStock,details_product_attribute;
    String details_product_price,details_product_mrp,details_product_unit_value,details_product_unit,details_product_rewards,details_product_increament,details_product_title;
    String details_product_class,details_product_subject,details_product_language;
    String details_product_status , product_startDate , product_EndDate , product_startTime ,product_endTime ,product_dealprice ,prodcut_stock;

    public static ImageView btn,img2;
    private TextView txtrate,txtTotal,txtBack;
    ListView listView,listView1;
   // ArrayList<Product_model> product_modelList;


    List<String> image_list;
    private TextView txtPrice,txtMrp;
    TextView txtName;
    ExpandableSecondTextView txtDesc;
    //Spinner spinner_size,spinner_color;
    RecyclerView recyclerView;
    TabLayout tabLayout;

    CardView cardView;

    private ElegantNumberButton numberButton;

    private Session_management sessionManagement;
    private String details_product_unit_price;

    public Details_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view= inflater.inflate( R.layout.fragment_details_, container, false);
     module=new Module();
        sessionManagement = new Session_management(getActivity());
        sessionManagement.cleardatetime();
        loadingBar=new ProgressDialog(getActivity());
        loadingBar.setMessage("Loading...");
        loadingBar.setCanceledOnTouchOutside(false);
       //    tabLayout =(TabLayout)view.findViewById(R.id.desc_tablayout);
       rv_cat = (RecyclerView) view.findViewById(R.id.related_recycler);
     //    gifImageView=(ImageView) view.findViewById(R.id.gifImageView);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        rv_cat.setLayoutManager(linearLayoutManager1);
      db_wish = new DatabaseHandlerWishList( getActivity() );
        db_cart=new DatabaseCartHandler(getActivity());
        Bundle bundle=getArguments();
       // variantList=new ArrayList<>();

        cat_id=bundle.getString("category_id");
        product_id=bundle.getString("product_id");
        product_images=bundle.getString("product_image");
        details_product_name=bundle.getString("product_name");
        details_product_desc=bundle.getString("product_description");
   //     details_product_color=bundle.getString("product_color");
       details_product_inStock=bundle.getString("in_stock");
       details_product_status = bundle.getString( "status" );
      // product_dealprice= bundle.getString("deal_price");
       //product_startDate= bundle.getString("start_date");
       //product_startTime= bundle.getString("start_time");
      //  product_EndDate=bundle.getString("end_date");
       // product_endTime=bundle.getString("end_time");
        prodcut_stock =bundle.getString( "stock");
      //  details_product_attribute=bundle.getString("product_attribute");

     //   details_product_size=bundle.getString("product_size");
       // details_product_unit_price =bundle.getString("unit_price");
        details_product_price=bundle.getString("price");
        details_product_mrp=bundle.getString("mrp");
        details_product_unit_value=bundle.getString("unit_value");

        details_product_unit=bundle.getString("unit");
        details_product_rewards=bundle.getString("rewards");
        details_product_increament=bundle.getString("increment");
        details_product_title=bundle.getString("title");
        seller_id=bundle.getString("seller_id");
        details_product_class=bundle.getString("book_class");
        details_product_subject=bundle.getString("subject");
        details_product_language=bundle.getString("language");


//         list=new ArrayList<String>();
//         list_atr_name=new ArrayList<String>();
//         list_id=new ArrayList<String>();
//         list_atr_value=new ArrayList<String>();
//         list_atr_mrp=new ArrayList<String>();
         btn_add=(Button)view.findViewById(R.id.btn_add);
      //  dialog_unit_type=(TextView)view.findViewById(R.id.unit_type);
//        dialog_txtId=(TextView)view.findViewById(R.id.txtId);
//        dialog_txtVar=(TextView)view.findViewById(R.id.txtVar);
        btn_checkout=(Button)view.findViewById(R.id.btn_f_Add_to_cart);
       // cardView=(CardView)view.findViewById(R.id.card_view2);
        txtPer=(TextView)view.findViewById(R.id.product_discount);
        tv_rewards=(TextView)view.findViewById(R.id.tv_rewards);
        sel_info=(TextView)view.findViewById(R.id.sel_info);
        featuresTitle=(TextView)view.findViewById(R.id.featuresTitle);
        rel_seller = (RelativeLayout)view.findViewById( R.id.rel_seller );

      //  rel_variant=(RelativeLayout)view.findViewById(R.id.rel_variant);
        btn=(ImageView)view.findViewById(R.id.img_product);
        recyclerView=view.findViewById(R.id.recylerView);
        //   listView=findViewById(R.id.lstView);
            wish_after=(ImageView)view.findViewById(R.id.wish_after );
            wish_before = (ImageView)view.findViewById( R.id.wish_before );

        image_list=new ArrayList<>();
        tv_rewards.setText(""+Double.parseDouble(details_product_rewards));
//        progressBar=(ProgressBar)view.findViewById(R.id.progress_bar);

        //txtColor=(TextView)view.findViewById(R.id.txtColor);
        //txtSize=(TextView)view.findViewById(R.id.txtSize);
        //   Glide.with(this).load(R.raw.basicloader).into(btn);
        txtName=(TextView)view.findViewById(R.id.details_product_name);
        txtDesc=(ExpandableSecondTextView) view.findViewById(R.id.details_product_description);
        txtPrice=(TextView)view.findViewById(R.id.details_product_price);
        txtMrp=(TextView)view.findViewById(R.id.details_product_mrp);

      //  btn_add_to_cart=(Button)view.findViewById(R.id.btnAdd_to_cart);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(details_product_name);
       // txtrate=(TextView)view.findViewById(R.id.product_rate);
        txtTotal=(TextView)view.findViewById(R.id.product_total);
        numberButton=(ElegantNumberButton)view.findViewById(R.id.product_qty);


        txtDesc.setText(details_product_desc);
        txtName.setText(details_product_name);
        txtPrice.setText( "\u20B9"+details_product_price );

        Double mrp = Double.parseDouble( details_product_mrp );
        Double price =Double.parseDouble( details_product_price );
        Double dif = mrp - price;
        if ( mrp >price)
        {
            double discount = (dif/ mrp) * 100;

            txtMrp.setText( "\u20B9" + details_product_mrp );
            txtMrp.setPaintFlags( txtMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
            txtPer.setText( Math.round(discount) + "%"  );
        }
        else
        {
            txtPer.setVisibility( View.GONE );
            txtMrp.setVisibility( View.GONE );
        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        btn_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                float qty = 1;
                String unt=details_product_unit_value+details_product_unit;
                Module module=new Module();
                module.setIntoCart(getActivity(),product_id,product_id,
                        product_images,cat_id,details_product_name,
                        details_product_price,details_product_desc,details_product_rewards
                        ,details_product_price,unt,details_product_increament,prodcut_stock
                        ,details_product_title,details_product_mrp,seller_id,details_product_class,details_product_subject,details_product_language,qty);

               // btn_add.setVisibility( View.GONE );
             //   numberButton.setVisibility( View.VISIBLE );
                numberButton.setNumber("1");
//                ((MainActivity) context).setCartCounter("" + db_cart.getCartCount());
                Toast.makeText( getActivity() ,"count" + db_cart.getCartCount(),Toast.LENGTH_LONG ).show();
            }
        } );
        numberButton.setOnValueChangeListener( new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                if(newValue<=1)
                {

                 //       db_cart.removeItemFromCart(product_id);

                 //   numberButton.setVisibility(View.GONE);
                //    btn_add.setVisibility(View.VISIBLE);
                }
                else {


                    float qty = Float.parseFloat(String.valueOf(newValue));
                    double unit_price=Double.parseDouble(db_cart.getUnitPrice(product_id));

                    String unt=details_product_unit_value+details_product_unit;
                    Module module=new Module();
                    module.setIntoCart(getActivity(),product_id,product_id,
                            product_images,cat_id,details_product_name,
                            String.valueOf(qty*unit_price),details_product_desc,details_product_rewards
                            ,details_product_price,unt,details_product_increament,prodcut_stock
                            ,details_product_title,details_product_mrp,seller_id,details_product_class,details_product_subject,details_product_language,qty);

                }
                }
        } );




        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                           if (ConnectivityReceiver.isConnected()) {
                    makeGetLimiteRequest();
                } else {
                    ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
                }

            }
        });


        wish_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wish_before.setVisibility(View.GONE);
                wish_after.setVisibility(View.VISIBLE);

                module.setIntoWish(getActivity(),product_id,
                        product_images,cat_id,details_product_name,
                        details_product_price,details_product_desc,details_product_inStock,details_product_status,details_product_rewards
                        ,details_product_unit_value,details_product_unit,details_product_increament,prodcut_stock
                        ,details_product_title,details_product_mrp,seller_id,details_product_class,details_product_subject,details_product_language);

            }
        });

        wish_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wish_before.setVisibility(View.VISIBLE);
                wish_after.setVisibility(View.GONE);

                try {

                    db_wish.removeItemFromWishlist(product_id);
                }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }



            }
        });


        return view;
    }

    private void updateintent() {
        Intent updates = new Intent("Grocery_cart");
        updates.putExtra("type", "update");
        getActivity().sendBroadcast(updates);
    }
    @Override
    public void onStart() {
        super.onStart();


      //  txtTotal.setText("\u20B9"+ String.valueOf(db_cart.getTotalAmount()));


            boolean st=db_cart.isInCart(product_id);
            if(st==true)
            {
             btn_add.setText( "Update Cart");
             numberButton.setNumber(db_cart.getCartItemQty(product_id));
          //   numberButton.setVisibility( View.VISIBLE);
            }

            int sell=Integer.parseInt(seller_id);
            if(sell<=0)
            {
           //  featuresTitle.setVisibility(View.GONE);
            rel_seller.setVisibility( View.GONE );
            }
            else
            {
                getSellerData(sell);
            }

            updateData();

            if(db_wish.isInWishlist(product_id))
            {
                wish_after.setVisibility(View.VISIBLE);
                wish_before.setVisibility(View.GONE);
            }
//        else
//        {
//
//        }

        //Toast.makeText(getActivity(),""+cat_id, Toast.LENGTH_LONG).show();
        makeRelatedProductRequest(cat_id);

//       try
//        {
//            image_list.clear();
//            JSONArray array=new JSONArray(product_images );
//           //Toast.makeText(this,""+product_images,Toast.LENGTH_LONG).show();
//            if(product_images.equals(null))
//            {
//                Toast.makeText(getActivity(),"There is no image for this product", Toast.LENGTH_LONG).show();
//            }
//            else
//            {
//                for(int i=0; i<=array.length()-1;i++)
//                {
//                    image_list.add(array.get(i).toString());
//
//                }
//
//            }

         //   Toast.makeText(getActivity(),""+image_list.get(0).toString(),Toast.LENGTH_LONG).show();
            Glide.with(getActivity())
                    .load( BaseURL.IMG_PRODUCT_URL +product_images )
                    .fitCenter()
                    .placeholder(R.drawable.icon)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(btn);

            /*if(details_product_color.equals(null) || details_product_color.equals("null"))
            {
                Toast.makeText(getActivity(),"color :"+ details_product_color,Toast.LENGTH_LONG).show();
            }
            else
            {
                cardView.setVisibility(View.VISIBLE);
            }*/

          //  makeGetProductColorSizeRequest(cat_id,product_id);

           // Toast.makeText(Product_Frag_details.this,""+image_list.toString(),Toast.LENGTH_LONG).show();

//
// imagesAdapter.notifyDataSetChanged();
        // makeGetProductRequest(cat_id,product_id);
       // product_images_adapter=new Product_images_Adapter(Product_Frag_details.this,image_list);
        //imagesAdapter=new Produccts_images_adapter(getActivity(),image_list);
       // progressBar.setVisibility(View.INVISIBLE);
        //recyclerView.setAdapter(imagesAdapter);


    }

    private void getSellerData(int sell) {

        String json_tag="json_seller";
        HashMap<String,String> map=new HashMap<>();
        map.put("user_id",String.valueOf(sell));


        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_SELLER_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    boolean status=response.getBoolean("responce");
                    if(status)
                    {
                        JSONArray array=response.getJSONArray("data");
                        for(int i=0; i<array.length();i++)
                        {
                           JSONObject object=array.getJSONObject(i);
                            SellerModel sellerModel=new SellerModel();
                            sellerModel.setUser_id(object.getString("user_id"));
                            sellerModel.setUser_name(object.getString("user_name"));
                            sellerModel.setUser_email(object.getString("user_email"));
                            sellerModel.setUser_fullname(object.getString("user_fullname"));
                            sel_info.setText("Seller : "+ sellerModel.getUser_fullname()+"\n \n"+ "Seller Owner : "+sellerModel.getUser_name()
                            +"\n \n Email : "+sellerModel.getUser_email());
                        }


                    }
                    else
                    {

                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);

    }


   /* private void makeGetProductColorSizeRequest(String cat_id, String product_id) {

        String tag_json_obj = "json_product_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cat_id", cat_id);
        params.put("product_id", product_id);

        final Object[] arrayObjects=new Object[2];
        final List list1=new ArrayList();
        final List list=new ArrayList();


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.PRODUCT_DETAILS, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //  Log.d(TAG, response.toString());



              /*  try {
                    String status = response.getString("responce");
                    //    Toast.makeText(Product_Frag_details.this,"asssssssssj"+response,Toast.LENGTH_LONG).show();

                    //    Toast.makeText(Product_Frag_details.this,""+status.toString()+"\n ",Toast.LENGTH_LONG).show();

                    if(status.equals("true")) {


                        JSONArray jsonArray = response.getJSONArray("data");

                        JSONObject jsonObject = jsonArray.getJSONObject(0);



                        // String sdf = jsonObject.getString("size");
                        String sdf = jsonObject.getString("color");
                        String size=jsonObject.getString("size");

                        if(!(sdf.equals("")) )
                        {

                            txtColor.setVisibility(View.GONE);



                        }
                        else if( !(sdf.equals("null")))
                        {
                            txtColor.setVisibility(View.GONE);
                        }

                        if(size.equals("") )
                        {

                            txtSize.setVisibility(View.GONE);


                        }
                        else if(!( size.equals("null")))
                        {
                            txtSize.setVisibility(View.GONE);
                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                // VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        // return list;


    }*/

//    private List<String> makeGetProductRequest(String cat_id, String product_id, final ListView listView, final ProgressBar pg, final Dialog dialog) {
//        final List list=new ArrayList();
//        String tag_json_obj = "json_product_req";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("cat_id", cat_id);
//        params.put("product_id", product_id);
//
//
//        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest( Request.Method.POST,
//                BaseURL.PRODUCT_DETAILS, params, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                //  Log.d(TAG, response.toString());
//
//
//
//                try {
//                    String status = response.getString("responce");
//                    //    Toast.makeText(Product_Frag_details.this,"asssssssssj"+response,Toast.LENGTH_LONG).show();
//
//                    //    Toast.makeText(Product_Frag_details.this,""+status.toString()+"\n ",Toast.LENGTH_LONG).show();
//
//                    if(status.equals("true")) {
//
//
//                        JSONArray jsonArray = response.getJSONArray("data");
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//                        // List list1=new ArrayList();
//
//                        String sdf = jsonObject.getString("size");
//                        String sdf1 = jsonObject.getString("color");
//
//                        if (sdf.isEmpty()) {
//                           // txtSize.setVisibility( View.GONE );
//                            pg.setVisibility( View.GONE);
//                            dialog.dismiss();
//                           // Toast.makeText(getActivity(), "There are no other size", Toast.LENGTH_LONG).show();
//                        } else if (sdf.equals("null")) {
//                          //  txtSize.setVisibility( View.GONE );
//                            pg.setVisibility( View.GONE);
//                            dialog.dismiss();
//                           // Toast.makeText(getActivity(), "There are no other size", Toast.LENGTH_LONG).show();
//                        } else {
//
//                            list.add("Select Size");
//                            JSONArray array = new JSONArray(sdf);
//                            for (int i = 0; i < array.length(); i++) {
//
//                                list.add(array.get(i));
//                            }
//                            String str[] = new String[list.size()];
//                            for (int l = 0; l < list.size(); l++) {
//                                str[l] = list.get(l).toString();
//                            }
//
//                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, str);
//
//                            listView.setAdapter(arrayAdapter);
//
//                            pg.setVisibility( View.GONE);
//                        }
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(),"Error"+error.getMessage(), Toast.LENGTH_LONG).show();
//                // VolleyLog.d(TAG, "Error: " + error.getMessage());
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//        return list;
//    }

//    private List<String> makeGetProductColorRequest(String cat_id, String product_id, final ListView listView, final ProgressBar pg, final Dialog dialog) {
//        final List list=new ArrayList();
//        String tag_json_obj = "json_product_req";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("cat_id", cat_id);
//        params.put("product_id", product_id);
//
//
//        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest( Request.Method.POST,
//                BaseURL.PRODUCT_DETAILS, params, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                //  Log.d(TAG, response.toString());
//
//
//
//                try {
//                    String status = response.getString("responce");
//                    //    Toast.makeText(Product_Frag_details.this,"asssssssssj"+response,Toast.LENGTH_LONG).show();
//
//                    //    Toast.makeText(Product_Frag_details.this,""+status.toString()+"\n ",Toast.LENGTH_LONG).show();
//
//                    if(status.equals("true")) {
//
//
//                        JSONArray jsonArray = response.getJSONArray("data");
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//                        // List list1=new ArrayList();
//
//                        // String sdf = jsonObject.getString("size");
//                        String sdf = jsonObject.getString("color");
//
//                        if (sdf.isEmpty()) {
//                            //txtColor.setVisibility( View.GONE );
//                            pg.setVisibility( View.GONE);
//                            dialog.dismiss();
//                            //Toast.makeText(getActivity(), "There are no other color", Toast.LENGTH_LONG).show();
//                        } else if (sdf.equals("null")) {
//                           // txtColor.setVisibility( View.GONE );
//                            pg.setVisibility( View.GONE);
//                            dialog.dismiss();
//                          //  Toast.makeText(getActivity(), "There are no other color", Toast.LENGTH_LONG).show();
//                        } else {
//
//                            list.add("Select color");
//                            JSONArray array = new JSONArray(sdf);
//                            for (int i = 0; i < array.length(); i++) {
//
//                                list.add(array.get(i));
//                            }
//                            String str[] = new String[list.size()];
//                            for (int l = 0; l < list.size(); l++) {
//                                str[l] = list.get(l).toString();
//                            }
//
//                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, str);
//
//                            listView.setAdapter(arrayAdapter);
//
//                            pg.setVisibility( View.GONE);
//                        }
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//             //   Toast.makeText(getContext().getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
//                 VolleyLog.d(TAG, "Error: " + error.getMessage());
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//        // return list;
//        return list;
//    }


//
//
//    private void makeRelatedProductRequest(String cat_id) {
//        loadingBar.show();
//        String tag_json_obj = "json_product_req";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("cat_id", cat_id);
//
//        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest( Request.Method.POST,
//                BaseURL.GET_PRODUCT_URL, params, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("rett" +
//                        "", response.toString());
//
//                try {
//
//                    Boolean status = response.getBoolean("responce");
//
//                    if (status) {
//                        ///         Toast.makeText(getActivity(),""+response.getString("data"),Toast.LENGTH_LONG).show();
//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<List<RelatedProductModel>>() {
//                        }.getType();
//                        product_modelList = gson.fromJson(response.getString("data"), listType);
//                        loadingBar.dismiss();
//                        adapter_product = new RelatedProductAdapter( getActivity(),product_modelList,product_id);
//
//                        rv_cat.setAdapter(adapter_product);
//                        adapter_product.notifyDataSetChanged();
//                        if (getActivity() != null) {
//                            if (product_modelList.isEmpty()) {
//
//                                loadingBar.dismiss();
//                                //  Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                    }
//                } catch (JSONException e) {
//                    loadingBar.dismiss();
//                    //   e.printStackTrace();
//                    String ex=e.getMessage();
//
//
//
//
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                //loadingBar.dismiss();
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    loadingBar.dismiss();
//                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//    }

    public int getDiscount(String price, String mrp)
    {
        double mrp_d= Double.parseDouble(mrp);
        double price_d= Double.parseDouble(price);
        double per=((mrp_d-price_d)/mrp_d)*100;
        double df= Math.round(per);
        int d=(int)df;
        return d;
    }

public boolean checkAttributeStatus(String atr)
{
    boolean sts=false;
    if(atr.equals("[]"))
    {
        sts=false;
    }
    else
    {
        sts=true;
    }
    return sts;
}
    private void makeRelatedProductRequest(String cat_id) {
        loadingBar.show();
        String tag_json_obj = "json_product_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cat_id", cat_id);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.GET_PRODUCT_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("rett" +
                        "", response.toString());

                try {

                    Boolean status = response.getBoolean("responce");

                    if (status) {
                        ///         Toast.makeText(getActivity(),""+response.getString("data"),Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Product_model>>() {
                        }.getType();
                        product_modelList = gson.fromJson(response.getString("data"), listType);
                        loadingBar.dismiss();
                        adapter_product = new RelatedProductAdapter( product_modelList,getActivity(),product_id);

                        rv_cat.setAdapter(adapter_product);
                        adapter_product.notifyDataSetChanged();
                        if (getActivity() != null) {
                            if (product_modelList.isEmpty()) {

                                loadingBar.dismiss();
                                //  Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                } catch (JSONException e) {
                    loadingBar.dismiss();
                    //   e.printStackTrace();
                    String ex=e.getMessage();




                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //loadingBar.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    loadingBar.dismiss();
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }



    private void makeGetLimiteRequest() {

        JsonArrayRequest req = new JsonArrayRequest( BaseURL.GET_LIMITE_SETTING_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        Double total_amount = Double.parseDouble(db_cart.getTotalAmount());


                        try {
                            // Parsing json array response
                            // loop through each json object

                            boolean issmall = false;
                            boolean isbig = false;

                            // arraylist list variable for store data;
                            ArrayList<HashMap<String, String>> listarray = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = (JSONObject) response
                                        .get(i);
                                int value;

                                if (jsonObject.getString("id").equals("1")) {
                                    value = Integer.parseInt(jsonObject.getString("value"));

                                    if (total_amount < value) {
                                        issmall = true;
                                        Toast.makeText(getActivity(), "" + jsonObject.getString("title") + " : " + value, Toast.LENGTH_SHORT).show();
                                    }
                                } else if (jsonObject.getString("id").equals("2")) {
                                    value = Integer.parseInt(jsonObject.getString("value"));

                                    if (total_amount > value) {
                                        isbig = true;
                                        Toast.makeText(getActivity(), "" + jsonObject.getString("title") + " : " + value, Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            if (!issmall && !isbig) {
                                if (sessionManagement.isLoggedIn()) {
                                    Bundle args = new Bundle();
                                    Fragment fm = new Delivery_fragment();
                                    fm.setArguments(args);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                            .addToBackStack(null).commit();
                                } else {
                                    //Toast.makeText(getActivity(), "Please login or regiter.\ncontinue", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(i);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Connection Time out", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    @Override
    public void onPause() {
        super.onPause();
        // unregister reciver
        getActivity().unregisterReceiver(mCart);
    }

    @Override
    public void onResume() {
        super.onResume();
        // register reciver
        getActivity().registerReceiver(mCart, new IntentFilter("Grocery_cart"));
    }


    private BroadcastReceiver mCart = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra("type");

            if (type.contentEquals("update")) {
                updateData();
            }
        }
    };

    public void updateData()
    {
        ((MainActivity) getActivity()).setCartCounter("" + db_cart.getCartCount());
    }


}
