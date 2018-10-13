package com.example.sadic.travelerapp.ui.payment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.ui.search.SearchActivity;
import com.example.sadic.travelerapp.utils.SharedPref;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecepitActivity extends AppCompatActivity {
    private static final String TAG = "RecepitActivity";

    private static final String username = "dragostoiann@gmail.com";
    private static final String password = "Darkn3ss";

    File mFile;
    //Multipart multipart;


    @BindView(R.id.tvId)
    TextView tvId;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.ivQRCode)
    ImageView ivQRCode;
    @BindView(R.id.tvRecepit)
    TextView tvRecepit;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.btGoSearch)
    Button btGoSearch;
    @BindView(R.id.tvDeparture)
    TextView tvDeparture;

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepit);
        ButterKnife.bind(this);
        SharedPref.init(this);
        email = SharedPref.read(SharedPref.EMAIL, "");



        //get Intent
        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Drawable d = ivQRCode.getDrawable();
        BitmapDrawable bitDw = ((BitmapDrawable) d);
        Bitmap bitmap = bitDw.getBitmap();
        mFile = savebitmap(bitmap);

        String from = email;
        String recipients = email;
        String subject = "TravelerApp";
        String text = "Trip ID: " + tvId.getText().toString() + "\n"
                + "Reservation Name: " + tvName.getText().toString() + "\n"
                + tvDeparture.getText().toString() + "\n"
                + tvAmount.getText().toString();
        String filename = mFile.toString();


        try {
            Message m = buildMessage(createSessionObject(), from, recipients, subject, text, filename);
            new RecepitActivity.SendMailTask().execute(m);
            Log.d(TAG, "onCreate: email sending**********************");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



//    private void sendEmail() {
//        Session session = createSessionObject();
//
//        try {
//            Message message = createMessage(session);
//
//            try {
//                addAttachment(mFile.toString());
//                message.setContent(multipart);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            new SendMailTask().execute(message);
//        } catch (AddressException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public void addAttachment(String filename) throws Exception {
//        BodyPart messageBodyPart = new MimeBodyPart();
//        DataSource source = new FileDataSource(filename);
//        messageBodyPart.setDataHandler(new DataHandler(source));
//        messageBodyPart.setFileName(filename);
//
//        //multipart.addBodyPart(messageBodyPart);
//    }

//    private Message createMessage(Session session) throws MessagingException, UnsupportedEncodingException {
//
//        String messageBody = "Trip ID: " + tvId.getText().toString() + "\n"
//                + "Reservation Name: " + tvName.getText().toString() + "\n"
//                + tvDeparture.getText().toString() + "\n"
//                + tvAmount.getText().toString();
//
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress("dragostoiann@gmail.com", "Dragos Stoian"));
//        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
//        message.setSubject("TravelerApp");
//        message.setText(messageBody);
//        return message;
//    }

//    private Session createSessionObject() {
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//
//        return Session.getInstance(properties, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//    }


    private static Message buildMessage(Session session, String from, String recipients,
                                        String subject, String text, String filename) throws MessagingException, AddressException
    {
        Log.d(TAG, "buildMessage: ***********************building message");

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        message.setSubject(subject);

        BodyPart messageTextPart = new MimeBodyPart();
        messageTextPart.setText(text);

        BodyPart messageAttachmentPart = new MimeBodyPart();
        DataSource source = new FileDataSource(new File(filename));
        messageAttachmentPart.setDataHandler(new DataHandler(source));
        messageAttachmentPart.setFileName(filename);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageTextPart);
        multipart.addBodyPart(messageAttachmentPart);
        message.setContent(multipart);

        return message;
    }

    private Session createSessionObject() {
        Log.d(TAG, "createSessionObject: session created*********************");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("shiv@rjtcompuquest.com", "SKR+90501");
            }
        });
    }

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory, "temp" + ".png");
        Log.d(TAG, "savebitmap: " + file.toString());
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp" + ".png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            tvName.setText(SharedPref.read(SharedPref.DISPLAY_NAME, ""));
            tvId.setText("Transaction: " + response.getString("id"));
            tvAmount.setText("Amount: " + paymentAmount + " $");
            tvStatus.setText("State: " + response.getString("state"));
            tvDeparture.setText("Departure: " + SharedPref.read(SharedPref.ROUTE_DEPARTURE, ""));

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter
                    .encode(response.getString("id"), BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQRCode.setImageBitmap(bitmap);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btGoSearch)
    public void onViewClicked() {
        Intent i = new Intent(RecepitActivity.this, SearchActivity.class);
        startActivity(i);
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(RecepitActivity.this, "Please wait",
                    "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
