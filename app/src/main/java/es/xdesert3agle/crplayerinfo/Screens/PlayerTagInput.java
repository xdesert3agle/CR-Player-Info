package es.xdesert3agle.crplayerinfo.Screens;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import net.steamcrafted.loadtoast.LoadToast;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.xdesert3agle.crplayerinfo.API_classes.Player;
import es.xdesert3agle.crplayerinfo.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlayerTagInput extends AppCompatActivity {
    @BindView(R.id.etPlayerTag) EditText etPlayerTag;
    @BindView(R.id.btnCheckTag) Button btnCheckTag;
    @BindView(R.id.btnCheckFav) LinearLayout btnCheckFav;

    //@BindView(R.id.tvErrorMsg) TextView tvErrorMsg;

    private String mErrorMsg;
    private Player player;
    private Intent intent;
    private boolean wasPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playertag_input);

        ButterKnife.bind(this);
        //ImgUtil.getMaterialIcon(GoogleMaterial.Icon.gmd_star)

        btnCheckFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayerTagInput.this, "Esto es un Relative Layout con un TextView", Toast.LENGTH_SHORT).show();
            }
        });

        etPlayerTag.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        btnCheckTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                final LoadToast lt = new LoadToast(PlayerTagInput.this)
                        .setText("Buscando...")
                        .setTranslationY(metrics.heightPixels * 5/8)
                        .setProgressColor(Color.RED)
                        .show();

                if (playerExists()){
                    if (!checkedBefore()) {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(getUserURL())
                                .get()
                                .addHeader("auth", getString(R.string.CR_API_KEY))
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lt.error();
                                    }
                                });

                                mErrorMsg = "Clash Royale está en mantenimiento.";
                                updateErrorMsg(mErrorMsg);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lt.success();
                                    }
                                });


                                switch (response.code()){
                                    case 200:
                                        mErrorMsg = ""; // Clean error message so it does not show

                                        // Converts the jsonString the server returned to JsonObject and removes the 'requiredForUpgrade' field on 'cards' array.
                                        JsonObject userInfo = cleanJson(new JsonParser().parse(response.body().string()).getAsJsonObject());

                                        // Parses 'userInfo' JsonObject into a 'Player' object.
                                        player = getPlayerFromJson(userInfo);

                                        intent = new Intent(PlayerTagInput.this, TabContainer.class).putExtra("playerObj", player);
                                        startActivity(intent);
                                        break;

                                    case 401:
                                        mErrorMsg = "Petición no autorizada. Contacta con el administrador de la aplicación.";
                                        break;

                                    case 400:
                                    case 404:
                                        mErrorMsg = "No existe ningún jugador con el ID #" + etPlayerTag.getText().toString() + ".";
                                        break;

                                    case 500:
                                        mErrorMsg = "El servidor no se encuentra disponible temporalmente debido a un problema.";
                                        break;

                                    case 503:
                                        mErrorMsg = "El servidor se encuentra en mantenimiento temporal.";
                                        break;

                                    case 522:
                                        mErrorMsg = "El servidor no se encuentra disponible temporalmente.";
                                        break;
                                }
                                updateErrorMsg(mErrorMsg);
                            }
                        });
                    }
                    else {
                        lt.success();
                        intent = new Intent(PlayerTagInput.this, TabContainer.class).putExtra("playerObj", player);
                        startActivity(intent);
                    }
                }
                else {
                    lt.error();
                    mErrorMsg = "El ID de jugador #" + etPlayerTag.getText().toString() + " no es válido.";
                    updateErrorMsg(mErrorMsg);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isApplicationInBackground()) {
            wasPaused = true;
            Log.d(PlayerTagInput.class.getSimpleName(), "wasPaused = true");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!wasPaused) {
            Log.d(PlayerTagInput.class.getSimpleName(), "onResume y no estaba pausado -> cleanUiElements");
            cleanUIElements();
        } else {
            Log.d(PlayerTagInput.class.getSimpleName(), "onResume y estaba pausado -> wasPaused = false");
            wasPaused = false;
        }
    }

    private boolean isApplicationInBackground() {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            final ComponentName topActivity = tasks.get(0).topActivity;
            return !topActivity.getPackageName().equals(getPackageName());
        }
        return false;
    }

    // Hide keyboard when touching somewhere on screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private boolean checkedBefore(){
        return (player != null && player.getTag().equals(etPlayerTag.getText().toString()));
    }

    private boolean playerExists(){
        return etPlayerTag.getText().toString().matches("^[0289CGJLPQRUVY]{3,10}$");
    }

    // Parses the JsonObject to a Player object.
    private Player getPlayerFromJson(JsonObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(), Player.class);
    }

    private JsonObject cleanJson(JsonObject jsonObject){
        for (int i = 0 ; i < jsonObject.getAsJsonArray("cards").size(); i++){
            jsonObject.getAsJsonArray("cards").get(i).getAsJsonObject().remove("requiredForUpgrade");
        }

        return jsonObject;
    }

    private String getUserURL(){
        return String.format("https://api.royaleapi.com/player/%1$s?keys=tag,name,trophies,rank,clan,chestCycle,cards", etPlayerTag.getText().toString());
    }

    private void cleanUIElements(){
        etPlayerTag.setText("");
        etPlayerTag.clearFocus();
        updateErrorMsg("");
    }

    // Update Ui element (TextView variant)
    private void updateUIElement(final TextView tv, final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(msg);
            }
        });
    }

    // Update UI element (EditText variant)
    private void updateUIElement(final EditText et, final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et.setText(msg);
            }
        });
    }


    public void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void updateErrorMsg(final String errorMsg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //tvErrorMsg.setText(errorMsg);

                AlphaAnimation alphaAnim = new AlphaAnimation(1.0f,0.0f);
                alphaAnim.setStartOffset(3000);
                alphaAnim.setDuration(400);

                alphaAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    public void onAnimationEnd(Animation animation) {
                        updateErrorMsg("");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                //tvErrorMsg.setAnimation(alphaAnim);
            }
        });
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}