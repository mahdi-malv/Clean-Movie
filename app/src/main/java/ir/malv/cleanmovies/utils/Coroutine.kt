package ir.malv.cleanmovies.utils

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CMScope : CoroutineScope {
    override val coroutineContext: CoroutineContext = Job()
}

fun cmScope(op: suspend CoroutineScope.()->Unit): Job = CMScope().launch(block = op)

/**
 * Change state for the given time
 */
fun <T> CoroutineScope.changeTemporarily(
    state: MutableState<T>,
    value: T, afterValue: T,
    timeInMillis: Long
) = launch {
    state.value = value
    delay(timeInMillis)
    state.value = afterValue
}
