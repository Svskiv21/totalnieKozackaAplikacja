package com.example.totalniekozackaaplikacja.widoki

import android.net.Uri
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.totalniekozackaaplikacja.R
import com.example.totalniekozackaaplikacja.dataAccess.MasterAndDatabase
import com.example.totalniekozackaaplikacja.dataAccess.repos.PlayerRepository
import com.example.totalniekozackaaplikacja.nav.RoutesMap
import com.example.totalniekozackaaplikacja.viewModels.AppViewModelProvider
import com.example.totalniekozackaaplikacja.viewModels.PlayerViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavHostController,
                playerViewModel: PlayerViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val isNameError = rememberSaveable { mutableStateOf(false) }
    val isEmailError = rememberSaveable { mutableStateOf(false) }
    var number by rememberSaveable{ mutableStateOf("") }
    val isNumberError = rememberSaveable { mutableStateOf(false) }
    var imageUri by rememberSaveable{ mutableStateOf<Uri?>(null) }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { imageUri = it }
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MasterAnd",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(bottom = 48.dp)
        )
        ProfileImageWithPicker(profileImageUri = imageUri, selectImageOnClick = {
            photoPicker
                .launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts
                    .PickVisualMedia
                    .ImageOnly)
                )
        })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextFieldWithError(
            label = "Enter Name",
            supportingText = "Name can't be empty",
            validation = { text: String -> text.isNotEmpty() },
            text = playerViewModel.name,
            updateText = { playerViewModel.updateName(it) },
            isError = isNameError
        )
        OutlinedTextFieldWithError(
            label = "Enter email",
            supportingText = "Invalid email",
            validation = { text: String -> text.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(text).matches() },
            text = playerViewModel.email,
            updateText = { playerViewModel.updateEmail(it) },
            isError = isEmailError
        )
        OutlinedTextFieldWithError(
            label = "Enter number of colors",
            supportingText = "Invalid number. Enter number between 4 and 7.",
            validation = { text: String -> text.isNotEmpty() && text.isDigitsOnly() && text.toInt() >= 4 && text.toInt() <= 7 },
            text = number,
            updateText = { number = it },
            isError = isNumberError
        )
        Button(
            onClick = {
                if(!isEmailError.value && !isNumberError.value && !isNameError.value) {
                    coroutineScope.launch {
                        playerViewModel.savePlayer()
                        navController.navigate(route = RoutesMap.Profile.route +
                                "/${playerViewModel.playerId}/$number?uri=$imageUri")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}

// to co w skrypcie trzeba bylo zrobic
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldWithError(label: String,
                               supportingText: String,
                               validation: (String)->Boolean,
                               text: String,
                               updateText: (String)->Unit,
                               isError: MutableState<Boolean>
){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = updateText,
        label = { Text(text = label) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = if (isError.value) Color.Red else Color.Gray,
            disabledLabelColor = if (isError.value) Color.Red else Color.Gray,
            disabledSupportingTextColor = if (isError.value) Color.Red else Color.Gray,
        ),
        isError = isError.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        supportingText = { if (!validation(text)) {
            Text(text = supportingText)
            isError.value = true
        } else {
            Text(text = "")
            isError.value = false
        }
        }
    )
}

@Composable
@Preview
fun ProfileImageWithPicker() {
    val imageUri = rememberSaveable{ mutableStateOf<Uri?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        imageUri.value = it
    }

    ProfileImageWithPicker(profileImageUri = imageUri.value, selectImageOnClick = {
        photoPicker
            .launch(
                PickVisualMediaRequest(
                    ActivityResultContracts
                .PickVisualMedia
                .ImageOnly)
            )
    })

}

@Composable
private fun ProfileImageWithPicker(profileImageUri: Uri?, selectImageOnClick: () -> Unit){
    Box{
        //wymaga dodania ikony w katalogu /res/drawable
        //(prawy przycisk | New | Vector asset)
        if (profileImageUri == null){
            Box {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .padding(20.dp),
                    painter = painterResource(id = R.drawable.baseline_question_mark_24),
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                )
                IconButton(onClick = selectImageOnClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)) {
                    Icon(painter = painterResource(id = R.drawable.baseline_question_mark_24),
                        contentDescription = "Image selector")
                }
            }
        } else {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .padding(20.dp),
                    model = profileImageUri,
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                )
                IconButton(onClick = selectImageOnClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_question_mark_24),
                        contentDescription = "Image selector"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    val navHostController = rememberNavController()
    val database: MasterAndDatabase = MasterAndDatabase.getDatabase(LocalContext.current)
    val playerRepository = PlayerRepository(database.playerDao())
    val playerViewModel = PlayerViewModel(playerRepository)
    LoginScreen(navController = navHostController, playerViewModel = playerViewModel)
}