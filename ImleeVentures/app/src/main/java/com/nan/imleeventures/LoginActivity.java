package com.nan.imleeventures;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    AutoCompleteTextView mEmailView;
    EditText mPasswordView, email_address, mobile_number, password, forgot_email,confirm_password;
    ScrollView mLoginFormView;
    Toolbar toolbar;
    Button reset, signup;
    ImageButton facebook_button, google_button;
    LinearLayout forgot_layout, signin_layout;
    View newuser_layout;
    TextView forgot_text, signup_text, signin_text;
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private static final String DIALOG_ERROR = "dialog_error";
    private boolean mResolvingError = false;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private LoginButton loginButton;
    private SignInButton signInButton;
    private CallbackManager callbackManager;
    private TextView loggedin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        /////////////////////////////           findviewbyids               //////////////////////////

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mobile_number = (EditText) findViewById(R.id.mobile);
        email_address = (EditText) findViewById(R.id.email_address);
        forgot_email = (EditText) findViewById(R.id.forgot_email);
        password = (EditText) findViewById(R.id.signup_password);
        confirm_password = (EditText)findViewById(R.id.signup_cofirmpassword);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        reset = (Button) findViewById(R.id.reset_password_button);
        signup = (Button) findViewById(R.id.signup_button);
        forgot_layout = (LinearLayout) findViewById(R.id.forgot_layout);
        newuser_layout = (LinearLayout) findViewById(R.id.newuser_layout);
        signin_layout = (LinearLayout) findViewById(R.id.email_login_form);
        forgot_text = (TextView) findViewById(R.id.forgot_password_text);
        signin_text = (TextView) findViewById(R.id.signin_text);
        signup_text = (TextView) findViewById(R.id.signup_text);
        facebook_button = (ImageButton) findViewById(R.id.fb_login_button);
        google_button = (ImageButton) findViewById(R.id.google_button);


        mResolvingError = savedInstanceState != null
                && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setHomeButtonEnabled(true);
            //
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        forgot_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_text.setVisibility(View.GONE);
                forgot_layout.setVisibility(View.VISIBLE);
            }
        });
        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin_layout.setVisibility(View.GONE);
                forgot_layout.setVisibility(View.GONE);
                newuser_layout.setVisibility(View.VISIBLE);
            }
        });
        signin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_layout.setVisibility(View.GONE);
                newuser_layout.setVisibility(View.GONE);
                forgot_text.setVisibility(View.VISIBLE);
                signin_layout.setVisibility(View.VISIBLE);

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_layout.setVisibility(View.GONE);
            }
        });
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mLoginFormView = (ScrollView) findViewById(R.id.login_form);


        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmailView.getText().toString();
                final String password1 = mPasswordView.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    email_address.setError("Enter Email!");

                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    mPasswordView.setError("Invalid Address!");

                    return;
                }


                //progressBar.setVisibility(View.VISIBLE);

                //authenticate user

                    mAuth.signInWithEmailAndPassword(email, password1)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    //progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (mPasswordView.length() < 6) {
                                            //mPasswordView.setError("Minimum pass len is 6");
                                            mPasswordView.setError("Password too short, enter minimum 6 characters!");

                                        } else {
                                            Toast.makeText(LoginActivity.this, "Something is wrong,Try again!", Toast.LENGTH_LONG).show();
                                        }
                                    } else {

                                        Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("email",email);
                                        startActivity(intent);
                                    /*Intent intent = new Intent(Ma.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();*/
                                    }
                                }
                            });
                }

        });




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = email_address.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                final String password2 = confirm_password.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    email_address.setError("Enter Email!");
                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    password.setError("Invalid Address!");

                    return;
                }

                if (password.length() < 6) {
                    password.setError("Password too short, enter minimum 6 characters!");
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                //create user
            if (password1.equals(password2)) {
                mAuth.createUserWithEmailAndPassword(email, password1)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                               //
                                // Toast.makeText(LoginActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Authentication failed \n" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(LoginActivity.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    intent.putExtra("email",email);
                                   // intent.putExtra("number",number);
                                    startActivity(intent);

                                    //startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    //finish();
                                }
                            }

                        });

            }
            else {
                Toast.makeText(LoginActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();

            }

            }
        });

        //////////////////////////////          google login               //////////////////////////

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButton.performClick();
                signIn();
            }
        });
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }

            }
        });



        /////////////////////////////           facebook login             //////////////////////////

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getApplicationContext(),
                            user.getEmail(),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(),
                            "not signedin",
                            Toast.LENGTH_SHORT).show();

                }

            }
        };

        facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday");
                                    String name = object.getString("name");
                                    String gender = object.getString("gender");
                                    loggedin.setText(email+" \n "+ birthday+"\n"
                                            + name + "\n" + gender + "\n"
                                    );

                                    mDatabase = FirebaseDatabase.getInstance().getReference();
                                    String mUserId= "1";
                                    mDatabase.child("users").child(mUserId).child("items").push().child("title").setValue((name).toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
                // App code
            }

            @Override
            public void onCancel() {
                // App code
                loggedin.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                loggedin.setText("Login attempt falied.");

            }
        });


    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {

            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
