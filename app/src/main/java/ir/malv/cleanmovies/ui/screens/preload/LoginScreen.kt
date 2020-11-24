package ir.malv.cleanmovies.ui.screens.preload

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import ir.malv.cleanmovies.response.Response
import ir.malv.cleanmovies.ui.utils.UiConstants
import ir.malv.cleanmovies.utils.Validator
import ir.malv.cleanmovies.utils.changeTemporarily
import kotlinx.coroutines.*

@ExperimentalFocus
@Composable
fun LoginScreen(viewModel: PreloadViewModel, onLoggedIn: () -> Unit, onMoveToRegister: () -> Unit) {
    val statusChangerJob: MutableState<Job?> = remember { mutableStateOf(null) }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val statusText = remember { mutableStateOf("") }

    when (val s = viewModel.currentUserState) {
        is Response.LOADING -> {
            statusText.value = "Logging in..."
        }
        is Response.FAIL<*, *> -> {
            statusText.value =
                "Failed to login: ${if (s.code == -1 || s.error.message == null) "Unknown" else s.error.message}"
        }
        is Response.SUCCESS -> {
            onLoggedIn()
            return
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val focusRequester = remember { FocusRequester() }
        Text(statusText.value)
        Spacer(modifier = Modifier.height(10.dp))
        EmailEdit(onChange = { email.value = it }, focusRequester)
        Spacer(modifier = Modifier.height(10.dp))
        PasswordEdit(onChange = { password.value = it }, focusRequester)
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = {
                if (viewModel.currentUserState is Response.LOADING) {
                    return@OutlinedButton
                } else if (Validator.validateEmail(email.value) &&
                    password.value.length > 5
                ) {
                    kotlin.runCatching { statusChangerJob.value?.cancel() }
                    statusText.value = "Logging in..."
                    viewModel.login(email.value, password.value)
                } else {
                    statusChangerJob.value = viewModel
                        .viewModelScope
                        .changeTemporarily(
                            statusText,
                            "Fill all values correctly",
                            "",
                            2000L
                        )
                }
            },
            colors = ButtonConstants.defaultButtonColors(backgroundColor = Color.Green)
        ) {
            when (viewModel.currentUserState) {
                is Response.LOADING -> CircularProgressIndicator(
                    modifier = Modifier.size(25.dp),
                    color = Color.White
                )
                else -> Text(text = "Login", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            "I don't have an account",
            style = TextStyle(color = Color.Green, fontSize = 15.sp),
            modifier = Modifier.clickable(onClick = {
                viewModel.currentUserState = Response.IDLE()
                onMoveToRegister()
            }),
        )

    }
}

@ExperimentalFocus
@Composable
fun EmailEdit(onChange: (String) -> Unit, focusRequester: FocusRequester) {
    val t = remember { mutableStateOf("") }
    TextField(
        value = t.value,
        textStyle = UiConstants.nightSensitiveColorStyle(),
        onValueChange = { value ->
            t.value = value
            onChange(value)
        },
        leadingIcon = { Icon(asset = Icons.Default.Email) },
        label = { Text(text = "Email") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        visualTransformation = VisualTransformation.None,
        onImeActionPerformed = { action, keyboardController ->
            focusRequester.requestFocus()
        }
    )
}

@ExperimentalFocus
@Composable
fun PasswordEdit(onChange: (String) -> Unit, focusRequester: FocusRequester) {
    val t = remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = t.value,
        textStyle = UiConstants.nightSensitiveColorStyle(),
        onValueChange = { value ->
            t.value = value
            onChange(value)
        },
        leadingIcon = { Icon(asset = Icons.Default.Security) },
        label = { Text(text = "Password") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        visualTransformation = PasswordVisualTransformation(),
        onImeActionPerformed = { action, controller ->
            controller?.hideSoftwareKeyboard()
        }
    )
}


