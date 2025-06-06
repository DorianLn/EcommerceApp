package fr.epf.min2.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QrCodeScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scannez un QR code")
        integrator.setCameraId(0) // apparamment, pour utiliser la caméra arrière
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val productId = result.contents
                if (productId != null) {
                    Log.d("QrCodeScannerActivity", "Scanned productId = $productId")
                    val intent = Intent(this, ProductDetailActivity::class.java).apply {
                        putExtra("qr_product_id", productId)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "ID produit invalide", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
        finish()
    }

}
