package com.example.nexus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun chatListUI(response:String, name:String, icon:Int) {

 Row(
     modifier = Modifier
         .fillMaxWidth()
         .padding(8.dp),
 horizontalArrangement = Arrangement.Start,

 ) {
     Image(painter = painterResource(id = icon),
         contentDescription = null ,
         modifier = Modifier
             .clip(CircleShape)
             .size(15.dp)
     )
     Spacer(modifier = Modifier.width(4.dp))
     Column(modifier  = Modifier, horizontalAlignment = Alignment.Start) {
Text(text = name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
         Spacer(modifier = Modifier.height(4.dp))

         if ("```" in response){
        val splitResponse= response.split("```")
        Column {
                 splitResponse.forEachIndexed{index,element->
            if(index%2!=0){
             codeCard(element)
            }
            else{
                        SelectionContainer {
                            Text(text = element,color = MaterialTheme.colorScheme.secondary,fontSize = 15.sp)
                        }
                 }
                 }
             }

                                }
         else{
            SelectionContainer {
                Text(text = response, fontSize = 15.sp,color = MaterialTheme.colorScheme.secondary)
            }
         }
     }
 }
}

@Composable
fun codeCard(code:String) {
val splitResponse = code.split("\n" , limit = 2)
val clipBoardManager = LocalClipboardManager.current

        Card(modifier = Modifier
            .padding(end = 8.dp)
            .fillMaxWidth()
            ,
            colors = CardDefaults.cardColors(
             containerColor = MaterialTheme.colorScheme.secondaryContainer
                                             ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                .padding(6.dp)) {
                Text(
                    text = splitResponse[0],
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
Row(modifier  = Modifier.clickable {                 clipBoardManager.setText(AnnotatedString(splitResponse[1]))
}) {
    Icon(
        imageVector = Icons.Outlined.ContentPaste,
        contentDescription = null,
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(vertical = 2.dp)
            .size(16.dp),
        tint = Color.White
    )

    Text(text = "Copy code", color = Color.White,
        modifier = Modifier
            .padding(horizontal = 4.dp)

    )
}

            }

            Text(
                text = splitResponse[1],
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        }




@Composable
fun offlineScreen(id:Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
Image(painter = painterResource(id = id), contentDescription = null)
   Text(text = "You Are Offline",color = MaterialTheme.colorScheme.secondary, fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }

}

