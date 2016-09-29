package com.example.jerry_san.tnp_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jerry_san.tnp_app.R;
import com.example.jerry_san.tnp_app.SessionManager;
import com.example.jerry_san.tnp_app.RESTCalls.SyncDatabase;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager sessionManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager=new SessionManager(getApplicationContext());
        SyncDatabase sync = new SyncDatabase(this);
        sync.execute();

        if(sessionManager.isLoggedIn()==false) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        else {

            Log.i("My_tag", "start");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            String title = "Welcome";
            CharSequence cs = title.subSequence(0, title.length());
            drawer.setDrawerTitle(Gravity.LEFT, cs);

            //get session details
            String array[] = sessionManager.getUserDetails();

            String u = array[0];
            String e = array[1];

            TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
            TextView user = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);

            CharSequence username = u.subSequence(0, u.length());
            CharSequence email_id = e.subSequence(0, e.length());

            user.setText(username);
            email.setText(email_id);

        }
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            finish();
        }
    }

    //            super.onBackPressed();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upload_company) {
            Intent intent = new Intent(this, CompanyRegActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_update_company) {
            Intent intent = new Intent(this, UpdateCompanyActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_stats) {
            Intent intent = new Intent(this, CompanyListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send_message) {
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_Notifications) {
            Intent intent = new Intent(this,MessageListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_log_out) {
            sessionManager.logoutUser();
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
