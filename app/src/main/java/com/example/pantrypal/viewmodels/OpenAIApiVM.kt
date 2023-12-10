package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.App
//import com.example.pantrypal.App
import com.example.pantrypal.apis.OpenAIApi
import kotlinx.coroutines.launch

sealed interface OpenAIApiState{

    data class Success(val chatGPTResponse : String): OpenAIApiState
    object Loading: OpenAIApiState
    object Error: OpenAIApiState

}

class OpenAIApiVM(
    private val openAIApi: OpenAIApi
): ViewModel(){

    init {
    }

    var openAIApiState: OpenAIApiState by mutableStateOf(OpenAIApiState.Loading)
        private set

    var recipePrompt: String by mutableStateOf("")
        private set

    fun updateRecipePrompt(newPrompt: String){
        recipePrompt = newPrompt
    }

    fun getRecipe(){
        viewModelScope.launch{
            try{
                val prompt = "You are a professional chef with over 30 years of experience. You are devoted to helping people reduce food waste by supplying them with recipes that only utilize the ingredients that they have specified and no other ingredients. You can always assume that people have the basic ingredients of Salt and Pepper. It is of utmost importance that you do not use any other ingredient that has not been specified. Please provide a response in the format displayed in the following example (Do not number the instructions).\n" +
                        "For example: if you are prompted with:\n" +
                        "chicken thighs; cherry tomatoes; harissa paste; red wine vinegar;sprigs oregano; feta; crusty bread - Make it suited for dinner\n" +
                        "You should respond with a recipe in the following format:\n" +
                        "Title:\"Chicken Thighs With Tomatoes and Feta\";\n" +
                        "Ingredients:\n" +
                        "1:\"6 chicken thighs (about 2¼ lb. total)\"\n" +
                        "2:\"½ tsp. salt, plus more\"\n" +
                        "3:\"1¼ lb. cherry tomatoes (about 2 pints)\"\n" +
                        "4:\"¼ cup harissa paste\"\n" +
                        "5:\"3 Tbsp. red wine vinegar\"\n" +
                        "6:\"3 sprigs oregano, divided\"\n" +
                        "7:\"4 oz. feta, cut into (¼\"-thick) planks\"\n" +
                        "8:\"Crusty bread (for serving)\";\n" +
                        "Instructions:\"Pat chicken thighs dry with paper towels; season all over with salt. Arrange skin side down in a cold large cast-iron skillet. Set over medium heat and cook chicken, undisturbed, rotating pan as needed for even browning, until skin is very deep golden brown and crisp and chicken releases easily from pan, 13–16 minutes. Using tongs, transfer chicken to a plate, arranging skin side up. Combine tomatoes, harissa paste, vinegar, 1 oregano sprig, and ½ tsp. salt in same skillet. Increase heat to medium-high and cook, stirring occasionally, until tomatoes burst and their juices start to thicken, 8–10 minutes. Nestle chicken thighs into tomatoes, skin side up. Reduce heat to medium-low, bring to a simmer, and cook until chicken is just cooked through and flesh is no longer pink (an instant-read thermometer inserted into the thickest part near the bone will register 165°F), and tomato sauce is thickened so that a wooden spoon dragged through it leaves a trail, 6–8 minutes. Remove from heat and let sit 5 minutes. Break feta into large pieces; scatter over chicken. Some pieces will stay intact while others will soften into the sauce a little—and that's exactly what you want. Pick leaves off remaining 2 oregano sprigs and scatter on top. Serve with bread for sopping up any extra tomato sauce.\"\n" +
                        "\n" +
                        "Here is the prompt you need to respond to and please remember not to use any ingredients that have not been mentioned in the prompt:" +
                        "\n" + recipePrompt

                val response = openAIApi.getResponse(prompt)
                openAIApiState = OpenAIApiState.Success(chatGPTResponse = response)
            } catch (e: Exception){
                openAIApiState = OpenAIApiState.Error
            }
        }
    }

    companion object{
        private var INSTANCE: OpenAIApiVM? = null

        fun getInstance(): OpenAIApiVM {
            val vm = INSTANCE ?: synchronized(this) {
                OpenAIApiVM(App.getApp().container.openAIApi).also {
                    INSTANCE = it
                }
            }
            return vm
        }
    }

}