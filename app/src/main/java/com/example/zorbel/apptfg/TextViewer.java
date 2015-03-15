package com.example.zorbel.apptfg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;


public class TextViewer extends ActionBarActivity {

    private WebView mWebView;
    private Button mButtonLike;
    private Button mButtonUnlike;
    private Button mButtonNotUnderstood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_viewer);
        mWebView = (WebView) findViewById(R.id.webView);
        mButtonLike = (Button) findViewById(R.id.buttonLike);
        mButtonUnlike = (Button) findViewById(R.id.buttonUnlike);
        mButtonNotUnderstood = (Button) findViewById(R.id.buttonNotUnderstood);
        getInformationProgram();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_viewer, menu);
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

    private void getInformationProgram() {
        String text = "<html>\n" +
                "\n" +
                "<h1>1.1 Participación ciudadana y gobierno abierto</h1>\n" +
                "\n" +
                "<h2>Extensión de la democracia y proceso constituyente</h2>\n" +
                "\n" +
                "<p>Los Piratas Europeos exigimos la redacción de un nuevo tratado de la Unión Europea\n" +
                "destinado a clarificar y sustituir los tratados actuales y hacer frente a la necesidad de una\n" +
                "reforma democrática de la Unión. Este tratado deberá ser realizado como un proceso\n" +
                "constituyente de manera participativa y abierta a toda la ciudadanía de la Unión,\n" +
                "ratificado a través de un referéndum.</p>\n" +
                "\n" +
                "<p>El actual proceso legislativo de la Unión Europea está dominado por el poder ejecutivo\n" +
                "(la Comisión Europea) a expensas del poder legislativo (el Parlamento Europeo). Los\n" +
                "Piratas Europeos buscamos un ajuste en el equilibrio de poder en las instituciones\n" +
                "europeas en favor de la rama legislativa.</p>\n" +
                "\n" +
                "<p>Los cambios en la legislación primaria (por ejemplo, un tratado) sólo deben entrar en\n" +
                "vigor si una mayoría de ciudadanos de la Unión aprueba los cambios en un voto\n" +
                "simultáneo, además de la exigencia actual de acuerdo a nivel gubernamental. El\n" +
                "ejecutivo de la Unión Europea debe velar para que estas votaciones se puedan realizar\n" +
                "incluso a corto plazo.</p>\n" +
                "\n" +
                "<p>Intentaremos, además, que los cambios en el funcionamiento de la UE permitan sustituir\n" +
                "de forma progresiva el sistema representativo actual por un sistema de democracia\n" +
                "participativa en la forma que se determine.</p>\n" +
                "\n" +
                "<h2>Supresión de los obstáculos injustos a la participación política</h2>\n" +
                "\n" +
                "<p>Los Piratas Europeos queremos que los ciudadanos puedan tener un impacto más\n" +
                "directo y más grande en el debate político y en el proceso de decisiones, tanto individual\n" +
                "como colectivamente. Por ello exigimos la eliminación de barreras injustas que impiden\n" +
                "la participación de nuevos partidos políticos en las elecciones, como la necesidad de\n" +
                "recoger un determinado número de avales en tiempo limitado. Entendemos que esta\n" +
                "medida lejos de asegurar una participación de la vida pública de calidad sólo impone\n" +
                "trabas a la libre expresión y participación ciudadana en las instituciones públicas, en este\n" +
                "caso europeas.</p>\n" +
                "\n" +
                "<p>También exigimos una mayor integración que permita a los ciudadanos europeos móviles\n" +
                "votar en su país de residencia y acabar con las barreras para la ciudadanía como el voto\n" +
                "rogado que causa que un gran número de migrantes europeos no puedan ejercer su voto\n" +
                "en su país de origen.</p>\n" +
                "\n" +
                "<h2>Mejoras en la participación ciudadana directa</h2>\n" +
                "\n" +
                "<p>Los Piratas Europeos nos esforzamos por crear un estándar común para las iniciativas\n" +
                "ciudadanas a nivel local, regional, estatal y europeo para reforzar la participación\n" +
                "ciudadana y trasladar la toma de decisiones al conjunto de la población. Para capacitar a\n" +
                "los ciudadanos, queremos que las iniciativas ciudadanas que puedan modificar\n" +
                "constituciones, leyes, etc, estén siempre seguidas de un referéndum si no son aprobadas\n" +
                "por la legislatura.</p>\n" +
                "\n" +
                "<p>Así, proponemos usar, mejorar y simplificar las Iniciativas Ciudadanas Europeas\n" +
                "(European Citizen Initiatives - ECIs) para suavizar los requisitos actuales (reducir número\n" +
                "de firmas, no exigir distribución en 7 países, etc) así como garantizar su difusión desde\n" +
                "canales oficiales de la Unión Europea.</p>\n" +
                "\n" +
                "<p>La expansión del referéndum a carácter vinculante debe ir acompañada de una\n" +
                "ampliación de su ámbito aplicación, inclusive a decisiones sobre la forma de Estado o las\n" +
                "relaciones a mantener entre los distintos pueblos en caso de que alguno ejerciese el\n" +
                "derecho de autodeterminación.</p>\n" +
                "\n" +
                "<h2>Refuerzo del Parlamento Europeo y democratización de las instituciones europeas</h3>\n" +
                "\n" +
                "<p>Actualmente el poder real reside en la Comisión Europea, siendo ésta la menos\n" +
                "democrática de las instituciones europeas.</p>\n" +
                "\n" +
                "<p>Por esto, promoveremos el fortalecimiento y la profundización de la democracia y la\n" +
                "transparencia en las distintas instituciones de la UE, así como la necesidad de reforzar el\n" +
                "papel del Parlamento Europeo para conseguir un mayor equilibrio de las instituciones y\n" +
                "una auténtica separación de poderes a nivel de la Unión.</p>\n" +
                "\n" +
                "</html>";
        mWebView.loadData(text, "text/html", null);

    }
}
