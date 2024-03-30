package com.example.nexus

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun nexusUi(viewModel: NexusViewModel, isDark:Boolean) {
    var prompt by remember {
        mutableStateOf("")
    }
val status by viewModel.networkConnectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.Available)


    val history = viewModel.chatHistory.collectAsState()

    val animationSpec = tween<Float>(durationMillis = 500) // Customize animation duration

    val animatedRotation = animateFloatAsState(
        targetValue = if (!isDark) 0f else 360f,
        animationSpec = animationSpec
    ).value

 if (status.toString().equals("Available")){
     Scaffold(
         topBar = {
             TopAppBar(
                 title = { Text(text="Nexus" , fontWeight = FontWeight.Bold,modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center)

                         },
                 navigationIcon = {
                     IconButton(
                         onClick = { viewModel.clearChat()}
                     ) {
                         Icon(
                             imageVector = Icons.Default.BorderColor,
                             contentDescription = null,
                             tint = MaterialTheme.colorScheme.secondary
                         )
                     }
                 } ,
                 actions = {
                     IconButton(onClick = { viewModel.changeTheme()}) {

                         Icon(
                             imageVector = if (!isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                             contentDescription = "Light/Dark Mode",
                             tint =MaterialTheme.colorScheme.secondary,
                             modifier = Modifier

                                 .graphicsLayer {
                                     rotationZ = animatedRotation 
                                 }
                         )
                     }
                 },
                 colors = TopAppBarDefaults.topAppBarColors(
                     containerColor = MaterialTheme.colorScheme.primary
                 )
             )
         }
     ) {
         Column(modifier = Modifier
             .fillMaxSize()
             .background(MaterialTheme.colorScheme.primary)
             .imePadding()
             .padding(it)
         ){

             if (history.value.isEmpty()){ //new chat screen
                 Box(modifier = Modifier
                     .fillMaxWidth()
                     .weight(1f)
                    , contentAlignment = Alignment.Center){
                     Image(painter = if(!isDark)painterResource(id = R.drawable.lightmodenexus) else painterResource(id = R.drawable.darkmodenexus) , //app icon to replace
                         contentDescription = null,
                         modifier = Modifier
                             .size(60.dp)
                             .clip(CircleShape),

                        )

                 }
             }
             else{  //when chat has started
                 LazyColumn(modifier =Modifier.weight(1f)) {
                     itemsIndexed(history.value){index,item ->
                         if (index%2==0){
                             chatListUI(response = item, name = "User", icon =R.drawable.userlogo_round )
                         }
                         else{
                             chatListUI(response = item, name = "Nexus", icon =if(!isDark) R.drawable.lightmodenexus else R.drawable.darkmodenexus)

                         }


                     }
                 }
             }
             val keyboardController = LocalSoftwareKeyboardController.current
             val focusManager = LocalFocusManager.current
             Row (modifier = Modifier
                 .fillMaxWidth()
                 .padding(8.dp)){
                 OutlinedTextField(value = prompt, onValueChange = { prompt = it },
                     modifier =Modifier.weight(1f),
                     shape = CircleShape,
                     colors = OutlinedTextFieldDefaults.colors(
                         focusedBorderColor = MaterialTheme.colorScheme.secondary,
                         unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                     ),
                     placeholder = { Text(text = "Prompt")})
                 IconButton(onClick = {
                     viewModel.response(prompt)
                     prompt = ""
                     focusManager.clearFocus()
                     keyboardController?.hide()
                 }) {
                     Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null,tint = MaterialTheme.colorScheme.secondary
                     )
                 }
             }
         }
     }
 }
    else{
offlineScreen(id = if(!isDark) R.drawable.internetofflogo_foreground else R.drawable.internetofflogodarkmode_foreground)
 }

 }







