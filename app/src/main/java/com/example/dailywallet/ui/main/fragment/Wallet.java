package com.example.dailywallet.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.SectionsPagerAdapter;
import com.example.dailywallet.ui.main.activity.AddReceiptActivity;
import com.example.dailywallet.ui.main.activity.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Wallet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wallet extends Fragment {

    private static final String ARG_WALLET_NAME = "argWalletName";

    private String wallet;
    private Button addReceipt;

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

        // Get context from PageAdapter
        Context context = v.getContext();

        //Init button view
        TextView walletName = v.findViewById(R.id.walletName);
        FloatingActionButton back = v.findViewById(R.id.backToHome);
        addReceipt = v.findViewById(R.id.addReceipt);

        //Set arguments
        if (getArguments() != null) {
            wallet = getArguments().getString(ARG_WALLET_NAME);
        }

        //Pass WalletName data
        walletName.setText(wallet);

        //Back floating button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        });

        //Button Add receipt
        addReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddReceiptActivity.class);
                startActivity(intent);
            }
        });

        //End
        return v;
    }

}