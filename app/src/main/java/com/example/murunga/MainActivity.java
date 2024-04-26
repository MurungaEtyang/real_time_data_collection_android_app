package com.example.murunga;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Dialog insertDataDialog;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ApiService using RetrofitClient
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Initialize the Dialog
        insertDataDialog = new Dialog(this);
        insertDataDialog.setContentView(R.layout.insert_data);

        // Find the "Close" button in the insert_data.xml layout
        Button closeButton = insertDataDialog.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataDialog.dismiss();
            }
        });

        // Find the "UPLOAD" button in the main layout
        Button uploadButton = findViewById(R.id.upload_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from EditText fields inside the dialog
                EditText pestNameEditText = insertDataDialog.findViewById(R.id.pest_name_edittext);
                EditText diseaseEditText = insertDataDialog.findViewById(R.id.disease_caused_edittext);
                EditText descriptionEditText = insertDataDialog.findViewById(R.id.description_edittext);
                String pestName = pestNameEditText.getText().toString();
                String disease = diseaseEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                // Make API call using Retrofit
                Call<Void> call = apiService.submitReport(pestName, disease, description, "");

                // Execute API call asynchronously
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response
                            Toast.makeText(MainActivity.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(MainActivity.this, "Failed to submit report. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        // Handle failure
                        Toast.makeText(MainActivity.this, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                });

                insertDataDialog.dismiss();
            }
        });
    }
}
