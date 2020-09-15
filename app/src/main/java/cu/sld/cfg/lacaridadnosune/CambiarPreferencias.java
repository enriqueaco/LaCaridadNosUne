package cu.sld.cfg.lacaridadnosune;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;

import static android.content.Context.MODE_PRIVATE;

/*
  Clase con método para guardar los datos descargados en las preferencias del usuario y
  para recuperarlos y ponerlos en la portada
  */

class CambiarPreferencias {

    /*var e = document.getElementById('publicacionesweb');
    // informacion basica
    var e1 = document.getElementById('informacionbasica');
    // sitios relacionados
    var e2 = document.getElementById('sitiosrelacionados');

    // publicaciones del sitio de infomed
    var e3 = document.getElementById('eninfomed');

    //libros de la bvs
    var e4 = document.getElementById('boletinaldia');
    // esenciales de infomed
    var e5 = document.getElementById("vigilancia");
    // cursos de la uvs
    var e6 = document.getElementById('eventos');
    // eventos
    var e7 = document.getElementById('galeria');
    */
    private SharedPreferences publicaciones_pref;




    public String publicaciones = "publicaciones";

    Context mContext;

    CambiarPreferencias(Context c) {
        mContext = c;

        publicaciones_pref = mContext.getSharedPreferences("publicaciones",MODE_PRIVATE);




    }


    @JavascriptInterface
       public void EditarPreferencias(String publicaciones_text){
       // método para guardar los diferentes apartados de información en
        // datos persistentes


            SharedPreferences.Editor publicaciones_ed = publicaciones_pref.edit();
            publicaciones_ed.putString(publicaciones,publicaciones_text);
            publicaciones_ed.commit();





        }



     // métodos para obtener la información necesaria
     // para mostrar en los diferentes apartados informativos

    @JavascriptInterface
        public String ObtenerPreferencia(){

        final String publicados = publicaciones_pref.getString("publicaciones","No hay datos");



            return publicados;
        }








}
