package com.example.totalniekozackaaplikacja.widoki

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.totalniekozackaaplikacja.nav.RoutesMap
import com.example.totalniekozackaaplikacja.viewModels.AppViewModelProvider
import com.example.totalniekozackaaplikacja.viewModels.ScoreViewModel
import kotlinx.coroutines.launch
import java.util.Collections

@Composable
@Preview
fun GameMainScreenPreview() {
    val navController = rememberNavController()
    GameMainScreen(navController = navController, number = 4, playerId = 0L, uri=null)
}

val gameColors = listOf(
    Color.Red, Color.Blue, Color.Cyan, Color.Green, Color.Gray, Color.Magenta, Color.Yellow)

@Composable
fun GameMainScreen(navController: NavHostController,
                   number: Int,
                   playerId: Long,
                   uri: String?,
                   scoreViewModel: ScoreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val selectedColorList = rememberSaveable() { mutableStateOf<List<List<Color>>>(listOf()) }
    val feedbackColorList = rememberSaveable() { mutableStateOf<List<List<Color>>>(listOf()) }
    val availableColorList = rememberSaveable() { mutableStateOf(gameColors.shuffled().take(number ?: 4)) }
    val correctColorList = rememberSaveable() { mutableStateOf(selectRandomColors(availableColorList.value)) }
    val rows = rememberSaveable() { mutableStateOf(1) }
    val clickable = rememberSaveable() { mutableStateOf<List<Boolean>>(listOf(false)) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = scoreViewModel.playerId) {
        scoreViewModel.playerId = playerId
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreText(attempts = scoreViewModel.points)
        LazyColumn(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(rows.value.coerceAtLeast(1)) { rowIndex ->
                // bierze listę z kolorami dla danego wiersza
                // jeśli lista jest null wtedy (?:) przypisuje listę z 4 białymi kolorami
                val selectedColors = selectedColorList.value.getOrNull(rowIndex) ?: List(4) { Color.White }
                val feedbackColors = feedbackColorList.value.getOrNull(rowIndex) ?: List(4) { Color.White }
                GameRow(
                    chosenColors = selectedColors,
                    feedbackColors = feedbackColors,
                    isClicked = clickable.value[rowIndex],
                    onCheckClick = {
                        //sprawdzamy czy gracz wybrał w tym ruchu jakieś kolory czy pozostawił białe przyciski
                        if (selectedColorList.value.size <= rowIndex) {
                            selectedColorList.value = selectedColorList.value.toMutableList().apply {
                                add(List(4) { Color.White} )
                            }
                        }
                        // Sprawdzamy czy numer wiersza odpowiada liczbie prób i czy przycisk jest klikalny
                        if (rowIndex + 1 == scoreViewModel.points && clickable.value[rowIndex]){
                            //jeśli tak to
                            feedbackColorList.value = feedbackColorList.value.toMutableList().apply {
                                // sprawdzamy czy rozmiar listy kolorów z informacją zwrotną odpowiada liczbie wierszy
                                // jeśli nie dodajemy listę 4 białych kolorów
                                if (size <= rowIndex) add(List(4) { Color.White})
                                //sprawdzamy czy użytkownik dobrze podał kolory
                                this[rowIndex] = checkColors(
                                    chosenColors = selectedColors,
                                    correctColors = correctColorList.value,
                                    notFoundColor = Color.White
                                )
                            }

                            //zwiększamy liczbę prób
                            scoreViewModel.updatePoints(scoreViewModel.points+1)
                            //sprawdzamy czy użytkownik wygrał
                            val isWinner = feedbackColorList.value.last().all { it == Color.Red }
                            if(isWinner){
                                // jeśli tak to
                                coroutineScope.launch {
                                    scoreViewModel.insertScore()
                                    navController.navigate(route = RoutesMap.Results.route + "/$playerId/$number/${scoreViewModel.points}?uri=$uri")
                                }
                            } else {
                                // w przeciwnym wypadku
                                rows.value++
                                clickable.value = clickable.value.toMutableList().apply {
                                    this[rowIndex] = false
                                    add(true)
                                }
                            }
                        }
                    },
                    onSelectColorClick = { buttonId ->
                        // Sprawdzamy czy numer wiersza odpowiada liczbie prób i czy przycisk jest klikalny
                        if (rowIndex + 1 == scoreViewModel.points && clickable.value[rowIndex]) {
                            // jeśli tak to
                            selectedColorList.value = selectedColorList.value.toMutableList().apply {
                                // sprawdzamy czy rozmiar listy wybranych kolorów odpowiada liczbie wierszy
                                // jeśli nie dodajemy listę 4 białych kolorów
                                if (size <= rowIndex) add(List(4) { Color.White})
                                // ustawiamy kolory w tym wierszu
                                this[rowIndex] = selectNextAvailableColor(
                                    availableColors = availableColorList.value,
                                    chosenColors = selectedColors,
                                    buttonId = buttonId
                                )
                            }
                            if (selectedColorList.value[rowIndex].none { it == Color.White }) {
                                // jeśli tak to ustawia, że można kliknac w przycisk sprawdzający sekwencję kolorów
                                clickable.value = clickable.value.toMutableList().apply {
                                    this[rowIndex] = true
                                }
                            }
                        }
                    }
                )
            }
        }
        BackButton(navController = navController)
    }
}

