package com.bpcl.textrecognition;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 200;
    public static final int STORAGE_REQUEST_CODE = 400;
    public static final int IMAGE_PICK_GALLERY_CODE = 1000;
    public static final int IMAGE_PICK_CAMERA_CODE = 1001;
    TextView mResultEt;
    ImageView mPreviewIv;
    Button button;
    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    TextView blood;
    TextView haemoglobin, fifteen_7, M;
    TextView Total_rbc, million_cmm, mill_rbc;
    TextView Total_wbc, cmm, fourthousand;
    TextView leucocyte;
    TextView Poly, fifty_three, fifty_four_pr;
    TextView lymphocytes, thirty_five_pr, sev_thir;
    TextView Eosinophils, zero_four, zero_one;
    TextView monocytes, zero_8, zero_2;
    TextView basophils, zero, zero_1;
    TextView Bloodindices;
    TextView pcv, fourty_7, MF;
    TextView mcv, eight_2, eighty;
    TextView mch, twenty_7, twenty_7_pg;
    TextView mchc, thirty_3, thirty_2;
    TextView rdw, thirteen, ele;
    TextView total_count, cmm3, cmm15;
    TextView diff, l_count, abs;
    TextView abs_eo, two_two_0, four_50;
    TextView abs_ly, two18, four80;
    TextView absmo, five20, eight00;
    TextView absne, three29, one80;
    TextView imm, ten, ten5;

    TableLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab = findViewById(R.id.tab);

        button = findViewById(R.id.click);
        mResultEt = findViewById(R.id.resultEt);
        mPreviewIv = findViewById(R.id.imageIv);
        String main_str = (String) mResultEt.getText();

        blood = findViewById(R.id.blood);

        haemoglobin = findViewById(R.id.haemoglobin);
        fifteen_7 = findViewById(R.id.fifteen_7);
        M = findViewById(R.id.M);

        Total_rbc = findViewById(R.id.Total_rbc);
        million_cmm = findViewById(R.id.million_cmm);
        mill_rbc = findViewById(R.id.mill_rbc);

        Total_wbc = findViewById(R.id.Total_wbc);
        cmm = findViewById(R.id.cmm);
        fourthousand = findViewById(R.id.fourthousand);

        leucocyte = findViewById(R.id.leucocyte);

        Poly = findViewById(R.id.Poly);
        fifty_three = findViewById(R.id.fifty_three);
        fifty_four_pr = findViewById(R.id.fifty_four_pr);

        lymphocytes = findViewById(R.id.lymphocytes);
        thirty_five_pr = findViewById(R.id.thirty_five_pr);
        sev_thir = findViewById(R.id.sev_thir);

        Eosinophils = findViewById(R.id.Eosinophils);
        zero_four = findViewById(R.id.zero_four);
        zero_one = findViewById(R.id.zero_one);

        monocytes = findViewById(R.id.monocytes);
        zero_8 = findViewById(R.id.zero_8);
        zero_2 = findViewById(R.id.zero_2);

        basophils = findViewById(R.id.basophils);
        zero = findViewById(R.id.zero);
        zero_1 = findViewById(R.id.zero_1);

        Bloodindices = findViewById(R.id.Bloodindices);

        pcv = findViewById(R.id.pcv);
        fourty_7 = findViewById(R.id.fourty_7);
        MF = findViewById(R.id.MF);

        mcv = findViewById(R.id.mcv);
        eight_2 = findViewById(R.id.eight_2);
        eighty = findViewById(R.id.eighty);

        mch = findViewById(R.id.mch);
        twenty_7 = findViewById(R.id.twenty_7);
        twenty_7_pg = findViewById(R.id.twenty_7_pg);

        mchc = findViewById(R.id.mchc);
        thirty_3 = findViewById(R.id.thirty_3);
        thirty_2 = findViewById(R.id.thirty_2);

        rdw = findViewById(R.id.rdw);
        thirteen = findViewById(R.id.thirteen);
        ele = findViewById(R.id.ele);

        total_count = findViewById(R.id.total_count);
        cmm3 = findViewById(R.id.cmm3);
        cmm15 = findViewById(R.id.cmm15);

        diff = findViewById(R.id.diff);
        l_count = findViewById(R.id.l_count);
