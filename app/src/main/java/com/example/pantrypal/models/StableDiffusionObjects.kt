package com.example.pantrypal.models

import com.example.pantrypal.database.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class ImageGenerationRequest(
    val key: String,
    val prompt: String,
    val negative_prompt: String? = null,
    val width: String,
    val height: String,
    val samples: String,
    val num_inference_steps: String,
    val safety_checker: String = "no",
    val enhance_prompt: String = "yes",
    val seed: String? = null,
    val guidance_scale: Double,
    val multi_lingual: String = "no",
    val panorama: String = "no",
    val self_attention: String = "no",
    val upscale: String="no",
    val embeddings_model: String? = null,
    val webhook: String? = null ,
    val track_id: String? = null
)

@Serializable
data class ImageGenerationResponse(
    val status: String,
    val generationTime: Double,
    val id: Int,
    val output: List<String>,
    val meta: Meta
){
    fun toRecipe(chatResponse: ChatResponse): Recipe {
        val out = chatResponse.toRecipe(this)

        return out
    }
}

@Serializable
data class Meta(
    val H: Int,
    val W: Int,
    val enable_attention_slicing: String,
    val file_prefix: String,
    val guidance_scale: Double,
    val model: String,
    val n_samples: Int,
    val negative_prompt: String,
    val outdir: String,
    val prompt: String,
    val revision: String,
    val safetychecker: String,
    val seed: Long,
    val steps: Int,
    val vae: String
)