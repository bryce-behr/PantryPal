package com.example.pantrypal

data class Recipe (
    val name: String,
    val ingredients: List<Pair<String, String>>,
    val recipe: String,
    val image: Int
)