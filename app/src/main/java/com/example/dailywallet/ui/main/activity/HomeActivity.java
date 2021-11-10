package com.example.dailywallet.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.activity.CreateWallet;
import com.example.dailywallet.ui.main.fragment.Wallet;
import com.example.dailywallet.ui.main.model.WalletModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity{

    private static final String TAG = "HomeActivity";

    //Se placer dans la collection de wallet
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference walletReference = db.collection("Wallet");

    private ListView editWalletList;
    private Button buttonCreateWallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //correspondance avec les élèments du visuel
        editWalletList = findViewById(R.id.wallet_list);
        buttonCreateWallet = findViewById(R.id.createWallet);

        //choisir son wallet
                // 1) se placer dans la colleciton et vérifier qu'elle existe
        walletReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                List<WalletModel> walletModelList = new ArrayList<>();
                List<String> walletListName = new ArrayList<>();

                // 2) Remplir la liste des wallets
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) { //documentSnapshot contain one document
                    WalletModel wallet = documentSnapshot.toObject(WalletModel.class);
                    wallet.setDocumentId(documentSnapshot.getId());
                    walletModelList.add(wallet);
                    walletListName.add(wallet.getName());

                }
                // 3) afficher les wallets dans la liste
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.activity_home_listview ,R.id.textView,walletListName);
                editWalletList.setAdapter(arrayAdapter);
            }
        });

        //créer son wallet avec le boutton
        buttonCreateWallet.setOnClickListener(view -> openActivityCreateWallet());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void openActivityCreateWallet(){
        Intent intent = new Intent(this, CreateWallet.class);
        startActivity(intent);
    }

    public void onSelectedWallet(){
        Intent intent = new Intent(this, Wallet.class);
        startActivity(intent);
    }
}