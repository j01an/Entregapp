import android.content.Context
import android.content.SharedPreferences
import com.example.voluntapp.models.UserModel
import com.google.gson.Gson

object MySharedPreferences {
        private lateinit var sharedPreferences: SharedPreferences

        fun init(sharedPrefs: SharedPreferences) {
                sharedPreferences = sharedPrefs
        }

        fun saveUserModel(userModel: UserModel) {
                val gson = Gson()
                val json = gson.toJson(userModel)
                sharedPreferences.edit().putString("userModel", json).apply()
        }

        fun getUserModel(): UserModel? {
                val gson = Gson()
                val json = sharedPreferences.getString("userModel", null)
                return gson.fromJson(json, UserModel::class.java)
        }

        fun clearSharedPreferences() {
                sharedPreferences.edit().clear().apply()
        }
}
