package com.samsung.multidevicecloud;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class WebActivity extends Activity {
    private WebView openBrowser = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        openBrowser = (WebView) findViewById(R.id.webview);
        openBrowser.setWebViewClient(new WebViewClient());
        WebSettings webSettings = openBrowser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        final EditText editText = new EditText(WebActivity.this);
        editText.setText("https://www.baidu.com");
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(WebActivity.this);
        inputDialog.setTitle("请输入网址").setView(editText);
        inputDialog.setPositiveButton("确定",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openBrowser.loadUrl(editText.getText().toString());
                }
            }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && openBrowser.canGoBack())
        {
            openBrowser.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
