package semen.sereda.waterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static ArrayList<ProductClass> productClassArrayList = new ArrayList<>();

    private static long back_pressed;
    private FloatingActionButton fab;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab = findViewById(R.id.fab);
        button = findViewById(R.id.btn_order);

        if (productClassArrayList != null) {
            for (ProductClass i : productClassArrayList) {
                if (savedInstanceState == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.selection, ProductFragment.newInstance(i))
                            .commit();
                }
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrderDescription();
            }
        });
        fab.setOnClickListener(this);
    }

    public void confirmOrderDescription() {
        DialogFragment newFragment = new OrderDescriptionFragment();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (productClassArrayList != null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.selection);
            layout.removeAllViews();
            for (ProductClass i : productClassArrayList) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.selection, ProductFragment.newInstance(i))
                        .commit();

            }
        }
    }

    public void BuyALL() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.selection);
        layout.removeAllViews();
    }

    @Override//выход по времени
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_order),
                    "Нажми еще раз чтобы выйти!", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
        }
        back_pressed = System.currentTimeMillis();
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.fab) {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
        }
    }
}