//        abs = findViewById(R.id.abs);


        abs_eo = findViewById(R.id.abs_eo);
        two_two_0 = findViewById(R.id.two_two_0);
        four_50 = findViewById(R.id.four_50);

        abs_ly = findViewById(R.id.abs_ly);
        two18 = findViewById(R.id.two18);
        four80 = findViewById(R.id.four80);

        absmo = findViewById(R.id.absmo);
        five20 = findViewById(R.id.five20);
        eight00 = findViewById(R.id.eight00);

        absne = findViewById(R.id.absne);
        three29 = findViewById(R.id.three29);
        one80 = findViewById(R.id.one80);

        imm = findViewById(R.id.imm);
        ten = findViewById(R.id.ten);
        ten5 = findViewById(R.id.ten5);

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main_str_et = (String) ((String) mResultEt.getText()).trim();
                String substr_blood_et = main_str_et.substring(0, 11);
                Log.e("gps", "crash");
                if (substr_blood_et.equals("Blood Count")) {
                    tab.setVisibility(View.VISIBLE);
                    String main_str = "Blood Count:haemoglobin:15.7 gm %M: 13.5 - 18.0 G % F: 12.0 - 16.0 G %Total R.B.C.:5.75 Million/cmmM: 4.1 - 6.3 mill./cmm F: 3.8 - 5.6 mill./cmmTotal W. B. C.:6.260 /cmm4.000 - 10.000 /cmmDifferential leucocyte Count:Polymorphs: 53 %(54 - 70 %)Lymphocytes: 35 %(17 - 32 %)Eosinophils: 04 %(01 - 04 %)Monocytes: 08 %(02 - 06 %)Basophils: 00 %(00 - 01 %)Blood Indices:P.C.V.: 47.6 %[M:40-54 % F:37-47 %]M. C. V.: 82.6 femtolitre[80 - 96 femtolitre]M. C. H.: 27.3 pg[27 - 33 pg]M. C. H. C.: 33.0 %[32 - 36 %]R.D.W.: 13.0[11.0 - 15.0]Total Platelet count: 3,03,000 /c.mm1,50,000 - 4,50,000 /c.mmDifferential Leucocyte Count:(Absolute)Absolute Eosinophil Count: 220 /c.mm0 - 450/c.mmAbsolute Lymphocyte Count: 2180/cmm1000 - 4800/cmmAbsolute Monocyte Count: 520/cmm0 - 800/cmmAbsolute Neutrophil Count: 3290/cmm1800 - 7700/cmmImmatrue granulocyte: 10/cmm10 - 50 /cmm\n";

                    String substr_blood = main_str.substring(0, 12);
                    blood.setText(substr_blood);

                    String substr_haemoglobin = main_str.substring(12, 23);
                    haemoglobin.setText(substr_haemoglobin);
                    int x1 = main_str_et.indexOf("bin");
                    int start_fifteen_7 = x1 + 3;
                    int y1 = main_str_et.indexOf("m %");
                    int end_fifteen_7 = y1 + 3;
                    if (end_fifteen_7 - start_fifteen_7 == 0) {
                        String substr_fifteen_7 = "NULL";
                        fifteen_7.setText(substr_fifteen_7);
                    } else {
                        String substr_fifteen_7 = main_str_et.substring(start_fifteen_7, end_fifteen_7).trim();
                        fifteen_7.setText(substr_fifteen_7);
                    }
//                    if (substr_fifteen_7.equals(null)) {
//                        fifteen_7.setText("NULL");
//                    } else {
//                        fifteen_7.setText(substr_fifteen_7);
//                    }
                    String substr_M = main_str.substring(33, 70);
                    M.setText(substr_M);

                    String substr_Total_rbc = main_str.substring(70, 82);
                    Total_rbc.setText(substr_Total_rbc);
                    int x2 = main_str_et.indexOf("R.B.C.");
                    int start_million_cmm = x2 + 6;
                    int y2 = main_str_et.indexOf("on/cmm");
                    int end_million_cmm = y2 + 6;
                    if (end_million_cmm - start_million_cmm == 0) {
                        String substr_million_cmm = "NULL";
                        million_cmm.setText(substr_million_cmm);
                    } else {
                        String substr_million_cmm = main_str_et.substring(start_million_cmm, end_million_cmm).trim();
                        million_cmm.setText(substr_million_cmm);
                    }
//                    String substr_million_cmm = main_str_et.substring(start_million_cmm, end_million_cmm).trim();
//                    if (substr_million_cmm.equals(null)) {
//                        million_cmm.setText("NULL");
//                    } else {
//                        million_cmm.setText(substr_million_cmm);
//                    }
                    String substr_mill_rbc = main_str.substring(99, 144);
                    mill_rbc.setText(substr_mill_rbc);

                    String substr_Total_wbc = main_str.substring(144, 158);
                    Total_wbc.setText(substr_Total_wbc);
                    int x3 = main_str_et.indexOf("W.B.C.");
                    int start_cmm = x3 + 6;
                    int y3 = main_str_et.indexOf("4.000");
                    int end_cmm = y3;
//                    if (end_cmm - start_cmm == 0 ) {
//                        String substr_cmm = "NULL";
//                        cmm.setText(substr_cmm);
//                    } else {
                    String substr_cmm = main_str_et.substring(start_cmm, end_cmm).trim();
                    fifteen_7.setText(substr_cmm);
//                    }
//                    String substr_cmm = main_str_et.substring(start_cmm, end_cmm).trim();
//                    if (substr_cmm.equals(null)) {
//                        cmm.setText("NULL");
//                    } else {
//                        cmm.setText(substr_cmm);
//                    }
                    String substr_fourthousand = main_str.substring(169, 188);
                    fourthousand.setText(substr_fourthousand);

                    String substr_leucocyte = main_str.substring(188, 217);
                    leucocyte.setText(substr_leucocyte);

                    String substr_Poly = main_str.substring(217, 227);
                    Poly.setText(substr_Poly);
                    int x4 = main_str_et.indexOf("rphs");
                    int start_fifty_three = x4 + 4;
                    int y4 = main_str_et.indexOf("(54");
                    int end_fifty_three = y4;
                    if (end_fifty_three - start_fifty_three == 0) {
                        String substr_fifty_three = "NULL";
                        fifty_three.setText(substr_fifty_three);
                    } else {
                        String substr_fifty_three = main_str_et.substring(start_fifty_three, end_fifty_three).trim();
                        fifty_three.setText(substr_fifty_three);
                    }
//                    String substr_fifty_three = main_str_et.substring(start_fifty_three, end_fifty_three).trim();
//                    if (substr_fifty_three.equals(null)) {
//                        fifty_three.setText("NULL");
//                    } else {
//                        fifty_three.setText(substr_fifty_three);
//                    }
                    String substr_fifty_four_pr = main_str.substring(233, 244);
                    fifty_four_pr.setText(substr_fifty_four_pr);

                    String substr_lymphocytes = main_str.substring(244, 255);
                    lymphocytes.setText(substr_lymphocytes);
                    int x5 = main_str_et.indexOf("hocytes");
                    int start_thirty_five_p = x5 + 7;
                    int y5 = main_str_et.indexOf("(17");
                    int end_thirty_five_p = y5;
                    if (end_thirty_five_p - start_thirty_five_p == 0) {
                        String substr_thirty_five_p = "NULL";
                        thirty_five_pr.setText(substr_thirty_five_p);
                    } else {
                        String substr_thirty_five_p = main_str_et.substring(start_thirty_five_p, end_thirty_five_p).trim();
                        thirty_five_pr.setText(substr_thirty_five_p);
                    }
//                    String substr_thirty_five_pr = main_str_et.substring(start_thirty_five_p, end_thirty_five_p).trim();
//                    if (substr_thirty_five_pr.equals(null)) {
//                        thirty_five_pr.setText("NULL");
//                    } else {
//                        thirty_five_pr.setText(substr_thirty_five_pr);
//                    }
                    String substr_sev_thir = main_str.substring(261, 272);
                    sev_thir.setText(substr_sev_thir);

                    String substr_Eosinophils = main_str.substring(272, 283);
                    Eosinophils.setText(substr_Eosinophils);
                    int x6 = main_str_et.indexOf("ophils");
                    int start_zero_four = x6 + 6;
                    int y6 = main_str_et.indexOf("(01");
                    int end_zero_four = y6;
                    if (end_zero_four - start_zero_four == 0) {
                        String substr_zero_four = "NULL";
                        zero_four.setText(substr_zero_four);
                    } else {
                        String substr_zero_four = main_str_et.substring(start_zero_four, end_zero_four).trim();
                        zero_four.setText(substr_zero_four);
                    }
//                    String substr_zero_four = main_str_et.substring(start_zero_four, end_zero_four).trim();
//                    if (substr_zero_four.equals(null)) {
//                        zero_four.setText("NULL");
//                    } else {
//                        zero_four.setText(substr_zero_four);
//                    }
                    String substr_zero_one = main_str.substring(289, 300);
                    zero_one.setText(substr_zero_one);

                    String substr_monocytes = main_str.substring(300, 309);
                    monocytes.setText(substr_monocytes);
                    int x7 = main_str_et.indexOf("nocytes");
                    int start_zero_8 = x7 + 7;
                    int y7 = main_str_et.indexOf("(02");
                    int end_zero_8 = y7;
                    if (end_zero_8 - start_zero_8 == 0) {
                        String substr_zero_8 = "NULL";
                        zero_8.setText(substr_zero_8);
                    } else {
                        String substr_zero_8 = main_str_et.substring(start_zero_8, end_zero_8).trim();
                        zero_8.setText(substr_zero_8);
                    }
//                    String substr_zero_8 = main_str_et.substring(start_zero_8, end_zero_8).trim();
//                    if (substr_zero_8.equals(null)) {
//                        zero_8.setText("NULL");
//                    } else {
//                        zero_8.setText(substr_zero_8);
//                    }
                    String substr_zero_2 = main_str.substring(315, 326);
                    zero_2.setText(substr_zero_2);

                    String substr_basophils = main_str.substring(326, 335);
                    basophils.setText(substr_basophils);
                    int x9 = main_str_et.indexOf("Baso");
                    int start_zero = x9 + 10;
                    int y9 = main_str_et.indexOf("(00");
                    int end_zero = y9;
                    if (end_zero - start_zero == 0) {
                        String substr_zero = "NULL";
                        zero.setText(substr_zero);
                    } else {
                        String substr_zero = main_str_et.substring(start_zero, end_zero).trim();
                        zero.setText(substr_zero);
                    }
//                    String substr_zero = main_str_et.substring(start_zero, end_zero).trim();
//                    if (substr_zero.equals(null)) {
//                        zero.setText("NULL");
//                    } else {
//                        zero.setText(substr_zero);
//                    }
                    String substr_zero_1 = main_str.substring(341, 352);
                    zero_1.setText(substr_zero_1);

                    String substr_blood_indices = main_str.substring(352, 366);
                    Bloodindices.setText(substr_blood_indices);

                    String substr_pcv = main_str.substring(366, 372);
                    pcv.setText(substr_pcv);
                    int x10 = main_str_et.indexOf("PCV");
                    int start_fourty_7 = x10 + 3;
                    int y10 = main_str_et.indexOf("M:40");
                    int end_fourty_7 = y10;
                    if (end_fourty_7 - start_fourty_7 == 0) {
                        String substr_fourty_7 = "NULL";
                        fourty_7.setText(substr_fourty_7);
                    } else {
                        String substr_fourty_7 = main_str_et.substring(start_fourty_7, end_fourty_7).trim().replace("[", " ");
                        fourty_7.setText(substr_fourty_7);
                    }
//                    String substr_fourty_7 = main_str_et.substring(start_fourty_7, end_fourty_7).trim().replace("[", " ");
//                    if (substr_fourty_7.equals(null)) {
//                        fourty_7.setText("NULL");
//                    } else {
//                        fourty_7.setText(substr_fourty_7);
//                    }
                    String substr_mf = main_str.substring(380, 401);
                    MF.setText(substr_mf);

                    String substr_mcv = main_str.substring(401, 409);
                    mcv.setText(substr_mcv);
                    int x11 = main_str_et.indexOf("MCV");
                    int start_eight_2 = x11 + 3;
                    int y11 = main_str_et.indexOf("80");
                    int end_eight_2 = y11;
                    if (end_eight_2 - start_eight_2 == 0) {
                        String substr_eight_2 = "NULL";
                        eight_2.setText(substr_eight_2);
                    } else {
                        String substr_eight_2 = main_str_et.substring(start_eight_2, end_eight_2).trim().replace("[", " ");
                        eight_2.setText(substr_eight_2);
                    }
//                    String substr_eight_2 = main_str_et.substring(start_eight_2, end_eight_2).trim().replace("[", " ");
//
//                    if (substr_eight_2.equals(null)) {
//                        eight_2.setText("NULL");
//                    } else {
//                        eight_2.setText(substr_eight_2);
//                    }
                    String substr_eighty = main_str.substring(426, 446);
                    eighty.setText(substr_eighty);

                    String substr_mch = main_str.substring(446, 454);
                    mch.setText(substr_mch);
                    int x13 = main_str_et.indexOf("MCH");
                    int start_twenty_7 = x13 + 3;
                    int y13 = main_str_et.indexOf("27");
                    int end_twenty_7 = y13;
                    if (end_twenty_7 - start_twenty_7 == 0) {
                        String substr_twenty_7 = "NULL";
                        twenty_7.setText(substr_twenty_7);
                    } else {
                        String substr_twenty_7 = main_str_et.substring(start_twenty_7, end_twenty_7).trim().replace("[", " ");
                        twenty_7.setText(substr_twenty_7);
                    }
//                    String substr_twenty_7 = main_str_et.substring(start_twenty_7, end_twenty_7).trim().replace("[", " ");
//                    if (substr_twenty_7.equals(null)) {
//                        twenty_7.setText("NULL");
//                    } else {
//                        twenty_7.setText(substr_twenty_7);
//                    }
                    String substr_twenty_7_pg = main_str.substring(463, 475);
                    twenty_7_pg.setText(substr_twenty_7_pg);

                    String substr_mchc = main_str.substring(475, 486);
                    mchc.setText(substr_mchc);
                    int x14 = main_str_et.indexOf("MCHC");
                    int start_thirty_3 = x14 + 4;
                    int y14 = main_str_et.indexOf("32-36");
                    int end_thirty_3 = y14;
                    if (end_thirty_3 - start_thirty_3 == 0 || start_thirty_3 - end_thirty_3 <= 0) {
                        String substr_thirty_3 = "NULL";
                        thirty_3.setText(substr_thirty_3);
//                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    } else {
                        String substr_thirty_3 = main_str_et.substring(start_thirty_3, end_thirty_3).trim().replace("[", " ");
                        thirty_3.setText(substr_thirty_3);
//                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
//                    String substr_thirty_3 = main_str_et.substring(start_thirty_3, end_thirty_3).trim().replace("[", " ");
//                    if (substr_thirty_3.equals(null)) {
//                        thirty_3.setText("NULL");
//                    } else {
//                        thirty_3.setText(substr_thirty_3);
//                    }
                    String substr_thirty_2 = main_str.substring(494, 505);
                    thirty_2.setText(substr_thirty_2);

                    String substr_rdw = main_str.substring(505, 511);
                    rdw.setText(substr_rdw);
                    int x15 = main_str_et.indexOf("RDW");
                    int start_thirteen = x15 + 3;
                    int y15 = main_str_et.indexOf("11.0");
                    int end_thirteen = y15;
                    if (end_thirteen - start_thirteen == 0) {
                        String substr_thirteen = "NULL";
                        thirteen.setText(substr_thirteen);
                    } else {
                        String substr_thirteen = main_str_et.substring(start_thirteen, end_thirteen).trim().replace("[", " ");
                        thirteen.setText(substr_thirteen);
                    }
//                    String substr_thirteen = main_str_et.substring(start_thirteen, end_thirteen).trim().replace("[", " ");
//                    if (substr_thirteen.equals(null)) {
//                        thirteen.setText("NULL");
//                    } else {
//                        thirteen.setText(substr_thirteen);
//                    }
                    String substr_ele = main_str.substring(517, 530);
                    ele.setText(substr_ele);

                    String substr_total_count = main_str.substring(530, 550);
                    total_count.setText(substr_total_count);
                    int x16 = main_str_et.indexOf("let Count");
                    int start_cmm3 = x16 + 9;
                    int y16 = main_str_et.indexOf("1,50");
                    int end_cmm3 = y16;
                    if (end_cmm3 - start_cmm3 == 0) {
                        String substr_cmm3 = "NULL";
                        cmm3.setText(substr_cmm3);
                    } else {
                        String substr_cmm3 = main_str_et.substring(start_cmm3, end_cmm3).trim();
                        cmm3.setText(substr_cmm3);
                    }
//                    String substr_cmm3 = main_str_et.substring(start_cmm3, end_cmm3).trim();
//                    if (substr_cmm3.equals(null)) {
//                        cmm3.setText("NULL");
//                    } else {
//                        cmm3.setText(substr_cmm3);
//                    }
                    String substr_cmm15 = main_str.substring(566, 591);
                    cmm15.setText(substr_cmm15);

                    String substr_diff = main_str.substring(591, 604);
                    diff.setText(substr_diff);
                    String substr_l_count = main_str.substring(604, 630);
                    l_count.setText(substr_l_count);
//                    String substr_abs = main_str.substring(619, 630);
//                     abs.setText(substr_abs);

                    String substr_abs_eo = main_str.substring(630, 655);
                    abs_eo.setText(substr_abs_eo);
                    int x17 = main_str_et.indexOf("Absolute Eosinophil Count");
                    int start_two_two_0 = x17 + 25;
                    int y17 = main_str_et.indexOf("0-45");
                    int end_two_two_0 = y17;
                    if (end_two_two_0 - start_two_two_0 == 0) {
                        String substr_two_two_0 = "NULL";
                        two_two_0.setText(substr_two_two_0);
                    } else {
                        String substr_two_two_0 = main_str_et.substring(start_two_two_0, end_two_two_0).trim();
                        two_two_0.setText(substr_two_two_0);
                    }
//                    String substr_two_two_0 = main_str_et.substring(start_two_two_0, end_two_two_0).trim();
//                    if (substr_two_two_0.equals(null)) {
//                        two_two_0.setText("NULL");
//                    } else {
//                        two_two_0.setText(substr_two_two_0);
//                    }
                    String substr_four_50 = main_str.substring(666, 678);
                    four_50.setText(substr_four_50);

                    String substr_abs_ly = main_str.substring(678, 703);
                    abs_ly.setText(substr_abs_ly);
                    int x18 = main_str_et.indexOf("hocyte Count");
                    int start_two18 = x18 + 12;
                    int y18 = main_str_et.indexOf("1000");
                    int end_two18 = y18;
                    if (end_two18 - start_two18 == 0) {
                        String substr_two18 = "NULL";
                        two18.setText(substr_two18);
                    } else {
                        String substr_two18 = main_str_et.substring(start_two18, end_two18).trim();
                        two18.setText(substr_two18);
                    }
//                    String substr_two18 = main_str_et.substring(start_two18, end_two18).trim();
//                    if (substr_two18.equals(null)) {
//                        two18.setText("NULL");
//                    } else {
//                        two18.setText(substr_two18);
//                    }
                    String substr_four80 = main_str.substring(713, 728);
                    four80.setText(substr_four80);

                    String substr_absmo = main_str.substring(728, 751);
                    absmo.setText(substr_absmo);
                    int x19 = main_str_et.indexOf("nocyte Count");
                    int start_five20 = x19 + 12;
                    int y19 = main_str_et.indexOf("0-8");
                    int end_five20 = y19;
                    if (end_five20 - start_five20 == 0) {
                        String substr_five20 = "NULL";
                        five20.setText(substr_five20);
                    } else {
                        String substr_five20 = main_str_et.substring(start_five20, end_five20).trim();
                        five20.setText(substr_five20);
                    }
//                    String substr_five20 = main_str_et.substring(start_five20, end_five20).trim();
//                    if (substr_five20.equals(null)) {
//                        five20.setText("NULL");
//                    } else {
//                        five20.setText(substr_five20);
//                    }
                    String substr_eight00 = main_str.substring(760, 771);
                    eight00.setText(substr_eight00);

                    String substr_absne = main_str.substring(771, 796);
                    absne.setText(substr_absne);
                    int x20 = main_str_et.indexOf("rophil Count");
                    int start_three29 = x20 + 12;
                    int y20 = main_str_et.indexOf("1800");
                    int end_three29 = y20;
                    if (end_three29 - start_three29 == 0 || start_three29 - end_three29 <= 0) {
                        String substr_three29 = "NULL";
                        three29.setText(substr_three29);
                    } else {
                        String substr_three29 = main_str_et.substring(start_three29, end_three29).trim();
                        three29.setText(substr_three29);
                    }
//                    String substr_three29 = main_str_et.substring(start_three29, end_three29).trim();
//                    if (substr_three29.equals(null)) {
//                        three29.setText("NULL");
//                    } else {
//                        three29.setText(substr_three29);
//                    }
                    String substr_one80 = main_str.substring(806, 821);
                    one80.setText(substr_one80);

                    String substr_imm = main_str.substring(821, 841);
                    imm.setText(substr_imm);
                    int x21 = main_str_et.indexOf("nulocyte");
                    int start_ten = x21 + 8;
                    int y21 = main_str_et.indexOf("10-5");
                    int end_ten = y21;
                    if (end_ten - start_ten == 0) {
                        String substr_ten = "NULL";
                        ten.setText(substr_ten);
                    } else {
                        String substr_ten = main_str_et.substring(start_ten, end_ten).trim();
                        ten.setText(substr_ten);
                    }
//                    String substr_ten = main_str_et.substring(start_ten, end_ten).trim();
//                    if (substr_ten.equals(null)) {
//                        ten.setText("NULL");
//                    } else {
//                        ten.setText(substr_ten);
//                    }
                    String substr_ten5 = main_str.substring(849, 861);
                    ten5.setText(substr_ten5);
                } else {
                    Toast.makeText(MainActivity.this, "Please Select Appropriate Image", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addImage) {
            showImageImportDialog();
        }

        if (id == R.id.settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showImageImportDialog() {
        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("select Image")
                .setItems(items, (dialog1, which) -> {
                    if (which == 0) {
                        if (!checkCameraPermission()) {
                            requestCameraPermission();
                        } else {
                            pickCamera();
                        }
                    }
                    if (which == 1) {
                        if (!checkStoragePermission()) {
                            requestStoragePermission();
                        } else {
                            pickGallery();
                        }
                    }
                });
        dialog.create().show();
    }

    private void pickGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);

    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] == PERMISSION_GRANTED;

                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] == PERMISSION_GRANTED;

                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {

                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
                button.setVisibility(View.VISIBLE);

            }

            if (requestCode == IMAGE_PICK_CAMERA_CODE) {

                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
                button.setVisibility(View.VISIBLE);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                mPreviewIv.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> item = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < item.size(); i++) {
                        TextBlock myItem = item.valueAt(i);
                        sb.append(myItem.getValue().trim());
                        sb.append("\n");
                    }

                    mResultEt.setText(sb.toString());
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
