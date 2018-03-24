package dnejad.marjan.rxdebounce

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.*
import retrofit2.Retrofit

import java.io.IOException
import java.util.concurrent.TimeUnit

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * Created by Marjan.Dnejad
 * on 3/24/2018.
 */

class ServiceGenerator() {
    val URL = "http://10.0.2.2:23947/"
    private var apiService: TaskService

    init {
        val okClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                            //cues it want to run as local in local server
                            .header("Host", "localhost")
                            .method(original.method(), original.body())

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .connectTimeout(5, TimeUnit.MINUTES) // for handle timeout exception
                .readTimeout(5, TimeUnit.MINUTES)
                .build()

        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create()

        val restAdapter = Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okClient)
                .build()

        apiService = restAdapter.create(TaskService::class.java)
    }

    fun getService(): TaskService {
        return apiService
    }
}

interface TaskService {
    @FormUrlEncoded
    @POST("AccountManager/checkUsername")
     fun checkUsername( @Field("username") username: String): Observable<ResponseBody>
}