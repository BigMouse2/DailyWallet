package com.example.dailywallet.ui.main.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.dailywallet.MainActivity;
import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.adaptater.ListViewWalletName;
import com.example.dailywallet.ui.main.adaptater.SectionsPagerAdapter;
import com.example.dailywallet.ui.main.model.WalletModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity{

    private static final String TAG = "HomeActivity";

    //Se placer dans la collection de wallet
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference walletReference = db.collection("Wallet");

    //Front object
    private Button buttonCreateWallet;
    private ListView editWalletList;
    private ArrayList<WalletModel> walletModelArrayList;

    //Public data
    public static String nameHomeWallet = "_";
    public static String idHomeWallet = "_";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialiser notre liste de wallet et l'afficher
        editWalletList = findViewById(R.id.wallet_list);
        walletModelArrayList = new ArrayList<>();
        loadDataInListView();


        //correspondance avec les élèments du visuel
        editWalletList = findViewById(R.id.wallet_list);
        buttonCreateWallet = findViewById(R.id.createWallet);



        //Recupère data Onclick Item list
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("wallet_name_list"));


        //créer son wallet avec le bouton
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

    //aller à la page de son wallet
    public void openActivityMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //aller à la page de son wallet
    public Intent passDatatoPageAdapter(){
        Intent intent = new Intent(this, SectionsPagerAdapter.class);
        return intent;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intentBroadcast) {

            // Get extra data included in the Intent
            nameHomeWallet = intentBroadcast.getStringExtra("selected_wallet_name");
            idHomeWallet = intentBroadcast.getStringExtra("selected_wallet_id");

            //OpenMainActivity
            openActivityMainActivity();

        }
    };

    private void loadDataInListView(){
        // below line is use to get data from Firebase firestore using collection in android.
        db.collection("Wallet").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing that list to our object class.
                                WalletModel walletModel = d.toObject(WalletModel.class);

                                // after getting data from Firebase we are storing that data in our array list
                                walletModelArrayList.add(walletModel);
                            }
                            // after that we are passing our array list to our adapter class.
                            ListViewWalletName adapter = new ListViewWalletName(HomeActivity.this, walletModelArrayList);

                            // after passing this array list to our adapter class we are setting our adapter to our list view.
                            editWalletList.setAdapter(adapter);

                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(HomeActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message when we get any error from Firebase.
                Toast.makeText(HomeActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}