package com.rdr.emergency;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_PERMISSION_CALL = 100;

    private Activity activity;
    private ArrayList<Numeros> lista_numeros = new ArrayList<Numeros>();
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        usuario = new Usuario(this);

        //ADS
        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("C0CF96E945519D079C648A192DE0E88D")
                .build();
        mAdView.loadAd(adRequest);

        //permissao
        checkPermission(this, Manifest.permission.CALL_PHONE, REQUEST_PERMISSION_CALL);

        adicionarNumero(R.string.number_denuncia,R.string.desc_denuncia,"181");
        adicionarNumero(R.string.number_samu,R.string.desc_samu,"192");
        adicionarNumero(R.string.number_bombeiros,R.string.desc_bombeiros,"193");

        adicionarNumero(R.string.number_policia_militar,R.string.desc_policia_militar,"190");
        adicionarNumero(R.string.number_policia_militar_inter,R.string.desc_policia_militar_inter,"112");
        adicionarNumero(R.string.number_policia_militar_inter,R.string.desc_policia_militar_inter,"911");

        adicionarNumero(R.string.number_guarda_municipal, R.string.desc_guarda_municipal, "153");

        adicionarNumero(R.string.number_mulher,R.string.desc_mulher,"180");
        adicionarNumero(R.string.number_policia_federal,R.string.desc_policia_federal,"194");

        adicionarNumero(R.string.number_policia_rodoviaria_federal,R.string.desc_policia_rodoviaria_federal,"191");
        adicionarNumero(R.string.number_policia_rodoviaria_estadual,R.string.desc_policia_rodoviaria_estadual,"198");

        adicionarNumero(R.string.number_policia_civil,R.string.desc_policia_civil,"197");
        adicionarNumero(R.string.number_defesa_civil,R.string.desc_defesa_civil,"199");

        adicionarNumero(R.string.number_procon,R.string.desc_procon,"151");
        adicionarNumero(R.string.number_vigilancia_sanitaria,R.string.desc_vigilancia_sanitaria,"150");

        //AdicionarNumero(R.string.number_transportes_publicos,R.string.desc_transportes_publicos,"118"); //nao existe mais

        adicionarNumero(R.string.number_direitos_humanos,R.string.desc_direitos_humanos,"100");
        //AdicionarNumero(R.string.number_ibama,R.string.desc_ibama,"152"); //nao existe mais

        adicionarNumero(R.string.number_cvv,R.string.desc_cvv,"141");
        //adicionarNumero(R.string.number_horacerta,R.string.desc_horacerta,"130");


        final ListView lstNumeros = (ListView) findViewById(R.id.lstNumerosEmergencia);
        lstNumeros.setAdapter(new ItemListBaseAdapter(this, lista_numeros));

        lstNumeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Numeros numero = (Numeros) lstNumeros.getItemAtPosition(position);
                final String snumero = numero.getTelefone();

                new AlertDialog.Builder(activity)
                        .setMessage(R.string.popup_call)
                        .setCancelable(false)
                        .setPositiveButton(R.string.popup_call_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {

                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + snumero));
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(activity, "No permission to call", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton(R.string.popup_call_no, null)
                        .show();
            }
        });

        //Botão para avaliar
        if (usuario.GetUserRating()==0) //depois ou nunca clicou
        {
            new AlertDialog.Builder(activity)
                    .setIcon(R.drawable.like)
                    .setTitle(R.string.popup_rating_title)
                    .setMessage(R.string.popup_rating_text)
                    .setNeutralButton(R.string.popup_rating_btn_later, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            usuario.SetUserRating(0);
                        }})

                    .setPositiveButton(R.string.popup_rating_btn_now, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            usuario.SetUserRating(1);

                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    })

                    .setNegativeButton(R.string.popup_rating_btn_never, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            usuario.SetUserRating(2);
                        }})
                    .show();
        }
    }


    private void adicionarNumero(int nome, int descricao, String telefone){
        Numeros n = new Numeros();
        n.setNome(this.getString(nome));
        n.setDescricao(descricao);
        n.setTelefone(telefone);
        lista_numeros.add(n);
    }

    private static void checkPermission(Activity activity, String permission, int requestid){

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        requestid);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.privacy:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_link)));
                startActivity(browserIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
