package br.unisc.pdm.trabalhodispositivos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.data.Contents;
import br.unisc.pdm.trabalhodispositivos.qrcode.QRCodeEncoder;

public class EncoderActivity extends Activity {

    private static final String TAG = EncoderActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encoder);

        // This assumes the view is full screen, which is a good assumption
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 7 / 8;

        try {
            QRCodeEncoder qrCodeEncoder = null;
            // qrCodeEncoder = new QRCodeEncoder("AT", null, Contents.Type.TEXT,
            // BarcodeFormat.CODABAR.toString(), smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("HI", null, Contents.Type.TEXT,
            // BarcodeFormat.CODE_39.toString(), smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("Hello", null,
            // Contents.Type.TEXT, BarcodeFormat.CODE_128.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("1234567891011", null,
            // Contents.Type.TEXT, BarcodeFormat.EAN_13.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("12345678", null,
            // Contents.Type.TEXT, BarcodeFormat.EAN_8.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("1234", null,
            // Contents.Type.TEXT, BarcodeFormat.ITF.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("2345", null,
            // Contents.Type.TEXT, BarcodeFormat.PDF_417.toString(),
            // smallerDimension);
            qrCodeEncoder = new QRCodeEncoder("Evento secreto - 1234", null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("12345678910", null,
            // Contents.Type.TEXT, BarcodeFormat.UPC_A.toString(),
            // smallerDimension);

            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView view = (ImageView) findViewById(R.id.image_view);
            view.setImageBitmap(bitmap);

            TextView contents = (TextView) findViewById(R.id.contents_text_view);
            contents.setText(qrCodeEncoder.getDisplayContents());
            setTitle(getString(R.string.app_name) + " - " + qrCodeEncoder.getTitle());
        } catch (WriterException e) {
            Log.e(TAG, "Could not encode barcode", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Could not encode barcode", e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_encoder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
