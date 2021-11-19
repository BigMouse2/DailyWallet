package com.example.dailywallet.ui.main.adaptater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.dailywallet.MainActivity;
import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.activity.HomeActivity;
import com.example.dailywallet.ui.main.model.WalletModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewWalletName extends ArrayAdapter<WalletModel> {

    private ArrayList<WalletModel> walletModelArrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    // below line is use to inflate the layout for our item of list view.
    public ListViewWalletName(@NonNull Context context, @NonNull List<WalletModel> walletModelList) {
        super(context, 0, walletModelList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView== null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_home_listview,parent,false);
        }
        // after inflating an item of listview item we are getting data from array list inside our modal class.
        WalletModel wallet = getItem(position);

        //Initializing our UI of list with items
        TextView name = listItemView.findViewById(R.id.idTextViewWalletNameHome);

        // after initializing our items we are setting data to our view.
        // below line is use to set data to our text view.
        name.setText(wallet.getName());

        //below line is used to add item click listener for our item listView
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Wallet clicked is : " + wallet.getName(), Toast.LENGTH_SHORT).show();
                int position = getPosition(wallet);
                mListener.onItemClick(position);
            }
        });
        return listItemView ;

    }
}
