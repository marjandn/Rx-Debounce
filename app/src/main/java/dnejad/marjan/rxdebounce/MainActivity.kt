package dnejad.marjan.rxdebounce

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import rx.Observable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configUserNameDebounce()
        
    }

    /*
    * when use android.widget.EditText use this process
    *
    * read more about RxJava Debounce operator here http://reactivex.io/documentation/operators/debounce.html
    *
    * only emit an item from an Observable if a particular timespan has passed without it emitting another item
    */
    private fun configUserNameDebounce() {
        val obs: Observable<String>

        obs = RxTextView.textChanges(edtUserName).filter { charSequence ->
            Log.e("filter run  ", charSequence.toString())
            charSequence.length >= 3
        }
        //if  you had this error
        //Caused by: android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
        // you should use debounce operator like this .debounce(3000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .debounce(3000, TimeUnit.MILLISECONDS) 
                .map { charSequence ->
                    Log.e("map run  ", charSequence.toString())
                    charSequence.toString()
                }
        obs.subscribe { s ->
            Log.e("obs.subscribe run  ", s)
            checkUsername(s)
        }
    }

    private fun checkUsername(s: String) {
        val serviceGenerator = ServiceGenerator()
        serviceGenerator.getService().checkUsername(s)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<ResponseBody>() {
                    override fun onComplete() {
                        log("get data from server Complete...!!")
                    }

                    override fun onNext(t: ResponseBody) {
                        if (t.string() == "valid")
                            setValidUsername()
                        else
                            setInValidUsername()
                    }

                    override fun onError(e: Throwable) {
                        toast(e.toString())
                    }

                })
    }

    private fun setInValidUsername() {
        tvUsernameStatus.text = " this username is available"
    }

    private fun setValidUsername() {
        tvUsernameStatus.text=" this username is invalid"
    }
}
