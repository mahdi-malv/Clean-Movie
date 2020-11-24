package ir.malv.cleanmovies.ui.screens.preload

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
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
import ir.malv.cleanmovies.ui.utils.purple200
import ir.malv.cleanmovies.ui.utils.purple500
import ir.malv.cleanmovies.utils.Validator
import ir.malv.cleanmovies.utils.changeTemporarily
import kotlinx.coroutines.Job


@ExperimentalFocus
@Composable
fun RegistrationScreen(
    viewModel: PreloadViewModel,
    onRegistered: () -> Unit,
    onMoveBackToLogin: () -> Unit
) {
    val statusChangerJob: MutableState<Job?> = remember { mutableStateOf(null) }
    val email = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val statusText = remember { mutableStateOf("") }


    when (val s = viewModel.currentUserState) {
        is Response.LOADING -> {
            statusText.value = "Sending request..."
        }
        is Response.FAIL<*, *> -> {
            statusText.value =
                "Failed to login: ${if (s.code == -1 || s.error.message == null) "Unknown" else s.error.message}"
        }
        is Response.SUCCESS -> {
            onRegistered()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val emailFocus = remember { FocusRequester() }
        val passwordFocus = remember { FocusRequester() }
        Text(statusText.value)
        Spacer(modifier = Modifier.height(10.dp))
        NameEdit(
            onChange = { name.value = it },
            validate = Validator::validateName,
            nextFocusRequester = emailFocus
        )
        Spacer(modifier = Modifier.height(10.dp))
        EmailEdit(
            onChange = { email.value = it },
            validate = Validator::validateEmail,
            focusRequester = emailFocus,
            nextFocusRequester = passwordFocus
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordEdit(
            onChange = { password.value = it },
            validate = Validator::validatesPassword,
            passwordFocus
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = {
                if (
                    Validator.validateName(name.value) &&
                    Validator.validateEmail(email.value) &&
                    Validator.validatesPassword(password.value)
                ) {
                    viewModel.register(email.value, password.value, name.value)
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
            colors = ButtonConstants.defaultButtonColors(backgroundColor = purple500)
        ) {
            when (viewModel.currentUserState) {
                is Response.LOADING -> CircularProgressIndicator(
                    modifier = Modifier.size(25.dp),
                    color = Color.White
                )
                else -> Text(text = "Register", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            "I already have an account",
            style = TextStyle(color = purple200, fontSize = 15.sp),
            modifier = Modifier.clickable(onClick = {
                viewModel.currentUserState = Response.IDLE()
                onMoveBackToLogin()
            }),
        )

    }
}

@ExperimentalFocus
@Composable
private fun PasswordEdit(
    onChange: (String) -> Unit,
    validate: (String) -> Boolean,
    focusRequester: FocusRequester
) {
    val error = remember { mutableStateOf(false) }
    val t = remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = t.value,
        textStyle = UiConstants.nightSensitiveColorStyle(),
        onValueChange = { value ->
            t.value = value
            onChange(value)
            if (value.isEmpty()) error.value = false
            else error.value = !validate(value)
        },
        leadingIcon = { Icon(asset = Icons.Default.Security) },
        label = { Text(text = if (error.value) "At least 6 characters" else "Password") },
        errorColor = Color.Red,
        isErrorValue = error.value,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        onImeActionPerformed = { action, softController ->
            if (action == ImeAction.Done) {
                softController?.hideSoftwareKeyboard()
            }
        },
        visualTransformation = PasswordVisualTransformation()
    )
}

@ExperimentalFocus
@Composable
private fun EmailEdit(
    onChange: (String) -> Unit,
    validate: (String) -> Boolean,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester
) {
    val error = remember { mutableStateOf(false) }
    val t = remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.focusRequester(focusRequester),
        textStyle = UiConstants.nightSensitiveColorStyle(),
        value = t.value,
        onValueChange = { value ->
            t.value = value
            onChange(value)
            if (value.isEmpty()) error.value = false
            else error.value = !validate(value)
        },
        leadingIcon = { Icon(asset = Icons.Default.Email) },
        label = { Text(text = if (error.value) "Not a valid email" else "Email") },
        errorColor = Color.Red,
        isErrorValue = error.value,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        onImeActionPerformed = { action, softController ->
            if (action == ImeAction.Done) {
                softController?.hideSoftwareKeyboard()
            } else if (action == ImeAction.Next) {
                nextFocusRequester.requestFocus()
            }
        },
    )
}

@ExperimentalFocus
@Composable
private fun NameEdit(
    onChange: (String) -> Unit,
    validate: (String) -> Boolean,
    nextFocusRequester: FocusRequester
) {
    val error = remember { mutableStateOf(false) }
    val t = remember { mutableStateOf("") }
    TextField(
        value = t.value,
        textStyle = UiConstants.nightSensitiveColorStyle(),
        onValueChange = { value ->
            t.value = value
            onChange(value)
            if (value.isEmpty()) error.value = false
            else error.value = !validate(value)
        },
        leadingIcon = { Icon(asset = Icons.Default.Person) },
        label = { Text(text = if (error.value) "At least 5 characters" else "Name") },
        errorColor = Color.Red,
        isErrorValue = error.value,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        ),
        onImeActionPerformed = { action, _ ->
            if (action == ImeAction.Next) {
                nextFocusRequester.requestFocus()
            }
        },
    )
}