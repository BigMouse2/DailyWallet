package com.example.dailywallet.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailywallet.MainActivity;
import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.model.WalletModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateWallet extends AppCompatActivity {

    private static final String TAG = "createWallet";

    private static final String KEY_TITLE = "title";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_STARTDATE = "startDate";
    private static final String KEY_ENDATE = "endDate";

    //elements du visuel
    private EditText editTextTitle;
    private EditText editTextAmount;
    private EditText editTextCurrency;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference walletReference = db.collection("Wallet");
    private DocumentReference walletDocument = db.document("Wallet/Wallet2");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        //correspondance avec les elements du visuel
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextAmount = findViewById(R.id.edit_text_amount);
        textViewData = findViewById(R.id.text_view_data);
//        textViewCurrency = findViewById(R.id.text_view_currency);
//        textViewStartDate = findViewById(R.id.text_view_startDate);
//        textViewEndDate = findViewById(R.id.endDate);




        Button button = findViewById(R.id.save);
        button.setOnClickListener(view -> {
            saveWallet();
           // openActivityMainActivity();

        });

        FloatingActionButton back = findViewById(R.id.back);
        back.setOnClickListener(view -> openHomeActivity());
    }

    @Override
    protected void onStart() {
        super.onStart();
        walletDocument.addSnapshotListener(this, (documentSnapshot, e) -> {
            if (e != null) {
                Toast.makeText(CreateWallet.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
                return;
            }

            if (documentSnapshot.exists()) {  //pass data into wallet
                WalletModel wallet =documentSnapshot.toObject(WalletModel.class);
                String name =  wallet.getName();
                float budgetAmount = wallet.getBudgetAmount();

                textViewData.setText("Title: " + name + "\n" + "Budget: " + budgetAmount);
            }else{
                textViewData.setText("");
            }
        });
    }

    public void saveWallet(){
        String name = editTextTitle.getText().toString();
        float budgetAmount = Float.parseFloat(editTextAmount.getText().toString());

        WalletModel wallet = new WalletModel(name,budgetAmount);

        walletReference.add(wallet)
                .addOnSuccessListener(aVoid -> Toast.makeText(CreateWallet.this,"Wallet Saved",Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(CreateWallet.this, "Error!",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                });
    }

    public void loadWallet(View v) {
        walletReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) { //QuerySnapshot contain all documents
                     //   List<WalletModel> walletModelList = new ArrayList<>();
                        String data ="";
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){ //documentSnapshot contain one document
                            //pass our data
                            WalletModel wallet  = documentSnapshot.toObject(WalletModel.class);
                        //    walletModelList.add(wallet);
                            String name = wallet.getName();
                            float amount = wallet.getBudgetAmount();

                            data += "Name: " +name + "\nBudget Amount : " +amount + "\n\n";
                        }
                      //  textViewData.setText(walletModelList);
                        textViewData.setText(data);
                    }
                });
 /*               .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        WalletModel wallet =documentSnapshot.toObject(WalletModel.class);
                        String name =  wallet.getName();
                        float budgetAmount = wallet.getBudgetAmount();

                        textViewData.setText("Title: " + name + "\n" + "budget: " + budgetAmount);
                    } else {
                        Toast.makeText(CreateWallet.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(CreateWallet.this, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                });*/
    }

    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    public void openActivityMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}