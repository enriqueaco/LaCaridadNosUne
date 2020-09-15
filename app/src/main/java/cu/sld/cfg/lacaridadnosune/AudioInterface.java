package cu.sld.cfg.lacaridadnosune;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.webkit.JavascriptInterface;

import java.io.IOException;


public class AudioInterface {
    Context mContext;
    MediaPlayer mp;




    AudioInterface(Context c) {
        mContext = c;
        mp = new MediaPlayer();

    }





    //Play an audio file from the webpage
    @JavascriptInterface
    public void playAudio(String aud) { //String aud - file name passed
        //from the JavaScript function


        try {
            AssetFileDescriptor fileDescriptor =
                    mContext.getAssets().openFd(aud);
            //mp = new MediaPlayer();





            mp.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            fileDescriptor.close();

            /*mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepareAsync();*/
            //mp.start();


              mp.prepare();
              mp.start();


        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Play an audio file from the webpage
    @JavascriptInterface
    public void playAudioCorto(String aud) { //String aud - file name passed
        //from the JavaScript function
        final MediaPlayer mp1;

        try {
            AssetFileDescriptor fileDescriptor =
                    mContext.getAssets().openFd(aud);
            mp1 = new MediaPlayer();
            mp1.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            fileDescriptor.close();
            mp1.prepare();
            mp1.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //Play an audio file from the webpage
    @JavascriptInterface
    public void stopAudio(String aud) { //String aud - file name passed
        //from the JavaScript function


        try {

            mp.stop();
            mp.release();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}