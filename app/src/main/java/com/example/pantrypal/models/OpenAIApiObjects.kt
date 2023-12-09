package com.example.pantrypal.models

import com.example.pantrypal.database.Recipe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(val model: String, val messages: List<Message>, val temperature: Double = 0.8)

@Serializable
data class Message(val role: String, val content: String)

@Serializable
data class ChatResponse(val id: String,
                        @SerialName(value = "object")
                        val object_: String,
                        val created: Long, val model: String, val usage: Usage, val choices: List<Choice>
){
    fun toRecipe(image: ImageGenerationResponse): Recipe {
        var responseList: List<String> = this.choices[0].message.content.split(";")

        if (responseList.size == 3) {
            var titleStr = responseList[0].split("\"")[1]
            var ingredientsStr = "{" + responseList[1].replace(":", "=") + "}"
            var instructionsStr = responseList[2].split("\"")[1]

            val out = Recipe(
                title = titleStr,
                ingredients = ingredientsStr,
                instructions = instructionsStr,
                image = image.output[0]
            )
            return out
        } else {
            val out = Recipe(
                title = "",
                ingredients = "",
                instructions = "",
                image = ""
            )
            return out
        }
    }

}

@Serializable
data class Usage(val prompt_tokens: Int, val completion_tokens: Int, val total_tokens: Int)

@Serializable
data class Choice(val message: Message, val finish_reason: String, val index: Int)
