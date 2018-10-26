[![](https://jitpack.io/v/pm-coffee/Android-RecyclerPager.svg)](https://jitpack.io/#pm-coffee/Android-RecyclerPager)


# Android-RecyclerPager

* This library is a library for using RecyclerView like ViewPager
* This is a library based on [lsjwzh/RecyclerViewPager](https://github.com/lsjwzh/RecyclerViewPager).
* This lib is using [PagerSnapHelper | Android Developers](https://developer.android.com/reference/android/support/v7/widget/PagerSnapHelper)
* This lib is only have the minimum functionality
    * Support TabLayout
    * Support Vertical and Horizontal orientation.
# How to import

* Step 1. Add the JitPack repository to your build file
    
    Add it in your `root build.gradle` at the end of repositories:
    
    ```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    ```

* Step 2. Add the dependency

    ```groovy
    dependencies {
        implementation 'com.github.pm-coffee:Android-RecyclerPager:0.0.2'
    }
    ```

# Usage

## XML

```xml
    <com.github.pmcoffee.android.recyclerpager.RecyclerPager
        android:id="@+id/recyclerPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal"
        app:layoutManager="android.support.v7.widget.LinearLayoutManager"
        />
```

* Required Attribute and Value
   * `android:orientation `
      * `horizontal` or `vertical`
   * `app:layoutManager="android.support.v7.widget.LinearLayoutManager"`

## TablayoutSupport

```java
TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, mAdapter);
```


# Known Issues

* When there is an Item with a vertical RecyclerView inside it is not good enough with CoordinatorLayout. (poor operability)

