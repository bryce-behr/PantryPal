package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.App
import com.example.pantrypal.apis.OpenAIApi
import com.example.pantrypal.apis.StableDiffusionApi
import com.example.pantrypal.database.Recipe
import kotlinx.coroutines.launch

sealed interface RecipeState{
    data class Success(val recipe: Recipe): RecipeState
    data class HalfSuccess(val recipe: Recipe): RecipeState
    data class LoadingSuccess(val recipe: Recipe): RecipeState
    object Loading: RecipeState
    object Error: RecipeState
}


class RecipeVM (
    private val openAIApi: OpenAIApi,
    private val stableDiffusionApi: StableDiffusionApi
): ViewModel(){

    private val chatGPTInitPrompt = "You are a professional chef with over 30 years of experience. You are devoted to helping people reduce food waste by supplying them with recipes that only utilize the ingredients that they have specified and no other ingredients. You can always assume that people have the basic ingredients of Salt and Pepper. It is of utmost importance that you do not use any other ingredient that has not been specified. Please provide a response in the format displayed in the following example (Do not number the instructions).\n" +
            "For example: if you are prompted with:\n" +
            "chicken thighs; cherry tomatoes; harissa paste; red wine vinegar;sprigs oregano; feta; crusty bread - Make it suited for dinner\n" +
            "You should respond with a recipe in the following format:\n" +
            "Title:\"Chicken Thighs With Tomatoes and Feta\"\n" +
            "Ingredients:\n" +
            "1:\"6 chicken thighs (about 2¼ lb. total)\"\n" +
            "2:\"½ tsp. salt, plus more\"\n" +
            "3:\"1¼ lb. cherry tomatoes (about 2 pints)\"\n" +
            "4:\"¼ cup harissa paste\"\n" +
            "5:\"3 Tbsp. red wine vinegar\"\n" +
            "6:\"3 sprigs oregano, divided\"\n" +
            "7:\"4 oz. feta, cut into (¼\"-thick) planks\"\n" +
            "8:\"Crusty bread (for serving)\"\n" +
            "Instructions:\"Pat chicken thighs dry with paper towels; season all over with salt. Arrange skin side down in a cold large cast-iron skillet. Set over medium heat and cook chicken, undisturbed, rotating pan as needed for even browning, until skin is very deep golden brown and crisp and chicken releases easily from pan, 13–16 minutes. Using tongs, transfer chicken to a plate, arranging skin side up. Combine tomatoes, harissa paste, vinegar, 1 oregano sprig, and ½ tsp. salt in same skillet. Increase heat to medium-high and cook, stirring occasionally, until tomatoes burst and their juices start to thicken, 8–10 minutes. Nestle chicken thighs into tomatoes, skin side up. Reduce heat to medium-low, bring to a simmer, and cook until chicken is just cooked through and flesh is no longer pink (an instant-read thermometer inserted into the thickest part near the bone will register 165°F), and tomato sauce is thickened so that a wooden spoon dragged through it leaves a trail, 6–8 minutes. Remove from heat and let sit 5 minutes. Break feta into large pieces; scatter over chicken. Some pieces will stay intact while others will soften into the sauce a little—and that's exactly what you want. Pick leaves off remaining 2 oregano sprigs and scatter on top. Serve with bread for sopping up any extra tomato sauce.\"\n" +
            "\n" +
            "Here is the prompt you need to respond to and please remember not to use any ingredients that have not been mentioned in the prompt:" +
            "\n"

    private val stableDiffusionInitChatGPTPrompt = "Can you please provide a prompt for a text to image AI that will result in it generating a photo-realistic picture  for a cook book for the following recipe:\n"

    private val stableDiffusionFinalChatGPTPrompt = "\nPlease only respond with the prompt for the text to image ai and take extra care to ensure that the image will resemble exactly what the recipe will create. Please respond only with the prompt for the text to image AI."
    var recipeState: RecipeState by mutableStateOf(RecipeState.Loading)
        private set

    private var recipePrompt: String by mutableStateOf(" - ")

    fun addIngredients(ingredients: MutableList<String>){
        var text = ""
        ingredients.forEach { x->
            text += "$x;"
        }
        text = text.dropLast(1)
        recipePrompt = text + recipePrompt
    }

    fun addMeal(meal: String){
        recipePrompt += meal
    }

    fun clearRecipeQuery(){
        recipeState = RecipeState.Loading
        recipePrompt = " - "
    }

    fun loadRecipe(recipe: Recipe){
        recipeState = RecipeState.Success(recipe)
    }

    fun getRecipe(){
        viewModelScope.launch{
            try{
                val chatGPTResponse = openAIApi.getResponse(chatGPTInitPrompt + recipePrompt)
                if (!isValidForRecipe(chatGPTResponse)){
                    throw Exception("ChatGPTResponse not valid for Recipe")
                }
                var recipe = stringToRecipe(chatGPTResponse)
                recipeState = RecipeState.LoadingSuccess(recipe)

                try{
                    val imgUrl = stableDiffusionApi.getImageUrl(stableDiffusionInitChatGPTPrompt + chatGPTResponse + stableDiffusionFinalChatGPTPrompt)
                    recipe = Recipe(
                        title = recipe.title,
                        ingredients = recipe.ingredients,
                        instructions = recipe.instructions,
                        image = imgUrl
                    )
                    recipeState = RecipeState.Success(recipe)
                } catch (t: Exception){
                    recipeState = RecipeState.HalfSuccess(recipe)
                }
            } catch (e: Exception){
                recipeState = RecipeState.Error
            }
        }
    }

    private fun isValidForRecipe(text: String): Boolean{
        return (text.contains("Title:"))&&(text.contains("Ingredients:"))&&(text.contains("Instructions"))
    }

    private fun stringToRecipe(newText: String): Recipe{
        var text = newText
        text = text.replace("Title:", " ")
        var temp = text.split("Ingredients:")
        val title = temp[0].replace("\"", "").trim()
        temp = temp[1].split("Instructions:")
        var ingredients = temp[0].trim()
        ingredients = ingredients.replace("\"", "")

        val instructions = temp[1].replace("\"", "").trim()

        return Recipe(
            title = title,
            ingredients = ingredients,
            instructions = instructions,
            image = ""
        )
    }

    companion object{
        private var INSTANCE: RecipeVM? = null

        fun getInstance(): RecipeVM {
            val vm = INSTANCE ?: synchronized(this) {
                RecipeVM(App.getApp().container.openAIApi, App.getApp().container.stableDiffusionApi).also {
                    INSTANCE = it
                }
            }
            return vm
        }
    }

}