# SmartIntent

SmartIntent is a simple Intent wrapper that adds type safety for passing your data between Android activities in Kotlin.

A simple example:
```kotlin
SmartIntent.startActivity(this, SomeActivity::class.java, SomeActivity::testString to editText.text.toString)
```

Basically, this library allows you to specify a custom initialization block for your new activity while allowing you to pass data safely to the new activity.

To execute the code in your startActivity, do this in your SomeActivity:
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    // ...

    SmartIntent.unwrapIntent(this)
    // testString is now set in this class to the correct value

    // ...
}
```

## Installation Instructions
This library can be used in your project through Gradle:

Just add the dependency:
```gradle
dependencies {
    compile 'me.akatkov.smartintent:smartintent:0.2'
}
```

## Coming Soon

There is progress being made on a SmartBundle class as well that will allow you to do a similar thing with Bundle during ```onSaveInstanceState```.

Example:
```kotlin
override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    super.onRestoreInstanceState(savedInstanceState)
    
    if (savedInstanceState != null) {
        SmartBundle.unwrapBundle(this, savedInstanceState)
        // do any setup now that properties are set
    }
}

override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    SmartBundle.saveInstanceState(this, outState, SecondActivity::testString to testString)
}
```

## TODO
- Decide if init blocks should stay
- More robust testing
