package com.example.dailywallet.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.activity.CreateWallet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Wallet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wallet extends Fragment {

    private static final String ARG_WALLET_NAME = "argWalletName";

    private String wallet;


    public Wallet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param wallet Parameter 1.
     * @return A new instance of fragment Wallet.
     */
    // TODO: Rename and change types and number of parameters
    public static Wallet newInstance(String wallet) {
        Wallet fragment = new Wallet();
        Bundle args = new Bundle();
        args.putString(ARG_WALLET_NAME, wallet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            wallet = getArguments().getString(ARG_WALLET_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);
        TextView walletName = v.findViewById(R.id.text_view_data);

        //set arguments
        if (getArguments() != null) {
            wallet = getArguments().getString(ARG_WALLET_NAME);
        }
        walletName.setText(wallet);


        return v;
    }

}