package com.example.cce_teste11.ileilao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cce_teste11.ileilao.DAO.ProductDao;
import com.example.cce_teste11.ileilao.DAO.UserDao;
import com.example.cce_teste11.ileilao.DataBaseSQLite.DataBaseHelper;
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.UserModel;

public class EditProdActivity extends AppCompatActivity {

    //User Dao
    ProductDao prodDao;
    UserDao userDao;
    ProductModel product;

    TextInputLayout password_input, email_input;
    EditText prod_name, prod_description, seller_email, seller_password;
    Button register_btn;
    Intent intent;
    Long prod_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prods);

        prodDao = new ProductDao(EditProdActivity.this);
        userDao = new UserDao(EditProdActivity.this);

        intent = getIntent();

        prod_id = intent.getLongExtra("prod_id", -1);

        prod_name = findViewById(R.id.name);
        prod_name.setText(intent.getStringExtra("prod_name"));

        prod_description = findViewById(R.id.description);
        prod_description.setText(intent.getStringExtra("prod_description"));

        email_input = findViewById(R.id.email_input);
        seller_email = findViewById(R.id.seller_email);
        seller_email.setText(intent.getStringExtra("prod_seller"));

        password_input = findViewById(R.id.password_input);
        seller_password = findViewById(R.id.seller_password);

        if(UserModel.getLogged_user().getType().compareTo("participante") == 0){
            seller_email.setVisibility(View.INVISIBLE);
            seller_password.setVisibility(View.INVISIBLE);
            email_input.setVisibility(View.INVISIBLE);
            password_input.setVisibility(View.INVISIBLE);
        }
        else{
            seller_email.setVisibility(View.VISIBLE);
            seller_password.setVisibility(View.VISIBLE);
            email_input.setVisibility(View.VISIBLE);
            password_input.setVisibility(View.VISIBLE);
        }

        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(prod_name.getText().toString().isEmpty() || prod_description.getText().toString().isEmpty())
                    Toast.makeText(EditProdActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                else {
                    product = getProduct();
                    if (product != null) {
                        Boolean result = prodDao.update(product);
                        if (result == false)
                            Toast.makeText(EditProdActivity.this, "Erro ao inserir novo produto", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("prod_name", product.getProd_name());
                        intent.putExtra("prod_description", product.getProd_description());
                        intent.putExtra("prod_seller", product.getSeller().getEmail());
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

    }

    private ProductModel getProduct(){

        UserModel userModel;
        if(UserModel.getLogged_user().getType().compareTo("participante") == 0)
            userModel = UserModel.getLogged_user();
        else
            userModel = userDao.findUserByEmailAndPassword(seller_email.getText().toString(), seller_password.getText().toString());

        if(userModel == null) {
            Toast.makeText(EditProdActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
            return null;
        }
        return new ProductModel(prod_id, prod_name.getText().toString(),
                prod_description.getText().toString(),
                new Boolean(true),
                userModel);
    }


}
