package edu.utem.ftmk.viewelements;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.utem.ftmk.viewelements.databinding.ActivitySearchStudentBinding;

public class SearchStudentActivity extends AppCompatActivity {

    ActivitySearchStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSearch.setOnClickListener(this::fnSearch);
    }

    private void fnSearch(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String strURL = "http://192.168.0.221/RESTAPI/rest_api.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL,
                                                        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(), "Getting some respond here",
                                                                        Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        binding.txtVwStudName2.setText(jsonObject.getString("stud_name"));
                        binding.txtVwStudGender.setText(jsonObject.getString("stud_gender"));
                        binding.txtVwStudNo.setText(jsonObject.getString("stud_no"));
                        binding.txtVwStudState.setText(jsonObject.getString("stud_state"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Unable to fetch student info", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String strStudNo = binding.edtStudID.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put("selectFn", "fnSearchStud");
                params.put("stud_no", strStudNo);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}