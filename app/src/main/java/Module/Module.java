package Module;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.ProductVariantModel;

import gogrocer.tcc.MainActivity;
import util.DatabaseCartHandler;

public class Module {


    public static void setIntoCart(Activity activity, String cart_id, String product_id, String product_images, String cat_id, String details_product_name, String details_product_price, String details_product_desc, String details_product_rewards, String details_product_unit_price, String details_product_unit,
                                   String details_product_increament, String details_product_inStock, String details_product_title, String details_product_mrp, String seller_id, float qty)
    {
        DatabaseCartHandler db_cart=new DatabaseCartHandler(activity);
        HashMap<String, String> mapProduct=new HashMap<String, String>();
        mapProduct.put("product_id", product_id);
        mapProduct.put("cart_id", cart_id);
        mapProduct.put("product_image",product_images);
        mapProduct.put("cat_id",cat_id);
        mapProduct.put("product_name",details_product_name);
        mapProduct.put("price", details_product_price);
        mapProduct.put("product_description",details_product_desc);
        mapProduct.put("rewards", details_product_rewards);
        mapProduct.put("unit_price",details_product_unit_price );
        mapProduct.put("unit", details_product_unit);
        mapProduct.put("increament",details_product_increament);
        mapProduct.put("stock",details_product_inStock);
        mapProduct.put("title",details_product_title);
        mapProduct.put("mrp",details_product_mrp);
        mapProduct.put("sid",seller_id);

        try {

            boolean tr = db_cart.setCart(mapProduct, qty);
            if (tr == true) {
                MainActivity mainActivity = new MainActivity();
                mainActivity.setCartCounter("" + db_cart.getCartCount());

                //   context.setCartCounter("" + holder.db_cart.getCartCount());
                Toast.makeText(activity, "Added to Cart" +db_cart.getCartCount(), Toast.LENGTH_LONG).show();
                int n = db_cart.getCartCount();
                updateintent(activity);
          //      txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));

            } else if (tr == false) {
                Toast.makeText(activity, "cart updated", Toast.LENGTH_LONG).show();
               // txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            // Toast.makeText(getActivity(), "" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }



    }



    public static void updateintent(Activity activity) {
        Intent updates = new Intent("Grocery_cart");
        updates.putExtra("type", "update");
        activity.sendBroadcast(updates);
    }




    }

