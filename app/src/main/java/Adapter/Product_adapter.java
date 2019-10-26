package Adapter;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Config.BaseURL;
import Fragment.Details_Fragment;
import Model.ProductVariantModel;
import Model.Product_model;
import gogrocer.tcc.MainActivity;
import gogrocer.tcc.R;
import util.DatabaseCartHandler;

import static android.content.Context.MODE_PRIVATE;


public class Product_adapter extends RecyclerView.Adapter<Product_adapter.MyViewHolder> {
    List<String> image_list;
    // Dialog dialog;
    ListView listView1;
    String atr_id="";
    String atr_product_id="";
    String attribute_name="";
    String attribute_value="";
    String attribute_mrp="";
    ArrayList<ProductVariantModel> variantList;
    ArrayList<ProductVariantModel> attributeList;
    ProductVariantAdapter productVariantAdapter;

    private List<Product_model> modelList;
    private Context context;
    int status=0;
    private DatabaseCartHandler dbcart;
    private DatabaseCartHandler db_cart;
    String language;
    SharedPreferences preferences;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_title, tv_price, tv_reward, tv_total, tv_contetiy ,tv_subcat_mrp ,tv_discount;
        public ImageView iv_logo, iv_plus, iv_minus, iv_remove;
        public RelativeLayout rel_click ,rel_varient;
        public Double reward;
        RelativeLayout rel_no;
        Button tv_add ;

