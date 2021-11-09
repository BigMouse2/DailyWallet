package com.example.dailywallet.ui.main.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailywallet.MainActivity;
import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.model.WalletModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CreateWallet extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "createWallet";

    private static final String KEY_TITLE = "title";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_STARTDATE = "startDate";
    private static final String KEY_ENDATE = "endDate";

    //elements du visuel
    private EditText editTextTitle;
    private EditText editTextAmount;
    private Spinner spinnerSelectedCurrency;
    private TextView textViewStartDate;
    private TextView textViewEndDate;

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
        spinnerSelectedCurrency = findViewById(R.id.spinnerCurrency);
        textViewStartDate = findViewById(R.id.text_view_startDate);
        textViewEndDate = findViewById(R.id.text_view_endDate);

        //choisir sa devise

        //choisir sa date de début
        findViewById(R.id.buttonPickStartDate).setOnClickListener(view -> showStartDatePickerDialog());

        //choisir sa date de fin
        findViewById(R.id.buttonPickEndDate).setOnClickListener(view -> showEndDatePickerDialog());

        //sauvegarder son wallet
        Button button = findViewById(R.id.save);
        button.setOnClickListener(view -> {
            saveWallet();
            openActivityMainActivity(); //aller à la page de son wallet
        });

        //retourner à la page home
        FloatingActionButton back = findViewById(R.id.back);
        back.setOnClickListener(view -> openHomeActivity());
    }

    @Override
    protected void onStart() {
        super.onStart(); //charger les données au lancement de l'activité
 /*       walletReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if( error != null){
                    return;
                }
                //List<WalletModel> walletModelList = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){ //documentSnapshot contain one document
                    WalletModel wallet  = documentSnapshot.toObject(WalletModel.class);
                    wallet.setDocumentId(documentSnapshot.getId());
                    //walletModelList.add(wallet);
                    sb.append("Name: " + wallet.getName() +"\n");

                }
                editTextTitle.setText(sb.toString());
            }
        });
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
        });*/
    }

    //sauvegarder son wallet en bdd
    public void saveWallet(){
        // 1) renseigner chaque attribut du model
        String name = editTextTitle.getText().toString();
        float budgetAmount = Float.parseFloat(editTextAmount.getText().toString());

        //appeler le constructeur
        WalletModel wallet = new WalletModel(name,budgetAmount);

        //ajouter le wallet à la collection + verification
        walletReference.add(wallet)
                .addOnSuccessListener(aVoid -> Toast.makeText(CreateWallet.this,"Wallet Saved",Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(CreateWallet.this, "Error!",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                });
    }

/*    public void loadWallet(View v) {
        walletReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) { //QuerySnapshot contain all documents
                       // List<WalletModel> walletModelList = new ArrayList<>();
                        StringBuilder sb = new StringBuilder();
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){ //documentSnapshot contain one document
                            //pass our data
                            WalletModel wallet  = documentSnapshot.toObject(WalletModel.class);
                          //  walletModelList.add(wallet);
                            wallet.setDocumentId(documentSnapshot.getId());
                            //sb.append(walletModelList.get().getName());
                            sb.append("Name: " + wallet.getName() +"\n");
                            sb.append("Budget Amount: " +wallet.getBudgetAmount());
                            sb.append("\n\n");
                        }
                        textViewData.setText(sb.toString());
                    }
                });
    }*/

    //retourner au menu d'accueil
    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    //aller à la page de son wallet
    public void openActivityMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //choisir sa date de début
    public void showStartDatePickerDialog(){
        DatePickerDialog startDatePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        startDatePickerDialog.show();
    }

    //choisir sa date de fin
    public void showEndDatePickerDialog(){
        DatePickerDialog startDatePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        startDatePickerDialog.show();
    }

    //retourne les dates choisies aux methods DatePicker
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String startDate = "Day/Month/Year:" +dayOfMonth +"/" + month + "/" +year;
        String endDate = "Day/Month/Year:" +dayOfMonth +"/" + month + "/" +year;
        textViewStartDate.setText(startDate);
        textViewEndDate.setText(endDate);
    }
}