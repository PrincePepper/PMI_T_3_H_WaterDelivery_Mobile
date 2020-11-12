package semen.sereda.waterapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static ArrayList<ProductClass> productClassArrayList = new ArrayList<>();
    private static final String FILE_NAME = "data.json";

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

    static String[] exportToJSON(Context context, ArrayList<ProductClass> dataList) {
        Gson gson = new Gson();

        String jsonString = gson.toJson(dataList);

        FileOutputStream fileOutputStream = null;

        try {
            byte[] aaa = jsonString.getBytes();
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return new String[]{"1", jsonString};
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new String[]{"0", jsonString};
    }

    public void BuyALL() {
        if (productClassArrayList.size() > 0) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.selection);
            layout.removeAllViews();
            String[] result = exportToJSON(this, productClassArrayList);
            productClassArrayList.clear();
            if (result[0].equals("1")) {
                Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
                DialogFragment newFragment = new SuccesfulDescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("res", result[1]);
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "Dialog");
            } else {
                Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
            }
        }

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