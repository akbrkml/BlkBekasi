package com.barateknologi.bbplk_cevest.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.barateknologi.bbplk_cevest.R;

public class Webview_baru extends AppCompatActivity
{

    WebView myBrowser;
    EditText Msg;
    Button btnSendMsg;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webvieww);
        myBrowser = (WebView)findViewById(R.id.mybrowser);
        myBrowser.getSettings().setJavaScriptEnabled(true);
        myBrowser.loadUrl("http://evaluasipbk.com/adminandroid/AlumniGathering/Default.Aspx");


       // myBrowser.loadUrl("file:///android_asset/index.html");
       //  myBrowser.loadUrl("file:///android_asset/projectsnode/server.js");

        // assets\projects\node-edge-test1
       // myBrowser.loadUrl("http://bbplkbekasi.org");
    }


}