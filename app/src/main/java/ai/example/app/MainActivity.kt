// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package ai.example.app

<<<<<<< HEAD
import ai.example.app.databinding.ActivityMainBinding
=======
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
import ai.onnxruntime.*
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
<<<<<<< HEAD
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.os.Parcelable
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
=======
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
<<<<<<< HEAD
import androidx.viewbinding.BuildConfig.LIBRARY_PACKAGE_NAME
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileDescriptor
=======
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.File
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
import java.lang.Runnable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val backgroundExecutor: ExecutorService by lazy { Executors.newSingleThreadExecutor() }
    private val labelData: List<String> by lazy { readLabels() }
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

<<<<<<< HEAD
    private lateinit var binding: ActivityMainBinding

=======
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
    private var ortEnv: OrtEnvironment? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalysis: ImageAnalysis? = null
    private var imgPath: String = ""
<<<<<<< HEAD
    private var photoUri: Uri? = null
    private var imageUri: Uri? = null

    private var launcher: ActivityResultLauncher<Intent>? = null
    private var imageChooser: Intent? = null
=======
    private var imageUri: Uri? = null
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
    private var cameraMode: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photoUri = setImageUri()

        // Request Camera permission
        if (allPermissionsGranted()) {
            ortEnv = OrtEnvironment.getEnvironment()
            changeMode(true)
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                handleImageRequest(intent)
            }
        }
        picker_btn.setOnClickListener {
            if (allPermissionsGranted()) {
                changeMode(false)
                if(imageChooser == null)
                    imageChooser = getPickImageIntent()
                launcher?.launch(imageChooser)
=======
        setContentView(R.layout.activity_main)
        // Request Camera permission
        if (allPermissionsGranted()) {
            ortEnv = OrtEnvironment.getEnvironment()
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        picker_btn.setOnClickListener {
            if (allPermissionsGranted()) {
                changeMode(false)
                chooseImage()
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
            }
        }

        camera_btn.setOnClickListener {
            if(allPermissionsGranted()){
                changeMode(true)
                startCamera()
            }
        }
    }

    fun changeMode(newMode: Boolean){
        if(newMode){
<<<<<<< HEAD
            binding.viewImage.setImageDrawable(null)
            binding.viewImage.visibility = GONE
            binding.previewImage.visibility = VISIBLE
        }else{
            binding.viewImage.visibility = VISIBLE
            binding.previewImage.visibility = GONE
        }
        cameraMode = newMode
=======
            img.visibility = GONE
            viewFinder.visibility = VISIBLE
        }else{
            img.visibility = VISIBLE
            viewFinder.visibility = GONE
        }
        cameraMode = newMode

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleImageRequest(data)
                }
            }
        }
    }

    private fun chooseImage() {
        startActivityForResult(getPickImageIntent(), 100)
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
    }

    private fun getPickImageIntent(): Intent? {
        var chooserIntent: Intent? = null
<<<<<<< HEAD
        var intentList: MutableList<Intent> = ArrayList()

        // photos from gallery
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        // take photo with camera app
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
=======

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598

        intentList = addIntentsToList(this, intentList, pickIntent)
        intentList = addIntentsToList(this, intentList, takePhotoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
<<<<<<< HEAD
                "Select an image"
=======
                "Selecione a foto"
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
            )
            chooserIntent!!.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    private fun setImageUri(): Uri {
<<<<<<< HEAD
        val folder = File("${getExternalFilesDir(null)}")
        val file = File(folder, "sample.jpg")
        imageUri = FileProvider.getUriForFile(
            this,
            "ai.example.app.fileProvider",
=======
        val folder = File("${getExternalFilesDir(Environment.DIRECTORY_DCIM)}")
        folder.mkdirs()

        Log.d("Chk", folder.absolutePath)

        val file = File(folder, "Image_Tmp.jpg")
        if (file.exists())
            file.delete()
        file.createNewFile()
        imageUri = FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID + ".fileProvider",
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
            file
        )
        imgPath = file.absolutePath
        return imageUri!!
    }

    private fun addIntentsToList(
        context: Context,
        list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    private fun handleImageRequest(data: Intent?) {
<<<<<<< HEAD
        if (data?.data != null){
            imageUri = data.data
        }else{
            imageUri = photoUri
        }

        binding.viewImage.setImageDrawable(null)
        binding.viewImage.setImageURI(imageUri)
=======

        if (data?.data != null){
            imageUri = data.data
        }

        Log.d("Chk", "It gets here")
        //val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        img.setImageURI(imageUri)
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598

        val bitmap = imageUri?.let { getBitmapFromUri(it) }
        if(bitmap != null){
            scope.launch {
                var predictor = ORTPredictor(createOrtSession(), ::updateUI)
                backgroundExecutor.execute { predictor.predict(bitmap) }
            }
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                    .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                    .build()
                    .also {
<<<<<<< HEAD
                        it.setSurfaceProvider(binding.previewImage.surfaceProvider)
=======
                        it.setSurfaceProvider(viewFinder.surfaceProvider)
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
                    }

            imageCapture = ImageCapture.Builder()
                    .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                    .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture, imageAnalysis
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

            setORTAnalyzer()
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getBitmapFromUri(uri: Uri, options: BitmapFactory.Options? = null): Bitmap? {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
<<<<<<< HEAD
        var image: Bitmap? = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        val exif = fileDescriptor?.let { ExifInterface(it) }
        image = exif?.let { image?.rotate(it) }
=======
        val image: Bitmap? = BitmapFactory.decodeFileDescriptor(fileDescriptor)
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
        parcelFileDescriptor?.close()
        return image
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundExecutor.shutdown()
        ortEnv?.close()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                        this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT
                ).show()
                finish()
            }

        }
    }

    private fun updateUI(result: Result) {
        if (result.detectedScore.isEmpty())
            return

        runOnUiThread {
<<<<<<< HEAD
            percent_meter.progress = (result.detectedScore[0] * 100).toInt()

            if(result.detectedIndices.isNotEmpty()){
                detected_item_1.text = labelData[result.detectedIndices[0]]
                detected_item_value_1.text = resources.getString(R.string.fpercent,
                    result.detectedScore[0] * 100)
            }

            if (result.detectedIndices.size > 1) {
                detected_item_2.text = labelData[result.detectedIndices[1]]
                detected_item_value_2.text = resources.getString(R.string.fpercent,
                    result.detectedScore[1] * 100)
=======
            percentMeter.progress = (result.detectedScore[0] * 100).toInt()
            detected_item_1.text = labelData[result.detectedIndices[0]]
            detected_item_value_1.text = "%.2f%%".format(result.detectedScore[0] * 100)

            if (result.detectedIndices.size > 1) {
                detected_item_2.text = labelData[result.detectedIndices[1]]
                detected_item_value_2.text = "%.2f%%".format(result.detectedScore[1] * 100)
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
            }

            if (result.detectedIndices.size > 2) {
                detected_item_3.text = labelData[result.detectedIndices[2]]
<<<<<<< HEAD
                detected_item_value_3.text = resources.getString(R.string.fpercent,
                    result.detectedScore[2] * 100)
            }

            inference_time_value.text = resources.getString(R.string.time_ms, result.processTimeMs)
=======
                detected_item_value_3.text = "%.2f%%".format(result.detectedScore[2] * 100)
            }

            inference_time_value.text = result.processTimeMs.toString() + "ms"
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
        }
    }

    // Read labels from file
    private fun readLabels(): List<String> {
        return resources.openRawResource(R.raw.labels).bufferedReader().readLines() // Change to your label file here
    }

    // Read ort model into a ByteArray, run in background
    private suspend fun readModel(): ByteArray = withContext(Dispatchers.IO) {
        val modelID = R.raw.model // Change to your model here
        resources.openRawResource(modelID).readBytes()
    }

    // Create a new ORT session in background
    private suspend fun createOrtSession(): OrtSession? = withContext(Dispatchers.Default) {
        ortEnv?.createSession(readModel())
    }

    // Create a new ORT session and then change the ImageAnalysis.Analyzer
    // This part is done in background to avoid blocking the UI
    private fun setORTAnalyzer(){
        scope.launch {
            imageAnalysis?.clearAnalyzer()
            imageAnalysis?.setAnalyzer(
                    backgroundExecutor,
                    ORTAnalyzer(createOrtSession(), ::updateUI)
            )
        }
    }

    companion object {
<<<<<<< HEAD
        const val TAG = "ORTImageClassifier"
=======
        public const val TAG = "ORTImageClassifier"
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
