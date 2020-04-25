# Retrofit LiveData Adapter Retrofit

[ ![Download](https://api.bintray.com/packages/taracha/live-data/RetrofitLiveDataCallAdapter/images/download.svg?version=1.0.0) ](https://bintray.com/taracha/live-data/RetrofitLiveDataCallAdapter/1.0.0/link)

This is a simple Retrofit Adapter for Android LiveData that you can easily integrate in your applications.

**Original Credit:** I leverage the classes from one of the [Android Architecture Google Samples](https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample).
This is Google's recommended way of architecting your apps if you choose to leverage [Architecture Components](https://developer.android.com/topic/libraries/architecture), especially LiveData.

## Set Up
To use the library you need to do the following steps:

**[1]** Add the following gradle dependency in your `app/build.gradle` file

```gradle
dependencies {
  implementation 'com.thedancercodes:livedatalibrary:1.0.1'
}
```

**[2]** Ensure that your Retrofit dependency is accessible from your project level `build.gradle` file. This is because the library leverages this dependency. Declare it as follows:
```gradle
ext {
    retrofit = '2.6.0' // This is the version of Retrofit you are using in your project.
}
```

## Permissions
Don't forget to add the following permission:

```
<uses-permission android:name="android.permission.INTERNET"/>
```

## Usage
Set the `LiveDataCallAdapterFactory` in the Retrofit Builder

```
Retrofit.Builder()
        .baseUrl(BASE_URL)
        // ....
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        // ....
        .build()
```

Update the your **ApiService** from the **Original Way** to the **New Way** as shown below:
#### Original Way - with `Call`:

```
interface PostService {

    @GET("posts")
    fun getPosts(): Call<Post>
}
```

#### New Way - with `LiveData`:

```
interface PostService {

    @GET("posts")
    fun getPosts(): LiveData<ApiResponse<Post>>
}
```

---
**NOTE:** Your service methods can now use `LiveData` as their return type. 👆🏾

Also, note the usage of the `ApiResponse` object.
It is a common class used by API responses when you want verify what is happening on your network call, so that you can ideally respond to the `Api Response`.
See the example implementation below: 👇🏾


```kotlin
    postViewModel.finalPosts.observe(this, Observer { response ->

        when(response) {
            is ApiResponse.ApiSuccessResponse -> {
                // Set new data into your adapter
                adapter.setPosts(response.body)
            }

            is ApiResponse.ApiErrorResponse -> {
              // Display the Error message in a Snack Bar.
              val snack = Snackbar
                  .make(recyclerview, response.errorMessage, Snackbar.LENGTH_LONG)
              snack.show()
            }

            is ApiResponse.ApiEmptyResponse -> {
              // Display the message for an Empty Response in a Snack Bar.
              val snack = Snackbar
                  .make(recyclerview, "Empty Response", Snackbar.LENGTH_LONG)
              snack.show()
            }
        }
    })
```

## License
```text
MIT License

Copyright (c) 2020 Roger Taracha

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
