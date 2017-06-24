package com.github.dicomflow.androiddicomflow.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.dicomflow.androiddicomflow.R;
import com.github.dicomflow.androiddicomflow.adapters.ServiceRecyclerViewAdapter;
import com.github.dicomflow.androiddicomflow.models.protocolo.services.DicomFlowProtocol;

import java.io.File;

/**
 * An activity representing a list of Serviços. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ServiceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ServiceListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    GMailSender sender = new GMailSender("dicomflow@gmail.com", "pr0t0c0l0ap1d1c0m");
                    sender.sendMail("This is Subject", "This is Body",
                            "dicomflow@gmail.com", "juracylucena@gmail.com");
                } catch (Exception e) {
                    Snackbar.make(view, "Erro no envio do email ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                Snackbar.make(view, "Email Enviado ... ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.serviço_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.serviço_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        DicomFlowProtocol dicomFlowProtocol = DicomFlowProtocol.getInstance();

        recyclerView.setAdapter(
                new ServiceRecyclerViewAdapter(
                        mTwoPane,
                        dicomFlowProtocol.SERVICOS));
    }

}
