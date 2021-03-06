# Android Image Classifier App

This repo is strongly based on https://github.com/microsoft/onnxruntime-inference-examples/tree/main/mobile/examples/image_classification/android.
Follow their steps to build and run the app.

The differences between both repositories are:
- You can pick an image from your gallery in this implementation;
- Quantization option removed.

To use your classifier, you must:

- convert your model to ORT format, rename it to "model.ort" and paste it to `app/src/main/res/raw/`;
- create a txt file named "labels.txt" with your labels at each line at the same order of the prediction index and paste it to `app/src/main/res/raw/`;

You can change the name of your App at `app/src/main/res/values/strings.xml`.

In order to use it in your phone, select **Build** -> **Build Bundle(s) / APK(s)** > **Build APK(s)**. 
The apk file will be located in `project-name/module-name/build/outputs/apk/`.

Example screenshot of a bird species predictor app trained with https://www.kaggle.com/gpiosenka/100-bird-species.

<img width=40% src="images/touchan.jpg" alt="App Screenshot"/>    <img width=40% src="images/ostrich.jpg" alt="App Screenshot"/>
