package com.example.cce_teste11.ileilao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cce_teste11.ileilao.DAO.UserDao;
import com.example.cce_teste11.ileilao.Model.UserModel;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password;
    Button signUpBtn;

    //Dropdown com os tipos de usuário
    private Spinner dropdown;

    //User DAO
    UserDao user_dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //DAO Instance
        user_dao = new UserDao(SignUpActivity.this);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        signUpBtn = (Button) findViewById(R.id.register_btn);
        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserModel user = getUserModel();
                if(user == null)
                    Toast.makeText(SignUpActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                else {
                    Boolean result = user_dao.insertUser(user);
                    if (result == false)
                        Toast.makeText(SignUpActivity.this, "Erro ao inserir novo usuário", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(SignUpActivity.this, "Novo usuário criado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("user_name", getUserModel().getName());
                        intent.putExtra("user_email", getUserModel().getEmail());
                        intent.putExtra("user_password", getUserModel().getPassword());
                        intent.putExtra("user_type", getUserModel().getType());
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

        //DropDown User Types - Participante ou Leiloeiro
        dropdown = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.user_types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        //-----
    }

    public UserModel getUserModel(){
        if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
            return null;
        return new UserModel(name.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                dropdown.getSelectedItem().toString());
    }
}
