# SmartIntent

SmartIntent is a simple Intent wrapper that adds type safety for passing your data between Android activities in Kotlin.

A simple example:
```kotlin
SmartIntent(this, SomeActivity::class.java).startActivity {
    // testString is a property on SomeActivity
    testString = editText.text.toString()
}
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
    compile 'me.akatkov.smartintent:smartintent:0.1'
}
```

## Coming Soon

There is progress being made on a SmartBundle class as well that will allow you to do a similar thing with Bundle during ```onSaveInstanceState```.

Example:
```kotlin
override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    SmartBundle(this, outState).saveInstanceState {
        // supports properties
        save(SecondActivity::testString) { value ->
            // this is called when it is restored
            testString = value
            textView.text = "Restored value: " + testString
        }
        
        // also supports not properties
        save("keyForBundle", valueToStore) { value ->
            // do something with valueToStore
        }
    }
}
```

## TODO
- Finish adding support for all of the Bundle put* methods
- More robust testing
