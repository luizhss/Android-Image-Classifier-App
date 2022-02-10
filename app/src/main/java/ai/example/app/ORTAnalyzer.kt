// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package ai.example.app

import ai.onnxruntime.*
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.util.*
import kotlin.math.exp
import kotlin.math.roundToInt


internal data class Result(
        var detectedIndices: List<Int> = emptyList(),
        var detectedScore: MutableList<Float> = mutableListOf<Float>(),
        var processTimeMs: Long = 0
) {}

// Get index of top 3 values
// This is for demo purpose only, there are more efficient algorithms for topK problems
fun getTop3(labelVals: FloatArray): List<Int> {
    var indices = mutableListOf<Int>()
    for (k in 0..2) {
        var max: Float = 0.0f
        var idx: Int = 0
        for (i in 0..labelVals.size - 1) {
            val label_val = labelVals[i]
            if (label_val > max && !indices.contains(i)) {
                max = label_val
                idx = i
            }
        }
<<<<<<< HEAD
        indices.add(idx)
    }
=======

        indices.add(idx)
    }

>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
    return indices.toList()
}

// Calculate the SoftMax for the input array
fun softMax(modelResult: FloatArray): FloatArray {
    val labelVals = modelResult.copyOf()
    val max = labelVals.max()
    var sum = 0.0f

    // Get the reduced sum
    for (i in labelVals.indices) {
        labelVals[i] = exp(labelVals[i] - max!!)
        sum += labelVals[i]
    }
<<<<<<< HEAD
=======

>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
    if (sum != 0.0f) {
        for (i in labelVals.indices) {
            labelVals[i] /= sum
        }
    }
<<<<<<< HEAD
    return labelVals
}

=======

    return labelVals
}

// Rotate the image of the input bitmap
fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
internal class ORTPredictor(
    private val ortSession: OrtSession?,
    private val callBack: (Result) -> Unit
) {
<<<<<<< HEAD

    fun predict(imgBitmap: Bitmap) {
        // Convert the input image to bitmap and resize to 224x224 for model input
        val bitmap = imgBitmap?.let { Bitmap.createScaledBitmap(it, 224, 224, false) }
=======
    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    fun predict(imgBitmap: Bitmap) {
        // Convert the input image to bitmap and resize to 224x224 for model input
        val cropBitmap = imgBitmap?.let { Bitmap.createScaledBitmap(it, 224, 224, false) }
        val exif: ExifInterface? = null
        val orientation: Int? = exif?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        var rotation : Float = 0.0f
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90.0f
            ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180.0f
            ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270.0f
        }
        val bitmap = cropBitmap?.rotate(rotation)

>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
        if (bitmap != null) {
            var result = Result()

            val imgData = preProcess(bitmap)
            val inputName = ortSession?.inputNames?.iterator()?.next()
            val shape = longArrayOf(1, 3, 224, 224)
            val env = OrtEnvironment.getEnvironment()
            env.use {
                val tensor = OnnxTensor.createTensor(env, imgData, shape)
                val startTime = SystemClock.uptimeMillis()
                tensor.use {
                    val output = ortSession?.run(Collections.singletonMap(inputName, tensor))
                    output.use {
                        result.processTimeMs = SystemClock.uptimeMillis() - startTime
                        @Suppress("UNCHECKED_CAST")
                        val rawOutput = ((output?.get(0)?.value) as Array<FloatArray>)[0]
                        val probabilities = softMax(rawOutput)
                        result.detectedIndices = getTop3(probabilities)
                        for (idx in result.detectedIndices) {
                            result.detectedScore.add(probabilities[idx])
                        }
                    }
                }
            }
            callBack(result)
        }
<<<<<<< HEAD
=======

>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
        ortSession?.close()
    }
}

internal class ORTAnalyzer(
        private val ortSession: OrtSession?,
        private val callBack: (Result) -> Unit
) : ImageAnalysis.Analyzer {

<<<<<<< HEAD
    // Rotate the image of the input bitmap
    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

=======
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
    override fun analyze(image: ImageProxy) {
        // Convert the input image to bitmap and resize to 224x224 for model input
        val imgBitmap = image.toBitmap()
        val rawBitmap = imgBitmap?.let { Bitmap.createScaledBitmap(it, 224, 224, false) }
        val bitmap = rawBitmap?.rotate(image.imageInfo.rotationDegrees.toFloat())

        if (bitmap != null) {
            var result = Result()

            val imgData = preProcess(bitmap)
            val inputName = ortSession?.inputNames?.iterator()?.next()
            val shape = longArrayOf(1, 3, 224, 224)
            val env = OrtEnvironment.getEnvironment()
            env.use {
                val tensor = OnnxTensor.createTensor(env, imgData, shape)
                val startTime = SystemClock.uptimeMillis()
                tensor.use {
                    val output = ortSession?.run(Collections.singletonMap(inputName, tensor))
                    output.use {
                        result.processTimeMs = SystemClock.uptimeMillis() - startTime
<<<<<<< HEAD
                        if(output != null){
                            val rawOutput = ((output?.get(0)?.value) as Array<FloatArray>)[0]
                            val probabilities = softMax(rawOutput)
                            result.detectedIndices = getTop3(probabilities)
                            for (idx in result.detectedIndices) {
                                result.detectedScore.add(probabilities[idx])
                            }
=======
                        @Suppress("UNCHECKED_CAST")
                        Log.d("Chk", output?.get(0).toString())
                        val rawOutput = ((output?.get(0)?.value) as Array<FloatArray>)[0]
                        val probabilities = softMax(rawOutput)
                        result.detectedIndices = getTop3(probabilities)
                        for (idx in result.detectedIndices) {
                            result.detectedScore.add(probabilities[idx])
>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
                        }
                    }
                }
            }
            callBack(result)
        }
<<<<<<< HEAD
=======

>>>>>>> e9d7ca3dd6b69d9dde53cfe340fa70e4d6679598
        image.close()
    }

    // We can switch analyzer in the app, need to make sure the native resources are freed
    protected fun finalize() {
        ortSession?.close()
    }
}