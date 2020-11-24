package ir.malv.cleanmovies.ui.screens.preload

import android.annotation.SuppressLint
import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.core.*
import androidx.compose.animation.core.AnimationConstants.Infinite
import androidx.compose.animation.transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import ir.malv.cleanmovies.R
import ir.malv.cleanmovies.response.Response
import ir.malv.cleanmovies.ui.utils.Center
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ViewState { A, B }

val opacity = FloatPropKey()
val length = IntPropKey()
val color = ColorPropKey()

@Composable
inline fun SplashScreen(viewModel: PreloadViewModel, crossinline onFinished: (Boolean) -> Unit) {
    val state = remember { mutableStateOf(ViewState.A) }
    viewModel.viewModelScope.launch {
        when (viewModel.fetchUserInstance()) {
            is Response.EMPTY -> {
                viewModel.currentUserState = Response.EMPTY()
                delay(1500)
                onFinished(false)
            }
            is Response.SUCCESS -> {
                delay(900)
                state.value = ViewState.B
                delay(600)
                onFinished(true)
            }
            else -> {}
        }
    }
    SplashText(state)
}

@SuppressLint("Range")
@Composable
fun SplashText(state: MutableState<ViewState>) {

    val definition = transitionDefinition<ViewState> {
        state(ViewState.A) {
            this[opacity] = 0f
            this[length] = 2
            this[color] = Color.Gray
        }
        state(ViewState.B) {
            this[opacity] = 1f
            this[length] = 5
            this[color] = Color.Black
        }

        transition {
            opacity using repeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                ),
                iterations = Infinite
            )
            length using tween(durationMillis = 2000)
            color using repeatable(
                animation = keyframes {
                    durationMillis = 1000
                    Color.LightGray at 0
                    Color.Gray at 500
                    Color.DarkGray at 900
                },
                iterations = Infinite
            )
        }
    }

    val toState = if (state.value == ViewState.A) ViewState.B else ViewState.A

    val trans = transition(
        definition = definition,
        toState = toState,
        initState = state.value
    )

    Center {
        SplashImage(state, trans)
    }


}

@Composable
fun SplashImage(state: MutableState<ViewState>, transitionState: TransitionState) {
    Box {
        Image(imageResource(id = R.drawable.logo),
            modifier = Modifier.size(140.dp),
            alpha = transitionState[opacity]
        )
        if (state.value == ViewState.B) Icon(
            Icons.Default.Check,
            tint = Color.Green,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.BottomEnd)
        )
    }
}