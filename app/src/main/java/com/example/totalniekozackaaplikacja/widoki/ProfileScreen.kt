package com.example.totalniekozackaaplikacja.widoki

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(){
    var name = rememberSaveable { mutableStateOf("") }
    var email = rememberSaveable { mutableStateOf("") }
    var numberOfColors = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MasterAnd",
            modifier = Modifier.padding(8.dp),
            fontSize = 40.sp,
            color = Color(red = 40, green = 80, blue = 200),
            fontWeight = FontWeight.Bold)

        Box(modifier = Modifier.padding(80.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            label = { Text("Enter name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = { Text("Enter email") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = numberOfColors.value,
            onValueChange = {
                if (it.isEmpty() || (it.toIntOrNull() in 1..100)) {
                    numberOfColors.value = it
                }
            },
            label = { Text("Wpisz ile kolorow chcesz 1...10") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = { /*TODO*/ }) {

            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { /*TODO*/ }) {
                
            }
        }
    }
}


@Composable
fun OutlinedTextWithFieldError(){

}

@Composable
fun ProfileImageWithPicker(){

}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}