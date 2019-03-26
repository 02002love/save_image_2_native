package com.imsong.saveimage2nativeexample;

import android.content.Context;
import android.os.Bundle;

import com.imsong.saveimage2native.SaveImage2NativePlugin;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);
  }
}
