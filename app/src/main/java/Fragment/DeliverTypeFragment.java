package Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gogrocer.tcc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliverTypeFragment extends Fragment {


    public DeliverTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_deliver_type, container, false );
    }

}
