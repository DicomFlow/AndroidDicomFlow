package com.github.dicomflow.androiddicomflow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.dicomflow.androiddicomflow.R;
import com.github.dicomflow.androiddicomflow.models.protocolo.services.Service;
import com.github.dicomflow.androiddicomflow.models.protocolo.services.request.RequestPut;
import com.github.dicomflow.androiddicomflow.models.protocolo.services.storage.StorageDelete;
import com.github.dicomflow.androiddicomflow.models.protocolo.xml.DicomFlowXmlSerializer;
import com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects.Credentials;
import com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects.DicomObject;
import com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects.Patient;
import com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects.Serie;
import com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects.Study;
import com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects.Url;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Certificate */
//        Service service = new CertificateRequest(
//                new Domain("google.com"),
//                new Mail("rbrico@gmail.com"),
//                new Port("32")
//        );
//
//        Service service = new CertificateConfirm(
//                new Domain("google.com"),
//                new Mail("rbrico@gmail.com"),
//                new Port("32"),
//                "my credentials",
//                "success"
//        );
//        Service service = new CertificateResult(
//                new Domain("google.com"),
//                new Mail("rbrico@gmail.com"),
//                new Port("32"),
//                "my credentials",
//                "success"
//        );
        /** STORAGE */
        /** SAVE **/

        ArrayList<Patient> patients = new ArrayList<Patient>();
        ArrayList<Study> studies = new ArrayList<>();
        List<Serie> series = new ArrayList<>();
        series.add(new Serie("1", "bodypart", "description", 1));
        studies.add(new Study("1","tipo","descricao do estudo", 1, 1l, series));
        studies.add(new Study("2","tipo","descricao do estudo 2", 2, 2l, series));
        patients.add(new Patient("053", "ricardo", "M", "31/10/1985", studies));
        patients.add(new Patient("054", "maria", "F", "31/10/1980", studies));
        ArrayList<String> values = new ArrayList<>();
        values.add("valor de credential 1");
        values.add("valor de credential 2");
        Credentials credentials = new Credentials(values);
        Url url = new Url("www.com...", credentials, patients);
//        Service service = new StorageSave(url);


        /** UPDATE **/
        DicomObject dicomObject = new DicomObject(credentials, "i", DicomObject.Type.Study);
//        Service service = new StorageUpdate(url, dicomObject);

        /** DELETE **/
        List<DicomObject> objects =  new ArrayList<>();
        objects.add(dicomObject);
        DicomObject dicomObject2 = new DicomObject(credentials, "3", DicomObject.Type.Instance);
        objects.add(dicomObject2);
        //Service service = new StorageDelete(objects);
        
        String requestType = "REPORT";
        Service service = new RequestPut(requestType, url);

        /*************/
        String xmlString = DicomFlowXmlSerializer.serialize(service);
        ((TextView) findViewById(R.id.text)).setText(xmlString);
    }

}
