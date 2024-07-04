package classes

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.core.content.ContextCompat.startActivity



//fun startActivity(intent: Intent) {
//
//}

fun hideKeyboard(activity: Activity) {
    val view = activity.findViewById<View>(android.R.id.content)
    if (view != null) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

