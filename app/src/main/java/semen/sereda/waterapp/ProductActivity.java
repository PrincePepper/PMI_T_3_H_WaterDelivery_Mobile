package semen.sereda.waterapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import static semen.sereda.waterapp.MainActivity.productClassArrayList;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private ImageView imageView;
    private EditText editText;
    private Button confirm;
    private String selected;
    private ActionBar actionBar;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editTextNumberDecimal);
        confirm = findViewById(R.id.confirm);

        ImageButton imageButton1 = findViewById(R.id.buttonplus);
        ImageButton imageButton2 = findViewById(R.id.buttonminus);

        confirm.setOnClickListener(ProductActivity.this);
        imageButton1.setOnClickListener(ProductActivity.this);
        imageButton2.setOnClickListener(ProductActivity.this);
        // Получаем экземпляр элемента Spinner
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(ProductActivity.this);
        // Настраиваем адаптер
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.products, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Вызываем адаптер
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = spinner.getSelectedItem().toString();
        switch (selected) {
            case "Water":
                imageView.setImageResource(R.drawable.water);
                break;
            case "Coca-Cola":
                imageView.setImageResource(R.drawable.coca_cola);
                break;
            default:
                imageView.setImageResource(R.drawable.mirinda);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonplus) {
            if (Integer.parseInt(editText.getText().toString()) < 1000000)
                count = Integer.parseInt(editText.getText().toString()) + 1;
            else count = 1000000;
            if (!editText.getText().toString().isEmpty()) {
                editText.setText(String.valueOf(count));
            } else editText.setText(String.valueOf(1));

        } else if (i == R.id.buttonminus) {
            if (Integer.parseInt(editText.getText().toString()) > 1)
                count = Integer.parseInt(editText.getText().toString()) - 1;
            if (!editText.getText().toString().isEmpty()
                    && !editText.getText().toString().equals("0")) {
                editText.setText(String.valueOf(count));
            }
        } else if (i == R.id.confirm) {
            if (!editText.getText().toString().isEmpty() && !editText.getText().toString().equals("0")) {
                if (Integer.parseInt(editText.getText().toString()) > 1000000) {
                    count = 1000000;
                    editText.setText(String.valueOf(count));
                    Toast.makeText(getApplicationContext(), "Превышен лимит", Toast.LENGTH_SHORT).show();
                    return;
                } else count = Integer.parseInt(editText.getText().toString());
                ProductClass product = new ProductClass(selected, count);
                boolean is_found_equal_product = false;
                for (int j = 0; j < productClassArrayList.size(); j++) {
                    if (productClassArrayList.get(j).getName().equals(product.getName())) {
                        int temp_count = productClassArrayList.get(j).getCount() + product.getCount();
                        if (productClassArrayList.get(j).getCount() + product.getCount() >= 1000000)
                            temp_count = 1000000;
                        productClassArrayList.get(j).setCount(temp_count);
                        is_found_equal_product = true;
                        break;
                    }
                }

                if (!is_found_equal_product) {
                    productClassArrayList.add(product);
                }

                actionBar.setDisplayHomeAsUpEnabled(false);
                Toast.makeText(getApplicationContext(), "Товар добавлен", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            } else {
//                Toast.makeText(getApplicationContext(), "Ошибка ввода", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.confirm), "Ошибка ввода", Snackbar.LENGTH_SHORT);
                View view = snackbar.getView();
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                snackbar.show();
            }
        }
    }
}