[![](https://jitpack.io/v/pm-coffee/Android-RecyclerPager.svg)](https://jitpack.io/#pm-coffee/Android-RecyclerPager)


# Android-RecyclerPager

* This library is a library for using RecyclerView like ViewPager
* This is a library based on [lsjwzh/RecyclerViewPager](https://github.com/lsjwzh/RecyclerViewPager).
* This lib is using [PagerSnapHelper | Android Developers](https://developer.android.com/reference/android/support/v7/widget/PagerSnapHelper)
* This lib is only have the minimum functionality
    * Support TabLayout
    * Support Vertical and Horizontal orientation.
    * Support Use fragment as recycler item
    
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
        implementation 'com.github.pm-coffee:Android-RecyclerPager:0.0.1'
    }
    ```

# Usage

yet

# Known Issues

* When there is an Item with a vertical RecyclerView inside it is not good enough with CoordinatorLayout. (poor operability)

