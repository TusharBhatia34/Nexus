package com.example.nexus


import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting

class Response {
    suspend fun response(prompt:String): String{
       try {
           val harassmentSafety = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
           val sexualSafety = SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)

           val hateSpeechSafety = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE)
           val generativeModel = GenerativeModel(
               modelName = "gemini-pro",
               apiKey = BuildConfig.GEMINI_API_KEY,
               safetySettings = listOf(harassmentSafety,
                   sexualSafety,hateSpeechSafety)
           )

           val chat = generativeModel.startChat()


           val response = chat.sendMessage(prompt)

           return response.text!!
       }
       catch (e:Exception){
return "Sorry, there is an unknown issue"

       }

    }
}