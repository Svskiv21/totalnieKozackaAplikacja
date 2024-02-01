package com.example.totalniekozackaaplikacja.widoki

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.totalniekozackaaplikacja.model.PlayerWithScores
import com.example.totalniekozackaaplikacja.nav.RoutesMap
import com.example.totalniekozackaaplikacja.viewModels.AppViewModelProvider
import com.example.totalniekozackaaplikacja.viewModels.PlayerWithScoresViewModel


@Composable
fun ResultScreen(navController: NavHostController,
                 profileId: Long,
                 colorNumber: Int,
                 recentScore: Int,
                 playerWithScoresViewModel: PlayerWithScoresViewModel = viewModel( factory = AppViewModelProvider.Factory)
) {
    val scores by playerWithScoresViewModel.playerWithScore.collectAsState()

    Column (
        modifier = Modifier.padding(10.dp)
    ) {
        TileText(text = "Results")
        RecentScore(score = recentScore)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items = scores.sortedBy { it.points },
                key = {score -> score.score_id}) {
                ListItem(playerWithScore = it)
            }
        }
        GameButtons(
            navController = navController,
            profileId = profileId,
            colorNumber = colorNumber)
    }

}

@Preview(showBackground = true)
@Composable
fun TitleTextPreview() {
    TileText(text = "Results")
}

@Composable
fun TileText(text: String) {
    Text(
        fontSize = 50.sp,
        modifier = Modifier.padding(bottom = 30.dp),
        text = text)
}

@Preview(showBackground = true)
@Composable
fun RecentScorePreview() {
    RecentScore(score = 4)
}

@Composable
fun RecentScore(score: Int) {
    Text(
        fontSize = 40.sp,
        modifier = Modifier
            .padding(bottom = 15.dp),
        text = "Recent score: $score")
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    val playerWithScores = PlayerWithScores(
        player_id = 0L,
        score_id = 0L,
        points = 4,
        name = "Sam",
        email = "samwise.gamgee@bagend.sh")
    ListItem(playerWithScore = playerWithScores)
}

@Composable
fun ListItem(playerWithScore: PlayerWithScores) {
    CompositionLocalProvider (
        LocalTextStyle provides TextStyle(fontSize = 30.sp)
    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .drawBehind {
                val strokeWidth = density * 2
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.Black,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            },
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = playerWithScore.name)
            Text(text = playerWithScore.points.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameButtonsPreview() {
    GameButtons(profileId = 0L, colorNumber = 0)
}

@Composable
fun GameButtons(navController: NavHostController = rememberNavController(), profileId: Long, colorNumber: Int) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { navController.navigate(route = RoutesMap.Login.route) }) {
            Text(text = "Logout")
        }
        Button(onClick = { navController.navigate(route = RoutesMap.Game.route + "/$profileId/$colorNumber") }) {
            Text(text = "Restart game")
        }
    }
}