import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.totalniekozackaaplikacja.R
import com.example.totalniekozackaaplikacja.nav.RoutesMap
import com.example.totalniekozackaaplikacja.viewModels.AppViewModelProvider
import com.example.totalniekozackaaplikacja.viewModels.PlayerViewModel

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(
        navController = rememberNavController(),
        uri = null,
        playerId = 0L,
        number = 5)
}

@Composable
fun ProfileScreen(navController: NavHostController,
                  uri: String?,
                  playerId: Long,
                  number: Int,
                  playerViewModel: PlayerViewModel = viewModel(
                      factory = AppViewModelProvider.Factory)
) {

    LaunchedEffect(
        key1 = playerViewModel.playerId) {
        playerViewModel.getPlayerById(playerId = playerId)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            horizontalAlignment =  Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ProfileImage(uri = if (uri == null) null else Uri.parse(uri))
            Username(username = playerViewModel.name)
            Email(email = playerViewModel.email)
            NumberOfColors(number = number)
        }
        NavButtons(navController = navController, number = number, profileId = playerId)
    }
}

@Composable
fun NavButtons(navController: NavHostController, number: Number, profileId: Long) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = { navController.navigate(route = RoutesMap.Login.route) }) {
            Text(text = "LogOut")
        }
        Button(onClick = { navController.navigate(
            route = RoutesMap.Game.route+"/${profileId}/${number}") }) {
            Text(text = "Play")
        }
    }
}

@Composable
fun NumberOfColors(number: Number) {
    Text(text = "Number of colors: $number")
}

@Composable
fun Email(email: String) {
    Text(text = "Email: $email")
}

@Composable
fun Username(username: String) {
    Text(
        modifier = Modifier
            .padding(bottom = 15.dp),
        fontSize = 36.sp,
        text = username
    )
}

@Composable
fun ProfileImage(uri: Uri?) {
    Box{
        if (uri != null){
            AsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .padding(20.dp),
                model = uri,
                contentDescription = "Profile image")
        } else {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
                    .padding(20.dp),
                painter = painterResource(id = R.drawable.baseline_question_mark_24),
                contentDescription = "Profile photo",
                contentScale = ContentScale.Crop
            )
        }
    }
}