package com.devfill.checkman_mvp.list_declarations.mvp.view;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.devfill.checkman_mvp.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;


import java.io.File;

public class DeclarationActivity extends AppCompatActivity {

    private String LOG_TAG = "DeclarationActivityTag";

    private String name;

    private PDFView pdfView;

    private Toolbar toolbar;

    private int mCurrentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_show);

        toolbar = (Toolbar) findViewById(R.id.toolbarDeclaration);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        pdfView = (PDFView) findViewById(R.id.pdfView);


        name = getIntent().getStringExtra("name");

        File filesDir = getExternalFilesDir("");

        loadPDF(new File(filesDir + "/" + name + ".pdf"));

    }

    private void loadPDF(File file){


        pdfView.fromFile(file)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)

                // allows to draw something on the current page, usually visible in the middle of the screen
                //  .onDraw(onDrawListener)
                // allows to draw something on all pages, separately for every page. Called only for visible pages
                //   .onDrawAll(onDrawListener)
                //  .onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
                  .onPageChange(new OnPageChangeListener() {
                      @Override
                      public void onPageChanged(int page, int pageCount) {
                          toolbar.setTitle("(" + (page + 1) + "/" + pageCount + ")" + name);
                      }
                  })
                //  .onPageScroll(onPageScrollListener)
                //  .onError(onErrorListener)
                   .onRender(new OnRenderListener() {
                       @Override
                       public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {

                           pdfView.fitToWidth(mCurrentPage);
                       }
                   }) // called after document is rendered for the first time
                // called on single tap, return true if handled, false to toggle scroll handle visibility
                //    .onTap(onTapListener)
                //////  .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                ///  .password(null)
                //   .scrollHandle(null)
                ////  .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                //
                //    .spacing(0)
                .load();


    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", pdfView.getCurrentPage());
        Log.d(LOG_TAG, "onSaveInstanceState");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPage = savedInstanceState.getInt("count");
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }

}
