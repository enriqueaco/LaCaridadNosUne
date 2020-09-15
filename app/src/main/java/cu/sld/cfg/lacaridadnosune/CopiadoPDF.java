 package cu.sld.cfg.lacaridadnosune;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

 public class CopiadoPDF {

     private Activity activity;

     public CopiadoPDF(Activity activity) {
         this.activity = activity;
     }
     @JavascriptInterface
     public void copyReadAssets(String  mac, String tipo)
     {

         String fichero="";


 //alert(mac);
         /*if (mac=="1"){

             fichero="Elimperioylaislaidependiente.pdf";

         }*/

         File fileDir = new File(Environment.getExternalStorageDirectory(), "/lacaridadnosune");

         fileDir.mkdirs();   // crear la ruta si no existe

         fichero="";
         File ficheronuevo = new File("");




             File file = new File(fileDir, "cancion.m4a");

             ficheronuevo = file;


         AssetManager assetManager = this.activity.getAssets();

         InputStream in = null;
         OutputStream out = null;

         //String strDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ File.separator + "Pdfs";
         //File fileDir = new File(strDir);




         try
         {

             in = assetManager.open(mac);  //leer el archivo de assets
             out = new BufferedOutputStream(new FileOutputStream(ficheronuevo)); //crear el archivo


             copyFile(in, out);
             in.close();
             in = null;
             out.flush();
             out.close();
             out = null;
         } catch (Exception e)
         {
             Log.e("tag", e.getMessage());
         }

         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             Uri apkURI = FileProvider.getUriForFile(this.activity.getApplicationContext(), this.activity.getPackageName() + ".provider", ficheronuevo);
             intent.setDataAndType(apkURI, "audio/*");
             intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
         } else {
             intent.setDataAndType(Uri.fromFile(ficheronuevo), "audio/*");
         }
         this.activity.startActivity(intent);



         /*
         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
         intent.setDataAndType(Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "Pdfs" + "/BREKENRIDG.pdf"), "application/pdf");
         startActivity(intent);*/
     }

     private void copyFile(InputStream in, OutputStream out) throws IOException
     {
         byte[] buffer = new byte[1024];
         int read;
         while ((read = in.read(buffer)) != -1)
         {
             out.write(buffer, 0, read);
         }
     }

 }
