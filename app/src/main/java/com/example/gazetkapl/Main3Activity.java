package com.example.gazetkapl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Main3Activity extends AppCompatActivity {

    PDFView pdfviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_2);

        pdfviewer=(PDFView)findViewById(R.id.pdfviewer);
        pdfviewer.fromAsset("demo2.pdf").load();

    }
}
