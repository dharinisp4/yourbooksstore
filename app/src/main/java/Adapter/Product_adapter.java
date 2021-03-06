package Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Config.BaseURL;
import Fragment.Details_Fragment;
import Model.Product_model;
import Module.Module;
import shoparounds.com.LoginActivity;
import shoparounds.com.R;
import util.DatabaseCartHandler;
import util.DatabaseHandlerWishList;
import util.Session_management;

import static android.content.Context.MODE_PRIVATE;


public class Product_adapter extends RecyclerView.Adapter<Product_adapter.MyViewHolder> {

    private List<Product_model> modelList = new ArrayList<>( );
    private Context context;
    private DatabaseCartHandler dbcart;
    private DatabaseHandlerWishList dbWish;
    String language;
    String user_id="";
    Session_management session_management;
    Module module;
    SharedPreferences preferences;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_title, tv_price, tv_reward, tv_total, tv_contetiy ,tv_subcat_mrp ,tv_discount,tv_reward_point;
        public ImageView iv_logo, iv_plus, iv_minus, iv_remove,wish_before,wish_after ,out_of_stock;
        public RelativeLayout rel_click;
        public Double reward;
        RelativeLayout rel_no,rel_stock;
        Button tv_add ;

        public MyViewHolder(View view) {
            super(view);

            rel_no =(RelativeLayout)view.findViewById( R.id.rel_no );
            rel_stock =(RelativeLayout)view.findViewById( R.id.rel_stock );
            tv_title = (TextView) view.findViewById(R.id.tv_subcat_title);
            tv_price = (TextView) view.findViewById(R.id.tv_subcat_price);
            tv_reward = (TextView) view.findViewById(R.id.tv_reward_point);
            tv_total = (TextView) view.findViewById(R.id.tv_subcat_total);
            tv_discount=(TextView)view.findViewById( R.id.product_discount );
            tv_reward_point=(TextView)view.findViewById( R.id.tv_reward_point );
            tv_contetiy = (TextView) view.findViewById(R.id.tv_subcat_contetiy);
            tv_subcat_mrp = (TextView) view.findViewById(R.id.tv_subcat_mrp);
            tv_add = (Button) view.findViewById(R.id.tv_subcat_add);
            iv_logo = (ImageView) view.findViewById(R.id.iv_subcat_img);
            iv_plus = (ImageView) view.findViewById(R.id.iv_subcat_plus);
            iv_minus = (ImageView) view.findViewById(R.id.iv_subcat_minus);
            iv_remove = (ImageView) view.findViewById(R.id.iv_subcat_remove);
            wish_before = (ImageView) view.findViewById(R.id.wish_before);
            wish_after = (ImageView) view.findViewById(R.id.wish_after);
            rel_click = (RelativeLayout) view.findViewById(R.id.rel_click);
            out_of_stock= view.findViewById( R.id.img_out_of_stock );
            session_management=new Session_management(context);
            user_id=session_management.getUserDetails().get(BaseURL.KEY_ID);
            module=new Module();

//            iv_remove.setVisibility(View.GONE);
//            iv_minus.setOnClickListener(this);
//            iv_plus.setOnClickListener(this);
          //  tv_add.setOnClickListener(this);
            // iv_logo.setOnClickListener(this);
            rel_click.setOnClickListener(this);
            CardView cardView = (CardView) view.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = getAdapterPosition();

             if(id== R.id.rel_click)
            {
                int in_stock=Integer.parseInt(modelList.get(position).getIn_stock());
                if(in_stock==0)
                {
                    Toast.makeText(context,"Out Of Stock",Toast.LENGTH_LONG).show();
                }
                else {
                    Details_Fragment details_fragment = new Details_Fragment();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Bundle args = new Bundle();

                    //Intent intent=new Intent(context, Product_details.class);
                    args.putString("product_id", modelList.get(position).getProduct_id());
                    args.putString("product_name", modelList.get(position).getProduct_name());
                    args.putString("category_id", modelList.get(position).getCategory_id());
                    args.putString("product_description", modelList.get(position).getProduct_description());
                    //         args.putString("deal_price",modelList.get(position).getDeal_price());
                    //       args.putString("start_date",modelList.get(position).getStart_date());
                    //     args.putString("start_time",modelList.get(position).getStart_time());
                    //   args.putString("end_date",modelList.get(position).getEnd_date());
                    // args.putString("end_time",modelList.get(position).getEnd_time());
                    args.putString("price", modelList.get(position).getPrice());
                    args.putString("mrp", modelList.get(position).getMrp());
                    args.putString("product_image", modelList.get(position).getProduct_image());
                    args.putString("status", modelList.get(position).getStatus());
                    args.putString("in_stock", modelList.get(position).getIn_stock());
                    args.putString("unit_value", modelList.get(position).getUnit_value());
                    args.putString("unit", modelList.get(position).getUnit());
                    args.putString("increament", modelList.get(position).getIncreament());
                    args.putString("rewards", modelList.get(position).getRewards());
                    args.putString("stock", modelList.get(position).getStock());
                    args.putString("title", modelList.get(position).getTitle());
                    args.putString("seller_id", modelList.get(position).getSeller_id());
                    args.putString("book_class", modelList.get(position).getBook_class());
                    args.putString("language", modelList.get(position).getLanguage());
                    args.putString("subject", modelList.get(position).getSubject());

                    details_fragment.setArguments(args);
                    FragmentManager fragmentManager = activity.getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contentPanel, details_fragment)
                            .addToBackStack(null).commit();

                }
            }


        }
    }

    public Product_adapter(List<Product_model> modelList, Context context) {
        this.modelList = modelList;
        dbcart = new DatabaseCartHandler(context);
        dbWish=new DatabaseHandlerWishList(context);
        module=new Module();
    }

    @Override
    public Product_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_rv, parent, false);
        context = parent.getContext();
        return new Product_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Product_adapter.MyViewHolder holder, final int position) {
        final Product_model mList = modelList.get( position );
        String first_image= module.getFirstImage(mList.getProduct_image(),context);

        Glide.with( context )
                .load( BaseURL.IMG_PRODUCT_URL + first_image )
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

        if(session_management.isLoggedIn())
        {
            if(dbWish.isInWishlist(mList.getProduct_id(),user_id))
            {
                holder.wish_after.setVisibility(View.VISIBLE);
                holder.wish_before.setVisibility(View.GONE);
            }
        }


   String bk_lang="";
        String lang=mList.getLanguage().toString();

            bk_lang=" | "+lang;


        if (language.contains( "english" )) {
            holder.tv_title.setText( mList.getProduct_name() +bk_lang );
        } else {
            holder.tv_title.setText( mList.getProduct_name() +bk_lang);

        }

        int stock=Integer.parseInt(mList.getStock());
        if(stock<=0)
        {
            holder.out_of_stock.setVisibility(View.VISIBLE);
            holder.tv_add.setVisibility( View.INVISIBLE );
            holder.rel_no.setVisibility( View.GONE );

            holder.wish_before.setVisibility( View.GONE );

        }
        else
        {
            holder.out_of_stock.setVisibility( View.GONE );
            holder.tv_add.setVisibility( View.VISIBLE );


        }


        holder.tv_reward_point.setText(""+Double.parseDouble(mList.getRewards()));
      //  holder.tv_reward.setText( mList.getRewards() );
        holder.tv_price.setText( context.getResources().getString( R.string.currency ) + mList.getPrice() );
        holder.tv_total.setText( context.getResources().getString( R.string.currency ) + mList.getPrice() );



        boolean is_inCart=dbcart.isInCart(mList.getProduct_id());
        if (is_inCart)
        {
            if (!(stock<=0))
            {

                holder.tv_add.setVisibility( View.GONE );
                holder.rel_no.setVisibility( View.VISIBLE );
                String qt = dbcart.getCartItemQty( mList.getProduct_id() );
                holder.tv_contetiy.setText( qt );
            }
            else
            {
                holder.tv_add.setVisibility( View.GONE );
            }
        }
        else
        {

        }


        double mrp = Double.parseDouble( mList.getMrp() );
        int discount=getDiscount(mList.getPrice(),mList.getMrp());
        if(mrp>price) {

            holder.tv_subcat_mrp.setPaintFlags( holder.tv_subcat_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
            holder.tv_subcat_mrp.setText( context.getResources().getString( R.string.currency ) + mList.getMrp() );
            holder.tv_discount.setText( discount + "%" );
        }
        else
        {
            holder.tv_subcat_mrp.setVisibility( View.GONE );
            holder.tv_discount.setVisibility( View.GONE );
        }

//        else {
//            holder.tv_add.setText(context.getResources().getString(R.string.tv_pro_add));
//        }
        Double items = Double.parseDouble( dbcart.getInCartItemQty( mList.getProduct_id() ) );
        // Double price = Double.parseDouble(mList.getPrice());
        Double reward = Double.parseDouble( mList.getRewards() );
        holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price );
       // holder.tv_reward.setText( context.getResources().getString( R.string.currency ) + reward * items );

        holder.tv_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(context,"2nd",Toast.LENGTH_LONG).show();
                int in_stock=Integer.parseInt(modelList.get(position).getIn_stock());
                if(in_stock==0)
                {
                    Toast.makeText(context,"Out of Stock",Toast.LENGTH_LONG).show();
                }
                else {
                    HashMap<String, String> map = new HashMap<>();
                    int qty = Integer.valueOf(holder.tv_contetiy.getText().toString());
                    Double price = Double.parseDouble(modelList.get(position).getPrice());

                    holder.tv_total.setText(context.getResources().getString(R.string.currency) + price);

                    preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
                    language = preferences.getString("language", "");


                    String unt=modelList.get(position).getUnit_value()+modelList.get(position).getUnit();
                    Module module=new Module();
                   module.setIntoCart((Activity) context,modelList.get(position).getProduct_id(),modelList.get(position).getProduct_id(),
                            modelList.get(position).getProduct_image(),modelList.get(position).getCategory_id(),modelList.get(position).getProduct_name(),modelList.get(position).getPrice(),modelList.get(position).getProduct_description(),modelList.get(position).getRewards(),
                           modelList.get(position).getPrice(),unt,modelList.get(position).getIncreament(),
                           modelList.get(position).getStock(),modelList.get(position).getTitle(),modelList.get(position).getMrp(),
                           modelList.get(position).getSeller_id(),modelList.get(position).getBook_class(),modelList.get(position).getSubject(),modelList.get(position).getLanguage(),qty);
                    updateintent();
                   holder.tv_add.setVisibility(View.GONE);
                    holder.rel_no.setVisibility( View.VISIBLE );



                }
            }
        } );
        holder.iv_plus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.valueOf(holder.tv_contetiy.getText().toString());
                int stock = Integer.parseInt(modelList.get(position).getStock());

                if (qty == stock) {
                    Toast.makeText(context, "We have only " + stock + " in Stock", Toast.LENGTH_LONG).show();
                } else {

                    qty = qty + 1;
                    holder.tv_contetiy.setText(String.valueOf(qty));

                    preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
                    language = preferences.getString("language", "");


                    String unt = modelList.get(position).getUnit_value() + modelList.get(position).getUnit();
                    double unit_price = Double.parseDouble(dbcart.getUnitPrice(modelList.get(position).getProduct_id()));
                    // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
                    Module module = new Module();
                    module.setIntoCart((Activity) context, modelList.get(position).getProduct_id(), modelList.get(position).getProduct_id(),
                            modelList.get(position).getProduct_image(), modelList.get(position).getCategory_id(), modelList.get(position).getProduct_name(),
                            String.valueOf(qty * unit_price), modelList.get(position).getProduct_description(), modelList.get(position).getRewards()
                            , modelList.get(position).getPrice(), unt, modelList.get(position).getIncreament(), modelList.get(position).getStock()
                            , modelList.get(position).getTitle(), modelList.get(position).getMrp(), modelList.get(position).getSeller_id(), modelList.get(position).getBook_class(), modelList.get(position).getSubject(), modelList.get(position).getLanguage(), qty);
                    updateintent();
                    //      Toast.makeText(context,""+dbcart.getTotalAmount(),Toast.LENGTH_LONG).show();

                    //   Double price = Double.parseDouble( modelList.get( position ).getPrice() );
                    //     holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price * qty );

                }
            }
        } );
        holder.iv_minus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = 1;
                if (!holder.tv_contetiy.getText().toString().equalsIgnoreCase( "" ))
                    qty = Integer.valueOf( holder.tv_contetiy.getText().toString() );


                if (qty > 1) {
                    qty = qty - 1;
                    holder.tv_contetiy.setText( String.valueOf( qty ) );
                    preferences = context.getSharedPreferences( "lan", MODE_PRIVATE );
                    language = preferences.getString( "language", "" );

                    String unt=modelList.get(position).getUnit_value()+modelList.get(position).getUnit();
                    double unit_price=Double.parseDouble(dbcart.getUnitPrice(modelList.get(position).getProduct_id()));
                    // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
                    Module module=new Module();
                    module.setIntoCart((Activity) context,modelList.get(position).getProduct_id(),modelList.get(position).getProduct_id(),
                            modelList.get(position).getProduct_image(),modelList.get(position).getCategory_id(),modelList.get(position).getProduct_name(),
                            String.valueOf(qty*unit_price),modelList.get(position).getProduct_description(),modelList.get(position).getRewards()
                            ,modelList.get(position).getPrice(),unt,modelList.get(position).getIncreament(),modelList.get(position).getStock()
                            ,modelList.get(position).getTitle(),modelList.get(position).getMrp(),modelList.get(position).getSeller_id(),modelList.get(position).getBook_class(),modelList.get(position).getSubject(),modelList.get(position).getLanguage(),qty);
                    updateintent();
                    //Toast.makeText(context,""+dbcart.getTotalAmount(),Toast.LENGTH_LONG).show();
                        holder.tv_add.setVisibility( View.GONE );
                        holder.rel_no.setVisibility( View.VISIBLE );

                    } else {
                        dbcart.removeItemFromCart( modelList.get(position).getProduct_id() );
                    updateintent();
                        holder.rel_no.setVisibility( View.GONE );
                        holder.tv_add.setVisibility( View.VISIBLE );
                    }
                    // Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
                    //  Double price = Double.parseDouble(map.get("price").trim());
                    Double price = Double.parseDouble( modelList.get( position ).getPrice() );
                    holder.tv_total.setText( context.getResources().getString( R.string.currency ) + price * qty );

                }

        } );


        holder.wish_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(session_management.isLoggedIn())
                {
                    holder.wish_before.setVisibility(View.GONE);
                    holder.wish_after.setVisibility(View.VISIBLE);
                    module.setIntoWish((Activity) context,mList.getProduct_id(),
                            mList.getProduct_image(),mList.getCategory_id(),mList.getProduct_name(),
                            mList.getPrice(),mList.getProduct_description(),mList.getIn_stock(),mList.getStatus(),mList.getRewards()
                            ,mList.getUnit_value(),mList.getUnit(),mList.getIncreament(),mList.getStock()
                            ,mList.getTitle(),mList.getMrp(),mList.getSeller_id(),mList.getBook_class(),mList.getSubject(),mList.getLanguage(),user_id);
                    updatewish();
                }
                else
                {
                    Intent i = new Intent(context, LoginActivity.class);
                    context.startActivity(i);
                }

            }
        });

        holder.wish_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.wish_before.setVisibility(View.VISIBLE);
                holder.wish_after.setVisibility(View.GONE);

                dbWish.removeItemFromWishlist(mList.getProduct_id(),user_id);
                updatewish();
            }
        });
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


    public int getDiscount(String price, String mrp)
    {
        double mrp_d=Double.parseDouble(mrp);
        double price_d=Double.parseDouble(price);
        double per=((mrp_d-price_d)/mrp_d)*100;
        double df=Math.round(per);
        int d=(int)df;
        return d;
    }

    private void updateintent() {
        Intent updates = new Intent("Grocery_cart");
        updates.putExtra("type", "cart");
        context.sendBroadcast(updates);
    }
    private  void updatewish()
    {
        Intent updates = new Intent("Grocery_wish");
        updates.putExtra("type", "wish");
        context.sendBroadcast(updates);

    }

    public String getBookLanguage(String lan)
    {
        String lang="";
        List<String> list=new ArrayList<>();
        try
        {
            JSONArray array=new JSONArray(lan);
            for(int i=0; i<array.length();i++)
            {
               String l=array.getString(i).toString();
                list.add(l);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        StringBuilder sb = new StringBuilder();

        // Appends characters one by one
        for (String  ch : list) {
            sb.append(ch);
            sb.append(",");
        }
        lang=sb.toString();
        return lang.substring(0,lang.length()-1);
    }
}