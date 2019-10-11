package Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Fragment.Details_Fragment;
import Config.BaseURL;
import Model.Product_model;
import gogrocer.tcc.MainActivity;
import gogrocer.tcc.R;
import util.DatabaseHandler;

import static android.content.Context.MODE_PRIVATE;


public class Product_adapter extends RecyclerView.Adapter<Product_adapter.MyViewHolder> {

    private List<Product_model> modelList = new ArrayList<>( );
    private Context context;
    private Activity activity;
    private DatabaseHandler dbcart;
    String language;
SharedPreferences preferences;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_title, tv_price, tv_reward, tv_total, tv_contetiy ,tv_mrp ;
        public ImageView iv_logo, iv_plus, iv_minus, iv_remove;
        public RelativeLayout rel_click;
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
            tv_contetiy = (TextView) view.findViewById(R.id.tv_subcat_contetiy);
            tv_add = (Button) view.findViewById(R.id.tv_subcat_add);
            tv_mrp =(TextView)view.findViewById( R.id.tv_subcat_mrp );
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
            int id = view.getId();
            int position = getAdapterPosition();
             if (id == R.id.tv_subcat_add) {


                HashMap<String, String> map = new HashMap<>();

                 int qty = Integer.valueOf(tv_contetiy.getText().toString());
                 Double price = Double.parseDouble(modelList.get( position ).getPrice());

                 tv_total.setText(context.getResources().getString(R.string.currency) + price );

                 preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
                language=preferences.getString("language","");


    map.put("product_id", modelList.get(position).getProduct_id());
    map.put("product_name", modelList.get(position).getProduct_name());
    map.put("category_id", modelList.get(position).getCategory_id());
    map.put("product_description", modelList.get(position).getProduct_description());
    map.put("deal_price", modelList.get(position).getDeal_price());
    map.put("start_date", modelList.get(position).getStart_date());
    map.put("start_time", modelList.get(position).getStart_time());
    map.put("end_date", modelList.get(position).getEnd_date());
    map.put("end_time", modelList.get(position).getEnd_time());
    map.put("price", modelList.get(position).getPrice());
    map.put("mrp",modelList.get( position ).getMrp());
    map.put("product_image", modelList.get(position).getProduct_image());
    map.put("status", modelList.get(position).getStatus());
    map.put("in_stock", modelList.get(position).getIn_stock());
    map.put("unit_value", modelList.get(position).getUnit_value());
    map.put("unit", modelList.get(position).getUnit());
    map.put("increament", modelList.get(position).getIncreament());
    map.put("rewards", modelList.get(position).getRewards());
    map.put("stock", modelList.get(position).getStock());
    map.put("title", modelList.get(position).getTitle());


                if (!tv_contetiy.getText().toString().equalsIgnoreCase("1")) {
                    if (dbcart.isInCart(map.get("product_id"))) {
                        dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                        tv_add.setVisibility( View.GONE );
                        rel_no.setVisibility( View.VISIBLE );
                      //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                    } else {
                        dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                        tv_add.setVisibility( View.GONE );
                        rel_no.setVisibility( View.VISIBLE );
                      //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                    }
                } else {
                  tv_add.setVisibility( View.VISIBLE );
                  rel_no.setVisibility( View.GONE );
                }
                Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));

