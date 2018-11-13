package com.example.cce_teste11.ileilao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cce_teste11.ileilao.DAO.UserDao;
import com.example.cce_teste11.ileilao.Model.UserModel;

public class SignInActivity extends AppCompatActivity {

    //User Dao
    UserDao user_dao;

    private EditText email, password;
    private TextView register;
    private Button signInBtn;
    private Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //DAO Instance
        user_dao = new UserDao(SignInActivity.this);

        //Cadastrar Link para a página de cadastro
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });
        //----

        //DropDown User Types - Participante ou Leiloeiro
        dropdown = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignInActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.user_types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        //-----

        signInBtn = (Button) findViewById(R.id.login_btn);
        signInBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();
                String userTypeTxt = dropdown.getSelectedItem().toString();
                Boolean result = user_dao.login(getUserModelToLogin());
                Toast.makeText(SignInActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                if(result){
                    openCreateProdsActivity();
                }
                else {
                    Toast.makeText(SignInActivity.this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void openSignUpActivity(){
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void openCreateProdsActivity(){
        Intent intent = new Intent(SignInActivity.this, CreateProdActivity.class);
        startActivity(intent);
    }

    public UserModel getUserModelToLogin(){
        return new UserModel(email.getText().toString(),
                password.getText().toString(),
                dropdown.getSelectedItem().toString());
    }

}

