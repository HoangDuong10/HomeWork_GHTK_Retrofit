import android.content.Context
import com.example.homework_ghtk_retrofit.model.Pokemon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pro.foodorder.prefs.MySharedPreferences

class DataStoreManager(context: Context) {
    private val gson :Gson = Gson()

    private val sharedPreferences: MySharedPreferences = MySharedPreferences(context)

    companion object {
        private const val KEY_POKEMON_LIST = "KEY_POKEMON_LIST"
    }

    fun savePokemonList(pokemonList: List<Pokemon>) {
        val json = gson.toJson(pokemonList)
        sharedPreferences.putStringValue(KEY_POKEMON_LIST, json)
    }

    fun getPokemonList(): List<Pokemon> {
        val json = sharedPreferences.getStringValue(KEY_POKEMON_LIST, "")
        return if (!json.isNullOrEmpty()) {
            try {
                val type = object : TypeToken<List<Pokemon>>() {}.type
                gson.fromJson<List<Pokemon>>(json, type)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        } else {
            emptyList()
        }
    }
}