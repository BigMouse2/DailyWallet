package com.example.dailywallet.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.activity.AddReceiptActivity;
import com.example.dailywallet.ui.main.activity.HomeActivity;
import com.example.dailywallet.ui.main.model.WalletModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.Nullable;

import ir.drax.constraintaccordionlist.AccordionItem;
import ir.drax.constraintaccordionlist.AccordionList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Wallet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wallet extends Fragment {

    private static final String TAG = "Wallet";

    //références à la bdd
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference walletReference = db.collection("Wallet");
    public DocumentReference walletDocumentRef;

    private Button addReceipt;


    public Wallet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param walletModel Parameter1.
     * @return A new instance of fragment Wallet.
     */

    public static Wallet newInstance(WalletModel walletModel){
        Wallet fragment = new Wallet();
        Bundle args = new Bundle();
        args.putParcelable("Wallet Model", walletModel); //key, value
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            WalletModel wallet = bundle.getParcelable("Wallet Model"); //key
            wallet.getName();
        }
        else{
            // ajouter une condition dans le cas du bundle nul (retour en arrière par exemple)
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        // Get context from PageAdapter
        Context context = v.getContext();

        //Init button view
        FloatingActionButton back = v.findViewById(R.id.backToHome);
        addReceipt = v.findViewById(R.id.addReceipt);

        //Init wallet data
        TextView walletName = v.findViewById(R.id.walletName);
        TextView date = v.findViewById(R.id.textDate);

        //Accordion
        AccordionList accordionList = v.findViewById(R.id.accordion);
        AccordionItem accordionItem = new AccordionItem("toto","contenu");

        /*String AccordionTitle = "Pizza";
        String DateAccordion ="08/11/2021";*/

        //Accordion Style
        accordionList.setHEADER_BG_COLOR(R.color.colorPrimary);
        accordionList.setARROW_ICON(R.drawable.arrow_down_light);

        accordionList
                .push(accordionItem)
                .build();

        //Set arguments
        Bundle bundle = this.getArguments();
        WalletModel walletModel;
        if (bundle != null) {
            //Pass data
            walletModel = bundle.getParcelable("Wallet Model");
            walletName.setText(walletModel.getName());
            StringBuilder sb = new StringBuilder();
            sb.append(walletModel.getStartDate()).append(" to ").append(walletModel.getEndDate());
            date.setText(sb);
        }

        //Back floating button
        back.setOnClickListener(view -> {
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        });

        //Button for Delete Wallet
        FloatingActionButton buttonDeleteWallet = v.findViewById(R.id.floatingActionButton2);
        buttonDeleteWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalletModel wallet = bundle.getParcelable("Wallet Model");
                openDialog();
                deleteWallet(wallet);

                //return to Home Activity
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);

            }
        });

        //Button Add receipt
        addReceipt.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddReceiptActivity.class);
            WalletModel wallet = bundle.getParcelable("Wallet Model");
            intent.putExtra("Wallet Model",wallet);
            startActivity(intent);
        });
        return v;
    }

    public void deleteWallet(WalletModel walletModel){
        walletReference.document(walletModel.getDocumentId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public void openDialog(){

    }
}