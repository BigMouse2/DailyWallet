package com.example.dailywallet.ui.main.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.activity.AddReceiptActivity;
import com.example.dailywallet.ui.main.activity.HomeActivity;
import com.example.dailywallet.ui.main.model.WalletModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Wallet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wallet extends Fragment {

    private static final String ARG_WALLET_NAME = "argWalletName";
    private static final String FRAGMENT_BUNDLE_KEY = "com.example.Wallet.FRAGMENT_BUNDLE_KEY";

    private WalletModel walletModel;

    private Button addReceipt;

    public Wallet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param walletModel Parameter2.
     * @return A new instance of fragment Wallet.
     */


    public static Wallet newInstance(WalletModel walletModel){
        Wallet fragment = new Wallet();
        Bundle args = new Bundle();
        args.putParcelable("wallet model", walletModel); //key, value
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            WalletModel wallet = bundle.getParcelable("wallet model"); //key
            wallet.getName();
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

        //Set arguments
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            WalletModel wallet = bundle.getParcelable("wallet model");
            //Pass data
            walletName.setText(wallet.getName());
            StringBuilder sb = new StringBuilder();
            sb.append(wallet.getStartDate()).append(" to ").append(wallet.getEndDate());
            date.setText(sb);
        }

        //Back floating button
        back.setOnClickListener(view -> {
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        });

        //Button Add receipt
        addReceipt.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddReceiptActivity.class);
            startActivity(intent);
        });
        return v;
    }

}