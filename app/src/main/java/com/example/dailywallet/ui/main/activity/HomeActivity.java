package com.example.dailywallet.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.activity.CreateWallet;
import com.example.dailywallet.ui.main.model.WalletModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference walletReference = db.collection("Wallet");

    private ListView editWalletList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //correspondance avec les élèments du visuel
        editWalletList = findViewById(R.id.wallet_list);

        editWalletList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityCreateWallet();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        walletReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                //List<WalletModel> walletModelList = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) { //documentSnapshot contain one document
                    WalletModel wallet = documentSnapshot.toObject(WalletModel.class);
                    wallet.setDocumentId(documentSnapshot.getId());
                    //walletModelList.add(wallet);
                    sb.append("Name: " + wallet.getName() + "\n");

                }
             //   textViewData.setText(sb.toString());
            }
        });
    }

    public void openActivityCreateWallet(){
        Intent intent = new Intent(this, CreateWallet.class);
        startActivity(intent);
    }

    public void onSelectedWallet(){

    }
}