# Calculator App (Java, Android)

A simple, clean calculator app built in Java for Android — supports add, subtract,
multiply, divide, percent, sign toggle, and clear/delete.

## How to open

1. Open **Android Studio**.
2. Choose **File > Open**, and select this `CalculatorApp` folder.
3. Let Gradle sync (it will download the wrapper automatically).
4. Click **Run** to install it on an emulator or device.

## Project structure

```
CalculatorApp/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/calculator/MainActivity.java
│       └── res/
│           ├── layout/activity_main.xml
│           ├── values/ (strings.xml, colors.xml, styles.xml)
│           ├── drawable/ (launcher icon shapes)
│           └── mipmap-anydpi-v26/ (adaptive launcher icon)
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Notes

- Minimum SDK: 21 (Android 5.0+)
- Target/Compile SDK: 34
- No external calculator libraries — all logic is in `MainActivity.java`
- If Android Studio asks to update the Gradle version or Android Gradle Plugin,
  accepting the suggestion is fine.

  I have modifid to check the git diff and git status

0065a6a2ff16a2f96e89f39f16dbf43ab57e9f65