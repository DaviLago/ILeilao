package com.example.cce_teste11.ileilao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cce_teste11.ileilao.DAO.ProductDao;
import com.example.cce_teste11.ileilao.DAO.UserDao;
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.UserModel;

public class CreateProdActivity extends AppCompatActivity {

    //User Dao
    ProductDao prodDao;
    UserDao userDao;
    ProductModel product;

    EditText name, description, seller_email, seller_password;
    Button register_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prods);

        prodDao = new ProductDao(CreateProdActivity.this);
        userDao = new UserDao(CreateProdActivity.this);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        seller_email = findViewById(R.id.seller_email);
        seller_password = findViewById(R.id.seller_password);

        if(UserModel.getLogged_user().getType().compareTo("participante") == 0){
            seller_email.setVisibility(View.INVISIBLE);
            seller_password.setVisibility(View.INVISIBLE);
        }
        else{
            seller_email.setVisibility(View.VISIBLE);
            seller_password.setVisibility(View.VISIBLE);
        }

        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                product = getProduct();
                if(product != null) {
                    Boolean result = prodDao.insertProduct(product);
                    if (result == false)
                        Toast.makeText(CreateProdActivity.this, "Erro ao inserir novo produto", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(CreateProdActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        prodDao.findAllProduct();

    }

    private ProductModel getProduct(){

        UserModel userModel;
        if(UserModel.getLogged_user().getType().compareTo("participante") == 0)
            userModel = UserModel.getLogged_user();
        else
            userModel = userDao.findUserByEmailAndPassword(seller_email.getText().toString(), seller_password.getText().toString());

        if(userModel == null) {
            Toast.makeText(CreateProdActivity.this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
            return null;
        }
        return new ProductModel(name.getText().toString(),
                description.getText().toString(),
                new Boolean(true),
                userModel);
    }


}
