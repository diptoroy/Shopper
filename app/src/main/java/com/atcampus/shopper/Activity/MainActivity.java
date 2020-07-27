package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atcampus.shopper.Fragment.AccountFragment;
import com.atcampus.shopper.Fragment.CartFragment;
import com.atcampus.shopper.Fragment.HomeFragment;
import com.atcampus.shopper.Fragment.OrderFragment;
import com.atcampus.shopper.Fragment.RewardFragment;
import com.atcampus.shopper.Fragment.WishlistFragment;
import com.atcampus.shopper.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.atcampus.shopper.Activity.RegisterActivity.setSignUpFragment;
import static com.atcampus.shopper.R.menu.menu;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Shopper");
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
//        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search){
            return true;
        }else if (id == R.id.main_notification){
            return true;
        }
//        else if (id == R.id.main_cart){
//            return true;
//        }
        return super.onOptionsItemSelected(item);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            getSupportActionBar().setTitle("Shopper");
                            selectedFragment = new HomeFragment();
                            break;
//                        case R.id.navigation_reward:
//                            selectedFragment = new RewardFragment();
//                            break;
                        case R.id.navigation_favorite:
                            getSupportActionBar().setTitle("My Wishlists");
                            selectedFragment = new WishlistFragment();
                            break;
                        case R.id.navigation_orders:
                            getSupportActionBar().setTitle("My Orders");
                            selectedFragment = new OrderFragment();
                            break;
                        case R.id.navigation_cart:

                            final Dialog userAlertDialog = new Dialog(MainActivity.this);
                            userAlertDialog.setContentView(R.layout.user_sign_in_dialog);
                            userAlertDialog.setCancelable(true);
                            userAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            Button signInBtn = userAlertDialog.findViewById(R.id.sign_in_btn);
                            Button signUpBtn = userAlertDialog.findViewById(R.id.sign_up_btn);

                            final Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);

                            signInBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    userAlertDialog.dismiss();
                                    setSignUpFragment = false;
                                    startActivity(registerIntent);
                                }
                            });

                            signUpBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    userAlertDialog.dismiss();
                                    setSignUpFragment = true;
                                    startActivity(registerIntent);
                                }
                            });
                            userAlertDialog.show();
//                            getSupportActionBar().setTitle("My Cart");
//                            invalidateOptionsMenu();
//                            selectedFragment = new CartFragment();
                            break;
                        case R.id.navigation_account:
                            getSupportActionBar().setTitle("My Account");
                            selectedFragment = new AccountFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };
}