@Composable
fun BackButton(navController: NavHostController) {
    Row (
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}

@Composable
fun ScoreText(attempts: Int) {
    Text(
        text = "You score: $attempts",
        fontSize = 36.sp,
        modifier = Modifier
            .padding(bottom = 20.dp)
    )
}

@Composable
fun CircularButton(onClick: () -> Unit, color: Color) {
    var animateColor by remember { mutableStateOf(false) }
    val animatedColor by animateColorAsState(
        targetValue = if (animateColor) color else Color.White,
        animationSpec = repeatable(4, animation = tween(500),
            repeatMode = RepeatMode.Reverse)
    )
    Button(modifier = Modifier
        .size(50.dp)
        .background(color = MaterialTheme.colorScheme.background),
        border = BorderStroke(width = 2.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.buttonColors(containerColor = animatedColor,
            contentColor = MaterialTheme.colorScheme.onBackground),
        onClick = {
            onClick()
            animateColor = true
        }) {
    }
}

@Composable
fun SelectableColorRow (colors: List<Color>, onClick: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        colors.forEachIndexed {
                index, color -> CircularButton(onClick = { onClick(index) }, color = color)
        }
    }
}

@Composable
fun SmallCircle(color: Color) {
    Box(modifier = Modifier
        .size(20.dp)
        .clip(CircleShape)
        .background(color)
        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape))
}

@Composable
fun FeedbackCircles(colors: List<Color>) {

    val colorAnimation0 = remember { Animatable(Color.White) }
    val colorAnimation1 = remember { Animatable(Color.White) }
    val colorAnimation2 = remember { Animatable(Color.White) }
    val colorAnimation3 = remember { Animatable(Color.White) }

    LaunchedEffect(colors) {
        colorAnimation0.animateTo(colors.getOrElse(0) { Color.White}, animationSpec = tween(500))
        colorAnimation1.animateTo(colors.getOrElse(1) { Color.White}, animationSpec = tween(500))
        colorAnimation2.animateTo(colors.getOrElse(2) { Color.White}, animationSpec = tween(500))
        colorAnimation3.animateTo(colors.getOrElse(3) { Color.White}, animationSpec = tween(500))
    }
    Column (
        modifier = Modifier.width(80.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(80.dp)
        ) {
            SmallCircle(color = colorAnimation0.value)
            SmallCircle(color = colorAnimation1.value)
        }
        Row (
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(80.dp)
        ) {
            SmallCircle(color = colorAnimation2.value)
            SmallCircle(color = colorAnimation3.value)
        }
    }
}

@Composable
fun GameRow(chosenColors: List<Color>,
            feedbackColors: List<Color>,
            isClicked: Boolean,
            onSelectColorClick: (Int) -> Unit,
            onCheckClick: () -> Unit) {

    var rowVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        rowVisible = true
    }

    val visibleState = remember { MutableTransitionState(false) }
    if (visibleState.targetState != isClicked)
        visibleState.targetState = isClicked

    AnimatedVisibility(visible = rowVisible, enter = expandVertically(expandFrom = Alignment.Top)) {
        Row {
            SelectableColorRow(colors = chosenColors,
                onClick = onSelectColorClick)
            AnimatedVisibility(visibleState = visibleState, enter = scaleIn(), exit = scaleOut()) {
                CheckButton(enabled = isClicked, onCheckClick = onCheckClick)
            }
            if (visibleState.isIdle && !visibleState.currentState) {
                // gdy przycisk zatwierdzenia jest niewidoczny
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .background(Color.White)
                )
            }
            FeedbackCircles(colors = feedbackColors)
        }
    }
}

@Composable
fun CheckButton(enabled: Boolean, onCheckClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .size(50.dp),
        colors = IconButtonDefaults.filledIconButtonColors(),
        enabled = enabled,
        onClick = onCheckClick
    ) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = "Check Button")
    }
}

fun selectNextAvailableColor(availableColors: List<Color>, chosenColors: List<Color>,
                             buttonId: Int): List<Color> {
    if ( availableColors.any { it !in chosenColors } ) {
        val nextColor = availableColors.first {it !in chosenColors}
        // przesuwamy listę o 1 w lewo aby możliwe było wybranie każdego koloru
        Collections.rotate(availableColors, -1)
        return chosenColors.toMutableList().also { it[buttonId] = nextColor }
    }

    return chosenColors.toMutableList().also { it[buttonId] = Color.White }
}

fun selectRandomColors(availableColors: List<Color>) : List<Color> =
    availableColors.shuffled().take(4)

// not found color = background color
fun checkColors(chosenColors: List<Color>, correctColors: List<Color>,
                notFoundColor: Color
) : List<Color> {
    val feedbackColors = mutableListOf<Color>()

    for (i in chosenColors.indices) {
        if (chosenColors[i] == correctColors[i]) {
            feedbackColors.add(Color.Red)
        } else if (correctColors.contains(chosenColors[i])) {
            feedbackColors.add(Color.Yellow)
        } else {
            feedbackColors.add(notFoundColor)
        }
    }

    return feedbackColors}
