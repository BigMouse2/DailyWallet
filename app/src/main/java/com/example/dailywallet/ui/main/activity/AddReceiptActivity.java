package com.example.dailywallet.ui.main.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailywallet.MainActivity;
import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.model.ReceiptModel;
import com.example.dailywallet.ui.main.model.WalletModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class AddReceiptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "addReceipt";

    private WalletModel walletModel;

    private String idWalletReference;

    //références à la bdd
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceWallet = db.collection("Wallet");

    private EditText nameReceipt;
    private EditText priceReceipt;
    private EditText notesReceipt;
    private TextView textViewReceiptDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_receipt);

        //récupération du wallet concerné
        Intent intent = getIntent();
        walletModel = intent.getParcelableExtra("Wallet Model");

        //Init front var
        Spinner mySpinner= findViewById(R.id.autocomplete_country);
        FloatingActionButton back = findViewById(R.id.backReceipt);
        nameReceipt = findViewById(R.id.nameReceipt);
        priceReceipt = findViewById(R.id.priceReceipt);
        textViewReceiptDate = findViewById(R.id.dateReceipt);
        notesReceipt = findViewById(R.id.notesReceipt);

        //Spinner
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(AddReceiptActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.countries_array))
        {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0   ) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Add new category"))
                {
                    //Toast.makeText(AddReceiptActivity.this, "dsdsdsdsdsdsdsd!", Toast.LENGTH_SHORT).show();
                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_addcategory, null);

                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        //ajouter sa date grâce à un datePicker
        findViewById(R.id.buttonPickReceiptDate).setOnClickListener(view -> showDatePickerDialog());

        //Back floating button binding
        back.setOnClickListener(view -> openActivityMainActivity());

        //sauvegarder son receipt
        Button button = findViewById(R.id.addReceipt);
        button.setOnClickListener(view -> {
            saveReceipt(); //save our Receipt
        });
   }

   @Override
   public void onStart(){
        super.onStart();
        idWalletReference = getIdWalletReference(walletModel);
   }

   public ReceiptModel saveReceipt(){

        // renseigner les attributs du model
       String name = nameReceipt.getText().toString();
       float price = Float.parseFloat(priceReceipt.getText().toString());
       String note = notesReceipt.getText().toString();
       String date = textViewReceiptDate.getText().toString();


       //appeler le constructeur
       ReceiptModel receipt = new ReceiptModel(name, date, note, price);
       //Add receipt
       collectionReferenceWallet.document(idWalletReference).collection("Receipt List").add(receipt)
               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                   @Override
                   public void onSuccess(DocumentReference documentReference) {
                       Toast.makeText(AddReceiptActivity.this, "Receipt Saved", Toast.LENGTH_SHORT).show();
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(AddReceiptActivity.this, "Error !", Toast.LENGTH_SHORT).show();
                       Log.d(TAG, e.toString());
                   }
               });
               return receipt;
   }


    //ouvrir le datePicker
    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "Day/Month/Year:" +dayOfMonth +"/" + month + "/" +year;
        textViewReceiptDate.setText(date);
    }

 //get selected ID wallet
   public String getIdWalletReference(@NonNull WalletModel walletModel){

        String id = walletModel.getDocumentId();
        return id;
   }

    //aller à la page de son wallet
    public void openActivityMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
    //    intent.putExtra("Wallet Model", walletModel);
        startActivity(intent);
    }
}