package echevasoft.cancionesrponline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    JcPlayerView JcPlayerView;
    private AdManagerAdView mAdManagerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        JcPlayerView = (JcPlayerView) findViewById(R.id.jcplayer);
        comprobarconexion();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdManagerAdView = findViewById(R.id.adManagerAdView);
        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
        mAdManagerAdView.loadAd(adRequest);

    }

    public void onDestroy() {
        super.onDestroy();
        JcPlayerView.kill();
        System.exit(0);
        finishAffinity();
        Log.d("suspend","sii");
    }

    public void comprobarconexion(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            importSong();
        } else {
            alertDialog();
        }
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Verifique su conexión a internet y vuelva a intentarlo");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        dialog.setNegativeButton("Reintentar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                comprobarconexion();
            }
        });
        final AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.show();

    }
    private void logout() {
        finish();
        System.exit(0);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Quieres salir de la App?");
        builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                JcPlayerView.kill();
                System.exit(0);
                finish();
            }
        });
        builder.setNegativeButton("Minimizar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                MainActivity.this.startActivity(intent);
            }
        });
        builder.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Calificar:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=echevasoft.cancionesrponline"));
                intent.setPackage("com.android.vending");
                startActivity(intent);
                return true;

            case R.id.Compartir:
                Intent intent2 = new Intent(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, "¡Descarga la App Canciones de River Plate y disfruta de más de 40 canciones del Más Grande, Lejos https://play.google.com/store/apps/details?id=echevasoft.cancionesrponline");
                startActivity(Intent.createChooser(intent2, "Compartir con"));
                return true;

            case R.id.Salir:
                JcPlayerView.kill();
                System.exit(0);
                finishAffinity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void importSong(){

        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL("A donde vayas, siempre estaremos","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/A%20donde%20vayas%2C%20siempre%20estaremos.mp3?alt=media&token=92e4be71-6dad-4bce-914d-7d5e11a77282"));
        jcAudios.add(JcAudio.createFromURL("A mi me volvió loco ser de River Plate", "https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/A%20mi%20me%20volvio%CC%81%20loco%20ser%20de%20River%20Plate.mp3?alt=media&token=55424485-2adb-440e-a0db-34da1ffcd8ab"));
        jcAudios.add(JcAudio.createFromURL("A ver si nos entendemos","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/A%20ver%20si%20nos%20entendemos.mp3?alt=media&token=0968a661-fe3f-4655-971c-5b489c5d71c9"));
        jcAudios.add(JcAudio.createFromURL("Al Millonario lo sigo a todos lados", "https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Al%20Millonario%20lo%20sigo%20a%20todos%20lados.mp3?alt=media&token=a9df365b-6402-46e4-aa71-dbf75acd7392"));
        jcAudios.add(JcAudio.createFromURL("Ay che bostero, mira que distintos somos","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Ay%20che%20bostero%2C%20mira%20que%20distintos%20somos.mp3?alt=media&token=e9637b9c-2bb4-409f-bc5c-d543360e6b14"));
        jcAudios.add(JcAudio.createFromURL("Banderas negras parlantes no hay","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Banderas%20negras%20parlantes%20no%20hay.mp3?alt=media&token=de55d996-5c72-49ee-b146-eebc7381b704"));
        jcAudios.add(JcAudio.createFromURL("Borracho siempre voy descontrolado","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Borracho%20siempre%20voy%20descontrolado.mp3?alt=media&token=c9889636-4334-42cf-a9b8-816d648759d1"));
        jcAudios.add(JcAudio.createFromURL("Che bostero","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Che%20bostero.mp3?alt=media&token=c55e8db8-7e54-4fc7-bd57-7a9ec21350be"));
        jcAudios.add(JcAudio.createFromURL("Como te duele","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Como%20te%20duele.mp3?alt=media&token=7763bfce-0859-4618-9b2e-30df13225052"));
        jcAudios.add(JcAudio.createFromURL("Decime boca","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Decime%20boca.mp3?alt=media&token=ec312796-bb6d-48c4-a831-3aa8e936654e"));
        jcAudios.add(JcAudio.createFromURL("El dia que me muera","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/El%20dia%20que%20me%20muera.mp3?alt=media&token=8ee6a95a-ec77-41d9-87d3-1c054a0c1095"));
        jcAudios.add(JcAudio.createFromURL("Gallardo cantando, festejos 9-12","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Gallardo%20cantando%2C%20festejos%209-12.mp3?alt=media&token=b1e1d4ee-94b7-4711-b809-690a081e9d43"));
        jcAudios.add(JcAudio.createFromURL("Hace tiempo que yo te vengo a alentar","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Hace%20tiempo%20que%20yo%20te%20vengo%20a%20alentar.mp3?alt=media&token=e95fa4eb-6d11-4e11-bcde-43d8315c0a27"));
        jcAudios.add(JcAudio.createFromURL("Hoo Millonario","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Hoo%20Millonario.mp3?alt=media&token=52339ddc-ebd4-415e-8f35-b29cc4b62c69"));
        jcAudios.add(JcAudio.createFromURL("La Copa Libertadores es mi obsesión","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/La%20Copa%20Libertadores%20es%20mi%20obsesio%CC%81n.mp3?alt=media&token=81feec0a-f4bc-4e4e-84bd-3e71c27cd020"));
        jcAudios.add(JcAudio.createFromURL("Llega el domingo","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Llega%20el%20domingo.mp3?alt=media&token=8b5f212a-2b81-4dc0-a5b5-cf0e9730e7e0"));
        jcAudios.add(JcAudio.createFromURL("Los bosteros son así","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Los%20bosteros%20son%20asi%CC%81.mp3?alt=media&token=a39bc846-0ffe-49f7-9c4d-7d426c6064df"));
        jcAudios.add(JcAudio.createFromURL("Los vieron corriendo por Libertador","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Los%20vieron%20corriendo%20por%20Libertador.mp3?alt=media&token=ef07230b-16ea-44cf-8eaa-7d49cd489321"));
        jcAudios.add(JcAudio.createFromURL("Muchos años estuvieron","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Muchos%20an%CC%83os%20estuvieron.mp3?alt=media&token=8e3f5d87-0f06-43aa-ace1-0c2db777d285"));
        jcAudios.add(JcAudio.createFromURL("Para ser campeón, hoy hay que ganar","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Para%20ser%20campeo%CC%81n%2C%20hoy%20hay%20que%20ganar.mp3?alt=media&token=a68a668d-610a-42eb-b763-6a0a1063e498"));
        jcAudios.add(JcAudio.createFromURL("Ponga huevo Millonario que hoy tenes que ganar","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Ponga%20huevo%20Millonario%20que%20hoy%20tenes%20que%20ganar.mp3?alt=media&token=8272839c-9d8c-4ab6-b406-60990955dd72"));
        jcAudios.add(JcAudio.createFromURL("Que loca esta la hinchada","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Que%20loca%20esta%20la%20hinchada.mp3?alt=media&token=a0b05ca3-afe0-41ce-aea9-8a62dc65423f"));
        jcAudios.add(JcAudio.createFromURL("Quiero dar la vuelta en la copa","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Quiero%20dar%20la%20vuelta%20en%20la%20copa.mp3?alt=media&token=4af40edd-7bc6-418a-acfd-3ab625af22f3"));
        jcAudios.add(JcAudio.createFromURL("River a todas partes yo voy contigo","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/River%20a%20todas%20partes%20yo%20voy%20contigo.mp3?alt=media&token=2cc39598-492b-4ade-9b3e-5e4e494389fc"));
        jcAudios.add(JcAudio.createFromURL("River mi buen amigo","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/River%20mi%20buen%20amigo.mp3?alt=media&token=907e6f4d-558a-4584-b3b6-95423144bece"));
        jcAudios.add(JcAudio.createFromURL("River vos sos mi vida, lo más grande de la Argentina","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/River%20vos%20sos%20mi%20vida%2C%20lo%20ma%CC%81s%20grande%20de%20la%20Argentina.mp3?alt=media&token=ccda4d7c-dd4f-43c0-9680-141bf0315d55"));
        jcAudios.add(JcAudio.createFromURL("Señores Yo soy del Gallinero","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Sen%CC%83ores%20Yo%20soy%20del%20Gallinero.mp3?alt=media&token=fd98e8f0-c397-4045-8cd9-97cb5b735ea5"));
        jcAudios.add(JcAudio.createFromURL("Señores yo soy de la banda","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Sen%CC%83ores%20yo%20soy%20de%20la%20banda.mp3?alt=media&token=a485f72f-352f-4429-8a83-03a847218330"));
        jcAudios.add(JcAudio.createFromURL("Solo le pido a Dios","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Solo%20le%20pido%20a%20Dios.mp3?alt=media&token=c8f8186a-b28f-4d53-b08a-2d5be118add8"));
        jcAudios.add(JcAudio.createFromURL("Somos los pibes que alentamos","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Somos%20los%20pibes%20que%20alentamos.mp3?alt=media&token=ba996d9f-a854-4286-8e41-e5ed998fb5c8"));
        jcAudios.add(JcAudio.createFromURL("Soy de River vago y atorrante - Previa","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Soy%20de%20River%20vago%20y%20atorrante%20-%20Previa.mp3?alt=media&token=94f4f62f-133b-44c8-b874-f5701c7b0431"));
        jcAudios.add(JcAudio.createFromURL("Soy de River y lo sigo a todos lados","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Soy%20de%20River%20y%20lo%20sigo%20a%20todos%20lados.mp3?alt=media&token=95ab58b3-11b1-4753-810e-42cddb5da4cb"));
        jcAudios.add(JcAudio.createFromURL("Te vi campeón","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Te%20vi%20campeo%CC%81n.mp3?alt=media&token=16deb2f5-a626-4729-b4fa-9ecf30572338"));
        jcAudios.add(JcAudio.createFromURL("Todos los domingos a la tarde","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Todos%20los%20domingos%20a%20la%20tarde.mp3?alt=media&token=9403c001-ecae-425d-9795-a23463c6f071"));
        jcAudios.add(JcAudio.createFromURL("Todos los palos que recibí","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Todos%20los%20palos%20que%20recibi%CC%81.mp3?alt=media&token=d1c6a643-ba77-4624-9dbb-c5caff05e728"));
        jcAudios.add(JcAudio.createFromURL("Vamo Millonario yo te sigo a todos lados","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Vamo%20Millonario%20yo%20te%20sigo%20a%20todos%20lados.mp3?alt=media&token=16a624c0-f13e-417d-a97c-e5cc9fb94458"));
        jcAudios.add(JcAudio.createFromURL("Vamos los Millo que tenemos que ganar","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Vamos%20los%20Millo%20que%20tenemos%20que%20ganar.mp3?alt=media&token=15cf1b5d-6512-409f-bcd3-e192f907a2fd"));
        jcAudios.add(JcAudio.createFromURL("Vamos los millonarios","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Vamos%20los%20millonarios.mp3?alt=media&token=82076adf-3867-43df-af05-75d9fe3d8aed"));
        jcAudios.add(JcAudio.createFromURL("Veni al Monumental - Previa","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Veni%20al%20Monumental%20-%20Previa.mp3?alt=media&token=3c11483b-36f7-4a04-aa56-d80a55d779e9"));
        jcAudios.add(JcAudio.createFromURL("Volvimo' a la cancha - Previa","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Volvimo'%20a%20la%20cancha%20-%20Previa.mp3?alt=media&token=8a1c9003-717f-4ad0-aebd-fc58c7bb385b"));
        jcAudios.add(JcAudio.createFromURL("Yo a vos te sigo aunque","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Yo%20a%20vos%20te%20sigo%20aunque.mp3?alt=media&token=4fa2e4fb-6f35-4fcc-81ea-c1c4fa4d85a6"));
        jcAudios.add(JcAudio.createFromURL("Yo campeón te vengo a ver","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Yo%20campeo%CC%81n%20te%20vengo%20a%20ver.mp3?alt=media&token=c74b9d1d-a226-412e-8a76-241820f065ed"));
        jcAudios.add(JcAudio.createFromURL("Yo paro en una banda","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Yo%20paro%20en%20una%20banda.mp3?alt=media&token=87cb4c4d-78d1-45eb-9239-f2ea2a2b6ba3"));
        jcAudios.add(JcAudio.createFromURL("Yo soy del gallinero.mp3","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Yo%20soy%20del%20gallinero.mp3?alt=media&token=ba5752bf-2f34-46ce-b45e-a547649a1a83"));
        jcAudios.add(JcAudio.createFromURL("Yo te quiero River Plate","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Yo%20te%20quiero%20River%20Plate.mp3?alt=media&token=6b56c931-0ed1-4e9f-a150-da9cd90eacac"));
        jcAudios.add(JcAudio.createFromURL("Yo te sigo porque vos sos mi locura","https://firebasestorage.googleapis.com/v0/b/reproductorrponline.appspot.com/o/Yo%20te%20sigo%20porque%20vos%20sos%20mi%20locura.mp3?alt=media&token=7f6ccf45-d62b-49d7-bed9-29e4fe8854cc"));

        JcPlayerView.initPlaylist(jcAudios, null);
        JcPlayerView.createNotification(R.drawable.rivermusica);

    }





}