package cu.sld.cfg.lacaridadnosune;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialogDown;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_PERMISSION_READING_STATE = 1;
    private static final String RequieredPermission = "";
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        android.support.v4.app.ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        setContentView(R.layout.activity_main);
        pDialogDown=new ProgressDialog(this);


        final WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setSoundEffectsEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);


        webView.addJavascriptInterface(new CopiadoPDF(this), "CopiarPDF");
        webView.addJavascriptInterface(new CambiarPreferencias(this), "Preferencias");
        webView.addJavascriptInterface(this, "Dialogos");
        webView.addJavascriptInterface(new AudioInterface(this), "AndAud");






        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        // cuando se haga  una solicitud  web fuera del app se abre el intent que corresponde

        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && ((url.startsWith("http://"))||(url.startsWith("https://")))) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }
        });




        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);



        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }





        String url = "file:///android_asset/index.html";
        webView.loadUrl(url);
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.v("Permission is granted");
                return true;
            } else {

                //Log.v(TAG,"Permission is revoked");
                android.support.v4.app.ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("amir", "onRequestPermissionsResult: called");
        switch (requestCode) {
            case REQUEST_PERMISSION_READING_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //    Toast.makeText(ImagenActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {

                }
        }

    }

    private void HandlePermission() {
        int permissionCheck = android.support.v4.content.ContextCompat.checkSelfPermission(
                this, RequieredPermission);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this,
                    RequieredPermission)) {
                showExplanationaboutPermission("Permission Needed", "Rationale", RequieredPermission, REQUEST_PERMISSION_READING_STATE);
            } else {
                requestPermission(RequieredPermission, REQUEST_PERMISSION_READING_STATE);
            }
        } else {
            // Toast.makeText(ImagenActivity.this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showExplanationaboutPermission(String title,
                                                String message,
                                                final String permission,
                                                final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        android.support.v4.app.ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }


    @JavascriptInterface
    public void MostrarDialogo(){

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                // Inicia diálogo  de actualizacion del contenido

                // Stuff that updates the UI

                pDialogDown.setMessage("Espere mientras se actualiza el contenido");
                pDialogDown.setCancelable(false);
                pDialogDown.show();

            }
        });



    }

    @JavascriptInterface
    public void MostrarDialogoError(){

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                // Inicia diálogo  de actualizacion del contenido

                // Stuff that updates the UI

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Debe conectarse a la WIFI o DATOS MOVILES para ver contenidos actualizados");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });



    }

    @JavascriptInterface
    public void QuitarDialogo(){

        // Terminar el diálogo de actualización de contenidos

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                // Stuff that updates the UI
                pDialogDown.dismiss();

            }
        });



    }
}
