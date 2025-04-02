package ru.mirea.expense.presentation

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import ru.mirea.uikit.AppScaffold
import java.io.File
import java.io.IOException

@Composable
fun ExpenseScreen() {
    AppScaffold {
        Test()
    }
}

@Composable
fun Test(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    var tempImageUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            selectedImageUri = if (success) {
                tempImageUri
            } else {
                null
            }
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                    Text(text = "Pick one photo")
                }
                Button(onClick = {
                    tempImageUri = createImageUri(context)
                    cameraLauncher.launch(tempImageUri!!)
                }) {
                    Text("Открыть камеру")
                }
            }
        }

        item {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }

    selectedImageUri?.let { scanQrFromUri(context, it) }
}

fun createImageUri(context: Context): Uri {
    val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}

fun scanQrFromUri(context: Context, imageUri: Uri) {
    try {
        val bitmap = getBitmapFromUri(context, imageUri) ?: return
        val result = decodeQrCode(bitmap)
        result?.let {
            Log.d("QRScanner", "QR Code: $it")
        } ?: Log.d("QRScanner", "QR Code not found")
    } catch (e: Exception) {
        Log.e("QRScanner", "Error scanning QR code", e)
    }
}

private fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

private fun decodeQrCode(bitmap: Bitmap): String? {
    val intArray = IntArray(bitmap.width * bitmap.height)
    bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    val luminanceSource: LuminanceSource = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
    val binaryBitmap = BinaryBitmap(HybridBinarizer(luminanceSource))
    return try {
        MultiFormatReader().decode(binaryBitmap).text
    } catch (e: Exception) {
        null
    }
}


@Preview
@Composable
private fun ExpenseScreenPreview() {
    ExpenseScreen()
}