        public MyViewHolder(View view) {
            super(view);

            rel_no =(RelativeLayout)view.findViewById( R.id.rel_no );
            tv_title = (TextView) view.findViewById(R.id.tv_subcat_title);
            tv_price = (TextView) view.findViewById(R.id.tv_subcat_price);
            tv_reward = (TextView) view.findViewById(R.id.tv_reward_point);
            tv_total = (TextView) view.findViewById(R.id.tv_subcat_total);
            tv_discount=(TextView)view.findViewById( R.id.product_discount );
            tv_contetiy = (TextView) view.findViewById(R.id.tv_subcat_contetiy);
            tv_subcat_mrp = (TextView) view.findViewById(R.id.tv_subcat_mrp);
            tv_add = (Button) view.findViewById(R.id.tv_subcat_add);
            iv_logo = (ImageView) view.findViewById(R.id.iv_subcat_img);
            iv_plus = (ImageView) view.findViewById(R.id.iv_subcat_plus);
            iv_minus = (ImageView) view.findViewById(R.id.iv_subcat_minus);
            iv_remove = (ImageView) view.findViewById(R.id.iv_subcat_remove);
            rel_click = (RelativeLayout) view.findViewById(R.id.rel_click);
            iv_remove.setVisibility(View.GONE);
            iv_minus.setOnClickListener(this);
            iv_plus.setOnClickListener(this);
            tv_add.setOnClickListener(this);
            iv_logo.setOnClickListener(this);
    rel_click.setOnClickListener(this);
            CardView cardView = (CardView) view.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
//            int id = view.getId();
//            int position = getAdapterPosition();
//             if (id == R.id.tv_subcat_add) {
//
//
//                HashMap<String, String> map = new HashMap<>();
//
//                 int qty = Integer.valueOf(tv_contetiy.getText().toString());
//                 Double price = Double.parseDouble(modelList.get( position ).getPrice());
//
//                 tv_total.setText(context.getResources().getString(R.string.currency) + price );
//
//                 preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
//                language=preferences.getString("language","");
//
//
//    map.put("product_id", modelList.get(position).getProduct_id());
//    map.put("product_name", modelList.get(position).getProduct_name());
//    map.put("category_id", modelList.get(position).getCategory_id());
//    map.put("product_description", modelList.get(position).getProduct_description());
//    map.put("deal_price", modelList.get(position).getDeal_price());
//    map.put("start_date", modelList.get(position).getStart_date());
//    map.put("start_time", modelList.get(position).getStart_time());
//    map.put("end_date", modelList.get(position).getEnd_date());
//    map.put("end_time", modelList.get(position).getEnd_time());
//    map.put("price", modelList.get(position).getPrice());
//    map.put( "mrp",modelList.get( position ).getMrp() );
//    map.put("product_image", modelList.get(position).getProduct_image());
//    map.put("status", modelList.get(position).getStatus());
//    map.put("in_stock", modelList.get(position).getIn_stock());
//    map.put("unit_value", modelList.get(position).getUnit_value());
//    map.put("unit", modelList.get(position).getUnit());
//    map.put("increament", modelList.get(position).getIncreament());
//    map.put("rewards", modelList.get(position).getRewards());
//    map.put("stock", modelList.get(position).getStock());
//    map.put("title", modelList.get(position).getTitle());
//
//
//                if (!tv_contetiy.getText().toString().equalsIgnoreCase("1")) {
//                    if (dbcart.isInCart(map.get("product_id"))) {
//                        dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
//                        tv_add.setVisibility( View.GONE );
//                        rel_no.setVisibility( View.VISIBLE );
//                      //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
//                    } else {
//                        dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
//                        tv_add.setVisibility( View.GONE );
//                        rel_no.setVisibility( View.VISIBLE );
//                      //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
//                    }
//                } else {
//                  tv_add.setVisibility( View.VISIBLE );
//                  rel_no.setVisibility( View.GONE );
//                }
//                Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
//
//              //  Double price = Double.parseDouble(map.get("price").trim());
//                Double reward = Double.parseDouble(map.get("rewards"));
//                tv_reward.setText(context.getResources().getString(R.string.currency) + reward * items);
//                tv_total.setText(context.getResources().getString(R.string.currency) + price * items);
//                ((MainActivity) context).setCartCounter("" + dbcart.getCartCount());
//                 tv_add.setVisibility( View.GONE );
//                 rel_no.setVisibility( View.VISIBLE );
//                 Toast.makeText( context ,"count" + dbcart.getCartCount(),Toast.LENGTH_LONG ).show();
//
//            }
//           else if (id == R.id.iv_subcat_plus) {
//                int qty = Integer.valueOf(tv_contetiy.getText().toString());
//                qty = qty + 1;
//                tv_contetiy.setText(String.valueOf(qty));
//                 HashMap<String, String> map = new HashMap<>();
//                 preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
//                 language=preferences.getString("language","");
//
//
//                 map.put("product_id", modelList.get(position).getProduct_id());
//                 map.put("product_name", modelList.get(position).getProduct_name());
//                 map.put("category_id", modelList.get(position).getCategory_id());
//                 map.put("product_description", modelList.get(position).getProduct_description());
//                 map.put("deal_price", modelList.get(position).getDeal_price());
//                 map.put("start_date", modelList.get(position).getStart_date());
//                 map.put("start_time", modelList.get(position).getStart_time());
//                 map.put("end_date", modelList.get(position).getEnd_date());
//                 map.put("end_time", modelList.get(position).getEnd_time());
//                 map.put("price", modelList.get(position).getPrice());
//                 map.put("mrp",modelList.get( position ).getMrp());
//                 map.put("product_image", modelList.get(position).getProduct_image());
//                 map.put("status", modelList.get(position).getStatus());
//                 map.put("in_stock", modelList.get(position).getIn_stock());
//                 map.put("unit_value", modelList.get(position).getUnit_value());
//                 map.put("unit", modelList.get(position).getUnit());
//                 map.put("increament", modelList.get(position).getIncreament());
//                 map.put("rewards", modelList.get(position).getRewards());
//                 map.put("stock", modelList.get(position).getStock());
//                 map.put("title", modelList.get(position).getTitle());
//                 if (!tv_contetiy.getText().toString().equalsIgnoreCase("0")) {
//                     if (dbcart.isInCart(map.get("product_id"))) {
//                         dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
//                         tv_add.setVisibility( View.GONE );
//                         rel_no.setVisibility( View.VISIBLE );
//                         //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
//                     } else {
//                         dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
//                         tv_add.setVisibility( View.GONE );
//                         rel_no.setVisibility( View.VISIBLE );
//                         //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
//                     }
//                 } else {
////                    dbcart.removeItemFromCart(map.get("product_id"));
////                    tv_add.setVisibility( View.VISIBLE );
//                 }
//                // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
//               //  Double price = Double.parseDouble(map.get("price").trim());
//                    Double price =Double.parseDouble( modelList.get( position ).getPrice() );
//                 tv_total.setText(context.getResources().getString(R.string.currency) + price * qty);
//
//
//             } else if (id == R.id.iv_subcat_minus) {
//
//                int qty = 0;
//                if (!tv_contetiy.getText().toString().equalsIgnoreCase(""))
//                    qty = Integer.valueOf(tv_contetiy.getText().toString());
//
//
//                if (qty > 0) {
//                    qty = qty - 1;
//                    tv_contetiy.setText(String.valueOf(qty));
//                    HashMap<String, String> map = new HashMap<>();
//                    preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
//                    language=preferences.getString("language","");
//
//
//                    map.put("product_id", modelList.get(position).getProduct_id());
//                    map.put("product_name", modelList.get(position).getProduct_name());
//                    map.put( "cart_id",modelList.get( position ).get )
//                    map.put("category_id", modelList.get(position).getCategory_id());
//                    map.put("product_description", modelList.get(position).getProduct_description());
//                    map.put("deal_price", modelList.get(position).getDeal_price());
//                    map.put("start_date", modelList.get(position).getStart_date());
//                    map.put("start_time", modelList.get(position).getStart_time());
//                    map.put("end_date", modelList.get(position).getEnd_date());
//                    map.put("end_time", modelList.get(position).getEnd_time());
//                    map.put("price", modelList.get(position).getPrice());
//                    map.put("mrp",modelList.get( position ).getMrp());
//                    map.put("product_image", modelList.get(position).getProduct_image());
//                    map.put("status", modelList.get(position).getStatus());
//                    map.put("in_stock", modelList.get(position).getIn_stock());
//                    map.put("unit_value", modelList.get(position).getUnit_value());
//                    map.put("unit", modelList.get(position).getUnit());
//                    map.put("increament", modelList.get(position).getIncreament());
//                    map.put("rewards", modelList.get(position).getRewards());
//                    map.put("stock", modelList.get(position).getStock());
//                    map.put("title", modelList.get(position).getTitle());
//                    if (!tv_contetiy.getText().toString().equalsIgnoreCase("0")) {
//                        if (dbcart.isInCart(map.get("product_id"))) {
//                            dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
//                            tv_add.setVisibility( View.GONE );
//                            rel_no.setVisibility( View.VISIBLE );
//                            //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
//                        } else {
//                            dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
//                            tv_add.setVisibility( View.GONE );
//                            rel_no.setVisibility( View.VISIBLE );
//                            //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
//                        }
//                    } else {
//                   dbcart.removeItemFromCart(map.get("product_id"));
//                   rel_no.setVisibility( View.GONE );
//                    tv_add.setVisibility( View.VISIBLE );
//                    }
//                    // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
//                    //  Double price = Double.parseDouble(map.get("price").trim());
//                    Double price =Double.parseDouble( modelList.get( position ).getPrice() );
//                    tv_total.setText(context.getResources().getString(R.string.currency) + price * qty);
//
//                }
//
//            }
//           else if(id== R.id.rel_click)
//             {
//                 Details_Fragment details_fragment=new Details_Fragment();
//                 AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                 Bundle args = new Bundle();
//
//                 //Intent intent=new Intent(context, Product_details.class);
//                 args.putString("product_id",modelList.get(position).getProduct_id());
//                 args.putString("product_name", modelList.get(position).getProduct_name());
//                 args.putString("category_id",modelList.get(position).getCategory_id());
//                 args.putString("product_description",modelList.get(position).getProduct_description());
//                 args.putString("deal_price",modelList.get(position).getDeal_price());
//                 args.putString("start_date",modelList.get(position).getStart_date());
//                 args.putString("start_time",modelList.get(position).getStart_time());
//                 args.putString("end_date",modelList.get(position).getEnd_date());
//                 args.putString("end_time",modelList.get(position).getEnd_time());
//                 args.putString("price",modelList.get(position).getPrice());
//                 args.putString( "mrp",modelList.get( position ).getMrp() );
//                 args.putString("product_image",modelList.get(position).getProduct_image());
//                 args.putString("status", modelList.get(position).getStatus());
//                 args.putString("in_stock", modelList.get(position).getIn_stock());
//                 args.putString("unit_value", modelList.get(position).getUnit_value());
//                 args.putString("unit", modelList.get(position).getUnit());
//                 args.putString("increament",modelList.get(position).getIncreament());
//                 args.putString("rewards",modelList.get(position).getRewards());
//                 args.putString("stock",modelList.get(position).getStock());
//                 args.putString("title",modelList.get(position).getTitle());
//                 details_fragment.setArguments(args);
//                 FragmentManager fragmentManager=activity.getFragmentManager();
//                 fragmentManager.beginTransaction().replace(R.id.contentPanel,details_fragment)
//                 .addToBackStack(null).commit();
//
//
//             }
//
//
        }
    }

    public Product_adapter(List<Product_model> modelList, Context context) {
        this.modelList = modelList;
        dbcart = new DatabaseCartHandler(context);
    }

    @Override
    public Product_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_rv, parent, false);
        context = parent.getContext();
        return new Product_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Product_adapter.MyViewHolder holder, final int position) {
        Product_model mList = modelList.get( position );


        Glide.with( context )
                .load( BaseURL.IMG_PRODUCT_URL + mList.getProduct_image() )
                .fitCenter()
                .placeholder( R.drawable.icon )
                .crossFade()
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .dontAnimate()
                .into( holder.iv_logo );
        preferences = context.getSharedPreferences( "lan", MODE_PRIVATE );
        language = preferences.getString( "language", "" );
        Double price = Double.valueOf( mList.getPrice() );
        Double qty = Double.parseDouble( (String) holder.tv_contetiy.getText() );
        holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price );
        if (language.contains( "english" )) {
            holder.tv_title.setText( mList.getProduct_name() );
        } else {
            holder.tv_title.setText( mList.getProduct_name() );

        }

        holder.tv_subcat_mrp.setPaintFlags( holder.tv_subcat_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
        holder.tv_reward.setText( mList.getRewards() );
        holder.tv_price.setText( context.getResources().getString( R.string.currency ) + mList.getPrice() );
        holder.tv_total.setText( context.getResources().getString( R.string.currency ) + mList.getPrice() );
        double mrp = Double.parseDouble( mList.getMrp() );
       double diff = mrp-price;
       if(mrp>price) {
           double discount = (diff / mrp) * 100;

           holder.tv_subcat_mrp.setText( context.getResources().getString( R.string.currency ) + mList.getMrp() );
           holder.tv_discount.setText( Math.round( discount )+ "%" );
       }
       else
       {
           holder.tv_subcat_mrp.setVisibility( View.GONE );
           holder.tv_discount.setVisibility( View.GONE );
       }
        if (Integer.valueOf( modelList.get( position ).getStock() ) <= 0) {
            holder.tv_add.setText( R.string.tv_out );
            holder.tv_add.setTextColor( context.getResources().getColor( R.color.black ) );
            holder.tv_add.setBackgroundColor( context.getResources().getColor( R.color.gray ) );
            holder.tv_add.setEnabled( false );
            holder.iv_minus.setEnabled( false );
            holder.iv_plus.setEnabled( false );
        } else if (dbcart.isInCart( mList.getProduct_id() )) {
            holder.tv_add.setVisibility( View.GONE );
            holder.rel_no.setVisibility( View.VISIBLE );
            holder.tv_contetiy.setText( dbcart.getCartItemQty( mList.getProduct_id() ) );
        }
//        else {
//            holder.tv_add.setText(context.getResources().getString(R.string.tv_pro_add));
//        }
        Double items = Double.parseDouble( dbcart.getInCartItemQty( mList.getProduct_id() ) );
        // Double price = Double.parseDouble(mList.getPrice());
        Double reward = Double.parseDouble( mList.getRewards() );
        holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price );
        holder.tv_reward.setText( context.getResources().getString( R.string.currency ) + reward * items );
        holder.tv_subcat_mrp.setPaintFlags( holder.tv_subcat_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        String atr=String.valueOf(mList.getProduct_attribute());
        if(atr.equals("[]"))
        {
            status=1;

            String p=String.valueOf(mList.getPrice());
            String m=String.valueOf(mList.getMrp());
            int mm = Integer.parseInt( m );
            int pp =Integer.parseInt( p );
            holder.tv_price.setText(context.getResources().getString(R.string.currency)+ mList.getPrice());
            if(mm>pp) {
                holder.tv_subcat_mrp.setText( context.getResources().getString( R.string.currency ) + mList.getMrp() );
                int discount = getDiscount( p, m );
                //Toast.makeText(getActivity(),""+atr,Toast.LENGTH_LONG).show();
                holder.tv_discount.setText( "" + discount + "% OFF" );
            }
            else
            {
                holder.tv_subcat_mrp.setVisibility( View.GONE );
                holder.tv_discount.setVisibility( View.GONE );
            }
         //   holder.txtrate.setVisibility(View.VISIBLE);
            holder.rel_variant.setVisibility(View.GONE);
         //   holder.txtrate.setText(mList.getUnit_value()+" "+mList.getUnit());

        }

        else
        {

            status=2;
            attributeList.clear();
            holder.rel_variant.setVisibility(View.VISIBLE);
//            String atr=String.valueOf(mList.getProduct_attribute());
            JSONArray jsonArr = null;
            try {

                jsonArr = new JSONArray(atr);
                for (int i = 0; i < jsonArr.length(); i++)
                {
                    ProductVariantModel model=new ProductVariantModel();
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    String atrid=jsonObj.getString("id");
                    String atrproductid=jsonObj.getString("product_id");
                    String attributename=jsonObj.getString("attribute_name");
                    String attributevalue=jsonObj.getString("attribute_value");
                    String attributemrp=jsonObj.getString("attribute_mrp");


                    model.setId(atrid);
                    model.setProduct_id(atrproductid);
                    model.setAttribute_value(attributevalue);
                    model.setAttribute_name(attributename);
                    model.setAttribute_mrp(attributemrp);

                    attributeList.add(model);

                    //     arrayList.add(new AttributeModel(atr_id,product_id,attribute_name,attribute_value));

                    //Toast.makeText(getActivity(),"id "+atr_id+"\n p_id "+product_id+"\n atr_name "+attribute_name+"\n atr_value "+attribute_value,Toast.LENGTH_LONG).show();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            try
            {




                atr_id=attributeList.get(0).getId();
                atr_product_id=attributeList.get(0).getProduct_id();
                attribute_name=attributeList.get(0).getAttribute_name();
                attribute_value=attributeList.get(0).getAttribute_value();
                attribute_mrp=attributeList.get(0).getAttribute_mrp();



                //     arrayList.add(new AttributeModel(atr_id,product_id,attribute_name,attribute_value));

                //Toast.makeText(getActivity(),"id "+atr_id+"\n p_id "+product_id+"\n atr_name "+attribute_name+"\n atr_value "+attribute_value,Toast.LENGTH_LONG).show();



                String atr_price=String.valueOf(attribute_value);
                String atr_mrp=String.valueOf(attribute_mrp);
                int atr_m =Integer.parseInt( atr_mrp );
                int atr_p =Integer.parseInt( atr_price );
                holder.tv_price.setText("\u20B9"+attribute_value.toString());
                if (atr_m>atr_p) {
                    int atr_dis = getDiscount( atr_price, atr_mrp );
                    holder.discount.setText( "" + atr_dis + "% OFF" );

                    holder.product_mrp.setText( "\u20B9" + attribute_mrp.toString() );
                }
                else
                {
                    holder.discount.setVisibility( View.GONE );
                    holder.product_mrp.setVisibility( View.GONE );
                }
                holder.dialog_txtId.setText(atr_id.toString()+"@"+"0");
                holder.dialog_unit_type.setText("\u20B9"+attribute_value+"/"+attribute_name);
                holder.dialog_txtVar.setText(attribute_value+"@"+attribute_name+"@"+attribute_mrp);
                //  holder.txtTotal.setText("\u20B9"+String.valueOf(list_atr_value.get(0).toString()));

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }


        }


        holder.tv_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product_model mList=modelList.get(position);
                String atr=String.valueOf(modelList.get(position).getProduct_attribute());
                if(atr.equals("[]"))
                {
                    HashMap<String, String> mapProduct = new HashMap<String, String>();
                    String unt=String.valueOf( mList.getUnit_value()+" "+mList.getUnit());
                    mapProduct.put("cart_id", mList.getProduct_id());
                    mapProduct.put("product_id", mList.getProduct_id());
                    mapProduct.put("product_image",mList.getProduct_image());
                    mapProduct.put("cat_id",mList.getCategory_id());
                    mapProduct.put("product_name",mList.getProduct_name());
                    mapProduct.put("price", mList.getPrice());
                    mapProduct.put("unit_price",mList.getPrice());
                    mapProduct.put("unit",unt);
                    mapProduct.put("mrp",mList.getMrp());
                    mapProduct.put("type","p");
                    try {

                        boolean tr = db_cart.setCart(mapProduct, (float) 1 );
                        if (tr == true) {
                            MainActivity mainActivity = new MainActivity();
                            mainActivity.setCartCounter("" + db_cart.getCartCount());

                            //   context.setCartCounter("" + holder.db_cart.getCartCount());
                            Toast.makeText(context, "Added to Cart", Toast.LENGTH_LONG).show();
                            int n= db_cart.getCartCount();
                            updateintent();


                        }
                        else if(tr==false)
                        {
                            Toast.makeText(context,"cart updated",Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    //Toast.makeText(context,"1\n"+status+"\n"+modelList.get(position).getProduct_attribute(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    //ProductVariantModel model=variantList.get(position);

                    String str_id=holder.dialog_txtId.getText().toString();
                    String s=holder.dialog_txtVar.getText().toString();
                    String[] st=s.split("@");
                    String st0=String.valueOf(st[0]);
                    String st1=String.valueOf(st[1]);
                    String st2=String.valueOf(st[2]);
                    String[] str=str_id.split("@");
                    String at_id=String.valueOf(str[0]);
                    int j=Integer.parseInt(String.valueOf(str[1]));
                    //       Toast.makeText(context,""+str[0].toString()+"\n"+str[1].toString(),Toast.LENGTH_LONG).show();
                    HashMap<String, String> mapProduct = new HashMap<String, String>();
                    mapProduct.put("cart_id",at_id);
                    mapProduct.put("product_id", mList.getProduct_id());
                    mapProduct.put("product_image",mList.getProduct_image());
                    mapProduct.put("cat_id",mList.getCategory_id());
                    mapProduct.put("product_name",mList.getProduct_name());
                    mapProduct.put("price", st0);
                    mapProduct.put("unit_price",st0);
                    mapProduct.put("unit",st1);
                    mapProduct.put("mrp",st2);
                    mapProduct.put("type","a");
                    //  Toast.makeText(context,""+attributeList.get(j).getId()+"\n"+mapProduct,Toast.LENGTH_LONG).show();
                    try {

                        boolean tr = dbcart.setCart(mapProduct, (float) 1 );
                        if (tr == true) {
                            MainActivity mainActivity = new MainActivity();
                            mainActivity.setCartCounter("" + dbcart.getCartCount());

                            //   context.setCartCounter("" + holder.db_cart.getCartCount());
                            Toast.makeText(context, "Added to Cart", Toast.LENGTH_LONG).show();
                            int n= dbcart.getCartCount();
                          //  updateintent();


                        }
                        else if(tr==false)
                        {
                            Toast.makeText(context,"cart updated",Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        } );
        holder.iv_plus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.valueOf( holder.tv_contetiy.getText().toString() );
                qty = qty + 1;
                holder.tv_contetiy.setText( String.valueOf( qty ) );
                HashMap<String, String> map = new HashMap<>();
                preferences = context.getSharedPreferences( "lan", MODE_PRIVATE );
                language = preferences.getString( "language", "" );


                map.put( "product_id", modelList.get( position ).getProduct_id() );
                map.put( "product_name", modelList.get( position ).getProduct_name() );
                map.put( "category_id", modelList.get( position ).getCategory_id() );
                map.put( "product_description", modelList.get( position ).getProduct_description() );
                map.put( "deal_price", modelList.get( position ).getDeal_price() );
                map.put( "start_date", modelList.get( position ).getStart_date() );
                map.put( "start_time", modelList.get( position ).getStart_time() );
                map.put( "end_date", modelList.get( position ).getEnd_date() );
                map.put( "end_time", modelList.get( position ).getEnd_time() );
                map.put( "price", modelList.get( position ).getPrice() );
                map.put( "mrp",modelList.get( position ).getMrp() );
                map.put( "product_image", modelList.get( position ).getProduct_image() );
                map.put( "status", modelList.get( position ).getStatus() );
                map.put( "in_stock", modelList.get( position ).getIn_stock() );
                map.put( "unit_value", modelList.get( position ).getUnit_value() );
                map.put( "unit", modelList.get( position ).getUnit() );
                map.put( "increament", modelList.get( position ).getIncreament() );
                map.put( "rewards", modelList.get( position ).getRewards() );
                map.put( "stock", modelList.get( position ).getStock() );
                map.put( "title", modelList.get( position ).getTitle() );
                if (!holder.tv_contetiy.getText().toString().equalsIgnoreCase( "1" )) {
                    if (dbcart.isInCart( map.get( "product_id" ) )) {
                        dbcart.setCart( map, Float.valueOf( holder.tv_contetiy.getText().toString() ) );
                        holder.tv_add.setVisibility( View.GONE );
                        holder.rel_no.setVisibility( View.VISIBLE );
                        //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                    } else {
                        dbcart.setCart( map, Float.valueOf( holder.tv_contetiy.getText().toString() ) );
                        holder.tv_add.setVisibility( View.GONE );
                        holder.rel_no.setVisibility( View.VISIBLE );
                        //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                    }
                } else {
//                    dbcart.removeItemFromCart(map.get("product_id"));
//                    tv_add.setVisibility( View.VISIBLE );
                }
                // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
                //  Double price = Double.parseDouble(map.get("price").trim());
                Double price = Double.parseDouble( modelList.get( position ).getPrice() );
                holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price * qty );

            }
        } );
        holder.iv_minus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = 0;
                if (!holder.tv_contetiy.getText().toString().equalsIgnoreCase( "" ))
                    qty = Integer.valueOf( holder.tv_contetiy.getText().toString() );


                if (qty > 0) {
                    qty = qty - 1;
                    holder.tv_contetiy.setText( String.valueOf( qty ) );
                    HashMap<String, String> map = new HashMap<>();
                    preferences = context.getSharedPreferences( "lan", MODE_PRIVATE );
                    language = preferences.getString( "language", "" );


                    map.put( "product_id", modelList.get( position ).getProduct_id() );
                    map.put( "product_name", modelList.get( position ).getProduct_name() );
                    map.put( "category_id", modelList.get( position ).getCategory_id() );
                    map.put( "product_description", modelList.get( position ).getProduct_description() );
                    map.put( "deal_price", modelList.get( position ).getDeal_price() );
                    map.put( "start_date", modelList.get( position ).getStart_date() );
                    map.put( "start_time", modelList.get( position ).getStart_time() );
                    map.put( "end_date", modelList.get( position ).getEnd_date() );
                    map.put( "end_time", modelList.get( position ).getEnd_time() );
                    map.put( "price", modelList.get( position ).getPrice() );
                    map.put( "mrp",modelList.get( position ).getMrp() );
                    map.put( "product_image", modelList.get( position ).getProduct_image() );
                    map.put( "status", modelList.get( position ).getStatus() );
                    map.put( "in_stock", modelList.get( position ).getIn_stock() );
                    map.put( "unit_value", modelList.get( position ).getUnit_value() );
                    map.put( "unit", modelList.get( position ).getUnit() );
                    map.put( "increament", modelList.get( position ).getIncreament() );
                    map.put( "rewards", modelList.get( position ).getRewards() );
                    map.put( "stock", modelList.get( position ).getStock() );
                    map.put( "title", modelList.get( position ).getTitle() );
                    if (!holder.tv_contetiy.getText().toString().equalsIgnoreCase( "0" )) {
                        if (dbcart.isInCart( map.get( "product_id" ) )) {
                            dbcart.setCart( map, Float.valueOf( holder.tv_contetiy.getText().toString() ) );
                            holder.tv_add.setVisibility( View.GONE );
                            holder.rel_no.setVisibility( View.VISIBLE );
                            //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                        } else {
                            dbcart.setCart( map, Float.valueOf( holder.tv_contetiy.getText().toString() ) );
                            holder.tv_add.setVisibility( View.GONE );
                            holder.rel_no.setVisibility( View.VISIBLE );
                            //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                        }
                    } else {
                        dbcart.removeItemFromCart( map.get( "product_id" ) );
                        holder.rel_no.setVisibility( View.GONE );
                        holder.tv_add.setVisibility( View.VISIBLE );
                    }
                    // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
                    //  Double price = Double.parseDouble(map.get("price").trim());
                    Double price = Double.parseDouble( modelList.get( position ).getPrice() );
                    holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price * qty );

                }
            }
        } );
    }
    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private void showImage(String image) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.product_image_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView iv_image_cancle = (ImageView) dialog.findViewById(R.id.iv_dialog_cancle);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_dialog_img);

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + image)
                .centerCrop()
                .placeholder(R.drawable.icon)
                .crossFade()
                .into(iv_image);

        iv_image_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


}