package semen.sereda.waterapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProductFragment extends Fragment {
    private ProductClass product;


    public static ProductFragment newInstance(ProductClass product) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putSerializable("Product", product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (ProductClass) getArguments().getSerializable("Product");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        TextView TextName = view.findViewById(R.id.name);
        TextView TextCount = view.findViewById(R.id.count);

        TextName.setText(product.getName());
        TextCount.setText(String.valueOf(product.getCount()));

        ImageView img = view.findViewById(R.id.img);

        Button deleteSelection = view.findViewById(R.id.deleteSelection);
        deleteSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().remove(ProductFragment.this).commit();
                }
                MainActivity.productClassArrayList.remove(product);
            }
        });

        switch (product.getName()) {
            case "Water":
                img.setImageResource(R.drawable.water);
                break;
            case "Coca-Cola":
                img.setImageResource(R.drawable.coca_cola);
                break;
            default:
                img.setImageResource(R.drawable.mirinda);
                break;
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}