              //  Double price = Double.parseDouble(map.get("price").trim());
                Double reward = Double.parseDouble(map.get("rewards"));
                tv_reward.setText(context.getResources().getString(R.string.currency) + reward * items);
                tv_total.setText(context.getResources().getString(R.string.currency) + price * items);
                ((MainActivity) context).setCartCounter("" + dbcart.getCartCount());
                 tv_add.setVisibility( View.GONE );
                 rel_no.setVisibility( View.VISIBLE );
                 Toast.makeText( context ,"count" + dbcart.getCartCount(),Toast.LENGTH_LONG ).show();

            }
           else if (id == R.id.iv_subcat_plus) {
                int qty = Integer.valueOf(tv_contetiy.getText().toString());
                qty = qty + 1;
                tv_contetiy.setText(String.valueOf(qty));
                 HashMap<String, String> map = new HashMap<>();
                 preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
                 language=preferences.getString("language","");


                 map.put("product_id", modelList.get(position).getProduct_id());
                 map.put("product_name", modelList.get(position).getProduct_name());
                 map.put("category_id", modelList.get(position).getCategory_id());
                 map.put("product_description", modelList.get(position).getProduct_description());
                 map.put("deal_price", modelList.get(position).getDeal_price());
                 map.put("start_date", modelList.get(position).getStart_date());
                 map.put("start_time", modelList.get(position).getStart_time());
                 map.put("end_date", modelList.get(position).getEnd_date());
                 map.put("end_time", modelList.get(position).getEnd_time());
                 map.put("price", modelList.get(position).getPrice());
                 map.put( "mrp",modelList.get( position ).getMrp() );
                 map.put("product_image", modelList.get(position).getProduct_image());
                 map.put("status", modelList.get(position).getStatus());
                 map.put("in_stock", modelList.get(position).getIn_stock());
                 map.put("unit_value", modelList.get(position).getUnit_value());
                 map.put("unit", modelList.get(position).getUnit());
                 map.put("increament", modelList.get(position).getIncreament());
                 map.put("rewards", modelList.get(position).getRewards());
                 map.put("stock", modelList.get(position).getStock());
                 map.put("title", modelList.get(position).getTitle());
                 if (!tv_contetiy.getText().toString().equalsIgnoreCase("0")) {
                     if (dbcart.isInCart(map.get("product_id"))) {
                         dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                         tv_add.setVisibility( View.GONE );
                         rel_no.setVisibility( View.VISIBLE );
                         //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                     } else {
                         dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                         tv_add.setVisibility( View.GONE );
                         rel_no.setVisibility( View.VISIBLE );
                         //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                     }
                 } else {
//                    dbcart.removeItemFromCart(map.get("product_id"));
//                    tv_add.setVisibility( View.VISIBLE );
                 }
                // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
               //  Double price = Double.parseDouble(map.get("price").trim());
                    Double price =Double.parseDouble( modelList.get( position ).getPrice() );
                 tv_total.setText(context.getResources().getString(R.string.currency) + price * qty);


             } else if (id == R.id.iv_subcat_minus) {

                int qty = 0;
                if (!tv_contetiy.getText().toString().equalsIgnoreCase(""))
                    qty = Integer.valueOf(tv_contetiy.getText().toString());


                if (qty > 0) {
                    qty = qty - 1;
                    tv_contetiy.setText(String.valueOf(qty));
                    HashMap<String, String> map = new HashMap<>();
                    preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
                    language=preferences.getString("language","");


                    map.put("product_id", modelList.get(position).getProduct_id());
                    map.put("product_name", modelList.get(position).getProduct_name());
                    map.put("category_id", modelList.get(position).getCategory_id());
                    map.put("product_description", modelList.get(position).getProduct_description());
                    map.put("deal_price", modelList.get(position).getDeal_price());
                    map.put("start_date", modelList.get(position).getStart_date());
                    map.put("start_time", modelList.get(position).getStart_time());
                    map.put("end_date", modelList.get(position).getEnd_date());
                    map.put("end_time", modelList.get(position).getEnd_time());
                    map.put("price", modelList.get(position).getPrice());
                    map.put( "mrp",modelList.get( position ).getMrp() );
                    map.put("product_image", modelList.get(position).getProduct_image());
                    map.put("status", modelList.get(position).getStatus());
                    map.put("in_stock", modelList.get(position).getIn_stock());
                    map.put("unit_value", modelList.get(position).getUnit_value());
                    map.put("unit", modelList.get(position).getUnit());
                    map.put("increament", modelList.get(position).getIncreament());
                    map.put("rewards", modelList.get(position).getRewards());
                    map.put("stock", modelList.get(position).getStock());
                    map.put("title", modelList.get(position).getTitle());
                    if (!tv_contetiy.getText().toString().equalsIgnoreCase("0")) {
                        if (dbcart.isInCart(map.get("product_id"))) {
                            dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                            tv_add.setVisibility( View.GONE );
                            rel_no.setVisibility( View.VISIBLE );
                            //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                        } else {
                            dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                            tv_add.setVisibility( View.GONE );
                            rel_no.setVisibility( View.VISIBLE );
                            //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                        }
                    } else {
                   dbcart.removeItemFromCart(map.get("product_id"));
                   rel_no.setVisibility( View.GONE );
                    tv_add.setVisibility( View.VISIBLE );
                    }
                    // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
                    //  Double price = Double.parseDouble(map.get("price").trim());
                    Double price =Double.parseDouble( modelList.get( position ).getPrice() );
                    tv_total.setText(context.getResources().getString(R.string.currency) + price * qty);

                }

            }
           else if(id==R.id.rel_click)
             {
                 Details_Fragment details_fragment=new Details_Fragment();
                 AppCompatActivity activity = (AppCompatActivity) view.getContext();
                 Bundle args = new Bundle();

                 //Intent intent=new Intent(context, Product_details.class);
                 args.putString("product_id",modelList.get(position).getProduct_id());
                 args.putString("product_name", modelList.get(position).getProduct_name());
                 args.putString("category_id",modelList.get(position).getCategory_id());
                 args.putString("product_description",modelList.get(position).getProduct_description());
                 args.putString("deal_price",modelList.get(position).getDeal_price());
                 args.putString("start_date",modelList.get(position).getStart_date());
                 args.putString("start_time",modelList.get(position).getStart_time());
                 args.putString("end_date",modelList.get(position).getEnd_date());
                 args.putString("end_time",modelList.get(position).getEnd_time());
                 args.putString("price",modelList.get(position).getPrice());
                 args.putString( "mrp",modelList.get( position ).getMrp() );
                 args.putString("product_image",modelList.get(position).getProduct_image());
                 args.putString("status", modelList.get(position).getStatus());
                 args.putString("in_stock", modelList.get(position).getIn_stock());
                 args.putString("unit_value", modelList.get(position).getUnit_value());
                 args.putString("unit", modelList.get(position).getUnit());
                 args.putString("increament",modelList.get(position).getIncreament());
                 args.putString("rewards",modelList.get(position).getRewards());
                 args.putString("stock",modelList.get(position).getStock());
                 args.putString("title",modelList.get(position).getTitle());
                 details_fragment.setArguments(args);
                 FragmentManager fragmentManager=activity.getFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.contentPanel,details_fragment)
                         .addToBackStack(null).commit();


             }


        }
    }

    public Product_adapter(List<Product_model> modelList, Context context) {
        this.modelList = modelList;
        dbcart = new DatabaseHandler(context);
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
                .centerCrop()
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
            holder.tv_title.setText( mList.getProduct_name_arb() );

        }
        holder.tv_reward.setText( mList.getRewards() );
        holder.tv_price.setText(context.getResources().getString( R.string.currency ) + mList.getPrice() );
        holder.tv_mrp.setText(context.getResources().getString(R.string.currency)+mList.getMrp()  );
        holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_mrp.setText( context.getResources().getString( R.string.currency )+mList.getMrp());
        holder.tv_total.setText( context.getResources().getString( R.string.currency ) + mList.getPrice() );
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

        holder.tv_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                int qty = Integer.valueOf( holder.tv_contetiy.getText().toString() );
                Double price = Double.parseDouble( modelList.get( position ).getPrice() );

                holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price );

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
                    holder.tv_add.setVisibility( View.VISIBLE );
                    holder.rel_no.setVisibility( View.GONE );
                }
                Double items = Double.parseDouble( dbcart.getInCartItemQty( map.get( "product_id" ) ) );


                holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price * items );
                ((MainActivity) context).setCartCounter( "" + dbcart.getCartCount() );
                holder.tv_add.setVisibility( View.GONE );
                holder.rel_no.setVisibility( View.VISIBLE );
                Toast.makeText( context, "count" + dbcart.getCartCount(), Toast.LENGTH_LONG ).show();

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

    private void showProductDetail(String image, String title, String description, String detail, final int position, String qty) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product_detail);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        final LinearLayout rel_no =(LinearLayout)dialog.findViewById( R.id.linear_no );
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_product_detail_img);
        final ImageView iv_minus = (ImageView) dialog.findViewById(R.id.iv_subcat_minus);
        final ImageView iv_plus = (ImageView) dialog.findViewById(R.id.iv_subcat_plus);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_product_detail_title);
        TextView tv_detail = (TextView) dialog.findViewById(R.id.tv_product_detail);
        final TextView tv_contetiy = (TextView) dialog.findViewById(R.id.tv_subcat_contetiy);
        final Button tv_add = (Button) dialog.findViewById(R.id.tv_subcat_add);

        tv_title.setText(title);
        tv_detail.setText(detail);
        tv_contetiy.setText(qty);
        tv_detail.setText(description);

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + image)
                .centerCrop()
                .crossFade()
                .into(iv_image);
        if (Integer.valueOf(modelList.get(position).getStock())<=0){
            tv_add.setText(R.string.tv_out);
            tv_add.setTextColor(context.getResources().getColor(R.color.black));
            tv_add.setBackgroundColor(context.getResources().getColor(R.color.gray));
            tv_add.setEnabled(false);
            iv_minus.setEnabled(false);
            iv_plus.setEnabled(false);
        }

        else if (dbcart.isInCart(modelList.get(position).getProduct_id())) {
            tv_add.setVisibility( View.GONE );
            rel_no.setVisibility( View.VISIBLE );
            //            tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
//            tv_contetiy.setText(dbcart.getCartItemQty(modelList.get(position).getProduct_id()));
        }

        else {
            rel_no.setVisibility( View.GONE );
            tv_add.setVisibility( View.VISIBLE );
            tv_add.setText(context.getResources().getString(R.string.tv_pro_add));
        }

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();
                preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
                language=preferences.getString("language","");

                    map.put("product_id", modelList.get(position).getProduct_id());
                    map.put("product_name", modelList.get(position).getProduct_name());
                    map.put("category_id", modelList.get(position).getCategory_id());
                    map.put("product_description", modelList.get(position).getProduct_description());
                    map.put("deal_price", modelList.get(position).getDeal_price());
                    map.put("start_date", modelList.get(position).getStart_date());
                    map.put("start_time", modelList.get(position).getStart_time());
                    map.put("end_date", modelList.get(position).getEnd_date());
                    map.put("end_time", modelList.get(position).getEnd_time());
                    map.put("price", modelList.get(position).getPrice());
                    map.put( "mrp",modelList.get( position ).getMrp() );
                    map.put("product_image", modelList.get(position).getProduct_image());
                    map.put("status", modelList.get(position).getStatus());
                    map.put("in_stock", modelList.get(position).getIn_stock());
                    map.put("unit_value", modelList.get(position).getUnit_value());
                    map.put("unit", modelList.get(position).getUnit());
                    map.put("increament", modelList.get(position).getIncreament());
                    map.put("rewards", modelList.get(position).getRewards());
                    map.put("stock", modelList.get(position).getStock());
                    map.put("title", modelList.get(position).getTitle());


                if (!tv_contetiy.getText().toString().equalsIgnoreCase("1")) {
                    if (dbcart.isInCart(map.get("product_id"))) {
                        dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                        tv_add.setVisibility( View.GONE );
                        rel_no.setVisibility( View.VISIBLE );
                       // tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                    } else {
                        dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                        tv_add.setVisibility( View.GONE );
                        rel_no.setVisibility( View.VISIBLE );
                      //  tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                    }
                } else {
                    dbcart.removeItemFromCart(map.get("product_id"));
                    tv_add.setVisibility(View.VISIBLE );
                    tv_add.setText(context.getResources().getString(R.string.tv_pro_add));
                }

                Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
                Double price = Double.parseDouble(map.get("price"));
                ((MainActivity) context).setCartCounter("" + dbcart.getCartCount());

                notifyItemChanged(position);
                tv_add.setVisibility( View.GONE );
                rel_no.setVisibility( View.VISIBLE );
            }
        });

        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.valueOf(tv_contetiy.getText().toString());
                qty = qty + 1;
                tv_contetiy.setText(String.valueOf(qty));
            }
        });

        iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = 1;
                if (!tv_contetiy.getText().toString().equalsIgnoreCase(""))
                    qty = Integer.valueOf(tv_contetiy.getText().toString());

                if (qty > 1) {
                    qty = qty - 1;
                    tv_contetiy.setText(String.valueOf(qty));
                }
            }
        });

    }

}