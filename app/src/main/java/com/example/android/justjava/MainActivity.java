package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        EditText nameEditText = findViewById(R.id.name_edit_text_view);
        String name = nameEditText.getText().toString();
        price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessae = createOrderSummary(name, price,hasWhippedCream,hasChocolate) ;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJave order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessae);
        intent.setData(Uri.parse(priceMessae));
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given price on the screen.
     */


    public void increment(View view) {
        if (quantity>=5){
            Toast.makeText(this,"cant order more than 5",Toast.LENGTH_SHORT).show();
            return;
        }

            quantity = quantity + 1;
        displayQuantity(quantity);

    }

    public void decrement(View view) {

        if (quantity == 1){

             Toast.makeText(this,"cant order less than 1 cup", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        displayQuantity(quantity);
    }

   


    /**
     *Method to calculate price
     * @return total price
     */
    private int calculatePrice(boolean wippedcream, boolean chocolate ){
        int basePrice = 5;
        if (wippedcream){
            basePrice = basePrice + 1;
        }
        if (chocolate){
            basePrice = basePrice + 2;
        }

        return (quantity * basePrice);

    }


    /**
     *Method to create order summary
     * @param price,
     * @param name
     *@param addWhippedCream
     * @param addChocolate   @return order summary
     *
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        return getString(R.string.order_summary_name)+ name + "\n"+getString(R.string.quantity) + quantity +

                getString(R.string.order_summary_wipped_cream)+addWhippedCream
        +getString(R.string.order_summary_chocolate)+ addChocolate
                + " \n "+getString(R.string.order_summary_price) + price +
                "\n "+getString(R.string.order_summary_thanku);
    }
}
