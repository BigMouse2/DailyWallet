package com.example.dailywallet.ui.main.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.Calendar;

public class CreateWallet extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "createWallet";

    //elements du visuel
    private EditText editTextTitle;
    private EditText editTextAmount;
    private Spinner spinnerSelectedCurrency;
    private TextView textViewStartDate;
    private TextView textViewEndDate;

    //init dates
    private String startDate ="";
    private String endDate ="";

    //init currency
    private String currency;

    //références à la bdd
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference walletReference = db.collection("Wallet");

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
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(CreateWallet.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.currency))
        {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0   ) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectedCurrency.setAdapter(myAdapter);
        spinnerSelectedCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                currency = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //choisir sa date de début
        findViewById(R.id.buttonPickStartDate).setOnClickListener(view -> showDatePickerDialog(getStartDateListener()));

        //choisir sa date de fin
        findViewById(R.id.buttonPickEndDate).setOnClickListener(view -> showDatePickerDialog(getEndDateListener()));

        //sauvegarder son wallet
        Button button = findViewById(R.id.save);
        button.setOnClickListener(view -> {
            openActivityMainActivity(); //save our wallet and open the FragmentWallet
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
    public WalletModel saveWallet(){
        // 1) renseigner les attributs du model
        String name = editTextTitle.getText().toString();
        float budgetAmount = Float.parseFloat(editTextAmount.getText().toString());
        String startDate = textViewStartDate.getText().toString();
        String endDate = textViewEndDate.getText().toString();

        //appeler le constructeur
        WalletModel wallet = new WalletModel(name, budgetAmount,currency,startDate,endDate);

        //ajouter le wallet à la collection + verification
        walletReference.add(wallet)
                .addOnSuccessListener((DocumentReference aVoid) -> {
                    Toast.makeText(CreateWallet.this, "Wallet Saved", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(CreateWallet.this, "Error!",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                });
        return wallet;
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
        saveWallet();
        WalletModel walletModel = saveWallet();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Wallet Model",walletModel);
        startActivity(intent);
    }

    //ouvrir le datePicker
    public void showDatePickerDialog(DatePickerDialog.OnDateSetListener listener){
        DatePickerDialog startDatePickerDialog = new DatePickerDialog(
                this,
                listener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        startDatePickerDialog.show();
    }

    //retourne les dates choisies au DatePickerDialog
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (startDate.isEmpty()) {
            startDate = +dayOfMonth + "/" + month + "/" + year;
            textViewStartDate.setText(startDate);

            showDatePickerDialog(getStartDateListener());
        } else {
            endDate = +dayOfMonth + "/" + month + "/" + year;
            textViewEndDate.setText((endDate));
        }
    }
    public DatePickerDialog.OnDateSetListener getStartDateListener() {
        return new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
             //   boolean showEndPicker = startDate.isEmpty();
                startDate = +dayOfMonth + "/" + month + "/" + year;
                textViewStartDate.setText(startDate);
            }
        };
    }

    //retourne la date de fin
    public DatePickerDialog.OnDateSetListener getEndDateListener() {
        return new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endDate = +dayOfMonth + "/" + month + "/" + year;
                textViewEndDate.setText(endDate);
            }
        };
    }
}