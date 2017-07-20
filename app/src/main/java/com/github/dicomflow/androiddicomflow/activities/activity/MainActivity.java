package com.github.dicomflow.androiddicomflow.activities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.github.dicomflow.androiddicomflow.R;
import com.github.dicomflow.androiddicomflow.activities.MainActivityFragment;
import com.github.dicomflow.androiddicomflow.activities.certificate.CertificateListActivity;
import com.github.dicomflow.androiddicomflow.activities.certificate.CertificateListFragment;
import com.github.dicomflow.androiddicomflow.activities.login.GoogleSignInActivity2;
import com.github.dicomflow.androiddicomflow.activities.requests.RequestPutsFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class MainActivity extends AppCompatActivity {

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    private GoogleApiClient mGoogleApiClient = null;
    private Handler mHandler;


    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_CERTIFICATES = "CERTIFICATES";
    private static final String TAG_REQUESTS = "REQUESTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mHandler = new Handler();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /** drawer **/

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName(user.getDisplayName()).withEmail(user.getEmail());
//                .withIcon(FontAwesome.Icon.faw_user);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Contatos").withIcon(GoogleMaterial.Icon.gmd_nature_people).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Requisições").withIcon(GoogleMaterial.Icon.gmd_account_box_mail).withIdentifier(2),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Sair").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(0)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            switch ( (short)drawerItem.getIdentifier()) {
                                case 0:
                                    signOut();
                                    startActivity(new Intent(MainActivity.this, GoogleSignInActivity2.class));
                                    finish();
                                    return true;
                                case 1:
                                    loadHomeFragment(drawerItem, TAG_CERTIFICATES);
                                    return true;
                                case 2:
                                    loadHomeFragment(drawerItem, TAG_REQUESTS);
                                    return true;
                                case 10:
                                    // launch new intent instead of loading fragment
                                    startActivity(new Intent(MainActivity.this, CertificateListActivity.class));
                                    result.closeDrawer();
                                    return true;
                            }
                        }

                        return false;
                    }

                    private void signOut() {
                        // Firebase sign out
                        FirebaseAuth.getInstance().signOut();
                        // Google sign out
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                        // Facebook sign out
                        LoginManager.getInstance().logOut();
                    }

                })
                .withSavedInstance(savedInstanceState)
                .build();
    }


    /***
     * Returns respected fragment that user
     * selected from navigation menu
     * @param drawerItem
     */
    private void loadHomeFragment(final IDrawerItem drawerItem, final String CURRENT_TAG) {

        if (drawerItem instanceof Nameable) {
            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(((Nameable) drawerItem).getName().getText(MainActivity.this));
        }

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            result.closeDrawer();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment(drawerItem.getIdentifier());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        result.closeDrawer();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
    }

    private Fragment getHomeFragment(Long item) {
        switch (item.shortValue()) {
            case 1:
                CertificateListFragment homeFragment = new CertificateListFragment();
                return homeFragment;
            case 2:
                RequestPutsFragment photosFragment = new RequestPutsFragment();
                return photosFragment;
            default:
                return new MainActivityFragment();
        }
    }
}
